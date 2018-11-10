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
import com.findandfix.workshop.databinding.FragmentAboutLayoutBinding;
import com.findandfix.workshop.viewmodel.AboutViewModel;

/**
 * Created by DELL on 28/05/2018.
 */

public class AboutFragment extends Fragment {

    private FragmentAboutLayoutBinding binding;
    private AboutViewModel viewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       binding= DataBindingUtil.inflate(inflater, R.layout.fragment_about_layout,container,false);
       View view=binding.getRoot();
       viewModel=new AboutViewModel();
       binding.setViewmodel(viewModel);
       return view;
    }
}
