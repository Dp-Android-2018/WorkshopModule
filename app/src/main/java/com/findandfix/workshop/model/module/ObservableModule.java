package com.findandfix.workshop.model.module;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.view.View;

import com.findandfix.workshop.ui.callback.scopes.AppScope;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by DELL on 14/05/2018.
 */

@Module
public class ObservableModule {

    @Provides
    @AppScope
    public ObservableField getObservableString(){
        return new ObservableField();
    }

    @Provides
    @AppScope
    public ObservableInt getObservableInt(){
        return new ObservableInt(View.GONE);
    }


    @Provides
    @AppScope
    public CompositeDisposable getCompositeDisposable(){
        return new CompositeDisposable();
    }
}
