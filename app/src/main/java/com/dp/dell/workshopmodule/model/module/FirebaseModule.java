package com.dp.dell.workshopmodule.model.module;

import com.dp.dell.workshopmodule.notification.MyFirebaseInstanceIdService;
import com.dp.dell.workshopmodule.view.ui.callback.scopes.AppScope;

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
