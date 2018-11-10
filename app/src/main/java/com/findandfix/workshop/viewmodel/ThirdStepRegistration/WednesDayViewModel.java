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

public class WednesDayViewModel extends BaseObservable implements UpdateTimeListener {

    public ObservableField<String> fromWed;
    public ObservableField<String> toWed;
    private Context context;
    public ObservableBoolean checker;
    private WorkdaysItem workdaysItem;
    private DisplayDialogNavigator callback;
    public WednesDayViewModel(Context context,DisplayDialogNavigator callback) {
        fromWed =new ObservableField<>();
        toWed =new ObservableField<>();
        checker=new ObservableBoolean();
        this.context=context;
        this.callback=callback;
    }

    @Override
    public void updateTime(String selectedTime,int code) {
        System.out.println("Code code :"+code);
        if(code== ConfigurationFile.Constants.FROM)
            fromWed.set(selectedTime);
        else
            toWed.set(selectedTime);
    }

    public void showTimePicker(View view){
        System.out.println("Clicked From:");
        CustomUtils.getInstance().showTimePickerDialog(context,this,view.getId()== R.id.et_wed_from ? ConfigurationFile.Constants.FROM: ConfigurationFile.Constants.To);
    }

    public void  onFromValueChanged(Editable e){
        if (((e.toString()).length()>0)&&(!ValidationUtils.isEmpty(toWed.get()))){
            checker.set(true);
            removeFromCalendar();
            addToCalendar();

        }else {
            checker.set(false);
        }
    }
    public void  onToValueChanged(Editable e){
        if (((e.toString()).length()>0)&&(!ValidationUtils.isEmpty(fromWed.get()))){
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
        toWed.set("");
        fromWed.set("");
    }

    public void addToCalendar(){
        if(CustomUtils.getInstance().checktimings(fromWed.get(),toWed.get())) {
            workdaysItem = new WorkdaysItem();
            workdaysItem.setDay("wednesday");
            workdaysItem.setFrom(fromWed.get());
            workdaysItem.setTo(toWed.get());
            ((MyApplication) MyApplication.getAppContext()).addDay(workdaysItem);
        }else {
            callback.updateUi(ConfigurationFile.Constants.TIME_ERROR);
            resetUi();
        }
    }

    public void removeFromCalendar(){
        if (((MyApplication)MyApplication.getAppContext()).getWorkingday().contains(workdaysItem))
        ((MyApplication)MyApplication.getAppContext()).removeDay(workdaysItem);
    }
}
