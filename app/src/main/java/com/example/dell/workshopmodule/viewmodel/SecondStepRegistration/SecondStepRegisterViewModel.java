package com.example.dell.workshopmodule.viewmodel.SecondStepRegistration;

import android.app.Activity;
import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.view.View;

import com.example.dell.workshopmodule.BR;
import com.example.dell.workshopmodule.R;
import com.example.dell.workshopmodule.model.request.RegisterRequest;
import com.example.dell.workshopmodule.model.global.BaseModel;
import com.example.dell.workshopmodule.model.global.BrandItem;
import com.example.dell.workshopmodule.utils.ConfigurationFile;
import com.example.dell.workshopmodule.utils.CustomUtils;
import com.example.dell.workshopmodule.view.ui.Application.MyApplication;
import com.example.dell.workshopmodule.view.ui.callback.DisplayDialogNavigator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 21/03/2018.
 */

public class SecondStepRegisterViewModel extends BaseObservable {


    public ObservableInt selectedValue;
    private DisplayDialogNavigator navigator;
    private Activity activity;
    public ObservableField<String>fields;
    public ObservableField<String> specializationText;
    public ObservableField<String> urgentText;
    public ObservableField<String> brandsText;
    private SecondStepValidateViewModel validateViewModel;
    private RegisterRequest registerRequest;
    public SecondStepRegisterViewModel(Activity activity, DisplayDialogNavigator navigator, SecondStepValidateViewModel validateViewModel, RegisterRequest registerRequest) {
        this.activity=activity;
        this.navigator=navigator;
        this.validateViewModel=validateViewModel;
        this.registerRequest=registerRequest;
        selectedValue=new ObservableInt();
        selectedValue.set(-1);
        fields=new ObservableField<>();
        specializationText=new ObservableField<>();
        urgentText=new ObservableField<>();
        brandsText=new ObservableField<>();
    }

    public void onResume() {
        EventBus.getDefault().register(activity);
    }

    public void onPause() {
        EventBus.getDefault().unregister(activity);
    }

    public void setSpecializationText(){
        specializationText.set(" ");
        specializationText.set(CustomUtils.getInstance().setSpecializationText());}

    public void setUrgentText(){
        urgentText.set("");
        urgentText.set(CustomUtils.getInstance().setUrgentText());
    }

    public void setBrandsText() {
        brandsText.set("");
        brandsText.set(CustomUtils.getInstance().setBrandsText());
    }

    public void displaySpecializationDialog(View view){
        navigator.updateUi(ConfigurationFile.Constants.DISPLAY_SPECIALIZATION_DIALOG);
    }
    public void displayUrgentTypesDialog(View view){
        navigator.updateUi(ConfigurationFile.Constants.DISPLAY_URGENT_TYPES_DIALOG);
    }

    public void displayBrandsDialog(View view){
        navigator.updateUi(ConfigurationFile.Constants.SHOW_BRANDS_DIALOG_CODE);
    }

    public void validate(View view){
       int code=validateViewModel.validator(selectedValue.get());
       if(code==1){
            if(selectedValue.get()==3) {
                registerRequest.setBrands(getBrandsIds());
                registerRequest.setSpecializations(getSpecializationIds());
                registerRequest.setUrgentTypes(getUrgentTypes());
            }else if(selectedValue.get()==2){
                List<Integer>wensh=new ArrayList<>();
                wensh.add(4);
                registerRequest.setUrgentTypes(wensh);
            }
            moveTOThirdStep();
       }
    }

    public List<Integer> getSpecializationIds(){
        List<Integer>specializeId=new ArrayList<>();
        for(BaseModel model:(((MyApplication)MyApplication.getAppContext()).getBasicspecializations())){
                specializeId.add(model.getId());
        }
        return specializeId;
    }

    public List<Integer> getBrandsIds(){
        List<Integer>brandsIds=new ArrayList<>();
        for(BrandItem brandItem:(((MyApplication)MyApplication.getAppContext()).getBasicBrands())){
            brandsIds.add(brandItem.getId());
        }
        return brandsIds;
    }

    public List<Integer> getUrgentTypes(){
        List<Integer>urgentTypesId=new ArrayList<>();
        for(BaseModel model:(((MyApplication)MyApplication.getAppContext()).getBasicUrgentTypes())){
            urgentTypesId.add(model.getId());
        }
        return urgentTypesId;
    }

    public void moveTOThirdStep(){
            navigator.NavigateBetweenActivities(registerRequest,ConfigurationFile.Constants.MOVE_TO_NEXT_ACTIVITY);}




    public void showDialog(View view) {
        navigator.updateUi(ConfigurationFile.Constants.SHOW_CITIES_DIALOG_CODE);
    }

    public void setFieldsData(BaseModel baseModel) {
        System.out.println("Validator Event :"+baseModel.getName());
        System.out.println("Selected Id :"+baseModel.getId());
        selectedValue.set(baseModel.getId());
        fields.set(baseModel.getName());
    }

    public void handleBackButton(View view) {
        (((MyApplication)MyApplication.getAppContext()).getBasicspecializations()).clear();
        (((MyApplication)MyApplication.getAppContext()).getBasicBrands()).clear();
        (((MyApplication)MyApplication.getAppContext()).getBasicUrgentTypes()).clear();
        navigator.NavigateBetweenActivities(null,ConfigurationFile.Constants.MOVE_TO_PREVIOUS_ACTIVITY);
    }



}
