package com.findandfix.workshop.ui.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.findandfix.workshop.R;
import com.findandfix.workshop.databinding.FragmentHappeningNowUrgentRequestLayoutBinding;
import com.findandfix.workshop.model.global.UrgentRequestData;
import com.findandfix.workshop.model.global.UserData;
import com.findandfix.workshop.network.ApiClient;
import com.findandfix.workshop.network.EndPoints;
import com.findandfix.workshop.ui.activity.MoreCompletedUrgentRequestActivity;
import com.findandfix.workshop.ui.activity.UrgentRequestActivity;
import com.findandfix.workshop.ui.adapter.AcceptedUrgentRequestsAdapter;
import com.findandfix.workshop.ui.adapter.CurrentUrgentRequestsAdapter;
import com.findandfix.workshop.utils.ConfigurationFile;
import com.findandfix.workshop.utils.CustomUtils;
import com.findandfix.workshop.utils.NetWorkConnection;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by DELL on 13/03/2018.
 */

public class HappenningNowUrgentRequestsFragment extends Fragment {
    private RecyclerView recyclerView,recyclerView2;
    private FragmentHappeningNowUrgentRequestLayoutBinding binding;
    private UserData userData;
    private List<UrgentRequestData>urgentRequestData;
    private ObservableList<UrgentRequestData> pendingUrgentRequestData;
    private Dialog dialog=null;
    CurrentUrgentRequestsAdapter normalAdapter=null;
    AcceptedUrgentRequestsAdapter adapter=null;

    ////////////////////////////////////////////////////
    private boolean loading=false;
    private String next=null;
    private int pageId=0;
    private int pos=0;
    ////////////////////////////////////////////////////Inflating Layout Of Fragment + Passing View Model Of Fragment //////////////////////
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_happening_now_urgent_request_layout,container,false);
        View v=binding.getRoot();
        //////////////////////Get User Data Which is Saved In Utils ////////////////////////////////////////////////////////////
        userData= CustomUtils.getInstance().getSaveUserObject(getActivity());
        urgentRequestData=new ArrayList<>();
        pendingUrgentRequestData=new ObservableArrayList<>();
        loading=true;
        /////////////////////////////////Handling If There is More Pending Urgent Requests Activity /////////////////////////////////
        binding.tvMore.setOnClickListener(v1 ->
        {
            Intent i=new Intent(getActivity(), MoreCompletedUrgentRequestActivity.class);
            i.putExtra(ConfigurationFile.IntentConstants.NORMAL_REQUEST_CHECKER,ConfigurationFile.Constants.HANDLE_MORE_PENDING_NORMAL_REQUEST_TOOLBAR);
            startActivity(i);
        });
        initializeRecycler();

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        getHappeningNowUrgentRequests();
        getPendingRequestCall();
    }
//////////////////////////Initializing Recycler View and Passing Adapter To them //////////////////////////////////////
    public void initializeRecycler(){
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        binding.rvCurrentUrgentRequests.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        binding.rvCurrentUrgentRequests.setItemAnimator(new DefaultItemAnimator());
        binding.rvCurrentUrgentRequests.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if ( ((LinearLayoutManager) binding.rvCurrentUrgentRequests.getLayoutManager()).findLastVisibleItemPosition()==urgentRequestData.size()-1) {
                    if (next!=null && loading ==false) {
                        loading=true;
                        System.out.println("Load More Data Success ");
                        getHappeningNowUrgentRequests();
                    }

                }
            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        binding.rvAcceptedUrgentRequests.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

        /*Set Pending Adapter */
        adapter = new AcceptedUrgentRequestsAdapter(pendingUrgentRequestData,1);
        binding.rvAcceptedUrgentRequests.setAdapter(adapter);
//////////////////////////////////////////////////////////////////////////////////////////////////
        /*Set Normal Adapter */
        normalAdapter = new CurrentUrgentRequestsAdapter(urgentRequestData);
        normalAdapter.setClickListener(position -> {
            System.out.println("Current Urgent Position :" + position);
            setDialog(position);
        });
        binding.rvCurrentUrgentRequests.setAdapter(normalAdapter);
        /////////////////////////////////////////////////////////////////////////////
        }
//////////////////////////Get Urgent Requests That is near From Workshop //////////////////////////////////////////////
    public void getHappeningNowUrgentRequests(){
        if(NetWorkConnection.isConnectingToInternet(getActivity().getApplicationContext())) {
            CustomUtils.getInstance().showProgressDialog(getActivity());
            System.out.println("Code Urgent cONNECTING");
            ApiClient.getClient().create(EndPoints.class).getWorkshopUrgentRequests(ConfigurationFile.Constants.API_KEY, CustomUtils.getInstance().getAppLanguage(getActivity()), ConfigurationFile.Constants.Content_Type, ConfigurationFile.Constants.Content_Type, "Bearer "+userData.getToken(),pageId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(workShopUrgentRequestResponseResponse -> {
                        CustomUtils.getInstance().cancelDialog();
                        int code=workShopUrgentRequestResponseResponse.code();
                        System.out.println("Code Urgent normal Urgent :"+code);
                        loading=false;
                        if(code==ConfigurationFile.Constants.SUCCESS_CODE){

                            pos=urgentRequestData.size();
                            System.out.println("Code Urgent size normal Urgent:"+workShopUrgentRequestResponseResponse.body().getData().size());
                            urgentRequestData.addAll(workShopUrgentRequestResponseResponse.body().getData());
                            next=workShopUrgentRequestResponseResponse.body().getLinks().getNext();
                            if (next!=null)
                                pageId=Integer.parseInt(next.substring(next.length()-1));
                            binding.tvInProgressRequestNum.setText(String.valueOf(workShopUrgentRequestResponseResponse.body().getData().size()));
                            setCurrentUrgentsAdapter();
                        }else if(code==ConfigurationFile.Constants.UNAUTHENTICATED_CODE){
                            CustomUtils.getInstance().logout(getActivity());
                        }else if(code==ConfigurationFile.Constants.UNSUBSCRIBE_CODE){
                            CustomUtils.getInstance().logout(getActivity());
                            //Snackbar.make(getActivity().findViewById(R.id.ll_container), R.string.unsubscribed,Snackbar.LENGTH_LONG).show();
                        }

                    }, throwable -> {
                        CustomUtils.getInstance().cancelDialog();
                        System.out.println("Code Urgent Ex NORM:"+throwable.getMessage());
                    });
        }else {
            Snackbar.make(getActivity().findViewById(R.id.ll_container),getActivity().getResources().getString(R.string.internet_connection),Snackbar.LENGTH_LONG).show();
        }
    }
///////////////////////Get Pending Urgent Requests Of Workshop ///////////////////////////////////////////////////////////////////////////
    public void getPendingRequestCall(){
        if(NetWorkConnection.isConnectingToInternet(getActivity().getApplicationContext())) {
         //   CustomUtils.getInstance().showProgressDialog(getActivity());
            System.out.println("Code Urgent Pending Pending cONNECTING");
            ApiClient.getClient().create(EndPoints.class).getWorkshopPendingRequests(ConfigurationFile.Constants.API_KEY, CustomUtils.getInstance().getAppLanguage(getActivity()), ConfigurationFile.Constants.Content_Type, ConfigurationFile.Constants.Content_Type, "Bearer "+userData.getToken(),0)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(workShopUrgentRequestResponseResponse -> {
                 //       CustomUtils.getInstance().cancelDialog();
                        int code=workShopUrgentRequestResponseResponse.code();
                        System.out.println("Code Urgent Pending:"+code);

                        if(code==ConfigurationFile.Constants.SUCCESS_CODE){
                            pendingUrgentRequestData.clear();
                            System.out.println("Code Urgent Pending size :"+workShopUrgentRequestResponseResponse.body().getData().size());
                           pendingUrgentRequestData.addAll(workShopUrgentRequestResponseResponse.body().getData());
                            setPendingUrgentRequests();
                        }else if(code==ConfigurationFile.Constants.UNAUTHENTICATED_CODE){
                            CustomUtils.getInstance().logout(getActivity());
                        }else if(code==ConfigurationFile.Constants.UNSUBSCRIBE_CODE){
                            CustomUtils.getInstance().logout(getActivity());
                          //  Snackbar.make(getActivity().findViewById(R.id.ll_container), R.string.unsubscribed,Snackbar.LENGTH_LONG).show();
                        }

                    }, throwable -> {
                      //  CustomUtils.getInstance().cancelDialog();
                        System.out.println("Code Urgent Ex Pending :"+throwable.getMessage());
                    });
        }else {
            Snackbar.make(getActivity().findViewById(R.id.ll_container),getActivity().getResources().getString(R.string.internet_connection),Snackbar.LENGTH_LONG).show();
        }
    }


    /////////////////Set Adapter Of Normal Urgent Request Activity ///////////////////////////////////
  public void setCurrentUrgentsAdapter(){

        if (urgentRequestData.size()>0) {
            System.out.println("Code Urgent Norm Adapter ");
                normalAdapter.notifyDataSetChanged();
                binding.rvCurrentUrgentRequests.scrollToPosition(pos);
        }else {
            binding.tvInProgressRequestNum.setVisibility(View.GONE);
            binding.rvCurrentUrgentRequests.setVisibility(View.GONE);
            binding.tvInProgressRequestTxt.setVisibility(View.GONE);
            binding.tvNoUrgentRequest.setVisibility(View.VISIBLE);
        }
  }
////////////////////Set Adapter Of Pending Urgent Requests ///////////////////////////////////////////
  public void setPendingUrgentRequests(){
        if (pendingUrgentRequestData.size()>0) {
            System.out.println("Code Urgent Pending Adapter ");
            adapter.notifyDataSetChanged();
            binding.rvAcceptedUrgentRequests.invalidate();
        }else {
            binding.tvMore.setVisibility(View.GONE);
            binding.rvAcceptedUrgentRequests.setVisibility(View.GONE);
            binding.tvAcceptedRequests.setVisibility(View.GONE);
            binding.tvNoPendingUrgentRequest.setVisibility(View.VISIBLE);
        }
  }

///////////////////////////Display Dialog ///////////////////////////////////////////////////////////////
    private void setDialog(int position){
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        LayoutInflater factory = LayoutInflater.from(getActivity());
        final View dialogView = factory.inflate(R.layout.dialog_urgent_request_layout, null);
        Button buttonAccept = dialogView.findViewById(R.id.btn_accept);
        buttonAccept.setOnClickListener(v -> {
            dialog.dismiss();
            acceptUrgentRequest(position);
        });

        Button buttonCancel=dialogView.findViewById(R.id.btn_cancel);
        buttonCancel.setOnClickListener(v -> dialog.dismiss());
        dialog.setContentView(dialogView);
        dialog.show();
    }

////////////////////////////// Accept Specific Urgent Request Call ////////////////////////////////////////////////
    public void acceptUrgentRequest(int position){
        if(NetWorkConnection.isConnectingToInternet(getActivity().getApplicationContext())) {
          //  binding.progressBar.setVisibility(View.VISIBLE);
            CustomUtils.getInstance().showProgressDialog(getActivity());
            System.out.println("Code Urgent cONNECTING");
            ApiClient.getClient().create(EndPoints.class).acceptUrgentRequest(ConfigurationFile.Constants.API_KEY, CustomUtils.getInstance().getAppLanguage(getActivity()), ConfigurationFile.Constants.Content_Type, ConfigurationFile.Constants.Content_Type, "Bearer "+userData.getToken(),urgentRequestData.get(position).getId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(defaultResponseResponse -> {
                       // binding.progressBar.setVisibility(View.GONE);
                        CustomUtils.getInstance().cancelDialog();
                        int code=defaultResponseResponse.code();
                        System.out.println("Code Urgent Accept Urgent :"+code);
                        if(code==ConfigurationFile.Constants.SUCCESS_CODE_Twice){

                            urgentRequestData.clear();
                            pendingUrgentRequestData.clear();
                            System.out.println("Code UrgentUrgent Calling>>");
                            /*getHappeningNowUrgentRequests();
                            getPendingRequestCall();*/
                            //UrgentRequestActivity.viewPagerAdapter.notifyDataSetChanged();
                            Intent i=new Intent(getActivity(),UrgentRequestActivity.class);
                            getActivity().startActivity(i);
                            getActivity().finish();

                        }else if(code==ConfigurationFile.Constants.UNAUTHENTICATED_CODE){
                            CustomUtils.getInstance().logout(getActivity());
                        }else if(code==ConfigurationFile.Constants.UNSUBSCRIBE_CODE){
                            Snackbar.make(getActivity().findViewById(R.id.ll_container), R.string.unsubscribed,Snackbar.LENGTH_LONG).show();
                        }else if(code==ConfigurationFile.Constants.CANT_COMPLETE_REQUEST_CODE){
                            Snackbar.make(getActivity().findViewById(R.id.ll_container), R.string.cant_complete_your_request,Snackbar.LENGTH_LONG).show();
                        }

                    }, throwable -> {
                        CustomUtils.getInstance().cancelDialog();
                      //  binding.progressBar.setVisibility(View.GONE);
                        System.out.println("Normat Request :"+throwable.getMessage());
                    });
        }else {
            Snackbar.make(getActivity().findViewById(R.id.ll_container),getActivity().getResources().getString(R.string.internet_connection),Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        urgentRequestData.clear();
    }
}
