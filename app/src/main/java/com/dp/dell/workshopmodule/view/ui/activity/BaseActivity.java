package com.dp.dell.workshopmodule.view.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.dp.dell.workshopmodule.model.ConnectionReceiver;
import com.dp.dell.workshopmodule.utils.CustomUtils;
import com.dp.dell.workshopmodule.view.ui.Application.MyApplication;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by DELL on 20/05/2018.
 */

public class BaseActivity extends AppCompatActivity implements ConnectionReceiver.ConnectionReceiverListener{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* boolean useDarkTheme= CustomUtils.getInstance().getDarkModeVal(getApplicationContext());
        if(useDarkTheme) {
            setTheme(R.style.AppTheme_Dark_NoActionBar);
        }*/
       /*Our Base Activity Which All Activities Extend From It To Chage Font Style If it's Arabic and Handle If Internent Connection Gone
       oR Back*/
        String lang=(CustomUtils.getInstance().getAppLanguage(getApplicationContext())!=null ? CustomUtils.getInstance().getAppLanguage(getApplicationContext()) : "en");
        CustomUtils.getInstance().saveAppLanguage(getApplicationContext(),lang);
        checkConnection();
    }

    @Override
    protected void attachBaseContext(Context newBase) {

        if (SplashActivity.AppLang.equals("ar"))
            super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
        else super.attachBaseContext(newBase);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if(!isConnected) {

            //show a No Internet Alert or Dialog
            Intent I=new Intent(BaseActivity.this,Testing.class);
            startActivity(I);
            finish();
            finishAffinity();

        }else{

            Intent I=new Intent(BaseActivity.this,MainActivity.class);
            startActivity(I);
            finish();
            finishAffinity();
            // dismiss the dialog or refresh the activity
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register connection status listener
        MyApplication.getInstance().setConnectionListener(this);
    }


    private void checkConnection() {
        boolean isConnected = ConnectionReceiver.isConnected();
        if(!isConnected) {
            //show a No Internet Alert or Dialog
            Intent I=new Intent(BaseActivity.this,Testing.class);
            startActivity(I);
            finish();
            finishAffinity();
        }
    }
}
