package com.example.dell.workshopmodule.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.view.View;

import com.example.dell.workshopmodule.BR;
import com.example.dell.workshopmodule.R;
import com.example.dell.workshopmodule.model.global.BaseModel;
import com.example.dell.workshopmodule.model.global.UserData;
import com.example.dell.workshopmodule.model.request.UpdateProfileRequest;
import com.example.dell.workshopmodule.utils.ConfigurationFile;
import com.example.dell.workshopmodule.utils.CustomUtils;
import com.example.dell.workshopmodule.view.ui.Application.MyApplication;
import com.example.dell.workshopmodule.view.ui.callback.BaseInterface;

import java.util.List;

/**
 * Created by DELL on 03/04/2018.
 */

public class EditProfileSpecializationViewModel extends BaseObservable {

    private UserData userData;
    public ObservableField<String>brands;
    public ObservableField<String>specialization;
    public ObservableField<String>urgentRequest;
    private BaseInterface callback;
    private UpdateProfileRequest updateProfileRequest;
    private HandleEditProfileViewModel handleEditProfileViewModel;
    private Context context;
    public EditProfileSpecializationViewModel(Context context, BaseInterface callback,HandleEditProfileViewModel handleeditProfileViewModel) {
        userData= CustomUtils.getInstance().getSaveUserObject(context);
        this.callback=callback;
        this.context=context;
        this.handleEditProfileViewModel=handleeditProfileViewModel;
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

        ((MyApplication) (MyApplication.getAppContext())).setBasicBrands(userData.getBrands());
        ((MyApplication) (MyApplication.getAppContext())).setBasicspecializations(userData.getSpecializations());
        ((MyApplication) (MyApplication.getAppContext())).setBasicUrgentTypes(userData.getUrgentRequestTypes());
    }



    public <E>String getDataAsText(List<E>data){
        String textValue="";
        for (E info :data){

            textValue=textValue+((BaseModel)info).getName()+"-";
        }
        textValue=textValue.substring(0,textValue.length()-1);
       return textValue;
    }

    public void showDialog(View view){
            if(view.getId()== R.id.iv_brands){
                    callback.updateUi(ConfigurationFile.Constants.SHOW_BRANDS_DIALOG_CODE);
            }else if (view.getId()==R.id.iv_specialization){
                callback.updateUi(ConfigurationFile.Constants.SHOW_SPECIALIZE_DIALOG_CODE);
            }else if (view.getId()==R.id.iv_urgents){
                callback.updateUi(ConfigurationFile.Constants.SHOW_URGENTS_DIALOG_CODE);
            }
    }

    public void setSpecializationText(){
       String specializationText=CustomUtils.getInstance().setSpecializationText();
       specialization.set(specializationText);
       }

    public void setBrandsText() {
        String brandsText = CustomUtils.getInstance().setBrandsText();
        System.out.println("Specializations :"+brandsText);
        brands.set(brandsText);
    }

    public void setUrgentText(){
        String urgentText="";
        urgentText=CustomUtils.getInstance().setUrgentText();
        urgentRequest.set(urgentText);}


    public void setProfileObject(UpdateProfileRequest updateProfileRequest){
        this.updateProfileRequest=updateProfileRequest;
    }

    public void updateProfile(View view){
                if(validate()) {
                    updateProfileRequest.setBrands(CustomUtils.getInstance().getBrandsIds());
                    updateProfileRequest.setSpecializations(CustomUtils.getInstance().getSpecializationIds());
                    updateProfileRequest.setUrgentTypes(CustomUtils.getInstance().getUrgentIds());
                    handleEditProfileViewModel.setEditProfileObject(updateProfileRequest,context,callback,userData.getToken());
                }
    }

    public boolean validate(){
        if(CustomUtils.getInstance().getBrandsIds().isEmpty() || CustomUtils.getInstance().getSpecializationIds().isEmpty() ||  CustomUtils.getInstance().getUrgentIds().isEmpty()) {
            callback.updateUi(ConfigurationFile.Constants.FILL_ALL_DATTA);
            return false;
        }else return true;
    }



}
