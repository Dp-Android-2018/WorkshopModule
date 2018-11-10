package com.findandfix.workshop.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;

import com.findandfix.workshop.R;
import com.findandfix.workshop.databinding.ActivitySecondStepRegisterLayoutBinding;
import com.findandfix.workshop.model.global.BaseModel;
import com.findandfix.workshop.model.request.RegisterRequest;
import com.findandfix.workshop.ui.callback.DisplayDialogNavigator;
import com.findandfix.workshop.ui.dialog.CustomBrandsDialog;
import com.findandfix.workshop.ui.dialog.CustomDialog;
import com.findandfix.workshop.ui.dialog.FieldsDialog;
import com.findandfix.workshop.utils.ConfigurationFile;
import com.findandfix.workshop.viewmodel.SecondStepRegistration.SecondStepRegisterViewModel;
import com.findandfix.workshop.viewmodel.SecondStepRegistration.SecondStepValidateViewModel;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by DELL on 21/03/2018.
 */

public class SecondStepRegisterActivity extends BaseActivity implements DisplayDialogNavigator {
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
    public void updateUi(int code) {

            if(code==ConfigurationFile.Constants.EMPTY_PROVIDER_TYPE){
                showSnackbar(R.string.empty_service_type);
            }else if(code== ConfigurationFile.Constants.EMPTY_WORKSHOP_SPECIALIZATION){
                showSnackbar( R.string.empty_workshop_specialization);
            }else if(code== ConfigurationFile.Constants.EMPTY_WORKSHOP_BRANDS){
                showSnackbar(R.string.empty_woekshop_brands);
            }else if(code== ConfigurationFile.Constants.EMPTY_WORKSHOP_URGENT_TYPES){
                showSnackbar(R.string.empty_urgent_types);
            }else if(code== ConfigurationFile.Constants.EMPTY_WENSH_TYPES){
                showSnackbar(R.string.choose_wensh_types);
            }else if(code== ConfigurationFile.Constants.SHOW_CITIES_DIALOG_CODE){
                FieldsDialog dialog=new FieldsDialog(SecondStepRegisterActivity.this);
                displayDialog(dialog);
            }else if(code== ConfigurationFile.Constants.SHOW_BRANDS_DIALOG_CODE){
                CustomBrandsDialog customBrandsDialog=new CustomBrandsDialog(SecondStepRegisterActivity.this,this);
                displayDialog(customBrandsDialog);
            }else if(code== ConfigurationFile.Constants.DISPLAY_URGENT_TYPES_DIALOG || code== ConfigurationFile.Constants.DISPLAY_SPECIALIZATION_DIALOG ){
                CustomDialog customDialog = new CustomDialog(SecondStepRegisterActivity.this, code, this);
                displayDialog(customDialog);
            }else if(code==ConfigurationFile.Constants.UPDATE_BRANDS_DIALOG)
                    secondStepRegisterViewModel.setBrandsText();
             else if (code == ConfigurationFile.Constants.DISPLAY_SPECIALIZATION_DIALOG_TEXT)
                      secondStepRegisterViewModel.setSpecializationText();
            else if (code == ConfigurationFile.Constants.DISPLAY_URGENT_TYPES_DIALOG_text)
                    secondStepRegisterViewModel.setUrgentText();

    }

    @Override
    public void NavigateBetweenActivities(RegisterRequest registerRequest, int code) {

        if(code==ConfigurationFile.Constants.MOVE_TO_NEXT_ACTIVITY){
            Intent i=new Intent(getApplicationContext(), WorkingDayActivity.class);
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
        secondStepRegisterViewModel = new SecondStepRegisterViewModel(SecondStepRegisterActivity.this, this,validateViewModel,registerRequest,getSupportFragmentManager());
        binding = DataBindingUtil.setContentView(this, R.layout.activity_second_step_register_layout);
        binding.setViewmodel(secondStepRegisterViewModel);
        binding.setValidateViewModel(validateViewModel);
    }

    public void displayDialog(Dialog dialog){
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    public void showSnackbar(int message){
        Snackbar.make(binding.rlParent,message,Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        secondStepRegisterViewModel.handleBackButton(binding.rlParent);
    }
}
