package com.dp.dell.workshopmodule.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;
import android.view.View;

import com.dp.dell.workshopmodule.R;
import com.dp.dell.workshopmodule.model.global.WorkDayItems;
import com.dp.dell.workshopmodule.model.request.RegisterRequest;
import com.dp.dell.workshopmodule.utils.ConfigurationFile;
import com.dp.dell.workshopmodule.utils.CustomUtils;
import com.dp.dell.workshopmodule.view.ui.Application.MyApplication;
import com.dp.dell.workshopmodule.view.ui.callback.BaseInterface;
import com.dp.dell.workshopmodule.view.ui.callback.UpdateTimeListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by DELL on 12/08/2018.
 */

public class WorkingDaysViewModel implements UpdateTimeListener{
    public String current;
    public String previous;
    public ObservableField<String>dayName;
    public ObservableField<String>mFrom;
    public ObservableField<String>mTo;
    public ObservableField<String>eFrom;
    public ObservableField<String>eTo;
    public List<WorkDayItems> workingDays;
    private HashMap<String,WorkDayItems>days;
    private int code;
    private Context context;
    private BaseInterface baseInterface;
    private RegisterRequest registerRequest;
    public WorkingDaysViewModel(Context context, BaseInterface baseInterface, RegisterRequest registerRequest) {
        this.context=context;
        this.baseInterface=baseInterface;
        this.registerRequest=registerRequest;
        current="saturday";
        dayName=new ObservableField<>(context.getString(R.string.sa));
        days=new HashMap<>();
        workingDays=new ArrayList<>();
        mFrom=new ObservableField<>("00:00");
        eFrom=new ObservableField<>("00:00");
        mTo=new ObservableField<>("00:00");
        eTo=new ObservableField<>("00:00");
    }

    public void validate(View view){
        previous=current;
        System.out.println("Previous :"+previous);
        if (view.getId()== R.id.tv_sa){
            current="saturday";
            dayName.set(context.getString(R.string.sa));
        }else if (view.getId()== R.id.tv_su){
            current="sunday";
            dayName.set(context.getString(R.string.sع));
        }else if (view.getId()== R.id.tv_mo){
            current="monday";
            dayName.set(context.getString(R.string.Mo));
        }else if (view.getId()== R.id.tv_tue){
            current="tuesday";
            dayName.set(context.getString(R.string.Tue));
        }else if (view.getId()== R.id.tv_wed){
            current="wednesday";
            dayName.set(context.getString(R.string.Wed));
        }else if (view.getId()== R.id.tv_thu){
            current="thursday";
            dayName.set(context.getString(R.string.Thu));
        }else if (view.getId()== R.id.tv_fri){
            current="friday";
            dayName.set(context.getString(R.string.fri));
        }

        getObject(0);
    }

    public void onResume(){
        workingDays.clear();
    }

    public void getObject(int checker){

        if (previous!=null) {
            if (!mFrom.get().equals("00:00") && !mTo.get().equals("00:00")) {
                WorkDayItems workDayItems = new WorkDayItems();
                if (checker==0)
                    workDayItems.setDay(previous);
                else workDayItems.setDay(current);
                workDayItems.setFrom(mFrom.get());
                workDayItems.setTo(mTo.get());
                workDayItems.setShift("morning");
                days.put(workDayItems.getDay() + "_morning", workDayItems);
                System.out.println("Work Daysss 1:"+" "+workDayItems.getDay() + "_morning"+"  "+new Gson().toJson(workDayItems));
            }
            if (!eFrom.get().equals("00:00") && !eTo.get().equals("00:00")) {
                WorkDayItems workDayItems = new WorkDayItems();
                if (checker==0)
                workDayItems.setDay(previous);
                else workDayItems.setDay(current);
                workDayItems.setFrom(eFrom.get());
                workDayItems.setTo(eTo.get());
                workDayItems.setShift("night");
                days.put(workDayItems.getDay() + "_night", workDayItems);
                System.out.println("Work Daysss 2:"+" "+workDayItems.getDay() + "_night"+"  "+new Gson().toJson(workDayItems));
            }


                 reset(checker);

        }
    }

    public void reset(int checker){
        if (days.containsKey(current+"_morning")){
            mFrom.set(days.get(current+"_morning").getFrom());
            mTo.set(days.get(current+"_morning").getTo());
        }else {
            mFrom.set("00:00");
            mTo.set("00:00");
        }

        if (days.containsKey(current+"_night")){
            eFrom.set(days.get(current+"_night").getFrom());
            eTo.set(days.get(current+"_night").getTo());
        }else {
            eFrom.set("00:00");
            eTo.set("00:00");
        }



            for (String name : days.keySet()) {
                System.out.println("Map Daysss :" + name + "  " + new Gson().toJson(days.get(name)));
                  if (checker==1) {
                      workingDays.add(days.get(name));
                      System.out.println("Daysss List :" + workingDays.size());
                  }
            }
        if (checker==1) {
            System.out.println("Days Size :"+workingDays.size());
            if (workingDays.size()==0){
                baseInterface.updateUi(ConfigurationFile.Constants.EMPTY_WORKSHOP_DAYS);
            }else {
                registerRequest.setWorkdays(workingDays);
                baseInterface.updateUi(ConfigurationFile.Constants.MOVE_TO_NEXT_ACTIVITY);

            }
        }
    }

    public void moveToNextAct(View view){
        getObject(1);
    }
    @Override
    public void updateTime(String selectedTime, int code) {
        if(code== ConfigurationFile.Constants.MORNING_FROM)
            mFrom.set(selectedTime);
        else if(code== ConfigurationFile.Constants.MORNING_TO)
            mTo.set(selectedTime);

        else if(code== ConfigurationFile.Constants.EVENING_FROM)
            eFrom.set(selectedTime);

        else if(code== ConfigurationFile.Constants.EVENING_TO)
            eTo.set(selectedTime);

    }

    public void showTimePicker(View view){
        if (view.getId()==R.id.et_morning_from)
            code=ConfigurationFile.Constants.MORNING_FROM;
        else  if (view.getId()==R.id.et_morning_to)
            code=ConfigurationFile.Constants.MORNING_TO;

        else  if (view.getId()==R.id.et_night_from)
            code=ConfigurationFile.Constants.EVENING_FROM;

        else  if (view.getId()==R.id.et_night_to)
            code=ConfigurationFile.Constants.EVENING_TO;
        CustomUtils.getInstance().showTimePickerDialog(context,this,code);
    }

    public void backToPreviousAct(View view){
        baseInterface.updateUi(ConfigurationFile.Constants.MOVE_TO_PREVIOUS_ACTIVITY);
    }
}
