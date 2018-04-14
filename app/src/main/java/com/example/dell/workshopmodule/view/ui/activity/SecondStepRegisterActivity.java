package com.example.dell.workshopmodule.view.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import com.example.dell.workshopmodule.R;
import com.example.dell.workshopmodule.databinding.ActivitySecondStepRegisterLayoutBinding;
import com.example.dell.workshopmodule.model.global.BaseModel;
import com.example.dell.workshopmodule.model.global.CountryItem;
import com.example.dell.workshopmodule.model.request.RegisterRequest;
import com.example.dell.workshopmodule.utils.ConfigurationFile;
import com.example.dell.workshopmodule.view.ui.callback.DisplayDialogNavigator;
import com.example.dell.workshopmodule.view.ui.dialog.CitiesDialog;
import com.example.dell.workshopmodule.view.ui.dialog.CustomBrandsDialog;
import com.example.dell.workshopmodule.view.ui.dialog.CustomDialog;
import com.example.dell.workshopmodule.view.ui.dialog.FieldsDialog;
import com.example.dell.workshopmodule.viewmodel.SecondStepRegistration.SecondStepRegisterViewModel;
import com.example.dell.workshopmodule.viewmodel.SecondStepRegistration.SecondStepValidateViewModel;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by DELL on 21/03/2018.
 */

public class SecondStepRegisterActivity extends AppCompatActivity implements DisplayDialogNavigator {
    ActivitySecondStepRegisterLayoutBinding binding;
    SecondStepRegisterViewModel secondStepRegisterViewModel;
    SecondStepValidateViewModel validateViewModel;
    private RegisterRequest registerRequest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getObject();
        initBinding();
    }


    @Override
    protected void onResume() {
        super.onResume();
        secondStepRegisterViewModel.onResume();
    }

    @Override
    public void displayDialog(int code) {
        CustomDialog customDialog = new CustomDialog(SecondStepRegisterActivity.this, code, this);
        customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        customDialog.show();
    }
    public void displayFieldsDialog(){
        FieldsDialog dialog=new FieldsDialog(SecondStepRegisterActivity.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    @Override
    public void updateWorkshopData(int code) {
        if (code == ConfigurationFile.Constants.DISPLAY_SPECIALIZATION_DIALOG)
            secondStepRegisterViewModel.setSpecializationText();
        else if (code == ConfigurationFile.Constants.DISPLAY_URGENT_TYPES_DIALOG)
            secondStepRegisterViewModel.setUrgentText();
        else
            secondStepRegisterViewModel.setBrandsText();
    }

    @Override
    public void displayBrandsDialog() {
        CustomBrandsDialog customBrandsDialog=new CustomBrandsDialog(SecondStepRegisterActivity.this,this);
        customBrandsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        customBrandsDialog.show();
    }

    @Override
    public void updateUi(int code) {
            if(code==ConfigurationFile.Constants.EMPTY_PROVIDER_TYPE){
                Snackbar.make(binding.rlParent, R.string.empty_service_type,Snackbar.LENGTH_LONG).show();
            }else if(code== ConfigurationFile.Constants.EMPTY_WORKSHOP_SPECIALIZATION){
                Snackbar.make(binding.rlParent, R.string.empty_workshop_specialization,Snackbar.LENGTH_LONG).show();
            }else if(code== ConfigurationFile.Constants.EMPTY_WORKSHOP_BRANDS){
                Snackbar.make(binding.rlParent, R.string.empty_woekshop_brands,Snackbar.LENGTH_LONG).show();
            }else if(code== ConfigurationFile.Constants.EMPTY_WORKSHOP_URGENT_TYPES){
                Snackbar.make(binding.rlParent, R.string.empty_urgent_types    ,Snackbar.LENGTH_LONG).show();
            }else if(code== ConfigurationFile.Constants.SHOW_CITIES_DIALOG_CODE){
                displayFieldsDialog();
            }
    }

    @Override
    public void NavigateBetweenActivities(RegisterRequest registerRequest, int code) {

        if(code==ConfigurationFile.Constants.MOVE_TO_NEXT_ACTIVITY){
            Intent i=new Intent(getApplicationContext(), ThirdStepRegisterActivity.class);
            i.putExtra(ConfigurationFile.SharedPrefConstants.PREF_REGISTER_OBJECT,registerRequest);
            startActivity(i);
        }else if(code==ConfigurationFile.Constants.MOVE_TO_PREVIOUS_ACTIVITY){
            finish();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        secondStepRegisterViewModel.onPause();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFielsEvent( BaseModel baseModel){
        secondStepRegisterViewModel.setFieldsData(baseModel);
    }


    public void getObject(){
        registerRequest=(RegisterRequest) getIntent().getSerializableExtra(ConfigurationFile.SharedPrefConstants.PREF_REGISTER_OBJECT);
    }

    public void initBinding() {
        validateViewModel=new SecondStepValidateViewModel(this);
        secondStepRegisterViewModel = new SecondStepRegisterViewModel(SecondStepRegisterActivity.this, this,validateViewModel,registerRequest);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_second_step_register_layout);
        binding.setViewmodel(secondStepRegisterViewModel);
        binding.setValidateViewModel(validateViewModel);

    }


}
