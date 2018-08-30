package com.dp.dell.workshopmodule.view.ui.callback.component;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;

import com.dp.dell.workshopmodule.model.module.ObservableModule;
import com.dp.dell.workshopmodule.view.ui.callback.scopes.AppScope;

import dagger.Component;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by DELL on 14/05/2018.
 */

@AppScope
@Component(modules = ObservableModule.class)
public interface ObservableComponent {
      ObservableField getObservable();
      ObservableInt getObservableInt();
      CompositeDisposable getComposite();

}
