package com.example.dell.workshopmodule.view.ui.activity;

import android.arch.lifecycle.ViewModel;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.dell.workshopmodule.R;
import com.example.dell.workshopmodule.databinding.ActivitySubscribtionPageLayoutBinding;
import com.example.dell.workshopmodule.viewmodel.SubscribePageViewModel;

/**
 * Created by DELL on 22/04/2018.
 */

public class SubScribtionPageActivity extends AppCompatActivity{
    ActivitySubscribtionPageLayoutBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            initBinding();
    }

    public void initBinding(){
            binding= DataBindingUtil.setContentView(this, R.layout.activity_subscribtion_page_layout);
            SubscribePageViewModel subscribePageViewModel=new SubscribePageViewModel(SubScribtionPageActivity.this);
            binding.setViewmodel(subscribePageViewModel);

    }
}
