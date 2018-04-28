package com.example.dell.workshopmodule.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.support.v4.app.Fragment;
import android.view.View;

import com.example.dell.workshopmodule.R;
import com.example.dell.workshopmodule.model.global.BaseModel;
import com.example.dell.workshopmodule.model.global.CountryItem;
import com.example.dell.workshopmodule.model.global.UserData;
import com.example.dell.workshopmodule.model.request.UpdateProfileRequest;
import com.example.dell.workshopmodule.utils.ConfigurationFile;
import com.example.dell.workshopmodule.utils.CustomUtils;
import com.example.dell.workshopmodule.utils.ValidationUtils;
import com.example.dell.workshopmodule.view.ui.callback.BaseInterface;

import org.greenrobot.eventbus.EventBus;

import butterknife.internal.Utils;

/**
 * Created by DELL on 03/04/2018.
 */

public class EditProfileInfoViewModel extends BaseObservable {

    private UserData userData;
    private Context context;
    public ObservableField<String> workshopName;
    public ObservableField<String> workshopEmail;
    public ObservableField<String> workshopPhone;
    public ObservableField<String> workshopCountry;
    public ObservableField<String> workshopCity;
    public ObservableField<String> workshopWebsite;
    private int countryId=-1,cityId=-1;
    private BaseInterface callback;
    private Fragment fragment;
    public EditProfileInfoViewModel(Context context, BaseInterface callback, Fragment fragment) {
        userData= CustomUtils.getInstance().getSaveUserObject(context);
        this.callback=callback;
        this.context=context;
        this.fragment=fragment;
        initializeVariables();
        setWorkshopData();

    }

    public void onResume(){
        EventBus.getDefault().register(fragment);
    }
    public void initializeVariables(){
        workshopName=new ObservableField<>();
        workshopEmail=new ObservableField<>();
        workshopPhone=new ObservableField<>();
        workshopCountry=new ObservableField<>();
        workshopCity=new ObservableField<>();
        workshopWebsite=new ObservableField<>();
    }

    public void setWorkshopData(){
        workshopName.set(userData.getName());
        workshopEmail.set(userData.getEmail());
        workshopPhone.set(userData.getMobile());
        workshopCountry.set(userData.getCountry().getName());
        workshopCity.set(userData.getCity().getName());
        workshopWebsite.set(userData.getWebsite());
        countryId=userData.getCountry().getId();
        cityId=userData.getCity().getId();

    }

    public void showDialog(View view){
            if (view.getId()== R.id.iv_countries_dialog)
                  callback.updateUi(ConfigurationFile.Constants.SHOW_DIALOG_CODE);
            else if(view.getId()==R.id.iv_cities_dialog)
                callback.updateUi(ConfigurationFile.Constants.SHOW_CITIES_DIALOG_CODE);
    }

    public void setCountryData(CountryItem countryItem) {
        countryId = countryItem.getId();
        workshopCountry.set(countryItem.getName());
    }

    public void setCityData(BaseModel baseModel) {
        cityId=baseModel.getId();
        workshopCity.set(baseModel.getName());
    }


    public void onPause(){
        EventBus.getDefault().unregister(fragment);
    }

    public void moveTONextTab(View view){

                 if (validate())
        EventBus.getDefault().post(getProfileObject());
    }

    public boolean validate(){
        if(ValidationUtils.isEmpty(workshopName.get()) || ValidationUtils.isEmpty(workshopEmail.get()) || ValidationUtils.isEmpty(workshopPhone.get()) || countryId==-1 || cityId==-1
                ||  ValidationUtils.isEmpty(workshopWebsite.get())){
            System.out.println("Invalid Data");
            callback.updateUi(ConfigurationFile.Constants.FILL_ALL_DATTA);
            return false;
        }else if(!ValidationUtils.isMail(workshopEmail.get())){
            System.out.println("Invalid mail:"+workshopEmail.get());
            callback.updateUi(ConfigurationFile.Constants.INVALID_EMAIL_FORMAT);
            return false;
        }else{

            return true;
        }


    }

    public UpdateProfileRequest getProfileObject(){
        UpdateProfileRequest updateProfileRequest=new UpdateProfileRequest();
        updateProfileRequest.setName(workshopName.get());
        updateProfileRequest.setEmail(workshopEmail.get());
        updateProfileRequest.setMobile(workshopPhone.get());
        updateProfileRequest.setCountryId(countryId);
        updateProfileRequest.setCityId(cityId);
        updateProfileRequest.setWebsite(workshopWebsite.get());
        return updateProfileRequest;
    }
}
