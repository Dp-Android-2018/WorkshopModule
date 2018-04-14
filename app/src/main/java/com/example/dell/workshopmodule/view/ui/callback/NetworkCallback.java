package com.example.dell.workshopmodule.view.ui.callback;

import com.example.dell.workshopmodule.model.response.CountryResponse;

import java.util.List;

/**
 * Created by DELL on 05/04/2018.
 */

public interface NetworkCallback {

    public <E>void  onSuccess(Object data,int code);

    public void  onFailure(Throwable throwable);

}
