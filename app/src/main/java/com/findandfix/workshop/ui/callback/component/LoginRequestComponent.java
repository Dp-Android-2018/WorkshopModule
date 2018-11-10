package com.findandfix.workshop.ui.callback.component;

import com.findandfix.workshop.model.module.LoginRequestModule;
import com.findandfix.workshop.model.request.LoginRequest;
import com.findandfix.workshop.ui.callback.scopes.AppScope;

import dagger.Component;

/**
 * Created by DELL on 14/05/2018.
 */


@AppScope
@Component(modules = LoginRequestModule.class)
public interface LoginRequestComponent {

    LoginRequest getLoginRequest();

}
