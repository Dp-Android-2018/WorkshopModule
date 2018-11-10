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
import com.findandfix.workshop.databinding.ActivityFirstStepLayoutBinding;
import com.findandfix.workshop.model.global.BaseModel;
import com.findandfix.workshop.model.global.CountryItem;
import com.findandfix.workshop.ui.callback.CallAnotherActivityNavigator;
import com.findandfix.workshop.ui.dialog.CitiesDialog;
import com.findandfix.workshop.ui.dialog.CountriesDialog;
import com.findandfix.workshop.utils.ConfigurationFile;
import com.findandfix.workshop.utils.CustomUtils;
import com.findandfix.workshop.viewmodel.FirstStepRegistration.FirstStepRegisterValidations;
import com.findandfix.workshop.viewmodel.FirstStepRegistration.FirstStepRegisterViewModel;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by DELL on 18/03/2018.
 */

public class FirstStepRegisterActivity extends BaseActivity implements CallAnotherActivityNavigator {

    private ActivityFirstStepLayoutBinding activityFirstStepLayoutBinding;
    private FirstStepRegisterViewModel firstStepRegisterViewModel;
    private FirstStepRegisterValidations validations;
    private  int PLACE_PICKER_REQUEST = 1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         initBinding();
    }

    ///////////Handling On Resume ///////////////////////////////
    @Override protected void onResume() {
        super.onResume();
        firstStepRegisterViewModel.onResume();
    }


    /////////////////////////Start Place Picker Activity ///////////////////////////////////////////
    @Override
    public void callActivity() {
        CustomUtils.getInstance().startPlacePicker(FirstStepRegisterActivity.this);
    }

    ///////////////////Update View Accoarding To Code Send From View Model ////////////////////////////
    @Override
    public void updateUi(int code) {
        if(code== ConfigurationFile.Constants.INVALID_LOCATION_CODE){
            showSnackBar(R.string.choose_location);
        }else if(code== ConfigurationFile.Constants.INVALID_COUNTRY_CODE){
            showSnackBar(R.string.select_country);
        }else if(code== ConfigurationFile.Constants.INVALID_CITY_CODE){
            showSnackBar(R.string.select_city);
        }else if(code== ConfigurationFile.Constants.SHOW_DIALOG_CODE){
            CountriesDialog dialog=new CountriesDialog(FirstStepRegisterActivity.this);
            showDialog(dialog);
        }else if(code== ConfigurationFile.Constants.SHOW_CITIES_DIALOG_CODE){
            CitiesDialog dialog=new CitiesDialog(FirstStepRegisterActivity.this,firstStepRegisterViewModel.getCountryId());
            showDialog(dialog);
        }else if(code==ConfigurationFile.Constants.NO_INTERNET_CONNECTION_CODE){
            showSnackBar(R.string.internet_connection);
        }else if(code==ConfigurationFile.Constants.SELECT_COUNTRY_CODE){
            showSnackBar( R.string.choose_country_first);
        }else if(code==ConfigurationFile.Constants.PERMISSION_GRANTED_LOCATION){
            firstStepRegisterViewModel.startPlacePicker();
        }   else if(code==ConfigurationFile.Constants.PERMISSION_DENIED)
            showSnackBar(R.string.permission_denied);
    }

    /////////////////////Display Dialog /////////////////////////////////////////////////////

    public void showDialog(Dialog dialog){
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }


    ////////////////////Send Data From Place Picker To View Model /////////////////////////////////
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        firstStepRegisterViewModel.onActivityResult(requestCode,resultCode,data,PLACE_PICKER_REQUEST);
    }

    //////////Handling On Pause /////////////////////////////////
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

///////////////////Use Event Bus To Get Country & City Data /////////////////////////////////////////////////////
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
///////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /////////Init Layout and Determine View Model Of Activity //////////////////////
    public void initBinding(){
        validations=new FirstStepRegisterValidations(FirstStepRegisterActivity.this,this);
        firstStepRegisterViewModel=new FirstStepRegisterViewModel(FirstStepRegisterActivity.this,this,validations);
        activityFirstStepLayoutBinding= DataBindingUtil.setContentView(this, R.layout.activity_first_step_layout);
        activityFirstStepLayoutBinding.setFirstStepValidation(validations);
        activityFirstStepLayoutBinding.setFirstStepRegisterViewModel(firstStepRegisterViewModel);

    }

    //////////////////////Display SnackBar With Message //////////////////////////////////////////////////
    public void showSnackBar(int message){
        Snackbar.make(activityFirstStepLayoutBinding.scrollViewParent, message,Snackbar.LENGTH_LONG).show();
    }
/////////////////Handle on Back Pressed ////////////////////////////////////////
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        firstStepRegisterViewModel.onBackPressed();

    }
}
