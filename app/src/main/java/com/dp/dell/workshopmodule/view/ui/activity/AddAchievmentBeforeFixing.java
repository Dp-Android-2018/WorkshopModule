package com.dp.dell.workshopmodule.view.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;

import com.dp.dell.workshopmodule.R;
import com.dp.dell.workshopmodule.databinding.ActivityAchievmentBeforeLayoutBinding;
import com.dp.dell.workshopmodule.utils.ConfigurationFile;
import com.dp.dell.workshopmodule.utils.CustomUtils;
import com.dp.dell.workshopmodule.view.ui.callback.BaseInterface;
import com.dp.dell.workshopmodule.viewmodel.AddAchievmentImageViewModel;
import com.dp.dell.workshopmodule.viewmodel.ToolbarViewModel;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;

public class AddAchievmentBeforeFixing extends BaseActivity implements BaseInterface{
    private ActivityAchievmentBeforeLayoutBinding binding;
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
        binding= DataBindingUtil.setContentView(AddAchievmentBeforeFixing.this, R.layout.activity_achievment_before_layout);
        viewModel=new AddAchievmentImageViewModel(AddAchievmentBeforeFixing.this,this,1);
        binding.setViewmodel(viewModel);

    }
    /* Specify Layout and properties Of Toolbar According To Code Which I Sent */
    public void handleToolbar(){
        setSupportActionBar( binding.toolbar.toolbar);
        binding.toolbar.setViewmodel(new ToolbarViewModel(AddAchievmentBeforeFixing.this, ConfigurationFile.Constants.HANDLE_ADD_ACHIEVMENT_TOOLBAR));
    }

    /* Update View According To Code Which Sent From View Model */
    @Override
    public void updateUi(int code) {
        if(code== ConfigurationFile.Constants.SHOW_DIALOG_CODE)
            CustomUtils.getInstance().showDialog(AddAchievmentBeforeFixing.this,viewModel);
        else if(code==ConfigurationFile.Constants.PERMISSION_DENIED)
            showSnackBar(R.string.permission_denied);
        else if(code==ConfigurationFile.Constants.PERMISSION_GRANTED_CAMERA)
            CustomUtils.getInstance().openCamera(AddAchievmentBeforeFixing.this);
        else if(code==ConfigurationFile.Constants.PERMISSION_GRANTED_GALLERY)
            CustomUtils.getInstance().openGallery(AddAchievmentBeforeFixing.this);
        else if(code==ConfigurationFile.Constants.CHOOSE_IMAGE_FROM_GALLERY)
            showSnackBar(R.string.choose_image);
        else if(code==ConfigurationFile.Constants.FILL_ALL_DATTA)
            showSnackBar(R.string.add_image);
    }
    /* Displaying Snackbar With Message */
    public void showSnackBar(int message){
        Snackbar.make(binding.rlParent,message,Snackbar.LENGTH_LONG).show();
    }
    /** Handle Data on Activity Result Of View Model */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            Image image = ImagePicker.getFirstImageOrNull(data);
            viewModel.onActivityResult(requestCode,resultCode,data,image);
            return;
        }
    }
}
