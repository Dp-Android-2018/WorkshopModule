package com.example.dell.workshopmodule.view.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dell.workshopmodule.R;
import com.example.dell.workshopmodule.databinding.ActivityEditProfileLayoutBinding;
import com.example.dell.workshopmodule.databinding.ActivityNormalRequestsLayoutBinding;
import com.example.dell.workshopmodule.databinding.TabEditProfileLayoutBinding;
import com.example.dell.workshopmodule.databinding.TabNormalRequestsLayoutBinding;
import com.example.dell.workshopmodule.view.ui.adapter.ViewPagerAdapter;
import com.example.dell.workshopmodule.view.ui.fragment.CompletedRequestsFragment;
import com.example.dell.workshopmodule.view.ui.fragment.EditProfileInfoFragment;
import com.example.dell.workshopmodule.view.ui.fragment.EditProfileSpecializationFragment;
import com.example.dell.workshopmodule.view.ui.fragment.HappeningNowFragment;
import com.example.dell.workshopmodule.view.ui.fragment.InProgressFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by DELL on 14/03/2018.
 */

public class NormalRequestActivity extends AppCompatActivity {

    private ActivityNormalRequestsLayoutBinding binding;
    private TabNormalRequestsLayoutBinding bindingTab1;
    private TabNormalRequestsLayoutBinding bindingTab2;
    private TabNormalRequestsLayoutBinding bindingTab3;
    private  ViewPagerAdapter viewPagerAdapter;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initBinding();
        setUpActionBar();
        initializeTabs();
    }


    public void initBinding(){
        binding= DataBindingUtil.setContentView(NormalRequestActivity.this,R.layout.activity_normal_requests_layout);
        bindingTab1=DataBindingUtil.inflate(getLayoutInflater(),R.layout.tab_normal_requests_layout,null,false);
        bindingTab2=DataBindingUtil.inflate(getLayoutInflater(),R.layout.tab_normal_requests_layout,null,false);
        bindingTab3=DataBindingUtil.inflate(getLayoutInflater(),R.layout.tab_normal_requests_layout,null,false);
    }

    public void setUpActionBar(){

        setSupportActionBar( binding.toolbar.toolbar);
        binding.toolbar.tvToolbarTitle.setText(R.string.normal_request);
        binding.toolbar.toolbar.setBackgroundColor(getResources().getColor(R.color.bluecolor));
        if(Build.VERSION.SDK_INT>=21){
            Window window=this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getApplicationContext().getResources().getColor(R.color.bluecolor));
        }
    }

    public void initializeTabs(){
        viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager(),true);
        setUpFragments();
        binding.viewpagerNormalRequests.setAdapter(viewPagerAdapter);
        binding.tabNormalRequest.setupWithViewPager(binding.viewpagerNormalRequests);

        bindingTab1.tvTab1.setText(R.string.happening_now);
        bindingTab2.tvTab1.setText(R.string.in_progress);
        bindingTab3.tvTab1.setText(R.string.done);

        binding.tabNormalRequest.getTabAt(0).setCustomView(bindingTab1.ll1);
        binding.tabNormalRequest.getTabAt(1).setCustomView(bindingTab2.ll1);
        binding.tabNormalRequest.getTabAt(2).setCustomView(bindingTab3.ll1);
    }


    public void setUpFragments( ){
        viewPagerAdapter.addFragment(new CompletedRequestsFragment(),"");
        viewPagerAdapter.addFragment(new InProgressFragment(),"");
        viewPagerAdapter.addFragment(new HappeningNowFragment(),"");
    }

}

