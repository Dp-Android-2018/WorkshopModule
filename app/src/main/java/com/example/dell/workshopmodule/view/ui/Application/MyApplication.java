package com.example.dell.workshopmodule.view.ui.Application;

import android.app.Application;
import android.content.Context;

import com.example.dell.workshopmodule.model.global.WorkdaysItem;
import com.example.dell.workshopmodule.model.global.BaseModel;
import com.example.dell.workshopmodule.model.global.BrandItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 24/03/2018.
 */

public class MyApplication extends Application {

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

    public ArrayList<WorkdaysItem>getWorkingday(){
        return workingday;
    }
}
