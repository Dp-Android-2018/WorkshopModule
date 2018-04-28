package com.example.dell.workshopmodule.view.ui.callback;

import com.example.dell.workshopmodule.model.request.RegisterRequest;

import retrofit2.http.PUT;

/**
 * Created by DELL on 24/03/2018.
 */

public interface DisplayDialogNavigator extends BaseInterface{

    public void NavigateBetweenActivities(RegisterRequest registerRequest,int code);
}
