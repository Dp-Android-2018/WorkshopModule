package com.example.dell.workshopmodule.view.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.dell.workshopmodule.R;
import com.example.dell.workshopmodule.databinding.ActivitySplashLayoutBinding;
import com.example.dell.workshopmodule.utils.ConfigurationFile;
import com.example.dell.workshopmodule.view.ui.callback.BaseInterface;
import com.example.dell.workshopmodule.viewmodel.SplashViewModel;

/**
 * Created by DELL on 04/04/2018.
 */

public class SplashActivity extends AppCompatActivity implements BaseInterface{
    private ActivitySplashLayoutBinding binding;
    private SplashViewModel viewModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intitBinding();
    }

    public void intitBinding(){
        viewModel=new SplashViewModel(getApplicationContext(),this);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_splash_layout);
        binding.setViewmodel(viewModel);
    }

    @Override
    public void updateUi(int code) {
            if(code== ConfigurationFile.Constants.MOVE_TO_MAIN_ACT)
                    moveToNextAct(0);
            else if(code== ConfigurationFile.Constants.MOVE_TO_LOGIN_ACT)
                    moveToNextAct(1);

    }

    public void moveToNextAct(int code){
        Intent i=null;
        if(code==0)
            i=new Intent(getApplicationContext(),MainActivity.class);
        else if(code==1)
            i=new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(i);
            finish();
    }
}
