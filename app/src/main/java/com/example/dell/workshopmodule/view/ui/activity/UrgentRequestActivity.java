package com.example.dell.workshopmodule.view.ui.activity;

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
import com.example.dell.workshopmodule.databinding.ActivityUrgentRequestLayoutBinding;
import com.example.dell.workshopmodule.databinding.TabNormalRequestsLayoutBinding;
import com.example.dell.workshopmodule.databinding.TabUrgentRequestLayoutBinding;
import com.example.dell.workshopmodule.view.ui.adapter.ViewPagerAdapter;
import com.example.dell.workshopmodule.view.ui.fragment.CompletedRequestsFragment;
import com.example.dell.workshopmodule.view.ui.fragment.CompletedUrgentRequestFragment;
import com.example.dell.workshopmodule.view.ui.fragment.HappeningNowFragment;
import com.example.dell.workshopmodule.view.ui.fragment.HappenningNowUrgentRequestsFragment;
import com.example.dell.workshopmodule.view.ui.fragment.InProgressFragment;
import com.example.dell.workshopmodule.view.ui.fragment.InProgressUrgentRequestFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by DELL on 13/03/2018.
 */

public class UrgentRequestActivity extends AppCompatActivity{

    private ActivityUrgentRequestLayoutBinding binding;
    private TabUrgentRequestLayoutBinding bindingTab1;
    private TabUrgentRequestLayoutBinding bindingTab2;
    private TabUrgentRequestLayoutBinding bindingTab3;
    private ViewPagerAdapter viewPagerAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initBinding();
        setUpActionBar();
        initializeTabs();
    }

    public void initBinding(){
        binding= DataBindingUtil.setContentView(UrgentRequestActivity.this,R.layout.activity_urgent_request_layout);
        bindingTab1=DataBindingUtil.inflate(getLayoutInflater(),R.layout.tab_urgent_request_layout,null,false);
        bindingTab2=DataBindingUtil.inflate(getLayoutInflater(),R.layout.tab_urgent_request_layout,null,false);
        bindingTab3=DataBindingUtil.inflate(getLayoutInflater(),R.layout.tab_urgent_request_layout,null,false);
    }

    public void setUpActionBar(){

        setSupportActionBar( binding.toolbar.toolbar);
        binding.toolbar.tvToolbarTitle.setText(R.string.uregnt_request);
        binding.toolbar.toolbar.setBackgroundColor(getResources().getColor(R.color.redColor));
        if(Build.VERSION.SDK_INT>=21){
            Window window=this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getApplicationContext().getResources().getColor(R.color.redColor));
        }
    }

    public void initializeTabs(){
        viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager(),true);
        setUpFragments();
        binding.viewpagerUrgentRequests.setAdapter(viewPagerAdapter);
        binding.tabUrgentRequest.setupWithViewPager(binding.viewpagerUrgentRequests);

        bindingTab1.tvTab1.setText(R.string.happening_now);
        bindingTab2.tvTab1.setText(R.string.in_progress);
        bindingTab3.tvTab1.setText(R.string.done);

        binding.tabUrgentRequest.getTabAt(0).setCustomView(bindingTab1.ll1);
        binding.tabUrgentRequest.getTabAt(1).setCustomView(bindingTab2.ll1);
        binding.tabUrgentRequest.getTabAt(2).setCustomView(bindingTab3.ll1);
    }


    public void setUpFragments( ){
        viewPagerAdapter.addFragment(new CompletedUrgentRequestFragment(),"");
        viewPagerAdapter.addFragment(new InProgressUrgentRequestFragment(),"");
        viewPagerAdapter.addFragment(new HappenningNowUrgentRequestsFragment(),"");
    }

}
