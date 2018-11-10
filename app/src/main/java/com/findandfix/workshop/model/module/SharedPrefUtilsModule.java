package com.findandfix.workshop.model.module;

import android.content.Context;

import com.findandfix.workshop.ui.callback.scopes.AppScope;
import com.findandfix.workshop.utils.SharedPrefrenceUtils;

import dagger.Module;
import dagger.Provides;

/**
 * Created by DELL on 14/05/2018.
 */

@Module(includes = ContextModule.class)
public class SharedPrefUtilsModule {

    @Provides
    @AppScope
    public SharedPrefrenceUtils getSharedPrefUtils(Context context){
        return new SharedPrefrenceUtils(context);
    }
}
