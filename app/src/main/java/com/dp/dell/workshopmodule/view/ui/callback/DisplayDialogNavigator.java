package com.dp.dell.workshopmodule.view.ui.callback;

import com.dp.dell.workshopmodule.model.request.RegisterRequest;

/**
 * Created by DELL on 24/03/2018.
 */

public interface DisplayDialogNavigator extends BaseInterface{

    public void NavigateBetweenActivities(RegisterRequest registerRequest,int code);
}
