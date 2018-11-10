package com.findandfix.workshop.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;

import com.findandfix.workshop.R;
import com.findandfix.workshop.databinding.ActivityAchievmentAfterLayoutBinding;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.findandfix.workshop.ui.callback.BaseInterface;
import com.findandfix.workshop.utils.ConfigurationFile;
import com.findandfix.workshop.utils.CustomUtils;
import com.findandfix.workshop.viewmodel.AddAchievmentImageViewModel;
import com.findandfix.workshop.viewmodel.ToolbarViewModel;

public class AddAchievmentAfterFixing extends BaseActivity implements BaseInterface {
    private ActivityAchievmentAfterLayoutBinding binding;
    private AddAchievmentImageViewModel viewModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        handleToolbar();
    }

    /*Inflating Ui and Declaring View Model
    * before Image and after Image Usee The Same View Model if checker==1 It will set before Imaage
    * other wise It Will Set After Image */
    public void initBinding(){
        binding= DataBindingUtil.setContentView(AddAchievmentAfterFixing.this, R.layout.activity_achievment_after_layout);
        viewModel=new AddAchievmentImageViewModel(AddAchievmentAfterFixing.this,this,2);
        binding.setViewmodel(viewModel);

    }
/* Specify Layout and properties Of Toolbar According To Code Which I Sent */
    public void handleToolbar(){
        setSupportActionBar( binding.toolbar.toolbar);
        binding.toolbar.setViewmodel(new ToolbarViewModel(AddAchievmentAfterFixing.this, ConfigurationFile.Constants.HANDLE_ADD_ACHIEVMENT_TOOLBAR));
    }

    /* Update View According To Code Which Sent From View Model */
    @Override
    public void updateUi(int code) {
        if(code== ConfigurationFile.Constants.SHOW_DIALOG_CODE)
            CustomUtils.getInstance().showDialog(AddAchievmentAfterFixing.this,viewModel);
        else if(code==ConfigurationFile.Constants.PERMISSION_DENIED)
            showSnackBar(R.string.permission_denied);
        else if(code==ConfigurationFile.Constants.PERMISSION_GRANTED_CAMERA)
            CustomUtils.getInstance().openCamera(AddAchievmentAfterFixing.this);
        else if(code==ConfigurationFile.Constants.PERMISSION_GRANTED_GALLERY)
            CustomUtils.getInstance().openGallery(AddAchievmentAfterFixing.this);
        else if(code==ConfigurationFile.Constants.CHOOSE_IMAGE_FROM_GALLERY)
            showSnackBar(R.string.choose_image);
        else if(code==ConfigurationFile.Constants.FILL_ALL_DATTA)
            showSnackBar(R.string.add_image);
        else if(code==ConfigurationFile.Constants.NO_INTERNET_CONNECTION_CODE)
            showSnackBar(R.string.internet_connection);
        else if(code==ConfigurationFile.Constants.CANT_COMPLETE_REQUEST_CODE)
            showSnackBar(R.string.cant_complete_your_request);

        else if(code==ConfigurationFile.Constants.UNAUTHENTICATED_CODE)
            CustomUtils.getInstance().logout(AddAchievmentAfterFixing.this);

        else if(code==ConfigurationFile.Constants.SUCCESS_CODE) {
            showSnackBar(R.string.achievment_added);
            delay();
        }



    }
/* Back To Main Activity After Adding Achievment Successfully */
    public void delay(){
        new Handler().postDelayed(() -> {
            Intent i=new Intent(AddAchievmentAfterFixing.this,MainActivity.class);
            startActivity(i);
            finishAffinity();
        }, ConfigurationFile.Constants.SPLASH_TIME_OUT);
    }

/* Displaying Snackbar With Message */
    public void showSnackBar(int message){
        Snackbar.make(binding.rlParent,message,Snackbar.LENGTH_LONG).show();
    }

    /* Handle Data on Activity Result Of View Model */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            Image image = ImagePicker.getFirstImageOrNull(data);
            viewModel.onActivityResult(requestCode,resultCode,data,image);
            return;
        }
    }
}

