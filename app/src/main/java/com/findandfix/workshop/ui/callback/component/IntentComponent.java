package com.findandfix.workshop.ui.callback.component;

import android.content.Intent;

import com.findandfix.workshop.model.module.IntentModule;
import com.findandfix.workshop.ui.callback.scopes.AppScope;

import dagger.Component;

/**
 * Created by DELL on 14/05/2018.
 */
@AppScope
@Component(modules = IntentModule.class)
public interface IntentComponent {

    Intent createIntent();
}
