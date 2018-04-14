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
import com.example.dell.workshopmodule.view.ui.Application.MyApplication;
import com.example.dell.workshopmodule.view.ui.callback.DisplayDialogNavigator;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 21/03/2018.
 */

public class SecondStepRegisterViewModel extends BaseObservable {


    public ObservableInt selectedValue;
    private DisplayDialogNavigator navigator;
    private boolean visibility;
    private Activity activity;
    public ObservableField<String>fields;
    private String specializationText;
    private String urgentText;
    private String brandsText;
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
        }

    public void onResume() {
        EventBus.getDefault().register(activity);
    }

    public void onPause() {
        EventBus.getDefault().unregister(activity);
    }

    public void setSpecializationText(){
        specializationText="";

        List<BaseModel>baseModels=((MyApplication)activity.getApplicationContext()).getBasicspecializations();
        for(BaseModel model:baseModels)
            specializationText=specializationText+model.getName()+" ";
            notifyPropertyChanged(BR.specializationText);
    }

    @Bindable
    public String getSpecializationText() {
        return specializationText;
    }

    public void setUrgentText(){
        urgentText="";
        List<BaseModel>baseModels=((MyApplication)activity.getApplicationContext()).getBasicUrgentTypes();
        for(BaseModel model:baseModels)
            urgentText=urgentText+model.getName()+" ";
        notifyPropertyChanged(BR.urgentText);

    }
    @Bindable
    public String getUrgentText() {
        return urgentText;
    }




    public void setBrandsText() {
        brandsText="";
        List<BrandItem>brandItems=((MyApplication)activity.getApplicationContext()).getBasicBrands();
        for(BrandItem items:brandItems)
            brandsText=brandsText+items.getName()+" ";
        notifyPropertyChanged(BR.brandsText);
    }

    @Bindable
    public String getBrandsText() {
        return brandsText;
    }






    @Bindable
    public boolean isVisibility() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
            notifyPropertyChanged(BR.visibility);
    }

    public void displaySpecializationDialog(View view){
        navigator.displayDialog(ConfigurationFile.Constants.DISPLAY_SPECIALIZATION_DIALOG);
    }
    public void displayUrgentTypesDialog(View view){
        navigator.displayDialog(ConfigurationFile.Constants.DISPLAY_URGENT_TYPES_DIALOG);
    }

    public void displayBrandsDialog(View view){
        navigator.displayBrandsDialog();
    }

    public void validate(View view){
       int code=validateViewModel.validator(selectedValue.get());
       if(code==1){

            registerRequest.setBrands(getBrandsIds());
            registerRequest.setSpecializations(getSpecializationIds());
            registerRequest.setUrgentTypes(getUrgentTypes());
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

    public void backtoFirstStep(){
        navigator.NavigateBetweenActivities(null,ConfigurationFile.Constants.MOVE_TO_PREVIOUS_ACTIVITY);}


    public void showDialog(View view) {
        navigator.updateUi(ConfigurationFile.Constants.SHOW_CITIES_DIALOG_CODE);
    }

    public void setFieldsData(BaseModel baseModel) {
        System.out.println("City Event :"+baseModel.getName());
        System.out.println("Selected Id :");
        selectedValue.set(baseModel.getId());
        fields.set(baseModel.getName());
    }

}
