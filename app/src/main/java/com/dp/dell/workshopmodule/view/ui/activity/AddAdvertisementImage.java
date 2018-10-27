package com.dp.dell.workshopmodule.view.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;

import com.dp.dell.workshopmodule.R;
import com.dp.dell.workshopmodule.databinding.ActivityAddAdvertismentImageLayoutBinding;
import com.dp.dell.workshopmodule.utils.ConfigurationFile;
import com.dp.dell.workshopmodule.utils.CustomUtils;
import com.dp.dell.workshopmodule.view.ui.callback.BaseInterface;
import com.dp.dell.workshopmodule.viewmodel.AddAdvImageViewModel;
import com.dp.dell.workshopmodule.viewmodel.ToolbarViewModel;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;

/**
 * Created by DELL on 01/05/2018.
 */

public class AddAdvertisementImage  extends BaseAdvertisementAct implements BaseInterface{

    private ActivityAddAdvertismentImageLayoutBinding binding;
    private AddAdvImageViewModel viewModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        handleToolbar();
    }
    ////////////////////////Inflating Layout Of ACTIVITY Define it's View Model ////////////////////////////////////////////////////////
    public void initBinding(){
        binding= DataBindingUtil.setContentView(AddAdvertisementImage.this,R.layout.activity_add_advertisment_image_layout);
        viewModel=new AddAdvImageViewModel(AddAdvertisementImage.this,this);
        binding.setViewmodel(viewModel);

    }
    //////////////////////SET ToolBar Color and define it's Properties //////////////////////////////////////////////////////////////////
    public void handleToolbar(){
        setSupportActionBar( binding.toolbar.toolbar);
        binding.toolbar.setViewmodel(new ToolbarViewModel(AddAdvertisementImage.this, ConfigurationFile.Constants.HANDLE_CUSTOM_ADD_ADVERTISING_TOOLBAR));
    }

    //////////////////////Update View According To Code /////////////////////////////////////////////////////////////
    @Override
    public void updateUi(int code) {
        if(code== ConfigurationFile.Constants.SHOW_DIALOG_CODE)
            CustomUtils.getInstance().showDialog(AddAdvertisementImage.this,viewModel);
        else if(code==ConfigurationFile.Constants.PERMISSION_DENIED)
            showSnackBar(R.string.permission_denied);
        else if(code==ConfigurationFile.Constants.PERMISSION_GRANTED_CAMERA)
            CustomUtils.getInstance().openCamera(AddAdvertisementImage.this);
        else if(code==ConfigurationFile.Constants.PERMISSION_GRANTED_GALLERY)
            CustomUtils.getInstance().openGallery(AddAdvertisementImage.this);
        else if(code==ConfigurationFile.Constants.CHOOSE_IMAGE_FROM_GALLERY)
            showSnackBar(R.string.choose_image);
    }
/////////////////Display Snack Bar With Message //////////////////////////////////////////////////////////////
    public void showSnackBar(int message){
        Snackbar.make(binding.rlParent,message,Snackbar.LENGTH_LONG).show();
    }
    /////////////////Handling On Activity Result With View Model ///////////////////////////////////
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            Image image = ImagePicker.getFirstImageOrNull(data);
            viewModel.onActivityResult(requestCode,resultCode,data,image);
            return;
        }
    }
}
