package com.findandfix.workshop.model.module;

import com.findandfix.workshop.notification.MyFirebaseInstanceIdService;
import com.findandfix.workshop.ui.callback.scopes.AppScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by DELL on 14/05/2018.
 */

@Module
public class FirebaseModule {

    @Provides
    @AppScope
    public MyFirebaseInstanceIdService getFirebaseService(){
        return new MyFirebaseInstanceIdService();
    }
}
