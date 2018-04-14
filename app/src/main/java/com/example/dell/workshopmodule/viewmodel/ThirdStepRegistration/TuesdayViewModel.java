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

public class TuesdayViewModel extends BaseObservable implements UpdateTimeListener{
    private ObservableField<String> fromTues;
    private ObservableField<String> toTues;
    private Context context;
    private boolean checker;
    private WorkdaysItem workdaysItem;
    public TuesdayViewModel(Context context) {
        fromTues =new ObservableField<>();
        toTues =new ObservableField<>();
        this.context=context;
    }

    @Override
    public void updateTime(String selectedTime,int code) {
        System.out.println("Code code :"+code);
        if(code== ConfigurationFile.Constants.FROM)
            setFromTues(selectedTime);
        else
            setToTues(selectedTime);
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
    public String getFromTues() {
        return fromTues.get();
    }

    public void setToTues(String toTues) {
        this.toTues.set(toTues);
        notifyPropertyChanged(BR.toTues);
    }

    public void setFromTues(String fromTues) {
        this.fromTues.set(fromTues);
        notifyPropertyChanged(BR.fromTues);
    }

    @Bindable
    public String getToTues() {
        return toTues.get();
    }

    public void  onFromValueChanged(Editable e){
        if (((e.toString()).length()>0)&&(!ValidationUtils.isEmpty(toTues.get()))){
            setChecker(true);
            removeFromCalendar();
            addToCalendar();

        }else {
            setChecker(false);
        }
    }
    public void  onToValueChanged(Editable e){
        if (((e.toString()).length()>0)&&(!ValidationUtils.isEmpty(fromTues.get()))){
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
            setFromTues("");
            setToTues("");
            removeFromCalendar();
        }
    }

    public void addToCalendar(){
        workdaysItem=new WorkdaysItem();
        workdaysItem.setDay("tuesday");
        workdaysItem.setFrom(fromTues.get());
        workdaysItem.setTo(toTues.get());
        ((MyApplication)MyApplication.getAppContext()).addDay(workdaysItem);
    }

    public void removeFromCalendar(){
        if (((MyApplication)MyApplication.getAppContext()).getWorkingday().contains(workdaysItem))
        ((MyApplication)MyApplication.getAppContext()).removeDay(workdaysItem);
    }
}
