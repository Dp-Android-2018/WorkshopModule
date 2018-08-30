package com.dp.dell.workshopmodule.view.ui.callback.component;

import com.dp.dell.workshopmodule.model.module.FirebaseModule;
import com.dp.dell.workshopmodule.notification.MyFirebaseInstanceIdService;
import com.dp.dell.workshopmodule.view.ui.callback.scopes.AppScope;

import dagger.Component;

/**
 * Created by DELL on 14/05/2018.
 */

@AppScope
@Component(modules = FirebaseModule.class)
public interface FirebaseComponent {
    MyFirebaseInstanceIdService getFirebaseService();
}
