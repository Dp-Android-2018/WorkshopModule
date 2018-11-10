package com.findandfix.workshop.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;
import android.widget.CheckBox;

import com.findandfix.workshop.BR;
import com.findandfix.workshop.model.global.BrandItem;
import com.findandfix.workshop.ui.Application.MyApplication;

/**
 * Created by DELL on 24/03/2018.
 */

public class CustomDialogBrandItemViewModel extends BaseObservable {

    private BrandItem brandItem;
    private boolean checked;
    public CustomDialogBrandItemViewModel(BrandItem brandItem) {

        this.brandItem=brandItem;
        setChecked();
    }

    public String getItemName(){
        return brandItem.getName();
    }

    public String getBrandPic(){
        return "http://new.findandfix.com/"+brandItem.getImage();
    }



    public void setBrandItem(BrandItem brandItem){
        this.brandItem=brandItem;
        notifyChange();
    }

    public void setCheckedListener(View v){
        if(((CheckBox)v).isChecked()) {
            ((MyApplication)(MyApplication.getAppContext())).addBrand(brandItem);
            checked=true;
        } else {
            ((MyApplication)(MyApplication.getAppContext())).removeBrandItem(brandItem);
            checked=false;
        }
        notifyChange();
        notifyPropertyChanged(BR.checked);
    }

    public void setChecked(){

        for (BrandItem brand : ((MyApplication) (MyApplication.getAppContext())).getBasicBrands()) {
            if (brand.getId() == brandItem.getId()) {
                checked = true;
                notifyPropertyChanged(BR.checked);
                ((MyApplication) (MyApplication.getAppContext())).addBrand(brandItem);
            }
        }
    }

    @Bindable
    public boolean getChecked(){
        return checked;
    }



}
