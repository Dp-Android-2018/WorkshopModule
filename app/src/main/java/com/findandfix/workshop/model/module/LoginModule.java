package com.findandfix.workshop.model.module;

import android.content.Context;

import com.findandfix.workshop.ui.callback.BaseInterface;
import com.findandfix.workshop.ui.callback.scopes.AppScope;
import com.findandfix.workshop.viewmodel.LoginViewModel;

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
