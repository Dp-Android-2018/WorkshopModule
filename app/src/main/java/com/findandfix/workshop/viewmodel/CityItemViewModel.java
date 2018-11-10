package com.findandfix.workshop.viewmodel;

import android.databinding.BaseObservable;
import android.view.View;

import com.findandfix.workshop.model.global.BaseModel;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by DELL on 20/03/2018.
 */

public class CityItemViewModel extends BaseObservable {

    private BaseModel city;
    public CityItemViewModel(BaseModel city) {
        this.city=city;
    }

    public void setCity(BaseModel city){
        this.city=city;
        notifyChange();
    }

    public String getCityName() {
        return city.getName();
    }

    public void onItemClicked(View view){
        EventBus.getDefault().post(city);
    }
}
