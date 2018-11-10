package com.findandfix.workshop.model.module;

import com.findandfix.workshop.ui.callback.BaseInterface;
import com.findandfix.workshop.ui.callback.scopes.AppScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by DELL on 07/05/2018.
 */

@Module
public class BaseCallbackModule {

    private BaseInterface callback;
    public BaseCallbackModule(BaseInterface callback) {
        this.callback=callback;
    }

    @Provides
    @AppScope
    public BaseInterface getCallback() {
        return callback;
    }
}
