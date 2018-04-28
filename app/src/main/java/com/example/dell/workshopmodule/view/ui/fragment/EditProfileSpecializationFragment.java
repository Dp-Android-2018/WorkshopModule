package com.example.dell.workshopmodule.view.ui.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.dell.workshopmodule.R;
import com.example.dell.workshopmodule.databinding.FragmentEditProfileInfoLayoutBinding;
import com.example.dell.workshopmodule.databinding.FragmentEditProfileSpecializationLayoutBinding;
import com.example.dell.workshopmodule.model.request.RegisterRequest;
import com.example.dell.workshopmodule.model.request.UpdateProfileRequest;
import com.example.dell.workshopmodule.utils.ConfigurationFile;
import com.example.dell.workshopmodule.utils.CustomUtils;
import com.example.dell.workshopmodule.view.ui.activity.MainActivity;
import com.example.dell.workshopmodule.view.ui.activity.SecondStepRegisterActivity;
import com.example.dell.workshopmodule.view.ui.callback.BaseInterface;
import com.example.dell.workshopmodule.view.ui.callback.DisplayDialogNavigator;
import com.example.dell.workshopmodule.view.ui.dialog.CustomBrandsDialog;
import com.example.dell.workshopmodule.view.ui.dialog.CustomDialog;
import com.example.dell.workshopmodule.view.ui.dialog.SubmitEditProfileDialog;
import com.example.dell.workshopmodule.viewmodel.EditProfileSpecializationViewModel;
import com.example.dell.workshopmodule.viewmodel.HandleEditProfileViewModel;

/**
 * Created by DELL on 14/03/2018.
 */

public class EditProfileSpecializationFragment extends Fragment implements BaseInterface
{

    FragmentEditProfileSpecializationLayoutBinding binding;
    EditProfileSpecializationViewModel viewModel;
    HandleEditProfileViewModel handleEditProfileViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_edit_profile_specialization_layout,container,false);
        View v=binding.getRoot();
        handleEditProfileViewModel=new HandleEditProfileViewModel();
        viewModel=new EditProfileSpecializationViewModel(getActivity().getApplicationContext(),this,handleEditProfileViewModel);
        binding.setViewmodel(viewModel);
        binding.setCompleterequestviewmodel(handleEditProfileViewModel);
        return v;
    }

    @Override
    public void updateUi(int code) {
            if(code== ConfigurationFile.Constants.SHOW_BRANDS_DIALOG_CODE){
                CustomBrandsDialog customBrandsDialog=new CustomBrandsDialog(getActivity(),this);
               showDialog(customBrandsDialog);
            } else if(code== ConfigurationFile.Constants.SHOW_SPECIALIZE_DIALOG_CODE){
                CustomDialog customDialog = new CustomDialog(getActivity(), ConfigurationFile.Constants.DISPLAY_SPECIALIZATION_DIALOG, this);
                showDialog(customDialog);
        } else if(code== ConfigurationFile.Constants.SHOW_URGENTS_DIALOG_CODE){
                CustomDialog customDialog = new CustomDialog(getActivity(), ConfigurationFile.Constants.DISPLAY_URGENT_TYPES_DIALOG, this);
                showDialog(customDialog);
        }else if (code == ConfigurationFile.Constants.UPDATE_BRANDS_DIALOG)
            viewModel.setBrandsText();
        else if (code == ConfigurationFile.Constants.DISPLAY_SPECIALIZATION_DIALOG_TEXT)
            viewModel.setSpecializationText();

        else if (code == ConfigurationFile.Constants.DISPLAY_URGENT_TYPES_DIALOG_text)
            viewModel.setUrgentText();

        else if (code == ConfigurationFile.Constants.FILL_ALL_DATTA)
                showSnackBar(getResources().getString(R.string.fill_data));

        else if (code == ConfigurationFile.Constants.SUCCESS_CODE) {
                showSnackBar(getString(R.string.profile_update_successfully));
            //    getActivity().startActivity(new Intent(getActivity(), MainActivity.class));
            }

            else if (code == ConfigurationFile.Constants.UNAUTHENTICATED_CODE) {
                showSnackBar(getString(R.string.unauthenticated));
                CustomUtils.getInstance().endSession(getActivity());
            }

        else if (code == ConfigurationFile.Constants.INVALID_DATA)
                showSnackBar(getResources().getString(R.string.invalid_data));
    }

    public void setProfileObject(UpdateProfileRequest updateProfileRequest){
        viewModel.setProfileObject(updateProfileRequest);
    }

    public void showDialog(Dialog dialog){
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    public void showSnackBar(String message){
        Snackbar.make(getView(),message,Snackbar.LENGTH_LONG).show();
    }







}