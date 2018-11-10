package com.findandfix.workshop.ui.callback;

import com.findandfix.workshop.model.request.RegisterRequest;

/**
 * Created by DELL on 24/03/2018.
 */

public interface DisplayDialogNavigator extends BaseInterface {

    public void NavigateBetweenActivities(RegisterRequest registerRequest, int code);
}
