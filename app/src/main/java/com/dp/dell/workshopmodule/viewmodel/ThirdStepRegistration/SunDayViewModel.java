package com.dp.dell.workshopmodule.viewmodel.ThirdStepRegistration;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.text.Editable;
import android.view.View;

import com.dp.dell.workshopmodule.BR;
import com.dp.dell.workshopmodule.R;
import com.dp.dell.workshopmodule.model.global.WorkdaysItem;
import com.dp.dell.workshopmodule.utils.ConfigurationFile;
import com.dp.dell.workshopmodule.utils.CustomUtils;
import com.dp.dell.workshopmodule.utils.ValidationUtils;
import com.dp.dell.workshopmodule.view.ui.Application.MyApplication;
import com.dp.dell.workshopmodule.view.ui.callback.DisplayDialogNavigator;
import com.dp.dell.workshopmodule.view.ui.callback.UpdateTimeListener;

/**
 * Created by DELL on 25/03/2018.
 */

public class SunDayViewModel extends BaseObservable implements UpdateTimeListener{

    public ObservableField<String> fromSun;
    public ObservableField<String> toSun;
    private Context context;
    public ObservableBoolean checker;
    private WorkdaysItem workdaysItem;
    private DisplayDialogNavigator callback;
    public SunDayViewModel(Context context,DisplayDialogNavigator callback) {
        fromSun =new ObservableField<>();
        toSun =new ObservableField<>();
        checker=new ObservableBoolean();
        this.context=context;
        this.callback=callback;
    }

    @Override
    public void updateTime(String selectedTime,int code) {
        System.out.println("Code code :"+code);
        if(code== ConfigurationFile.Constants.FROM)
            fromSun.set(selectedTime);
        else
            toSun.set(selectedTime);
    }

    public void showTimePicker(View view){
        System.out.println("Clicked From:");
        CustomUtils.getInstance().showTimePickerDialog(context,this, view.getId()== R.id.et_sun_from ? ConfigurationFile.Constants.FROM: ConfigurationFile.Constants.To);
    }

    public void  onFromValueChanged(Editable e){
        if (((e.toString()).length()>0)&&(!ValidationUtils.isEmpty(toSun.get()))){
            checker.set(true);
            removeFromCalendar();
            addToCalendar();

        }else {
            checker.set(false);
        }
    }
    public void  onToValueChanged(Editable e){
        if (((e.toString()).length()>0)&&(!ValidationUtils.isEmpty(fromSun.get()))){
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
        fromSun.set("");
        toSun.set("");
    }

    public void addToCalendar(){
        if(CustomUtils.getInstance().checktimings(fromSun.get(),fromSun.get())) {
            workdaysItem = new WorkdaysItem();
            workdaysItem.setDay("sunday");
            workdaysItem.setFrom(fromSun.get());
            workdaysItem.setTo(fromSun.get());
            ((MyApplication) MyApplication.getAppContext()).addDay(workdaysItem);
        } else {
            callback.updateUi(ConfigurationFile.Constants.TIME_ERROR);
            resetUi();
        }
    }

    public void removeFromCalendar(){
        if (((MyApplication)MyApplication.getAppContext()).getWorkingday().contains(workdaysItem))
        ((MyApplication)MyApplication.getAppContext()).removeDay(workdaysItem);
    }

}
