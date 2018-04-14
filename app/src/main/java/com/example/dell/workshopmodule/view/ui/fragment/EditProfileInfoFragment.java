package com.example.dell.workshopmodule.view.ui.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.dell.workshopmodule.R;
import com.example.dell.workshopmodule.databinding.FragmentEditProfileInfoLayoutBinding;
import com.example.dell.workshopmodule.view.ui.activity.AddAdvertismentRange;
import com.example.dell.workshopmodule.view.ui.activity.AdvertismentPublished;
import com.example.dell.workshopmodule.view.ui.adapter.CurrentRequestsAdapter;
import com.example.dell.workshopmodule.view.ui.adapter.OfferSentAdapter;
import com.example.dell.workshopmodule.view.ui.dialog.SubmitEditProfileDialog;
import com.example.dell.workshopmodule.viewmodel.EditProfileInfoViewModel;

/**
 * Created by DELL on 14/03/2018.
 */

public class EditProfileInfoFragment extends Fragment {

    FragmentEditProfileInfoLayoutBinding binding;
    EditProfileInfoViewModel viewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_edit_profile_info_layout,container,false);
        View v=binding.getRoot();
        viewModel=new EditProfileInfoViewModel(getActivity().getApplicationContext());
        binding.setViewmodel(viewModel);
        /*Button b= (Button)v.findViewById(R.id.btn_submit);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubmitEditProfileDialog submitEditProfileDialog=new SubmitEditProfileDialog(getContext());
                submitEditProfileDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                submitEditProfileDialog.show();
            }
        });*/
        return v;
    }


}