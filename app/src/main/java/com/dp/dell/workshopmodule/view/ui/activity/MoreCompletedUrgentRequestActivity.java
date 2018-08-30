package com.dp.dell.workshopmodule.view.ui.activity;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dp.dell.workshopmodule.R;
import com.dp.dell.workshopmodule.databinding.ActivityMoreCompletedRequestsLayoutBinding;
import com.dp.dell.workshopmodule.model.global.RequestData;
import com.dp.dell.workshopmodule.model.global.UrgentRequestData;
import com.dp.dell.workshopmodule.model.global.UserData;
import com.dp.dell.workshopmodule.model.response.WorkShopRequestResponse;
import com.dp.dell.workshopmodule.model.response.WorkShopUrgentRequestResponse;
import com.dp.dell.workshopmodule.network.ApiClient;
import com.dp.dell.workshopmodule.network.EndPoints;
import com.dp.dell.workshopmodule.utils.ConfigurationFile;
import com.dp.dell.workshopmodule.utils.CustomUtils;
import com.dp.dell.workshopmodule.utils.NetWorkConnection;
import com.dp.dell.workshopmodule.view.ui.adapter.AcceptedUrgentRequestsAdapter;
import com.dp.dell.workshopmodule.view.ui.adapter.CompletedUrgentRequestAdapter;
import com.dp.dell.workshopmodule.view.ui.adapter.RequestsAdapter;
import com.dp.dell.workshopmodule.viewmodel.ToolbarViewModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by DELL on 07/08/2018.
 */

public class MoreCompletedUrgentRequestActivity extends BaseActivity {

    private UserData userData;
    private ObservableList<UrgentRequestData> completedUrgentRequests;
    public ObservableList<UrgentRequestData>pendingUrgentRequestData;
    private ActivityMoreCompletedRequestsLayoutBinding binding;
    private boolean loading=false;
    private String next=null;
    private int pageId=0;
    private int pos=0;
    private int chercker=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(MoreCompletedUrgentRequestActivity.this, R.layout.activity_more_completed_requests_layout);
        setUpActionBar();
        userData = CustomUtils.getInstance().getSaveUserObject(getApplicationContext());
        completedUrgentRequests = new ObservableArrayList<>();
        pendingUrgentRequestData = new ObservableArrayList<>();
        loading = true;
        chercker = getIntent().getIntExtra(ConfigurationFile.IntentConstants.NORMAL_REQUEST_CHECKER, 0);
        //////////////////////Recycler View Of Completed Request //////////////////////////////////////////////////////
        binding.rvCompletedRequests.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));

        binding.rvCompletedRequests.setItemAnimator(new DefaultItemAnimator());
        binding.rvCompletedRequests.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (chercker == ConfigurationFile.Constants.COMPLETED_NORMAL_REQUEST_CODE) {
                    if (((GridLayoutManager) binding.rvCompletedRequests.getLayoutManager()).findLastVisibleItemPosition() == completedUrgentRequests.size() - 1) {
                        if (next != null && loading == false) {
                            loading = true;
                            System.out.println("Load More Data Success ");
                            getCompletedUrgentRequests();
                        }

                    }
                }else if (chercker == ConfigurationFile.Constants.HANDLE_MORE_PENDING_NORMAL_REQUEST_TOOLBAR) {
                    if (((GridLayoutManager) binding.rvCompletedRequests.getLayoutManager()).findLastVisibleItemPosition() == pendingUrgentRequestData.size() - 1) {
                        if (next != null && loading == false) {
                            loading = true;
                            System.out.println("Load More Data Success ");
                            getPendingRequestCall();
                        }

                    }
                }
            }
        });

        if (chercker == ConfigurationFile.Constants.COMPLETED_NORMAL_REQUEST_CODE)
            getCompletedUrgentRequests();

        else if (chercker == ConfigurationFile.Constants.HANDLE_MORE_PENDING_NORMAL_REQUEST_TOOLBAR)
            getPendingRequestCall();
    }

    public void setUpActionBar(){

        setSupportActionBar( binding.toolbar.toolbar);
        if (chercker==ConfigurationFile.Constants.COMPLETED_NORMAL_REQUEST_CODE)
            binding.toolbar.setViewmodel(new ToolbarViewModel(MoreCompletedUrgentRequestActivity.this, ConfigurationFile.Constants.HANDLE_MORE_COMPLETED_URHENT_REQUEST_TOOLBAR));
       else   binding.toolbar.setViewmodel(new ToolbarViewModel(MoreCompletedUrgentRequestActivity.this, ConfigurationFile.Constants.HANDLE_MORE_PENDING_URHENT_REQUEST_TOOLBAR));
    }

    public void getCompletedUrgentRequests(){
        if(NetWorkConnection.isConnectingToInternet(getApplicationContext())) {
            System.out.println("Code cONNECTING");
              CustomUtils.getInstance().showProgressDialog(MoreCompletedUrgentRequestActivity.this);
            ApiClient.getClient().create(EndPoints.class).getWorkshopCompletedUrgentRequests(ConfigurationFile.Constants.API_KEY, CustomUtils.getInstance().getAppLanguage(getApplicationContext()), ConfigurationFile.Constants.Content_Type, ConfigurationFile.Constants.Content_Type, "Bearer "+userData.getToken(),pageId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(workShopUrgentRequestResponseResponse -> {

                        loading=false;
                                       CustomUtils.getInstance().cancelDialog();
                        int code=workShopUrgentRequestResponseResponse.code();
                        System.out.println("Code :"+code);

                        if(code==ConfigurationFile.Constants.SUCCESS_CODE){
                            pos=completedUrgentRequests.size();
                            completedUrgentRequests.addAll(workShopUrgentRequestResponseResponse.body().getData());
                            next=workShopUrgentRequestResponseResponse.body().getLinks().getNext();
                            if (next!=null)
                                pageId=Integer.parseInt(next.substring(next.length()-1));

                            System.out.println("Page Id Data :"+pageId);
                            setAdapter(1);
                        }else if(code==ConfigurationFile.Constants.UNAUTHENTICATED_CODE){
                            CustomUtils.getInstance().endSession(MoreCompletedUrgentRequestActivity.this);
                        }else if(code==ConfigurationFile.Constants.UNSUBSCRIBE_CODE){
                            CustomUtils.getInstance().endSession(MoreCompletedUrgentRequestActivity.this);}

                    }, throwable -> {
                                     CustomUtils.getInstance().cancelDialog();
                        System.out.println("Normat Request :"+throwable.getMessage());
                    });
        }else {
            Snackbar.make(binding.getRoot(),getResources().getString(R.string.internet_connection),Snackbar.LENGTH_LONG).show();
        }
    }


    public void getPendingRequestCall(){
        if(NetWorkConnection.isConnectingToInternet(getApplicationContext())) {
               CustomUtils.getInstance().showProgressDialog(MoreCompletedUrgentRequestActivity.this);
            System.out.println("Code Urgent Pending Pending cONNECTING");
            ApiClient.getClient().create(EndPoints.class).getWorkshopPendingRequests(ConfigurationFile.Constants.API_KEY, CustomUtils.getInstance().getAppLanguage(getApplicationContext()), ConfigurationFile.Constants.Content_Type, ConfigurationFile.Constants.Content_Type, "Bearer "+userData.getToken(),pageId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(workShopUrgentRequestResponseResponse -> {
                              CustomUtils.getInstance().cancelDialog();
                        int code=workShopUrgentRequestResponseResponse.code();
                        System.out.println("Code Urgent Pending:"+code);
                        loading=false;
                        if(code==ConfigurationFile.Constants.SUCCESS_CODE){
                            System.out.println("Code Urgent Pending size :"+workShopUrgentRequestResponseResponse.body().getData().size());
                            pos=pendingUrgentRequestData.size();
                            pendingUrgentRequestData.addAll(workShopUrgentRequestResponseResponse.body().getData());
                            next=workShopUrgentRequestResponseResponse.body().getLinks().getNext();
                            if (next!=null)
                                pageId=Integer.parseInt(next.substring(next.length()-1));
                            setAdapter(2);
                        }else if(code==ConfigurationFile.Constants.UNAUTHENTICATED_CODE){
                            CustomUtils.getInstance().endSession(MoreCompletedUrgentRequestActivity.this);
                        }else if(code==ConfigurationFile.Constants.UNSUBSCRIBE_CODE){
                            CustomUtils.getInstance().endSession(MoreCompletedUrgentRequestActivity.this);}

                    }, throwable -> {
                          CustomUtils.getInstance().cancelDialog();
                        System.out.println("Code Urgent Ex Pending :"+throwable.getMessage());
                    });
        }else {
            Snackbar.make(binding.getRoot(),getResources().getString(R.string.internet_connection),Snackbar.LENGTH_LONG).show();
        }
    }

    public void setAdapter(int checker){
        if (checker==1) {
            CompletedUrgentRequestAdapter adapter = new CompletedUrgentRequestAdapter(completedUrgentRequests,2);
            binding.rvCompletedRequests.setAdapter(adapter);
        }else if (checker==2){
            AcceptedUrgentRequestsAdapter adapter=new AcceptedUrgentRequestsAdapter(pendingUrgentRequestData,2);
            binding.rvCompletedRequests.setAdapter(adapter);
        }
        binding.rvCompletedRequests.scrollToPosition(pos);
    }
}
