package com.findandfix.workshop.model.module;

import android.content.Context;
import android.content.Intent;

import com.findandfix.workshop.ui.callback.scopes.AppScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by DELL on 14/05/2018.
 */

@Module(includes = {ContextModule.class,CustomClassModule.class})
public class IntentModule {

    @Provides
    @AppScope
    public Intent createIntent(Context context,Class c){
        return new Intent(context,c);
    }
}
