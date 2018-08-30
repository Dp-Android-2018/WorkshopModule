package com.dp.dell.workshopmodule.view.ui.callback.component;

import com.dp.dell.workshopmodule.model.module.SharedPrefUtilsModule;
import com.dp.dell.workshopmodule.utils.SharedPrefrenceUtils;
import com.dp.dell.workshopmodule.view.ui.callback.scopes.AppScope;

import dagger.Component;

/**
 * Created by DELL on 14/05/2018.
 */

@AppScope
@Component(modules = SharedPrefUtilsModule.class)
public interface SharedprefUtilsComponent {

    SharedPrefrenceUtils getSharedPrefUtils();
}
