package com.findandfix.workshop.ui.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.findandfix.workshop.R;
import com.findandfix.workshop.databinding.ActivityLoginLayoutBinding;
import com.findandfix.workshop.ui.Application.MyApplication;
import com.findandfix.workshop.ui.callback.BaseInterface;
import com.findandfix.workshop.utils.ConfigurationFile;
import com.findandfix.workshop.utils.SharedPrefrenceUtils;
import com.findandfix.workshop.viewmodel.LoginViewModel;

import java.util.Observable;
import java.util.Observer;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by DELL on 10/03/2018.
 */

public class LoginActivity extends BaseActivity implements Observer,BaseInterface {

    private ActivityLoginLayoutBinding activityLoginBinding;
    Dialog dialog=null;
    public static boolean active=false;

     LoginViewModel loginViewModel;
    private SharedPrefrenceUtils pref;
    public static int SETTINGS_ACTION = 1;
    @Override
    protected void attachBaseContext(Context newBase) {

        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        if (SETTINGS_ACTION==1){

            setTheme(R.style.AppTheme_Dark_NoActionBar);
            SETTINGS_ACTION=0;

        }
        super.onCreate(savedInstanceState);
        loginViewModel=new LoginViewModel(LoginActivity.this, LoginActivity.this, LoginActivity.this);
        active=true;
      //  MyApplication.getLoginComponent(this).inject(this);
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

    /////////////////Updating Ui According To Code Of View Model /////////////////////////////////////////////////

    @Override
    public void updateUi(int code) {
        System.out.println("Code Code Code :"+code);
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
        else if(code==ConfigurationFile.Constants.ACCOUNT_ACTIVATED_SUCCESSFULLY)
            Snackbar.make(activityLoginBinding.rlParent, R.string.account_activated_successfully,Snackbar.LENGTH_LONG).show();

        else if(code==ConfigurationFile.Constants.UNSUBSCRIBE_CODE)
           setDialog();
    }

    /////////////////////////Display Dialog If Account Suspended ////////////////////////////////////////////

    private void setDialog(){

        dialog = new Dialog(LoginActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        LayoutInflater factory = LayoutInflater.from(getApplicationContext());
        final View dialogView = factory.inflate(R.layout.dialog_suspended_layout, null);
        Button btnCancel = dialogView.findViewById(R.id.btn_cancel);

        btnCancel.setOnClickListener(v -> dialog.cancel());

        dialog.setContentView(dialogView);
        dialog.show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginViewModel.reset();
    }

    ///////////Inflating Ui + moving to ForgetPasswordMobileActivity if user forget password /////////////////////////
    public void initBinding(){
        activityLoginBinding=DataBindingUtil.setContentView(LoginActivity.this,R.layout.activity_login_layout);
        activityLoginBinding.tvLoginForgotPassword.setOnClickListener(v -> {
            Intent i=new Intent(LoginActivity.this,ForgetPasswordMobileActivity.class);
            startActivity(i);
        });
        activityLoginBinding.setLoginViewModel(loginViewModel);
    }

    public void subscribe(){
        loginViewModel.addObserver(this);
    }

    //////////////////Make an Intent To another Activity ////////////////////////////////////////
    public void moveToNextActivity(int checker){



        Intent i=null;
        if(checker==0) {
                i = MyApplication.getIntentComponent(MainActivity.class).createIntent();

        }else if(checker==1)
            i=MyApplication.getIntentComponent(FirstStepRegisterActivity.class).createIntent();
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finishAffinity();
    }

    @Override
    protected void onPause() {
        super.onPause();
        active=false;
    }
}
