package com.dp.dell.workshopmodule.view.ui.callback.component;

import android.content.Intent;


import com.dp.dell.workshopmodule.model.module.IntentModule;
import com.dp.dell.workshopmodule.view.ui.callback.scopes.AppScope;

import dagger.Component;

/**
 * Created by DELL on 14/05/2018.
 */
@AppScope
@Component(modules = IntentModule.class)
public interface IntentComponent {

    Intent createIntent();
}
