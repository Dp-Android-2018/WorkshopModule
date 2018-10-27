package com.dp.dell.workshopmodule.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.view.View;

import com.dp.dell.workshopmodule.R;
import com.dp.dell.workshopmodule.model.global.BaseModel;
import com.dp.dell.workshopmodule.model.global.CountryItem;
import com.dp.dell.workshopmodule.model.global.UserData;
import com.dp.dell.workshopmodule.model.request.UpdateProfileRequest;
import com.dp.dell.workshopmodule.utils.ConfigurationFile;
import com.dp.dell.workshopmodule.utils.CustomUtils;
import com.dp.dell.workshopmodule.utils.ValidationUtils;
import com.dp.dell.workshopmodule.view.ui.activity.WorkshopProfileImagesActivity;
import com.dp.dell.workshopmodule.view.ui.callback.BaseInterface;

import org.greenrobot.eventbus.EventBus;

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
    public EditProfileInfoViewModel(Context context, BaseInterface callback) {
        userData= CustomUtils.getInstance().getSaveUserObject(context);
        this.callback=callback;
        this.context=context;
        initializeVariables();
        setWorkshopData();

    }

    public void onResume(){
        if(!EventBus.getDefault().isRegistered(context))
           EventBus.getDefault().register(context);
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
            if (view.getId()== R.id.iv_countries_dialog  || view.getId()==R.id.rl_countries)
                  callback.updateUi(ConfigurationFile.Constants.SHOW_DIALOG_CODE);
            else if(view.getId()==R.id.iv_cities_dialog || view.getId()==R.id.rl_city)
                callback.updateUi(ConfigurationFile.Constants.SHOW_CITIES_DIALOG_CODE);
    }

    public void moveToWorkshopProfileImagesActivity(View view){
        Intent profileImagesActivity= new Intent(context, WorkshopProfileImagesActivity.class);
        context.startActivity(profileImagesActivity);
    }

    public void setCountryData(CountryItem countryItem) {
        countryId = countryItem.getId();
        workshopCountry.set(countryItem.getName());
        cityId=-1;
        workshopCity.set("");
    }

    public void setCityData(BaseModel baseModel) {
        cityId=baseModel.getId();
        workshopCity.set(baseModel.getName());
    }


    public void onPause(){
        EventBus.getDefault().unregister(context);
    }


    public boolean validate(){
        System.out.println("Workshop Phone 1 :"+workshopPhone.get());
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
    public int countryId(){
        return countryId;
    }
}
