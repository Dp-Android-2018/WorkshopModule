package com.dp.dell.workshopmodule.view.ui.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import com.dp.dell.workshopmodule.R;
import com.dp.dell.workshopmodule.databinding.ActivitySplashLayoutBinding;
import com.dp.dell.workshopmodule.utils.ConfigurationFile;
import com.dp.dell.workshopmodule.utils.CustomUtils;
import com.dp.dell.workshopmodule.view.ui.callback.BaseInterface;
import com.dp.dell.workshopmodule.viewmodel.SplashViewModel;

import java.util.Locale;

/**
 * Created by DELL on 04/04/2018.
 */

public class SplashActivity extends AppCompatActivity implements BaseInterface{
    private ActivitySplashLayoutBinding binding;
    private SplashViewModel viewModel;
    private static final String PREFS_NAME = "prefs";
    private static final String PREF_DARK_THEME = "dark_theme";
    public static  String AppLang;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


                     intitBinding();



    }
    /////////Init Layout and Determine View Model Of Activity //////////////////////
    public void intitBinding(){
        changeAppLanguage();
        viewModel=new SplashViewModel(SplashActivity.this,this);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_splash_layout);
        binding.setViewmodel(viewModel);
    }

/* Method To Select App Language At The beginnig Of the App
    and Determine Which Layout will be used */

    public void changeAppLanguage(){
        System.out.println("Lang App :"+CustomUtils.getInstance().getAppLanguage(getApplicationContext()));
        String lang=(CustomUtils.getInstance().getAppLanguage(getApplicationContext())!=null ? CustomUtils.getInstance().getAppLanguage(getApplicationContext()) : "en");
        Locale locale = new Locale(lang);
        CustomUtils.getInstance().saveAppLanguage(getApplicationContext(),lang);
        AppLang=CustomUtils.getInstance().getAppLanguage(getApplicationContext());
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }

    /* Get Callback From View Model and Update Ui */
    @Override
    public void updateUi(int code) {
            if(code== ConfigurationFile.Constants.MOVE_TO_MAIN_ACT)
                    moveToNextAct(0);
            else if(code== ConfigurationFile.Constants.MOVE_TO_LOGIN_ACT)
                    moveToNextAct(1);

            else if(code== ConfigurationFile.Constants.CANT_COMPLETE_REQUEST_CODE)
                Snackbar.make(binding.rlLayout, R.string.cant_complete_your_request,Snackbar.LENGTH_LONG).show();
    }
/* Move To Main Act If User Logged and Move To Login Act If he Isn't logged before */
    public void moveToNextAct(int code){
        Intent i=null;
        if(code==0)
            i=new Intent(getApplicationContext(),MainActivity.class);
        else if(code==1)
            i=new Intent(getApplicationContext(),LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finishAffinity();
    }



}
