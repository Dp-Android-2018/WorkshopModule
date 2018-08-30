package com.dp.dell.workshopmodule.view.ui.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dp.dell.workshopmodule.R;
import com.dp.dell.workshopmodule.databinding.FragmentCompletedRequestsLayoutBinding;
import com.dp.dell.workshopmodule.utils.ConfigurationFile;
import com.dp.dell.workshopmodule.utils.CustomUtils;
import com.dp.dell.workshopmodule.view.ui.activity.MoreCompletedNormalRequestActivity;
import com.dp.dell.workshopmodule.view.ui.callback.BaseInterface;
import com.dp.dell.workshopmodule.viewmodel.CompletedRequest.CompletedRequestsViewModel;

/**
 * Created by DELL on 12/03/2018.
 */

public class CompletedRequestsFragment extends Fragment implements BaseInterface{
   private FragmentCompletedRequestsLayoutBinding binding;
   private CompletedRequestsViewModel viewModel;

   //////////////////////Inflating Ui & Declaring View Model ///////////////////////////////////////////////////////////
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_completed_requests_layout,container,false);
        View v=binding.getRoot();
        viewModel=new CompletedRequestsViewModel(getActivity(),this);
        binding.setCompletedViewModel(viewModel);
        binding.rvCompletedRequests.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
        binding.tvMore.setOnClickListener(v1 -> {
            Intent i=new Intent(getActivity(), MoreCompletedNormalRequestActivity.class);
            i.putExtra(ConfigurationFile.IntentConstants.NORMAL_REQUEST_CHECKER,ConfigurationFile.Constants.COMPLETED_NORMAL_REQUEST_CODE);
            getActivity().startActivity(i);
        });
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.onResume();
    }
    ///////////////////////Update View According To View Model Code ///////////////////////////////////////////

    @Override
    public void updateUi(int code) {
        if(code== ConfigurationFile.Constants.NO_INTERNET_CONNECTION_CODE)
            Snackbar.make(getActivity().findViewById(R.id.ll_parent), R.string.internet_connection,Snackbar.LENGTH_LONG).show();

        else if(code== ConfigurationFile.Constants.UNAUTHENTICATED_CODE)
            CustomUtils.getInstance().logout(getActivity());

        else if (code==ConfigurationFile.Constants.NO_DATA){
            binding.rvCompletedRequests.setVisibility(View.GONE);
            binding.tvCompleteRequestTxt.setVisibility(View.GONE);
            binding.tvCompleteRequestNum.setVisibility(View.GONE);
            binding.ivNoRequests.setVisibility(View.VISIBLE);
            binding.tvNoRequests.setVisibility(View.VISIBLE);
        }
    }
}
