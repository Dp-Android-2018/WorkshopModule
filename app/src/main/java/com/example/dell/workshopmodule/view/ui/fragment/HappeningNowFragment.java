package com.example.dell.workshopmodule.view.ui.fragment;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.example.dell.workshopmodule.databinding.FragmentHappeningNowLayoutBinding;
import com.example.dell.workshopmodule.utils.ConfigurationFile;
import com.example.dell.workshopmodule.utils.CustomUtils;
import com.example.dell.workshopmodule.view.ui.adapter.CurrentRequestsAdapter;
import com.example.dell.workshopmodule.view.ui.adapter.OfferSentAdapter;
import com.example.dell.workshopmodule.view.ui.callback.BaseInterface;
import com.example.dell.workshopmodule.view.ui.dialog.UrgentRequestDialog;
import com.example.dell.workshopmodule.viewmodel.HappeningNow.CurrentRequestsViewModel;

/**
 * Created by DELL on 12/03/2018.
 */

public class HappeningNowFragment extends Fragment implements BaseInterface{
    FragmentHappeningNowLayoutBinding binding;
    CurrentRequestsViewModel viewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_happening_now_layout,container,false);
        View v=binding.getRoot();
        viewModel=new CurrentRequestsViewModel(getActivity().getApplicationContext(),this);
        binding.setRequestviewModel(viewModel);
        binding.rvCurrentRequests.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
        binding.rvRequstsWithOffers.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
        return v;
    }

    @Override
    public void updateUi(int code) {
            if(code== ConfigurationFile.Constants.NO_INTERNET_CONNECTION_CODE)
                 Snackbar.make(binding.llParent, R.string.internet_connection,Snackbar.LENGTH_LONG).show();

            else if(code== ConfigurationFile.Constants.UNAUTHENTICATED_CODE)
                CustomUtils.getInstance().logout(getActivity());
    }
}
