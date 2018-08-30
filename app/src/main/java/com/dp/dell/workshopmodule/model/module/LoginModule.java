package com.dp.dell.workshopmodule.model.module;

import android.content.Context;

import com.dp.dell.workshopmodule.view.ui.callback.BaseInterface;
import com.dp.dell.workshopmodule.view.ui.callback.scopes.AppScope;
import com.dp.dell.workshopmodule.viewmodel.LoginViewModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by DELL on 07/05/2018.
 */

@Module(includes = {ContextModule.class,BaseCallbackModule.class})
public class LoginModule {


    @Provides
    @AppScope
    public LoginViewModel createLoginModel(Context context, BaseInterface callback){
        return null;
    }
}
