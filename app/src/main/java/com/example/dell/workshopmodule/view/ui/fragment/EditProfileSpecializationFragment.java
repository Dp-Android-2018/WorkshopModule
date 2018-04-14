package com.example.dell.workshopmodule.view.ui.fragment;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.dell.workshopmodule.R;
import com.example.dell.workshopmodule.databinding.FragmentEditProfileInfoLayoutBinding;
import com.example.dell.workshopmodule.databinding.FragmentEditProfileSpecializationLayoutBinding;
import com.example.dell.workshopmodule.view.ui.dialog.SubmitEditProfileDialog;
import com.example.dell.workshopmodule.viewmodel.EditProfileSpecializationViewModel;

/**
 * Created by DELL on 14/03/2018.
 */

public class EditProfileSpecializationFragment extends Fragment {

    FragmentEditProfileSpecializationLayoutBinding binding;
    EditProfileSpecializationViewModel viewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

       /* b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubmitEditProfileDialog submitEditProfileDialog=new SubmitEditProfileDialog(getContext());
                submitEditProfileDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                submitEditProfileDialog.show();
            }
        });*/
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_edit_profile_specialization_layout,container,false);
        View v=binding.getRoot();
        viewModel=new EditProfileSpecializationViewModel(getActivity().getApplicationContext());
        binding.setViewmodel(viewModel);
        return v;
    }
}