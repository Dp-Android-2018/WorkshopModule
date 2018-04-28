package com.example.dell.workshopmodule.view.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.example.dell.workshopmodule.R;
import com.example.dell.workshopmodule.databinding.ActivityFinalStepRegisterLayoutBinding;
import com.example.dell.workshopmodule.model.request.RegisterRequest;
import com.example.dell.workshopmodule.utils.ConfigurationFile;
import com.example.dell.workshopmodule.view.ui.callback.BaseInterface;
import com.example.dell.workshopmodule.viewmodel.FinalStep.FinalStepRegisterViewModel;
import com.example.dell.workshopmodule.viewmodel.FinalStep.HandleRegisterRequestViewModel;

/**
 * Created by DELL on 26/03/2018.
 */

public class FinalStepRegisterActivity extends AppCompatActivity implements BaseInterface{

    ActivityFinalStepRegisterLayoutBinding binding;
    FinalStepRegisterViewModel viewModel;
    HandleRegisterRequestViewModel registerRequestViewModel;
    RegisterRequest registerRequest;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getObject();
        initBinding();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        viewModel.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }

    @Override
    public void updateUi(int code) {
        if(code== ConfigurationFile.Constants.SHOW_DIALOG_CODE)
                showDialog();
        else if(code==ConfigurationFile.Constants.PERMISSION_DENIED)
                    showSnackBar(R.string.permission_denied);
        else if(code==ConfigurationFile.Constants.PERMISSION_GRANTED_CAMERA)
            openCamera();
        else if(code==ConfigurationFile.Constants.PERMISSION_GRANTED_GALLERY)
            openGallery();
        else if(code==ConfigurationFile.Constants.CHOOSE_IMAGE_FROM_GALLERY)
                        showSnackBar(R.string.choose_image);
        else if(code== ConfigurationFile.Constants.MOVE_TO_PREVIOUS_ACTIVITY)
                    moveToPreviousActivity();

        else if(code== ConfigurationFile.Constants.INVALID_DATA)
                        showSnackBar(R.string.invalid_data);
        else if(code== ConfigurationFile.Constants.MOVE_TO_NEXT_ACTIVITY)
            MoveToNextActivity(new Intent(this,MainActivity.class));


        else if(code== ConfigurationFile.Constants.MOVE_TO_SUBSCRIBTION_ACTIVITY)
            MoveToNextActivity(new Intent(this,SubScribtionPageActivity.class));

        else if(code==ConfigurationFile.Constants.NO_INTERNET_CONNECTION_CODE)
                    showSnackBar(R.string.internet_connection);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        viewModel.onActivityResult(requestCode,resultCode,data);
    }

    public void initBinding(){

        registerRequestViewModel=new HandleRegisterRequestViewModel(getApplicationContext(),this);
        viewModel=new FinalStepRegisterViewModel(FinalStepRegisterActivity.this,this,registerRequest,registerRequestViewModel);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_final_step_register_layout);
        binding.setViewmodel(viewModel);
        binding.setRegisterviewmodel(registerRequestViewModel);
    }

    public void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(FinalStepRegisterActivity.this);
        builder.setTitle("Choose Pictures")
                .setItems(R.array.media, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        viewModel.askForPermission(which);
                    }
                });

        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }

    public void openCamera(){
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, ConfigurationFile.Constants.CAMERA_REQUEST);
    }

    public void openGallery(){
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto , ConfigurationFile.Constants.GALLERY_REQUEST);
    }

    public void MoveToNextActivity(Intent I){
        startActivity(I);
        finish();
    }

    public void showSnackBar(int message){
        Snackbar.make(binding.rlParent,message,Snackbar.LENGTH_LONG).show();
    }
    public void moveToPreviousActivity(){finish();}

    public void getObject(){
        registerRequest=(RegisterRequest) getIntent().getSerializableExtra(ConfigurationFile.SharedPrefConstants.PREF_REGISTER_OBJECT);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        viewModel.handleBackAction(binding.rlParent);
    }
}
