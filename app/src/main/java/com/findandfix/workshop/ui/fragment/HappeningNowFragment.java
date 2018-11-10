package com.findandfix.workshop.ui.fragment;

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

import com.findandfix.workshop.R;
import com.findandfix.workshop.databinding.FragmentHappeningNowLayoutBinding;
import com.findandfix.workshop.ui.activity.MoreCompletedNormalRequestActivity;
import com.findandfix.workshop.ui.callback.BaseInterface;
import com.findandfix.workshop.utils.ConfigurationFile;
import com.findandfix.workshop.utils.CustomUtils;
import com.findandfix.workshop.viewmodel.HappeningNow.CurrentRequestsViewModel;

/**
 * Created by DELL on 12/03/2018.
 */

public class HappeningNowFragment extends Fragment implements BaseInterface {
    FragmentHappeningNowLayoutBinding binding;
    CurrentRequestsViewModel viewModel;


    //////////////////////////Inflating Ui And Passing View Model ///////////////////////////////////////////////////////////////
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_happening_now_layout,container,false);
        View v=binding.getRoot();

        viewModel=new CurrentRequestsViewModel(getActivity(),this);
        binding.setRequestviewModel(viewModel);
        //////////////////Navigate to More Normal Requests Activity /////////////////////////////////////////////
        binding.tvMore.setOnClickListener(v1 -> {
            Intent i=new Intent(getActivity(), MoreCompletedNormalRequestActivity.class);
            i.putExtra(ConfigurationFile.IntentConstants.NORMAL_REQUEST_CHECKER,ConfigurationFile.Constants.HANDLE_MORE_PENDING_NORMAL_REQUEST_TOOLBAR);
            startActivity(i);

        });

        /////////////Navigate To More Sent Offers Requests Activity ///////////////////////////////////////////////////////
        binding.tvMore2.setOnClickListener(v1 -> {
            Intent i=new Intent(getActivity(), MoreCompletedNormalRequestActivity.class);
            i.putExtra(ConfigurationFile.IntentConstants.NORMAL_REQUEST_CHECKER,ConfigurationFile.Constants.HANDLE_MORE_NORMAL_REQUEST_TOOLBAR);
            startActivity(i);

        });

        ////////////////////Initializing RecyclerView ////////////////////////////////////////////////////////////////////////
        binding.rvCurrentRequests.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
        binding.rvRequstsWithOffers.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.onResume();
    }


    @Override
    public void onPause() {
        super.onPause();
        viewModel.onPause();
    }

    ///////////////////////Update View Accoarding To Code From View Model ///////////////////////////////////////////////////////////////////////////
    @Override
    public void updateUi(int code) {
            if(code== ConfigurationFile.Constants.NO_INTERNET_CONNECTION_CODE)
                 Snackbar.make(getActivity().findViewById(R.id.ll_parent), R.string.internet_connection,Snackbar.LENGTH_LONG).show();

            else if(code== ConfigurationFile.Constants.UNAUTHENTICATED_CODE)
                CustomUtils.getInstance().logout(getActivity());

            else if(code== ConfigurationFile.Constants.NO_DATA){
                binding.rvCurrentRequests.setVisibility(View.GONE);
                binding.tvCurrentRequest.setVisibility(View.GONE);
                binding.tvNoRequests.setVisibility(View.VISIBLE);
            }else if(code== ConfigurationFile.Constants.NO_DATA_2){
                binding.rvRequstsWithOffers.setVisibility(View.GONE);
                binding.tvAcceptedRequests.setVisibility(View.GONE);
                binding.tvNoPendingRequests.setVisibility(View.VISIBLE);
            }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
