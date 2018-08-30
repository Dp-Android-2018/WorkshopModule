package com.dp.dell.workshopmodule.view.ui.callback.component;

import com.dp.dell.workshopmodule.model.module.LoginModule;
import com.dp.dell.workshopmodule.view.ui.activity.LoginActivity;
import com.dp.dell.workshopmodule.view.ui.callback.scopes.AppScope;

import dagger.Component;

/**
 * Created by DELL on 07/05/2018.
 */
@AppScope
@Component(modules = LoginModule.class)
public interface LoginComponent {
    void inject(LoginActivity loginActivity);
}
