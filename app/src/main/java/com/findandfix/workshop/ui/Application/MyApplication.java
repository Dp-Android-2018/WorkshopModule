package com.findandfix.workshop.ui.Application;

import android.app.Application;
import android.content.Context;

import com.asha.nightowllib.NightOwl;
import com.findandfix.workshop.R;
import com.findandfix.workshop.ui.callback.component.DaggerFirebaseComponent;
import com.findandfix.workshop.ui.callback.component.DaggerIntentComponent;
import com.findandfix.workshop.ui.callback.component.DaggerLoginComponent;
import com.findandfix.workshop.ui.callback.component.DaggerLoginRequestComponent;
import com.findandfix.workshop.ui.callback.component.DaggerObservableComponent;
import com.findandfix.workshop.ui.callback.component.DaggerSharedprefUtilsComponent;
import com.findandfix.workshop.model.ConnectionReceiver;
import com.findandfix.workshop.model.global.BaseModel;
import com.findandfix.workshop.model.global.BrandItem;
import com.findandfix.workshop.model.global.WorkdaysItem;
import com.findandfix.workshop.model.module.BaseCallbackModule;
import com.findandfix.workshop.model.module.ContextModule;
import com.findandfix.workshop.model.module.CustomClassModule;
import com.findandfix.workshop.model.module.LoginRequestModule;
import com.findandfix.workshop.ui.callback.BaseInterface;
import com.findandfix.workshop.ui.callback.component.FirebaseComponent;
import com.findandfix.workshop.ui.callback.component.IntentComponent;
import com.findandfix.workshop.ui.callback.component.LoginComponent;
import com.findandfix.workshop.ui.callback.component.LoginRequestComponent;
import com.findandfix.workshop.ui.callback.component.ObservableComponent;
import com.findandfix.workshop.ui.callback.component.SharedprefUtilsComponent;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by DELL on 24/03/2018.
 */

public class MyApplication extends Application {

    private String secondUserName;
    private static MyApplication mInstance;
    /////////////////////////////Specializations Data /////////////////////////////
    private ArrayList<BaseModel> tempspecializations =new ArrayList<>();
    private ArrayList<BaseModel> basicspecializations =new ArrayList<>();
    /////////////////////////////////////////////////////////////////////////////
    ///////////////////////UrgentRequests Data ////////////////////////////////
    private ArrayList<BaseModel> tempUrgentTypes =new ArrayList<>();
    private ArrayList<BaseModel> basicUrgentTypes =new ArrayList<>();
    ///////////////////////////////////////////////////////////////////////
    ///////////////////////Brands Data ////////////////////////////////
    private ArrayList<BrandItem> tempBrands =new ArrayList<>();
    private ArrayList<BrandItem> basicBrands =new ArrayList<>();
    /////////////////////////////Working Day//////////////////////////////////////////
    private ArrayList<WorkdaysItem>workingday=new ArrayList<>();
    private int CustomDialogCode=-1;
    ////////////////////////////////////////////////////////////////////
    private static Context context;

    public void onCreate() {
        super.onCreate();
        MyApplication.context = getApplicationContext();
        mInstance = this;
        NightOwl.builder().defaultMode(0).create();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("font/Arabic_Font.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );


    }


    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    public void setConnectionListener(ConnectionReceiver.ConnectionReceiverListener listener) {
        ConnectionReceiver.connectionReceiverListener = listener;
    }
    public static Context getAppContext() {
        return MyApplication.context;
    }

  ////////////////////////////////Handling Specializations /////////////////////////////////////////////
    public void addSpecialize(BaseModel model){
        tempspecializations.add(model);
    }
    public void removeSpecialization(BaseModel model){
        tempspecializations.remove(model);
        System.out.println("Removed Data :"+tempspecializations.size());
    }

    public static LoginComponent getLoginComponent(BaseInterface baseInterface){
        LoginComponent loginComponent= DaggerLoginComponent.builder()
                .baseCallbackModule(new BaseCallbackModule(baseInterface))
                .contextModule(new ContextModule(context))
                .build();
        return loginComponent;
    }


    public static IntentComponent getIntentComponent(Class c){
        IntentComponent intentComponent= DaggerIntentComponent.builder()
                .customClassModule(new CustomClassModule(c))
                .contextModule(new ContextModule(context)).build();
        return intentComponent;
    }

    public static ObservableComponent getObservableComponent(){
        ObservableComponent observableComponent= DaggerObservableComponent.builder().build();
        return observableComponent;
    }

    public static FirebaseComponent getFirebaseComponent(){
       FirebaseComponent firebaseComponent= DaggerFirebaseComponent.builder().build();
        return firebaseComponent;
    }

    public static LoginRequestComponent getLoginRequestComponent(String email, String phone, String password, String token){
        LoginRequestComponent loginRequestComponent= DaggerLoginRequestComponent.builder()
                .loginRequestModule(new LoginRequestModule(email,phone,password,token))
                .build();
        return loginRequestComponent;
    }

   public static SharedprefUtilsComponent getSharedprefUtilsComponent(){
        SharedprefUtilsComponent sharedprefUtilsComponent= DaggerSharedprefUtilsComponent.builder()
                .contextModule(new ContextModule(context))
                .build();
        return sharedprefUtilsComponent;

    }


    public ArrayList<BaseModel> getTempspecializations(){
            return tempspecializations;
    }

    public void setTempspecializations(ArrayList<BaseModel> tempspecializations){
        this.tempspecializations = tempspecializations;
    }
    public void clearSpecializations(){
        tempspecializations.clear();
    }

    public void setBasicspecializations(List<BaseModel>temp){
        basicspecializations.clear();
        basicspecializations.addAll(temp);
        tempspecializations.clear();
        System.out.println("Removed Basic :"+basicspecializations.size());
    }

    public void setSecondUserName(String secondUserName){
        this.secondUserName=secondUserName;
    }

    public String getSecondUserName(){
            return secondUserName;
    }

    public ArrayList<BaseModel> getBasicspecializations() {
        return basicspecializations;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////Handling Urgent Types /////////////////////////////////////////////
    public void addUrgentType(BaseModel model){
        tempUrgentTypes.add(model);
    }
    public void removeUrgentType(BaseModel model){tempUrgentTypes.remove(model);}
    public ArrayList<BaseModel> getTempUrgentTypes(){
        return tempUrgentTypes;
    }
    public void setTempUrgentTypes(ArrayList<BaseModel> tempUrgentTypes){this.tempUrgentTypes = tempUrgentTypes;}
    public void clearUrgentTypes(){
        tempUrgentTypes.clear();
    }
    public void setBasicUrgentTypes(List<BaseModel>temp){
        basicUrgentTypes.clear();
        basicUrgentTypes.addAll(temp);
        tempUrgentTypes.clear();
    }
    public ArrayList<BaseModel> getBasicUrgentTypes() {
        return basicUrgentTypes;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////Handling BRANDS Types /////////////////////////////////////////////
    public void addBrand(BrandItem brandItem){
        tempBrands.add(brandItem);
    }
    public void removeBrandItem(BrandItem brandItem){tempBrands.remove(brandItem);}
    public ArrayList<BrandItem> getTempBrands(){
        return tempBrands;
    }
    public void setTempBrands(ArrayList<BrandItem> tempBrands){this.tempBrands = tempBrands;}
    public void clearTempBrands(){
        tempBrands.clear();
    }
    public void setBasicBrands(List<BrandItem>temp){
        basicBrands.clear();
        basicBrands.addAll(temp);
        tempBrands.clear();
    }
    public ArrayList<BrandItem> getBasicBrands() {
        return basicBrands;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////

    public int getCustomDialogCode() {
        return CustomDialogCode;
    }

    public void setCustomDialogCode(int customDialogCode) {
        CustomDialogCode = customDialogCode;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void addDay(WorkdaysItem workdaysItem){
        workingday.add(workdaysItem);
        System.out.println("Working Day Size :"+workingday.size());
    }

    public void removeDay(WorkdaysItem workdaysItem){
        System.out.println("Working Day Size :"+workingday.size());
        if(workingday.contains(workdaysItem))
        workingday.remove(workdaysItem);
        System.out.println("Working Day Size :"+workingday.size());
    }

    public void clearCalendar(){
        workingday.clear();
        System.out.println("Working Day Size :"+workingday.size());
    }

    public ArrayList<WorkdaysItem>getWorkingday(){
        return workingday;
    }
}
