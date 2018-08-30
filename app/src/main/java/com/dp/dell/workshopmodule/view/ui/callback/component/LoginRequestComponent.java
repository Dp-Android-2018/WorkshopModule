package com.dp.dell.workshopmodule.view.ui.callback.component;

import com.dp.dell.workshopmodule.model.module.LoginRequestModule;
import com.dp.dell.workshopmodule.model.request.LoginRequest;
import com.dp.dell.workshopmodule.view.ui.callback.scopes.AppScope;

import dagger.Component;

/**
 * Created by DELL on 14/05/2018.
 */


@AppScope
@Component(modules = LoginRequestModule.class)
public interface LoginRequestComponent {

    LoginRequest getLoginRequest();

}
