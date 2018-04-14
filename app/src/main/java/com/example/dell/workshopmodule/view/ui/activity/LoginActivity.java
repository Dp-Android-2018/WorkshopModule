package com.example.dell.workshopmodule.view.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.dell.workshopmodule.R;
import com.example.dell.workshopmodule.databinding.ActivityLoginLayoutBinding;
import com.example.dell.workshopmodule.utils.ConfigurationFile;
import com.example.dell.workshopmodule.utils.SharedPrefrenceUtils;
import com.example.dell.workshopmodule.view.ui.callback.BaseInterface;
import com.example.dell.workshopmodule.viewmodel.LoginViewModel;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by DELL on 10/03/2018.
 */

public class LoginActivity extends AppCompatActivity implements Observer,BaseInterface{

    private ActivityLoginLayoutBinding activityLoginBinding;
    private LoginViewModel loginViewModel;
    private SharedPrefrenceUtils pref;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        initBinding();
        subscribe();

    }


    @Override
    public void update(Observable o, Object arg) {
            if(o instanceof LoginViewModel) {

                moveToNextActivity(0);
            }
    }

    @Override
    public void updateUi(int code) {
        if(code==ConfigurationFile.Constants.FILL_ALL_DATTA)
            Snackbar.make(activityLoginBinding.rlParent, R.string.fill_data,Snackbar.LENGTH_LONG).show();
        else if(code==ConfigurationFile.Constants.INVALID_USERNAME_PASSWORD_LOGIN_CODE)
            Snackbar.make(activityLoginBinding.rlParent, R.string.invalid_email_password,Snackbar.LENGTH_LONG).show();
        else if(code==ConfigurationFile.Constants.INVALID_DATA)
            Snackbar.make(activityLoginBinding.rlParent, R.string.invalid_data,Snackbar.LENGTH_LONG).show();

        else if(code==ConfigurationFile.Constants.MOVE_TO_REGISTER_ACTIVITY)
            moveToNextActivity(1);

        else if(code==ConfigurationFile.Constants.NO_INTERNET_CONNECTION_CODE)
            Snackbar.make(activityLoginBinding.rlParent, R.string.internet_connection,Snackbar.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginViewModel.reset();
    }

    public void initBinding(){
        loginViewModel=new LoginViewModel(getApplicationContext(),this);
        activityLoginBinding=DataBindingUtil.setContentView(LoginActivity.this,R.layout.activity_login_layout);
        activityLoginBinding.setLoginViewModel(loginViewModel);
    }

    public void subscribe(){
        loginViewModel.addObserver(this);
    }

    public void moveToNextActivity(int checker){
        Intent i=null;
        if(checker==0)
              i=new Intent(this,MainActivity.class);
        else
            i=new Intent(this,FirstStepRegisterActivity.class);

        startActivity(i);
        finish();
    }




}
