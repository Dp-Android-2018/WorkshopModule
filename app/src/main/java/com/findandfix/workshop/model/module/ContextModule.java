package com.findandfix.workshop.model.module;

import android.content.Context;

import com.findandfix.workshop.ui.callback.scopes.AppScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by DELL on 07/05/2018.
 */
@Module
public class ContextModule {

    private Context context;
    public ContextModule(Context context) {
        this.context=context;
    }

    @Provides
    @AppScope
    public Context getContext(){
        return context;
    }
}
