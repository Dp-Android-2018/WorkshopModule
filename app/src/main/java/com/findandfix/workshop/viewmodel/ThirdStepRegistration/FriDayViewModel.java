package com.findandfix.workshop.viewmodel.ThirdStepRegistration;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.text.Editable;
import android.view.View;

import com.findandfix.workshop.R;
import com.findandfix.workshop.model.global.WorkdaysItem;
import com.findandfix.workshop.ui.Application.MyApplication;
import com.findandfix.workshop.ui.callback.DisplayDialogNavigator;
import com.findandfix.workshop.ui.callback.UpdateTimeListener;
import com.findandfix.workshop.utils.ConfigurationFile;
import com.findandfix.workshop.utils.CustomUtils;
import com.findandfix.workshop.utils.ValidationUtils;

/**
 * Created by DELL on 25/03/2018.
 */

public class FriDayViewModel extends BaseObservable implements UpdateTimeListener {

    public ObservableField<String> fromFri;
    public ObservableField<String> toFri;
    private Context context;
    public ObservableBoolean checker;
    private WorkdaysItem workdaysItem;
    private DisplayDialogNavigator callback;
    public FriDayViewModel(Context context,DisplayDialogNavigator callback) {
        fromFri =new ObservableField<>();
        toFri =new ObservableField<>();
        checker=new ObservableBoolean();
        this.context=context;
        this.callback=callback;
    }

    @Override
    public void updateTime(String selectedTime,int code) {
        System.out.println("Code code :"+code);
        if(code== ConfigurationFile.Constants.FROM)
            fromFri.set(selectedTime);
        else
            toFri.set(selectedTime);
    }

    public void showTimePicker(View view){
        CustomUtils.getInstance().showTimePickerDialog(context,this, view.getId()== R.id.et_fri_from ?  ConfigurationFile.Constants.FROM:ConfigurationFile.Constants.To);
    }
    public void  onFromValueChanged(Editable e){
        if (((e.toString()).length()>0)&&(!ValidationUtils.isEmpty(toFri.get()))){
            checker.set(true);
            removeFromCalendar();
            addToCalendar();
        }else {
            checker.set(false);
        }
    }
    public void  onToValueChanged(Editable e){
        if (((e.toString()).length()>0)&&(!ValidationUtils.isEmpty(fromFri.get()))){
            checker.set(true);
            removeFromCalendar();
            addToCalendar();
        }else {
            checker.set(false);
        }
    }

    public void resetDay(View view){
        if(checker.get()) {
            resetUi();
            removeFromCalendar();
        }
    }

    public void resetUi(){
        checker.set(false);
        fromFri.set("");
        toFri.set("");
    }

    public void addToCalendar(){
        if(CustomUtils.getInstance().checktimings(fromFri.get(),toFri.get())) {
            workdaysItem = new WorkdaysItem();
            workdaysItem.setDay("friday");
            workdaysItem.setFrom(fromFri.get());
            workdaysItem.setTo(toFri.get());
            ((MyApplication) MyApplication.getAppContext()).addDay(workdaysItem);
        }else  {
            callback.updateUi(ConfigurationFile.Constants.TIME_ERROR);
            resetUi();
        }
    }

    public void removeFromCalendar(){
        if (((MyApplication)MyApplication.getAppContext()).getWorkingday().contains(workdaysItem))
        ((MyApplication)MyApplication.getAppContext()).removeDay(workdaysItem);
    }
}

