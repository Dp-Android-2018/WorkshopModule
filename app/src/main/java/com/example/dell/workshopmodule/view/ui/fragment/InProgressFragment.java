package com.example.dell.workshopmodule.view.ui.fragment;

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

import com.example.dell.workshopmodule.R;
import com.example.dell.workshopmodule.databinding.FragmentInProgressLayoutBinding;
import com.example.dell.workshopmodule.utils.ConfigurationFile;
import com.example.dell.workshopmodule.utils.CustomUtils;
import com.example.dell.workshopmodule.view.ui.callback.BaseInterface;
import com.example.dell.workshopmodule.viewmodel.InProgress.InProgressViewModel;

/**
 * Created by DELL on 12/03/2018.
 */

public class InProgressFragment extends Fragment implements BaseInterface{

    FragmentInProgressLayoutBinding binding;
    InProgressViewModel viewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_in_progress_layout,container,false);
        View v=binding.getRoot();
        viewModel=new InProgressViewModel(getActivity().getApplicationContext(),this);
        binding.setProgressViewModel(viewModel);
        binding.rvInProgressRequests.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
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
