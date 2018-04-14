package com.example.dell.workshopmodule.view.ui.fragment;

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

import com.example.dell.workshopmodule.R;
import com.example.dell.workshopmodule.databinding.FragmentCompletedRequestsLayoutBinding;
import com.example.dell.workshopmodule.utils.ConfigurationFile;
import com.example.dell.workshopmodule.utils.CustomUtils;
import com.example.dell.workshopmodule.view.ui.adapter.CompletedRequestsAdapter;
import com.example.dell.workshopmodule.view.ui.adapter.CurrentRequestsAdapter;
import com.example.dell.workshopmodule.view.ui.adapter.OfferSentAdapter;
import com.example.dell.workshopmodule.view.ui.callback.BaseInterface;
import com.example.dell.workshopmodule.viewmodel.CompletedRequest.CompletedRequestsViewModel;
import com.example.dell.workshopmodule.viewmodel.InProgress.InProgressViewModel;

/**
 * Created by DELL on 12/03/2018.
 */

public class CompletedRequestsFragment extends Fragment implements BaseInterface{
   private FragmentCompletedRequestsLayoutBinding binding;
   private CompletedRequestsViewModel viewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_completed_requests_layout,container,false);
        View v=binding.getRoot();
        viewModel=new CompletedRequestsViewModel(getActivity().getApplicationContext(),this);
        binding.setCompletedViewModel(viewModel);
        binding.rvCompletedRequests.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        return v;
    }

    @Override
    public void updateUi(int code) {
        if(code== ConfigurationFile.Constants.NO_INTERNET_CONNECTION_CODE)
            Snackbar.make(binding.rlParent, R.string.internet_connection,Snackbar.LENGTH_LONG).show();

        else if(code== ConfigurationFile.Constants.UNAUTHENTICATED_CODE)
            CustomUtils.getInstance().logout(getActivity());
    }
}
