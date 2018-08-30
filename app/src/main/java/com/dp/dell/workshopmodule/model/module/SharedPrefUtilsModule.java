package com.dp.dell.workshopmodule.model.module;

import android.content.Context;

import com.dp.dell.workshopmodule.utils.SharedPrefrenceUtils;
import com.dp.dell.workshopmodule.view.ui.callback.scopes.AppScope;

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
