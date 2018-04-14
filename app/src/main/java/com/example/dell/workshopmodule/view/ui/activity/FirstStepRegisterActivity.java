package com.example.dell.workshopmodule.view.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import com.example.dell.workshopmodule.R;
import com.example.dell.workshopmodule.databinding.ActivityFirstStepLayoutBinding;
import com.example.dell.workshopmodule.model.global.BaseModel;
import com.example.dell.workshopmodule.model.global.CountryItem;
import com.example.dell.workshopmodule.utils.ConfigurationFile;
import com.example.dell.workshopmodule.view.ui.callback.CallAnotherActivityNavigator;

import com.example.dell.workshopmodule.view.ui.dialog.CitiesDialog;
import com.example.dell.workshopmodule.view.ui.dialog.CountriesDialog;
import com.example.dell.workshopmodule.viewmodel.FirstStepRegistration.FirstStepRegisterValidations;
import com.example.dell.workshopmodule.viewmodel.FirstStepRegistration.FirstStepRegisterViewModel;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.ui.PlacePicker;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by DELL on 18/03/2018.
 */

public class FirstStepRegisterActivity extends AppCompatActivity implements CallAnotherActivityNavigator {

    private ActivityFirstStepLayoutBinding activityFirstStepLayoutBinding;
    private FirstStepRegisterViewModel firstStepRegisterViewModel;
    private FirstStepRegisterValidations validations;
    private  int PLACE_PICKER_REQUEST = 1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         initBinding();

    }


    @Override
    protected void onResume() {
        super.onResume();
        firstStepRegisterViewModel.onResume();
    }


    @Override
    public void callActivity() {
        try {

            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateUi(int code) {
        if(code== ConfigurationFile.Constants.INVALID_LOCATION_CODE){
            Snackbar.make(activityFirstStepLayoutBinding.scrollViewParent, R.string.choose_location,Snackbar.LENGTH_LONG).show();
        }else if(code== ConfigurationFile.Constants.INVALID_COUNTRY_CODE){
            Snackbar.make(activityFirstStepLayoutBinding.scrollViewParent, R.string.select_country,Snackbar.LENGTH_LONG).show();
        }else if(code== ConfigurationFile.Constants.INVALID_CITY_CODE){
            Snackbar.make(activityFirstStepLayoutBinding.scrollViewParent, R.string.select_city,Snackbar.LENGTH_LONG).show();
        }else if(code== ConfigurationFile.Constants.SHOW_DIALOG_CODE){
            CountriesDialog dialog=new CountriesDialog(FirstStepRegisterActivity.this);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        }else if(code== ConfigurationFile.Constants.SHOW_CITIES_DIALOG_CODE){
            CitiesDialog dialog=new CitiesDialog(FirstStepRegisterActivity.this,firstStepRegisterViewModel.getCountryId());
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        }else if(code==ConfigurationFile.Constants.NO_INTERNET_CONNECTION_CODE){
            Snackbar.make(activityFirstStepLayoutBinding.scrollViewParent, R.string.internet_connection,Snackbar.LENGTH_LONG).show();
        }else if(code==ConfigurationFile.Constants.SELECT_COUNTRY_CODE){
            Snackbar.make(activityFirstStepLayoutBinding.scrollViewParent, R.string.choose_country_first,Snackbar.LENGTH_LONG).show();
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        firstStepRegisterViewModel.onActivityResult(requestCode,resultCode,data,PLACE_PICKER_REQUEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        firstStepRegisterViewModel.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        firstStepRegisterViewModel.reset();
        validations.reset();}


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent( CountryItem countryItem){
        System.out.println("City Event 1");
        firstStepRegisterViewModel.setCountryData(countryItem);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCityMessageEvent( BaseModel baseModel){
        System.out.println("City Event 2");
        if(!(baseModel instanceof CountryItem))
        firstStepRegisterViewModel.setCityData(baseModel);
    }


    public void initBinding(){
        validations=new FirstStepRegisterValidations(getApplicationContext(),this);
        firstStepRegisterViewModel=new FirstStepRegisterViewModel(FirstStepRegisterActivity.this,this,validations);
        activityFirstStepLayoutBinding= DataBindingUtil.setContentView(this, R.layout.activity_first_step_layout);
        activityFirstStepLayoutBinding.setFirstStepValidation(validations);
        activityFirstStepLayoutBinding.setFirstStepRegisterViewModel(firstStepRegisterViewModel);

    }


}
