package com.dp.dell.workshopmodule.view.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.widget.ImageView;

import com.dp.dell.workshopmodule.R;
import com.dp.dell.workshopmodule.databinding.ActivityFinalStepRegisterLayoutBinding;
import com.dp.dell.workshopmodule.model.request.RegisterRequest;
import com.dp.dell.workshopmodule.utils.ConfigurationFile;
import com.dp.dell.workshopmodule.utils.CustomUtils;
import com.dp.dell.workshopmodule.view.ui.callback.BaseInterface;
import com.dp.dell.workshopmodule.viewmodel.FinalStep.FinalStepRegisterViewModel;
import com.dp.dell.workshopmodule.viewmodel.FinalStep.HandleRegisterRequestViewModel;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ReturnMode;
import com.esafirm.imagepicker.model.Image;

import java.util.ArrayList;

/**
 * Created by DELL on 26/03/2018.
 */

public class FinalStepRegisterActivity extends BaseActivity implements BaseInterface {

    ActivityFinalStepRegisterLayoutBinding binding;
    FinalStepRegisterViewModel viewModel;
    RegisterRequest registerRequest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getObject();
        initBinding();
        // Activity or Fragment
    }

    ////////////Update View According To Code Sent From View Model ///////////////////////////////
    @Override
    public void updateUi(int code) {
        System.out.println("Code Data :" + code);
        if (code == ConfigurationFile.Constants.SHOW_DIALOG_CODE)
            CustomUtils.getInstance().showDialog(FinalStepRegisterActivity.this, viewModel);
        else if (code == ConfigurationFile.Constants.PERMISSION_DENIED)
            showSnackBar(R.string.permission_denied);
        else if (code == ConfigurationFile.Constants.PERMISSION_GRANTED_CAMERA) {
            CustomUtils.getInstance().openCamera(FinalStepRegisterActivity.this);

        } else if (code == ConfigurationFile.Constants.PERMISSION_GRANTED_GALLERY) {
            CustomUtils.getInstance().openGallery(FinalStepRegisterActivity.this);
        } else if (code == ConfigurationFile.Constants.CHOOSE_IMAGE_FROM_GALLERY)
            showSnackBar(R.string.choose_image);
        else if (code == ConfigurationFile.Constants.MOVE_TO_PREVIOUS_ACTIVITY)
            moveToPreviousActivity();

        else if (code == ConfigurationFile.Constants.INVALID_DATA)
            showSnackBar(R.string.invalid_data);
        else if (code == ConfigurationFile.Constants.MOVE_TO_NEXT_ACTIVITY)
            MoveToNextActivity(new Intent(this, MainActivity.class));


        else if (code == ConfigurationFile.Constants.NO_INTERNET_CONNECTION_CODE)
            showSnackBar(R.string.internet_connection);

    }

    /////////////////Sending Data From Implicit Intent To View Model (On Activity Result ) ////////////////////////
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // viewModel.onActivityResult(requestCode,resultCode,data);
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            Image image = ImagePicker.getFirstImageOrNull(data);
            viewModel.onActivityResult(requestCode, resultCode, data, image);
            return;
        }
    }

    ///////////////////Inflating Ui + Declaring It's View Model ////////////////////////////////////////////
    public void initBinding() {
        viewModel = new FinalStepRegisterViewModel(FinalStepRegisterActivity.this, this, registerRequest);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_final_step_register_layout);
        binding.setViewmodel(viewModel);

    }

    /////////////Move To Social Media Activity ///////////////////////////////////////////
    public void MoveToNextActivity(Intent I) {
        Intent i = new Intent(getApplicationContext(), WorkshopProfileActivity.class);
        i.putExtra(ConfigurationFile.SharedPrefConstants.PREF_REGISTER_OBJECT, registerRequest);
        startActivity(i);
    }

    ////////////////Displaying Snack Bar /////////////////////////////////////////////////
    public void showSnackBar(int message) {
        Snackbar.make(binding.rlParent, message, Snackbar.LENGTH_LONG).show();
    }

    //////////////Finish Current Activity And Back To Previous One /////////////////////////////////////
    public void moveToPreviousActivity() {
        finish();
    }

    /////////////////Get Extra Which is Sent From Previous one //////////////////////////////////////////////
    public void getObject() {
        registerRequest = (RegisterRequest) getIntent().getSerializableExtra(ConfigurationFile.SharedPrefConstants.PREF_REGISTER_OBJECT);
    }

    /////////////////////Handle Back Action In View Model /////////////////////////////////////////////////////
    @Override
    public void onBackPressed() {
        viewModel.handleBackAction(binding.rlParent);
    }
}
