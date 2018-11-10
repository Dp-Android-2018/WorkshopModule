package com.findandfix.workshop.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.findandfix.workshop.R;
import com.findandfix.workshop.databinding.FragmentSubscribtionPageLayoutBinding;
import com.findandfix.workshop.viewmodel.SubscribePageViewModel;

/**
 * Created by DELL on 07/05/2018.
 */

public class SubscribitonFragment extends Fragment {
    FragmentSubscribtionPageLayoutBinding binding;
    SubscribePageViewModel viewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_subscribtion_page_layout,container,false);
        View v=binding.getRoot();
        viewModel=new SubscribePageViewModel(getActivity().getApplicationContext());
        binding.setViewmodel(viewModel);
        return v;
    }
}
