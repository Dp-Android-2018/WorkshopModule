package com.findandfix.workshop.model.module;

import com.findandfix.workshop.ui.callback.scopes.AppScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by DELL on 14/05/2018.
 */

@Module
public class CustomClassModule {

    private Class aClass;

    public CustomClassModule(Class aClass) {
        this.aClass = aClass;
    }

    @Provides
    @AppScope
    public Class getaClass() {
        return aClass;
    }
}
