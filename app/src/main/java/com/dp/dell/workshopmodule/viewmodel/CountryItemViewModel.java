package com.dp.dell.workshopmodule.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.view.View;

import com.dp.dell.workshopmodule.model.global.CountryItem;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by DELL on 03/04/2018.
 */

public class CountryItemViewModel extends BaseObservable {

    private Context context;
    private CountryItem countryItem;
    public CountryItemViewModel(Context context, CountryItem countryItem) {
        this.context=context;
        this.countryItem=countryItem;
    }

    public void setCountryData(CountryItem countryItem){
        this.countryItem=countryItem;
        notifyChange();
    }

    public String getCountryName(){
        return countryItem.getName();
    }


    public String getCountryPic(){
        //return countryItem.getName();
        return "http://new.findandfix.com/"+countryItem.getImage();
    }

    public void onItemClicked(View view){
        EventBus.getDefault().post(countryItem);
    }

}
