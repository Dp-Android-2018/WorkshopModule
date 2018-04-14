package com.example.dell.workshopmodule.viewmodel.ThirdStepRegistration;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableField;
import android.text.Editable;
import android.view.View;


import com.example.dell.workshopmodule.BR;
import com.example.dell.workshopmodule.model.global.WorkdaysItem;
import com.example.dell.workshopmodule.utils.ConfigurationFile;
import com.example.dell.workshopmodule.utils.CustomUtils;
import com.example.dell.workshopmodule.utils.ValidationUtils;
import com.example.dell.workshopmodule.view.ui.Application.MyApplication;
import com.example.dell.workshopmodule.view.ui.callback.UpdateTimeListener;

/**
 * Created by DELL on 25/03/2018.
 */

public class SaturdayViewModel extends BaseObservable implements UpdateTimeListener{


    private ObservableField<String>fromSat;
    private ObservableField<String>toSat;
    private Context context;
    private boolean checker;
    private WorkdaysItem workdaysItem;
    public SaturdayViewModel(Context context) {
        fromSat=new ObservableField<>();
        toSat=new ObservableField<>();
        this.context=context;
    }

    @Override
    public void updateTime(String selectedTime,int code) {
        System.out.println("Code code :"+code);
        if(code==ConfigurationFile.Constants.FROM)
                setFromSat(selectedTime);
        else
                setToSat(selectedTime);
    }

    public void showTimePickerFrom(View view){
        System.out.println("Clicked From:");
        CustomUtils.getInstance().showTimePickerDialog(context,this, ConfigurationFile.Constants.FROM);
    }

    public void showTimePickerTo(View view){
        System.out.println("Clicked To:");
        CustomUtils.getInstance().showTimePickerDialog(context,this, ConfigurationFile.Constants.To);
    }



    @Bindable
    public String getFromSat() {
        return fromSat.get();
    }

    public void setToSat(String toSat) {
        this.toSat.set(toSat);
        notifyPropertyChanged(BR.toSat);
    }

    public void setFromSat(String fromSat) {
        this.fromSat.set(fromSat);
        notifyPropertyChanged(BR.fromSat);
    }

    @Bindable
    public String getToSat() {
        return toSat.get();
    }

    public void  onFromValueChanged(Editable e){
            if (((e.toString()).length()>0)&&(!ValidationUtils.isEmpty(toSat.get()))){
                setChecker(true);
                removeFromCalendar();
                addToCalendar();

            }else {
                setChecker(false);
            }
    }
    public void  onToValueChanged(Editable e){
        if (((e.toString()).length()>0)&&(!ValidationUtils.isEmpty(fromSat.get()))){
            setChecker(true);
            removeFromCalendar();
            addToCalendar();

        }else {
            setChecker(false);
        }
    }

    @Bindable
    public boolean isChecker() {
        return checker;
    }

    public void setChecker(boolean checker) {
        this.checker = checker;
        notifyPropertyChanged(BR.checker);
    }

    public void resetDay(View view){
        if(checker) {
            setChecker(false);
            setFromSat("");
            setToSat("");
            removeFromCalendar();
        }
    }

    public void addToCalendar(){
         workdaysItem=new WorkdaysItem();
        workdaysItem.setDay("saturday");
        workdaysItem.setFrom(fromSat.get());
        workdaysItem.setTo(toSat.get());
        ((MyApplication)MyApplication.getAppContext()).addDay(workdaysItem);
    }

    public void removeFromCalendar(){
        if (((MyApplication)MyApplication.getAppContext()).getWorkingday().contains(workdaysItem))
        ((MyApplication)MyApplication.getAppContext()).removeDay(workdaysItem);
    }


}
