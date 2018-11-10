package com.findandfix.workshop.ui.callback.component;

import com.findandfix.workshop.model.module.SharedPrefUtilsModule;
import com.findandfix.workshop.ui.callback.scopes.AppScope;
import com.findandfix.workshop.utils.SharedPrefrenceUtils;

import dagger.Component;

/**
 * Created by DELL on 14/05/2018.
 */

@AppScope
@Component(modules = SharedPrefUtilsModule.class)
public interface SharedprefUtilsComponent {

    SharedPrefrenceUtils getSharedPrefUtils();
}
