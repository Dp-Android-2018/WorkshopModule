package com.findandfix.workshop.ui.activity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.findandfix.workshop.R;
import com.findandfix.workshop.databinding.ActivityNormalRequestsLayoutBinding;
import com.findandfix.workshop.databinding.TabNormalRequestsLayoutBinding;
import com.findandfix.workshop.ui.adapter.ViewPagerAdapter;
import com.findandfix.workshop.ui.fragment.CompletedRequestsFragment;
import com.findandfix.workshop.ui.fragment.HappeningNowFragment;
import com.findandfix.workshop.ui.fragment.InProgressFragment;
import com.findandfix.workshop.utils.ConfigurationFile;
import com.findandfix.workshop.viewmodel.ToolbarViewModel;

/**
 * Created by DELL on 14/03/2018.
 */

public class NormalRequestActivity extends BaseActivity {

    private ActivityNormalRequestsLayoutBinding binding;
    private TabNormalRequestsLayoutBinding bindingTab1;
    private TabNormalRequestsLayoutBinding bindingTab2;
    private TabNormalRequestsLayoutBinding bindingTab3;
    private ViewPagerAdapter viewPagerAdapter;
    BroadcastReceiver broadcastReceiver=null;
    public static boolean active=false;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initBinding();
        setUpActionBar();
        initializeTabs();
        registerReciever();
    }

    @Override
    protected void onStart() {
        super.onStart();
        active=true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        active=false;
    }
//////////////////////Handle Update View When Notification Fired //////////////////////////////////////////////////////
    public void registerReciever(){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("CLOSE_ALL");
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
            //  binding.viewpagerNormalRequests.setCurrentItem(1);
                binding.viewpagerNormalRequests.setAdapter(null);
                initializeTabs();
                binding.viewpagerNormalRequests.setCurrentItem(1);
            }
        };
        registerReceiver(broadcastReceiver, intentFilter);
    }


    /////////Init Layout and Determine View Model Of Activity //////////////////////
    public void initBinding(){
        binding= DataBindingUtil.setContentView(NormalRequestActivity.this,R.layout.activity_normal_requests_layout);
        bindingTab1=DataBindingUtil.inflate(getLayoutInflater(),R.layout.tab_normal_requests_layout,null,false);
        bindingTab2=DataBindingUtil.inflate(getLayoutInflater(),R.layout.tab_normal_requests_layout,null,false);
        bindingTab3=DataBindingUtil.inflate(getLayoutInflater(),R.layout.tab_normal_requests_layout,null,false);
    }

    /////////////////// // Method To Determine Toolbar Color and Visibility Of it's Item //
    public void setUpActionBar(){
        setSupportActionBar( binding.toolbar.toolbar);
        binding.toolbar.setViewmodel(new ToolbarViewModel(NormalRequestActivity.this, ConfigurationFile.Constants.HANDLE_NORMAL_REQUEST_TOOLBAR));
    }
//////////////////////////Declaring TABS OF THE ACTIVITY ///////////////////////////////////////////////////////////////////
    public void initializeTabs(){
        viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager(),true);
        setUpFragments();
        binding.viewpagerNormalRequests.setAdapter(viewPagerAdapter);
        binding.tabNormalRequest.setupWithViewPager(binding.viewpagerNormalRequests);

        if (getIntent()!=null){
            if (getIntent().getIntExtra(ConfigurationFile.IntentConstants.OFFER_ID,0)!=0)
                binding.viewpagerNormalRequests.setCurrentItem(1);
        }
        bindingTab1.tvTab1.setText(R.string.happening_now);
        bindingTab2.tvTab1.setText(R.string.in_progress);
        bindingTab3.tvTab1.setText(R.string.done);

        binding.tabNormalRequest.getTabAt(0).setCustomView(bindingTab1.ll1);
        binding.tabNormalRequest.getTabAt(1).setCustomView(bindingTab2.ll1);
        binding.tabNormalRequest.getTabAt(2).setCustomView(bindingTab3.ll1);

        binding.viewpagerNormalRequests.setEnabled(false);
    }

/////////////////////////Declaring Fragments Of Tabs //////////////////////////////////////////////////
    public void setUpFragments(){
        viewPagerAdapter.addFragment(new CompletedRequestsFragment(),"");
        viewPagerAdapter.addFragment(new InProgressFragment(),"");
        viewPagerAdapter.addFragment(new HappeningNowFragment(),"");


    }

    ////////////UnRegister Reciever /////////////////////////////////////////////
    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(broadcastReceiver);

    }

}

