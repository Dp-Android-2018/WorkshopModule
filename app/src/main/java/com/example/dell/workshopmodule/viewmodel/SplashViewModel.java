package com.example.dell.workshopmodule.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.os.Handler;

import com.example.dell.workshopmodule.utils.ConfigurationFile;
import com.example.dell.workshopmodule.utils.CustomUtils;
import com.example.dell.workshopmodule.view.ui.callback.BaseInterface;


/**
 * Created by DELL on 04/04/2018.
 */

public class SplashViewModel extends BaseObservable {

    private Context context;
    private BaseInterface callback;
    public SplashViewModel(Context context, BaseInterface callback) {
            this.context=context;
            this.callback=callback;
            delay();
    }

    public void delay(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                    moveTonextActivity();
            }
        }, ConfigurationFile.Constants.SPLASH_TIME_OUT);
    }

    public void moveTonextActivity(){
        if(CustomUtils.getInstance().getSaveUserObject(context)!=null){
            callback.updateUi(ConfigurationFile.Constants.MOVE_TO_MAIN_ACT);
        }else {
                callback.updateUi(ConfigurationFile.Constants.MOVE_TO_LOGIN_ACT);
        }
    }
}
