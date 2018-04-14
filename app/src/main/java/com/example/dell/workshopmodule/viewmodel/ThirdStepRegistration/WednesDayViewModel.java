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

public class WednesDayViewModel extends BaseObservable implements UpdateTimeListener{

    private ObservableField<String> fromWed;
    private ObservableField<String> toWed;
    private Context context;
    private boolean checker;
    private WorkdaysItem workdaysItem;
    public WednesDayViewModel(Context context) {
        fromWed =new ObservableField<>();
        toWed =new ObservableField<>();
        this.context=context;
    }

    @Override
    public void updateTime(String selectedTime,int code) {
        System.out.println("Code code :"+code);
        if(code== ConfigurationFile.Constants.FROM)
            setFromWed(selectedTime);
        else
            setToWed(selectedTime);
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
    public String getFromWed() {
        return fromWed.get();
    }

    public void setToWed(String toWed) {
        this.toWed.set(toWed);
        notifyPropertyChanged(BR.toWed);
    }

    public void setFromWed(String fromWed) {
        this.fromWed.set(fromWed);
        notifyPropertyChanged(BR.fromWed);
    }

    @Bindable
    public String getToWed() {
        return toWed.get();
    }

    public void  onFromValueChanged(Editable e){
        if (((e.toString()).length()>0)&&(!ValidationUtils.isEmpty(toWed.get()))){
            setChecker(true);
            removeFromCalendar();
            addToCalendar();

        }else {
            setChecker(false);
        }
    }
    public void  onToValueChanged(Editable e){
        if (((e.toString()).length()>0)&&(!ValidationUtils.isEmpty(fromWed.get()))){
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
            setToWed("");
            setToWed("");
            removeFromCalendar();
        }
    }

    public void addToCalendar(){
        workdaysItem=new WorkdaysItem();
        workdaysItem.setDay("wednesday");
        workdaysItem.setFrom(fromWed.get());
        workdaysItem.setTo(toWed.get());
        ((MyApplication)MyApplication.getAppContext()).addDay(workdaysItem);
    }

    public void removeFromCalendar(){
        if (((MyApplication)MyApplication.getAppContext()).getWorkingday().contains(workdaysItem))
        ((MyApplication)MyApplication.getAppContext()).removeDay(workdaysItem);
    }
}
