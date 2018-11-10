package com.findandfix.workshop.ui.callback.component;

import com.findandfix.workshop.model.module.LoginModule;
import com.findandfix.workshop.ui.activity.LoginActivity;
import com.findandfix.workshop.ui.callback.scopes.AppScope;

import dagger.Component;

/**
 * Created by DELL on 07/05/2018.
 */
@AppScope
@Component(modules = LoginModule.class)
public interface LoginComponent {
    void inject(LoginActivity loginActivity);
}
