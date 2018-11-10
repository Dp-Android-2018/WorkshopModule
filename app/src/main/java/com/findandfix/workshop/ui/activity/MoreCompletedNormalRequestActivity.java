package com.findandfix.workshop.ui.activity;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.findandfix.workshop.R;
import com.findandfix.workshop.databinding.ActivityMoreCompletedRequestsLayoutBinding;
import com.findandfix.workshop.model.global.RequestData;
import com.findandfix.workshop.model.global.UserData;
import com.findandfix.workshop.network.ApiClient;
import com.findandfix.workshop.network.EndPoints;
import com.findandfix.workshop.ui.adapter.RequestsAdapter;
import com.findandfix.workshop.utils.ConfigurationFile;
import com.findandfix.workshop.utils.CustomUtils;
import com.findandfix.workshop.utils.NetWorkConnection;
import com.findandfix.workshop.viewmodel.ToolbarViewModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by DELL on 07/08/2018.
 */

public class MoreCompletedNormalRequestActivity extends BaseActivity {

    private UserData userData;
    private ObservableList<RequestData> completedRequests;
    public ObservableList<RequestData>pendingRequest;
    public ObservableList<RequestData>normalRequest;
    private ActivityMoreCompletedRequestsLayoutBinding binding;
    private boolean loading=false;
    private String next=null;
    private int pageId=0;
    private int pos=0;
    private int chercker=0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(MoreCompletedNormalRequestActivity.this, R.layout.activity_more_completed_requests_layout);
        setUpActionBar();
        userData=CustomUtils.getInstance().getSaveUserObject(getApplicationContext());
        completedRequests=new ObservableArrayList<>();
        pendingRequest=new ObservableArrayList<>();
        normalRequest=new ObservableArrayList<>();
        loading=true;
        chercker=getIntent().getIntExtra(ConfigurationFile.IntentConstants.NORMAL_REQUEST_CHECKER,0);
        //////////////////////Recycler View Of Completed Request //////////////////////////////////////////////////////
        binding.rvCompletedRequests.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));

        binding.rvCompletedRequests.setItemAnimator(new DefaultItemAnimator());
        binding.rvCompletedRequests.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (chercker==ConfigurationFile.Constants.COMPLETED_NORMAL_REQUEST_CODE) {
                    if (((GridLayoutManager) binding.rvCompletedRequests.getLayoutManager()).findLastVisibleItemPosition() == completedRequests.size() - 1) {
                        if (next != null && loading == false) {
                            loading = true;
                            System.out.println("Load More Data Success ");
                                getCompletedRequests();
                        }

                    }
                } else if (chercker==ConfigurationFile.Constants.HANDLE_MORE_PENDING_NORMAL_REQUEST_TOOLBAR) {
                    if (((GridLayoutManager) binding.rvCompletedRequests.getLayoutManager()).findLastVisibleItemPosition() == pendingRequest.size() - 1) {
                        if (next != null && loading == false) {
                            loading = true;
                            System.out.println("Load More Data Success ");
                                getPendingRequestCall();
                        }

                    }
                }else if (chercker==ConfigurationFile.Constants.HANDLE_MORE_NORMAL_REQUEST_TOOLBAR) {
                    if (((GridLayoutManager) binding.rvCompletedRequests.getLayoutManager()).findLastVisibleItemPosition() == normalRequest.size() - 1) {
                        if (next != null && loading == false) {
                            loading = true;
                            System.out.println("Load More Data Success ");
                            getNormalRequests();
                        }

                    }
                }
            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////PENDING REQUESTS RECYCLER ////////////////////////////////////////////////////////////

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        if (chercker==ConfigurationFile.Constants.COMPLETED_NORMAL_REQUEST_CODE)
            getCompletedRequests();

        else if (chercker==ConfigurationFile.Constants.HANDLE_MORE_PENDING_NORMAL_REQUEST_TOOLBAR)
            getPendingRequestCall();

        else if (chercker==ConfigurationFile.Constants.HANDLE_MORE_NORMAL_REQUEST_TOOLBAR)
            getNormalRequests();
    }

    public void setUpActionBar(){

        setSupportActionBar( binding.toolbar.toolbar);
        binding.toolbar.setViewmodel(new ToolbarViewModel(MoreCompletedNormalRequestActivity.this, ConfigurationFile.Constants.HANDLE_MORE_COMPLETED_NORMAL_REQUEST_TOOLBAR));
   /*  if (chercker==ConfigurationFile.Constants.COMPLETED_NORMAL_REQUEST_CODE)
        binding.toolbar.setViewmodel(new ToolbarViewModel(MoreCompletedNormalRequestActivity.this, ConfigurationFile.Constants.HANDLE_MORE_COMPLETED_NORMAL_REQUEST_TOOLBAR));
     else if (chercker==ConfigurationFile.Constants.HANDLE_MORE_PENDING_NORMAL_REQUEST_TOOLBAR)
         binding.toolbar.setViewmodel(new ToolbarViewModel(MoreCompletedNormalRequestActivity.this, ConfigurationFile.Constants.HANDLE_MORE_PENDING_NORMAL_REQUEST_TOOLBAR));

     else
         binding.toolbar.setViewmodel(new ToolbarViewModel(MoreCompletedNormalRequestActivity.this, ConfigurationFile.Constants.HANDLE_MORE_NORMAL_REQUEST_TOOLBAR));*/
    }

    public void getCompletedRequests(){
        if(NetWorkConnection.isConnectingToInternet(getApplicationContext())) {
            System.out.println("Code cONNECTING");
              CustomUtils.getInstance().showProgressDialog(this);

            ApiClient.getClient().create(EndPoints.class).getWorkshopCompletedRequests(ConfigurationFile.Constants.API_KEY, CustomUtils.getInstance().getAppLanguage(getApplicationContext()), ConfigurationFile.Constants.Content_Type, ConfigurationFile.Constants.Content_Type, "Bearer "+userData.getToken(),pageId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(workShopRequestResponseResponse -> {
                        CustomUtils.getInstance().cancelDialog();
                        loading=false;
                        int code=workShopRequestResponseResponse.code();
                        System.out.println("Code :"+code);

                        if(code==ConfigurationFile.Constants.SUCCESS_CODE){
                       //     completedRequests.clear();
                            System.out.println("Code :"+workShopRequestResponseResponse.body().getData().size());
                            pos=completedRequests.size();
                            completedRequests.addAll(workShopRequestResponseResponse.body().getData());
                            next=workShopRequestResponseResponse.body().getLinks().getNext();
                            if (next!=null)
                                pageId=Integer.parseInt(next.substring(next.length()-1));
                            setAdapter(1);
                        }else if(code==ConfigurationFile.Constants.UNAUTHENTICATED_CODE ||code==ConfigurationFile.Constants.UNSUBSCRIBE_CODE ){
                                 CustomUtils.getInstance().endSession(MoreCompletedNormalRequestActivity.this);
                        }

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
                CustomUtils.getInstance().showProgressDialog(this);
            System.out.println("Token :"+userData.getToken());
            ApiClient.getClient().create(EndPoints.class).getWorkshopPendingNormalRequests(ConfigurationFile.Constants.API_KEY, CustomUtils.getInstance().getAppLanguage(getApplicationContext()), ConfigurationFile.Constants.Content_Type, ConfigurationFile.Constants.Content_Type,"Bearer "+userData.getToken() )
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(workShopRequestResponseResponse -> {
                        CustomUtils.getInstance().cancelDialog();
                        loading=false;
                        int code=workShopRequestResponseResponse.code();

                        if(code==ConfigurationFile.Constants.SUCCESS_CODE){
                            pos=pendingRequest.size();
                            pendingRequest.addAll(workShopRequestResponseResponse.body().getData());
                            next=workShopRequestResponseResponse.body().getLinks().getNext();
                            if (next!=null)
                                pageId=Integer.parseInt(next.substring(next.length()-1));
                            setAdapter(2);

                        }else if(code==ConfigurationFile.Constants.UNAUTHENTICATED_CODE || code==ConfigurationFile.Constants.UNSUBSCRIBE_CODE){
                            CustomUtils.getInstance().endSession(MoreCompletedNormalRequestActivity.this);
                        }

                    }, throwable -> {
                         CustomUtils.getInstance().cancelDialog();
                        System.out.println("Pending Request :"+throwable.getMessage());
                    });
        }else {
            Snackbar.make(binding.getRoot(),getResources().getString(R.string.internet_connection),Snackbar.LENGTH_LONG).show();
        }
    }






    public void getNormalRequests(){
        if(NetWorkConnection.isConnectingToInternet(getApplicationContext())) {
            System.out.println("Code Login cONNECTING");
            CustomUtils.getInstance().showProgressDialog(MoreCompletedNormalRequestActivity.this);
            ApiClient.getClient().create(EndPoints.class).getWorkshopNormalRequests(ConfigurationFile.Constants.API_KEY, CustomUtils.getInstance().getAppLanguage(getApplicationContext()), ConfigurationFile.Constants.Content_Type, ConfigurationFile.Constants.Content_Type, "Bearer "+userData.getToken(),pageId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(workShopRequestResponseResponse -> {
                        CustomUtils.getInstance().cancelDialog();
                        int code=workShopRequestResponseResponse.code();
                        System.out.println("Code Login:"+code);
                        loading=false;
                        if(code==ConfigurationFile.Constants.SUCCESS_CODE){
                            //  normalRequest.clear();
                            pos=normalRequest.size();
                            System.out.println("Code Login Data:"+workShopRequestResponseResponse.body().getData().size());
                            normalRequest.addAll(workShopRequestResponseResponse.body().getData());

                            next=workShopRequestResponseResponse.body().getLinks().getNext();
                            if (next!=null)
                                pageId=Integer.parseInt(next.substring(next.length()-1));
                            setAdapter(3);
                        }else if(code==ConfigurationFile.Constants.UNAUTHENTICATED_CODE || code==ConfigurationFile.Constants.UNSUBSCRIBE_CODE){
                           CustomUtils.getInstance().endSession(MoreCompletedNormalRequestActivity.this);
                        }

                    }, throwable -> {
                        CustomUtils.getInstance().cancelDialog();
                        System.out.println("Normat Request :"+throwable.getMessage());
                    });
        }else {
            Snackbar.make(binding.getRoot(),getResources().getString(R.string.internet_connection),Snackbar.LENGTH_LONG).show();
        }
    }


    public void setAdapter(int checker){
        if (checker==1) {
            RequestsAdapter adapter = new RequestsAdapter(completedRequests, 5);
            binding.rvCompletedRequests.setAdapter(adapter);

        }else if (checker==2){
            RequestsAdapter adapter = new RequestsAdapter(pendingRequest,6);
            binding.rvCompletedRequests.setAdapter(adapter);
        }else {
            RequestsAdapter adapter = new RequestsAdapter(normalRequest,1);
            binding.rvCompletedRequests.setAdapter(adapter);
        }
        binding.rvCompletedRequests.scrollToPosition(pos);
    }
}
