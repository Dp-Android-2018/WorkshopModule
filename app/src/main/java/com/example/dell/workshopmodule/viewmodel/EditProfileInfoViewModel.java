package com.example.dell.workshopmodule.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;

import com.example.dell.workshopmodule.model.global.UserData;
import com.example.dell.workshopmodule.utils.CustomUtils;

/**
 * Created by DELL on 03/04/2018.
 */

public class EditProfileInfoViewModel extends BaseObservable {

    private UserData userData;
    public ObservableField<String> workshopName;
    public ObservableField<String> workshopEmail;
    public ObservableField<String> workshopPhone;
    public ObservableField<String> workshopCountry;
    public ObservableField<String> workshopCity;
    public ObservableField<String> workshopWebsite;
    public EditProfileInfoViewModel(Context context) {
        userData= CustomUtils.getInstance().getSaveUserObject(context);
        initializeVariables();
        setWorkshopData();

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
        workshopCountry.set(userData.getCountry());
        workshopCity.set(userData.getCity());
        workshopWebsite.set(userData.getWebsite());
    }
}
