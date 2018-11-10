package com.findandfix.workshop.ui.callback.component;

import com.findandfix.workshop.model.module.FirebaseModule;
import com.findandfix.workshop.notification.MyFirebaseInstanceIdService;
import com.findandfix.workshop.ui.callback.scopes.AppScope;

import dagger.Component;

/**
 * Created by DELL on 14/05/2018.
 */

@AppScope
@Component(modules = FirebaseModule.class)
public interface FirebaseComponent {
    MyFirebaseInstanceIdService getFirebaseService();
}
