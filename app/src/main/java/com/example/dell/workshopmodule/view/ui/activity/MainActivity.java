package com.example.dell.workshopmodule.view.ui.activity;

import android.animation.Animator;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import com.example.dell.workshopmodule.R;
import com.example.dell.workshopmodule.databinding.ActivityMainBinding;
import com.example.dell.workshopmodule.model.response.UrgentRequestTypeResponse;
import com.example.dell.workshopmodule.utils.ConfigurationFile;
import com.example.dell.workshopmodule.view.ui.callback.BaseInterface;
import com.example.dell.workshopmodule.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity implements BaseInterface {

    ActivityMainBinding binding;
    ////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //////////////////////////////////////////////////////////////////////
            initBinding();
            setUpToolBar();
            handlAnimation();
        ///////////////////////////////////////////////////////////////////
    }

    public void initBinding(){
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main);
        MainViewModel mainViewModel=new MainViewModel(MainActivity.this,this);
        binding.setViewModel(mainViewModel);
    }

    public void setUpToolBar(){
            setSupportActionBar(binding.toolbar.toolbar);
            binding.toolbar.tvToolbarTitle.setText(R.string.find_fix);
            binding.toolbar.toolbar.setBackgroundColor(getResources().getColor(R.color.colorBlueLight));
        if(Build.VERSION.SDK_INT>=21){
            Window window=this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getApplicationContext().getResources().getColor(R.color.colorBlueLight));
        }
    }
    public void handlAnimation(){
        binding.animationView.playAnimation();
        binding.animationView.setMaxProgress(0.3F);

    }

    @Override
    public void updateUi(int code) {
        if(code== ConfigurationFile.Constants.COMPLETE_ANIMATION_CODE)
                completeImage();
        else if(code== ConfigurationFile.Constants.NORMAL_REQUEST_ACTIVITY)
            startActivity(new Intent(MainActivity.this,NormalRequestActivity.class));
        else if(code== ConfigurationFile.Constants.URGENT_REQUEST_ACTIVITY)
            startActivity(new Intent(MainActivity.this,UrgentRequestActivity.class));
        else if(code== ConfigurationFile.Constants.EDIT_PROFILE_ACTIVITY)
            startActivity(new Intent(MainActivity.this,EditProfileInfoActivity.class));
        else if(code== ConfigurationFile.Constants.ADD_ADVERTISEMENT_ACTIVITY)
            startActivity(new Intent(MainActivity.this,AddAdvertisement.class));
    }

    public void completeImage(){
        binding.animationView.playAnimation();
        binding.animationView.setMinProgress(0.3F);
        binding.animationView.setMaxProgress(1.0F);
    }
}
