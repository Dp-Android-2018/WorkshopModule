package com.findandfix.workshop.ui.fragment;

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

import com.findandfix.workshop.R;
import com.findandfix.workshop.ui.dialog.SubmitEditProfileDialog;

/**
 * Created by DELL on 14/03/2018.
 */

public class EditProfileInfoFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_edit_profile_info_layout,container,false);
        Button b= (Button)v.findViewById(R.id.btn_submit);
        b.setOnClickListener(v1 -> {
            SubmitEditProfileDialog submitEditProfileDialog=new SubmitEditProfileDialog(getContext());
            submitEditProfileDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            submitEditProfileDialog.show();
        });
        return v;
    }
}