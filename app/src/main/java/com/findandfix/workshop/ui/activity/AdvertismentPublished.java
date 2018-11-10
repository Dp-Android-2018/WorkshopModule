package com.findandfix.workshop.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.findandfix.workshop.R;
import com.findandfix.workshop.databinding.ActivityAdvertismentPublishedBinding;
import com.findandfix.workshop.utils.ConfigurationFile;
import com.findandfix.workshop.viewmodel.ToolbarViewModel;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by DELL on 13/03/2018.
 */

public class AdvertismentPublished extends BaseAdvertisementAct {
    private ActivityAdvertismentPublishedBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().post(1);
        initBinding();
        handleToolbar();
    }

    ///////////////////Regiser & UnRegister Event Bus To Finish all Activities /////////////////////////////////////
    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
    /////////////////////////////Init Layout Of Activity And Ask User If he wants to Add ANOTHER ADV/////////////////
    public void initBinding(){
        binding= DataBindingUtil.setContentView(AdvertismentPublished.this,R.layout.activity_advertisment_published);
        binding.btnAddAdv.setOnClickListener(v -> {
            Intent i=new Intent(getApplicationContext(),AddAdvertisement.class);
            startActivity(i);
        });
    }
    /////////////////Handle Toolbar ColOR AND DEFINE IT'S PROPERTIES /////////////////////////////////////////////////
    public void handleToolbar(){
        setSupportActionBar( binding.toolbar.toolbar);
        binding.toolbar.setViewmodel(new ToolbarViewModel(AdvertismentPublished.this, ConfigurationFile.Constants.HANDLE_CUSTOM_ADD_ADVERTISING_TOOLBAR));
    }
    /////////////////Handle On Back Pressed /////////////////////////////////////////////////
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(AdvertismentPublished.this,MainActivity.class);
        startActivity(i);
        finish();
    }
}
