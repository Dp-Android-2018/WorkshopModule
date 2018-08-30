package com.dp.dell.workshopmodule.viewmodel.FirstStepRegistration;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.databinding.ObservableList;
import android.text.Editable;
import android.view.View;

import com.dp.dell.workshopmodule.R;
import com.dp.dell.workshopmodule.model.global.BaseModel;
import com.dp.dell.workshopmodule.model.global.CountryItem;
import com.dp.dell.workshopmodule.model.request.EmailRequest;
import com.dp.dell.workshopmodule.model.request.MobileRequest;
import com.dp.dell.workshopmodule.model.request.RegisterRequest;
import com.dp.dell.workshopmodule.network.ApiClient;
import com.dp.dell.workshopmodule.network.EndPoints;
import com.dp.dell.workshopmodule.utils.ConfigurationFile;
import com.dp.dell.workshopmodule.utils.CustomUtils;
import com.dp.dell.workshopmodule.utils.NetWorkConnection;
import com.dp.dell.workshopmodule.utils.ValidationUtils;
import com.dp.dell.workshopmodule.view.ui.activity.LoginActivity;
import com.dp.dell.workshopmodule.view.ui.activity.SecondStepRegisterActivity;
import com.dp.dell.workshopmodule.view.ui.callback.CallAnotherActivityNavigator;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

/**
 * Created by DELL on 18/03/2018.
 */

public class FirstStepRegisterViewModel extends BaseObservable {

    public ObservableField<String> workshopName;
    public ObservableField<String> email;
    public ObservableField<String> workshopPhone;
    public ObservableField<String> workshopWebsite;
    public ObservableField<String> password;
    public ObservableField<String> confirmpassword;
    public ObservableField<String> location;
    public ObservableField<String> country;
    public ObservableField<String> city;
    public ObservableInt progressDialog;
    private double lat, lang;
    private CompositeDisposable compositeDisposable;
    public ObservableList<BaseModel> cities;
    private CallAnotherActivityNavigator navigator;
    private Context context;
    private FirstStepRegisterValidations validations;
    private int countryId = -1;
    public ObservableInt cityId;
    private int validEmail = -1;
    private int validPhone = -1;
    private RxPermissions rxPermissions;
    public FirstStepRegisterViewModel(Context context, CallAnotherActivityNavigator navigator, FirstStepRegisterValidations validations) {
        this.navigator = navigator;
        this.context = context;
        this.validations = validations;
        rxPermissions=new RxPermissions((Activity)context);
        initializeVariables();
    }

    public void initializeVariables() {
        email = new ObservableField<>();
        workshopPhone = new ObservableField<>();
        workshopWebsite = new ObservableField<>();
        password = new ObservableField<>();
        confirmpassword = new ObservableField<>();
        location = new ObservableField<>();
        cities = new ObservableArrayList<>();
        workshopName = new ObservableField<>();
        cityId = new ObservableInt(-1);
        progressDialog=new ObservableInt(View.GONE);
        compositeDisposable = new CompositeDisposable();
        country = new ObservableField<>();
        city=new ObservableField<>();
    }


    public void onResume() {
        EventBus.getDefault().register(context);
    }

    public void onPause() {
        EventBus.getDefault().unregister(context);
    }


    public void askForLocationPermission(View view) {
        CustomUtils.getInstance().requireLocationPermission(rxPermissions,navigator);
    }

    public void checkemail(String email) {

        if(NetWorkConnection.isConnectingToInternet(context)) {
            CustomUtils.getInstance().showProgressDialog((Activity)context);
            EmailRequest emailRequest = new EmailRequest(email);
            Disposable disposable = ApiClient.getClient().create(EndPoints.class).checkEmail(ConfigurationFile.Constants.API_KEY,CustomUtils.getInstance().getAppLanguage(context), ConfigurationFile.Constants.Content_Type, ConfigurationFile.Constants.Content_Type, emailRequest)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(integerResponse -> {
                        CustomUtils.getInstance().cancelDialog();
                        if (integerResponse.code() == ConfigurationFile.Constants.SUCCESS_CODE) {
                            System.out.println("Code code :" + integerResponse.body().toString());
                            if (integerResponse.body() == 0) {
                                System.out.println("Code MAIL Success");
                                validEmail = 0;
                                validations.setEmaildError("");
                            } else {
                                System.out.println("Code MAIL Failed");
                                validEmail = 1;
                                validations.setEmaildError(context.getString(R.string.invalid_email_format));
                            }

                            if (validPhone != -1) {
                                CustomUtils.getInstance().cancelDialog();
                                //progressDialog.set(View.GONE);
                                validateData();
                            }
                        }else if (integerResponse.code() == ConfigurationFile.Constants.UNAUTHENTICATED_CODE ||
                                integerResponse.code() == ConfigurationFile.Constants.UNSUBSCRIBE_CODE){
                            CustomUtils.getInstance().endSession((Activity)context);
                        }

                    }, throwable -> CustomUtils.getInstance().cancelDialog());
            compositeDisposable.add(disposable);
        }else {
            navigator.updateUi(ConfigurationFile.Constants.NO_INTERNET_CONNECTION_CODE);
        }

    }


    public void checkeMobile(String mobile) {
        if(NetWorkConnection.isConnectingToInternet(context)) {
           // CustomUtils.getInstance().showProgressDialog((Activity)context);
        MobileRequest mobileRequest = new MobileRequest(mobile);
        Disposable disposable = ApiClient.getClient().create(EndPoints.class).checkMobile(ConfigurationFile.Constants.API_KEY,CustomUtils.getInstance().getAppLanguage(context), ConfigurationFile.Constants.Content_Type, ConfigurationFile.Constants.Content_Type, mobileRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integerResponse -> {
         //           CustomUtils.getInstance().cancelDialog();
                    if (integerResponse.code() == ConfigurationFile.Constants.SUCCESS_CODE) {
                        System.out.println("Code code :" + integerResponse.body().toString());
                        if (integerResponse.body() == 0) {
                            System.out.println("Code Phone Success");
                            validPhone = 0;
                            validations.setWorkshopPhoneError("");
                        } else {
                            System.out.println("Code PHONE Failed");
                            validPhone = 1;
                            validations.setWorkshopPhoneError(context.getString(R.string.invalid_phone_format));
                        }

                        if (validEmail!=-1) {
                          //  progressDialog.set(View.GONE);
                         //   CustomUtils.getInstance().cancelDialog();
                            validateData();
                        }
                    }else if (integerResponse.code() == ConfigurationFile.Constants.UNAUTHENTICATED_CODE ||
                            integerResponse.code() == ConfigurationFile.Constants.UNSUBSCRIBE_CODE){
                        CustomUtils.getInstance().endSession((Activity)context);
                    }

                }, throwable -> {
                    //CustomUtils.getInstance().cancelDialog();
                });
        compositeDisposable.add(disposable);
        }else {
            navigator.updateUi(ConfigurationFile.Constants.NO_INTERNET_CONNECTION_CODE);
        }

    }


    public void startPlacePicker() {
        navigator.callActivity();
    }

    public void showDialog(View view) {
        if(view.getId()==R.id.rl_sign_up_country)
              navigator.updateUi(ConfigurationFile.Constants.SHOW_DIALOG_CODE);
        else {
            if(countryId!=-1)
            navigator.updateUi(ConfigurationFile.Constants.SHOW_CITIES_DIALOG_CODE);
            else
                navigator.updateUi(ConfigurationFile.Constants.SELECT_COUNTRY_CODE);
        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data, int PLACE_PICKER_REQUEST) {

        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, context);
                location.set(String.format("Place: %s", place.getAddress()));
                LatLng lng = place.getLatLng();
                lat = lng.latitude;
                lang = lng.longitude;
                System.out.println("Location Data Lat :"+lat);
                System.out.println("Location Data Lang :"+lang);
            }
        }
    }


    public void onEmailChanged(Editable e) {
        if (ValidationUtils.isMail(e.toString())) {
            validations.setEmaildError("");
        } else {
            validations.setEmaildError(context.getString(R.string.invalid_email_format));
        }
    }


    public void onMobileChanged(Editable e) {

        if (ValidationUtils.isPhone(e.toString())) {
            validations.setWorkshopPhoneError("");
        } else {
            validations.setWorkshopPhoneError(context.getString(R.string.invalid_phone_format));
        }
    }

    public void onPasswordChanged(Editable e) {

        if (e.toString().length()>=8) {
            validations.setPasswordError("");
        } else {
            validations.setPasswordError(context.getString(R.string.password_length));
        }
    }

    public void onConfirmPasswordChanged(Editable e) {

        if (e.toString().length()<8) {
            validations.setPasswordConfirmationError(context.getString(R.string.password_length));

        } else if(!e.toString().equals(password.get())){
            validations.setPasswordConfirmationError(context.getString(R.string.matches_password));
        }else {
            validations.setPasswordConfirmationError("");
        }
    }

    public void validateUniqeMailandPhone(View view) {

        if(email.get()==null){
            validations.setEmaildError(context.getString(R.string.reqired_field));
        }else if (workshopPhone.get()==null){
            validations.setWorkshopPhoneError(context.getString(R.string.reqired_field));
        }else {
            if(NetWorkConnection.isConnectingToInternet(context)) {
               // progressDialog.set(View.VISIBLE);
                checkeMobile(workshopPhone.get());
                checkemail(email.get());
            }else {
                navigator.updateUi(ConfigurationFile.Constants.NO_INTERNET_CONNECTION_CODE);
            }
        }
    }

    public void validateData(){
        int validatecode = validations.validator(workshopName.get(), email.get(), validEmail, password.get(), confirmpassword.get(),
                workshopPhone.get(), workshopWebsite.get(), location.get(), lat, lang, countryId, cityId.get(),validPhone);
        if (validatecode == 1)
            moveTOSecondStep();
    }

    public void moveTOSecondStep() {
        RegisterRequest registerRequest = initializeRegisterData();
        Intent i = new Intent(context, SecondStepRegisterActivity.class);
        i.putExtra(ConfigurationFile.SharedPrefConstants.PREF_REGISTER_OBJECT, registerRequest);
        context.startActivity(i);

    }

    public RegisterRequest initializeRegisterData() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setName(workshopName.get());
        registerRequest.setEmail(email.get());
        registerRequest.setCountryId(countryId);
        registerRequest.setCityId(cityId.get());
        registerRequest.setLatitude(String.valueOf(lat));
        registerRequest.setLongitude(String.valueOf(lang));
        registerRequest.setMobile(workshopPhone.get());
        registerRequest.setPassword(password.get());
        registerRequest.setPasswordConfirmation(confirmpassword.get());
        registerRequest.setWebsite(workshopWebsite.get());
        return registerRequest;
    }

    public void reset() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed())
            compositeDisposable.dispose();
        compositeDisposable = null;
        context = null;
    }

    public void setCountryData(CountryItem countryItem) {
        countryId = countryItem.getId();
        country.set(countryItem.getName());
    }


    public void setCityData(BaseModel baseModel) {
        System.out.println("City Event :"+baseModel.getName());
        cityId.set(baseModel.getId());
        city.set(baseModel.getName());
    }

    public int getCountryId(){
        return countryId;
    }

    public void onBackPressed(){
        Intent i=new Intent(context,LoginActivity.class);
        context.startActivity(i);
        ((Activity)context).finish();
        ((Activity)context).finishAffinity();
    }




}
