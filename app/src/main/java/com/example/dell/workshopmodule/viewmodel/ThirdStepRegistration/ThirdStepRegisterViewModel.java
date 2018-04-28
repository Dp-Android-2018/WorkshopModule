package com.example.dell.workshopmodule.viewmodel.ThirdStepRegistration;

import android.content.Context;
import android.databinding.BaseObservable;
import android.view.View;

import com.example.dell.workshopmodule.model.request.RegisterRequest;
import com.example.dell.workshopmodule.utils.ConfigurationFile;
import com.example.dell.workshopmodule.view.ui.Application.MyApplication;
import com.example.dell.workshopmodule.view.ui.callback.DisplayDialogNavigator;

/**
 * Created by DELL on 25/03/2018.
 */

public class ThirdStepRegisterViewModel extends BaseObservable {

    private DisplayDialogNavigator navigator;
    private Context context;
    private RegisterRequest registerRequest;
    public ThirdStepRegisterViewModel(Context context, DisplayDialogNavigator navigator, RegisterRequest registerRequest) {
        this.context=context;
        this.navigator=navigator;
        this.registerRequest=registerRequest;
    }

    public void validate(View view){
        if(((MyApplication)(MyApplication.getAppContext())).getWorkingday().size()!=0){
            registerRequest.setWorkdays(((MyApplication)(MyApplication.getAppContext())).getWorkingday());
            navigator.NavigateBetweenActivities(registerRequest,ConfigurationFile.Constants.MOVE_TO_NEXT_ACTIVITY);
        }else {
            navigator.updateUi(ConfigurationFile.Constants.CUSTOM_ERROR_CODE);
        }

    }

    public void backToPreviousAct(View view){
        ((MyApplication)MyApplication.getAppContext()).clearCalendar();
        navigator.NavigateBetweenActivities(null,ConfigurationFile.Constants.MOVE_TO_PREVIOUS_ACTIVITY);
    }





}
