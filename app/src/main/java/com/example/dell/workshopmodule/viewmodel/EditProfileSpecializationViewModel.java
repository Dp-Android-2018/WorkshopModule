package com.example.dell.workshopmodule.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;

import com.example.dell.workshopmodule.model.global.BaseModel;
import com.example.dell.workshopmodule.model.global.UserData;
import com.example.dell.workshopmodule.utils.CustomUtils;

import java.util.List;

/**
 * Created by DELL on 03/04/2018.
 */

public class EditProfileSpecializationViewModel extends BaseObservable {

    private UserData userData;
    public ObservableField<String>brands;
    public ObservableField<String>specialization;
    public ObservableField<String>urgentRequest;
    public EditProfileSpecializationViewModel(Context context) {
        userData= CustomUtils.getInstance().getSaveUserObject(context);
       initializeData();
       setWorkShopData();
    }

    public void initializeData(){
        brands=new ObservableField<>();
        specialization=new ObservableField<>();
        urgentRequest=new ObservableField<>();
    }

    public void setWorkShopData(){
        brands.set(getDataAsText(userData.getBrands()));
        specialization.set(getDataAsText(userData.getSpecializations()));
        urgentRequest.set(getDataAsText(userData.getUrgentRequestTypes()));
    }

    public <E>String getDataAsText(List<E>data){
        String textValue="";
        for (E info :data){

            textValue=textValue+((BaseModel)info).getName()+"-";
        }
        textValue=textValue.substring(0,textValue.length()-1);
       return textValue;
    }


}
