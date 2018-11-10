package com.findandfix.workshop.viewmodel.ThirdStepRegistration;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.BaseObservable;
import android.view.View;

import com.findandfix.workshop.model.request.RegisterRequest;
import com.findandfix.workshop.ui.Application.MyApplication;
import com.findandfix.workshop.ui.callback.DisplayDialogNavigator;
import com.findandfix.workshop.utils.ConfigurationFile;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by DELL on 25/03/2018.
 */

public class ThirdStepRegisterViewModel extends BaseObservable {

    private DisplayDialogNavigator navigator;
    private Activity activity;
    private RegisterRequest registerRequest;
    private BroadcastReceiver receiver;
    public ThirdStepRegisterViewModel(Activity activity, DisplayDialogNavigator navigator, RegisterRequest registerRequest) {
        this.activity=activity;
        this.navigator=navigator;
        this.registerRequest=registerRequest;
        handleBrodcast();
    }

    public void onResume() {
        EventBus.getDefault().register(activity);
    }

    public void validate(View view){
        if(((MyApplication)(MyApplication.getAppContext())).getWorkingday().size()!=0){
          //  registerRequest.setWorkdays(((MyApplication)(MyApplication.getAppContext())).getWorkingday());
            navigator.NavigateBetweenActivities(registerRequest,ConfigurationFile.Constants.MOVE_TO_NEXT_ACTIVITY);
        }else {
            navigator.updateUi(ConfigurationFile.Constants.CUSTOM_ERROR_CODE);
        }

    }

    public void backToPreviousAct(View view){
        ((MyApplication)MyApplication.getAppContext()).clearCalendar();
        navigator.NavigateBetweenActivities(null,ConfigurationFile.Constants.MOVE_TO_PREVIOUS_ACTIVITY);
    }

    public void onPause() {
        EventBus.getDefault().unregister(activity);
        unRegisterBroadcast();
    }

    public void handleBrodcast(){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.package.ACTION_LOGOUT");
        receiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                activity.finish();
            }
        };
        activity.registerReceiver(receiver, intentFilter);
    }

    public void unRegisterBroadcast(){
        if (receiver != null) {
            activity.unregisterReceiver(receiver);
            receiver = null;
        }
    }





}
