package com.findandfix.workshop.ui.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.findandfix.workshop.R;
import com.findandfix.workshop.databinding.FragmentCompletedUrgentRequestsLayoutBinding;
import com.findandfix.workshop.model.global.UrgentRequestData;
import com.findandfix.workshop.model.global.UserData;
import com.findandfix.workshop.network.ApiClient;
import com.findandfix.workshop.network.EndPoints;
import com.findandfix.workshop.ui.activity.MoreCompletedUrgentRequestActivity;
import com.findandfix.workshop.ui.adapter.CompletedUrgentRequestAdapter;
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

public class CompletedUrgentRequestFragment extends Fragment {
    private RecyclerView recyclerView;
    FragmentCompletedUrgentRequestsLayoutBinding binding;
    private UserData userData;
    private List<UrgentRequestData> completedUrgentRequests;

    ////////////////////////////////////////////////////Inflating Layout Of Fragment + Passing View Model Of Fragment /////////////////////
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_completed_urgent_requests_layout,container,false);
        View v=binding.getRoot();
        /////////////////////////////////Handling If There is More Completed Urgent Requests  /////////////////////////////////
        binding.tvMore.setOnClickListener(v1 -> {
            Intent i=new Intent(getActivity(), MoreCompletedUrgentRequestActivity.class);
            i.putExtra(ConfigurationFile.IntentConstants.NORMAL_REQUEST_CHECKER,ConfigurationFile.Constants.COMPLETED_NORMAL_REQUEST_CODE);
            getActivity().startActivity(i);
        });

        //////////////////////Get User Data Which is Saved In Utils ////////////////////////////////////////////////////////////
        userData= CustomUtils.getInstance().getSaveUserObject(getActivity());
        completedUrgentRequests=new ArrayList<>();
        initializeRecycler();
        getCompletedUrgentRequests();
        return v;
    }
///////////////////////Initializing Recycler View ////////////////////////////////////////////////////////////////////////
    public void initializeRecycler(){
        binding.rvUrgentCompletedRequests.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
    }
////////////////////////////Get Completed Requests //////////////////////////////////////////////////////////////////////////
    public void getCompletedUrgentRequests(){
        if(NetWorkConnection.isConnectingToInternet(getActivity().getApplicationContext())) {
            System.out.println("Code cONNECTING");
          //  CustomUtils.getInstance().showProgressDialog(getActivity());
            ApiClient.getClient().create(EndPoints.class).getWorkshopCompletedUrgentRequests(ConfigurationFile.Constants.API_KEY, CustomUtils.getInstance().getAppLanguage(getActivity()), ConfigurationFile.Constants.Content_Type, ConfigurationFile.Constants.Content_Type, "Bearer "+userData.getToken(),0)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(workShopUrgentRequestResponseResponse -> {
        //                CustomUtils.getInstance().cancelDialog();
                        int code=workShopUrgentRequestResponseResponse.code();
                        System.out.println("Code :"+code);

                        if(code==ConfigurationFile.Constants.SUCCESS_CODE){
                            completedUrgentRequests.clear();
                            System.out.println("Code size :"+workShopUrgentRequestResponseResponse.body().getData().size());
                            completedUrgentRequests.addAll(workShopUrgentRequestResponseResponse.body().getData());
                            binding.tvCompleteRequestNum.setText(String.valueOf(workShopUrgentRequestResponseResponse.body().getMeta().getTotal()));
                            setCompletedRequestsAdapter();
                        }else if(code==ConfigurationFile.Constants.UNAUTHENTICATED_CODE){
                            CustomUtils.getInstance().logout(getActivity());
                        }else if(code==ConfigurationFile.Constants.UNSUBSCRIBE_CODE){
                            CustomUtils.getInstance().logout(getActivity());
                            //Snackbar.make(getActivity().findViewById(R.id.ll_container), R.string.unsubscribed,Snackbar.LENGTH_LONG).show();
                        }

                    }, throwable -> {
          //              CustomUtils.getInstance().cancelDialog();
                        System.out.println("Normat Request :"+throwable.getMessage());
                    });
        }else {
            Snackbar.make(getActivity().findViewById(R.id.ll_container),getActivity().getResources().getString(R.string.internet_connection),Snackbar.LENGTH_LONG).show();
        }
    }
//////////////////////////Set Adapter Of Recycler View //////////////////////////////////////////////////////////////////////////////////////
    public void setCompletedRequestsAdapter(){
        if (completedUrgentRequests.size()>0) {
            CompletedUrgentRequestAdapter adapter = new CompletedUrgentRequestAdapter(completedUrgentRequests,1);
            binding.rvUrgentCompletedRequests.setAdapter(adapter);
        }else {
            binding.tvMore.setVisibility(View.GONE);
            binding.rvUrgentCompletedRequests.setVisibility(View.GONE);
            binding.tvCompleteRequestNum.setVisibility(View.GONE);
            binding.tvRequestTxt.setVisibility(View.GONE);
            binding.tvCompleteRequestTxt.setVisibility(View.GONE);
            binding.ivNoRequests.setVisibility(View.VISIBLE);
            binding.tvNoRequests.setVisibility(View.VISIBLE);
        }
    }






}
