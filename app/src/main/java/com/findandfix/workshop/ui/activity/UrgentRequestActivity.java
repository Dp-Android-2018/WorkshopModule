package com.findandfix.workshop.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.findandfix.workshop.R;
import com.findandfix.workshop.databinding.ActivityUrgentRequestLayoutBinding;
import com.findandfix.workshop.databinding.TabUrgentRequestLayoutBinding;
import com.findandfix.workshop.ui.adapter.ViewPagerAdapter;
import com.findandfix.workshop.ui.fragment.CompletedUrgentRequestFragment;
import com.findandfix.workshop.ui.fragment.HappenningNowUrgentRequestsFragment;
import com.findandfix.workshop.ui.fragment.InProgressUrgentRequestFragment;
import com.findandfix.workshop.utils.ConfigurationFile;
import com.findandfix.workshop.viewmodel.ToolbarViewModel;

/**
 * Created by DELL on 13/03/2018.
 */

public class UrgentRequestActivity extends BaseActivity {

    private ActivityUrgentRequestLayoutBinding binding;
    private TabUrgentRequestLayoutBinding bindingTab1;
    private TabUrgentRequestLayoutBinding bindingTab2;
    private TabUrgentRequestLayoutBinding bindingTab3;
    private ViewPagerAdapter viewPagerAdapter;
    public static boolean active=false;
    private BroadcastReceiver broadcastReceiver=null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initBinding();
        setUpActionBar();
        initializeTabs();
        registerReciever();
    }
////////////////////////////Register BroadCat Reciever For Updating View When New Notification Coming //////////////////////////////////////
    public void registerReciever(){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("CLOSE_ALL");
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //  binding.viewpagerNormalRequests.setCurrentItem(1);
                binding.viewpagerUrgentRequests.setAdapter(null);
                initializeTabs();
                binding.viewpagerUrgentRequests.setCurrentItem(1);
            }
        };
        registerReceiver(broadcastReceiver, intentFilter);
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


//////////////////////////////// UnRegister BroadCast Reciever ////////////////////////////////////////////////////////
    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(broadcastReceiver);

    }
////////////////////////////////////////Declaring Custom Tabs Layout //////////////////////////////////////////////////////
    public void initBinding(){
        binding= DataBindingUtil.setContentView(UrgentRequestActivity.this,R.layout.activity_urgent_request_layout);
        bindingTab1=DataBindingUtil.inflate(getLayoutInflater(),R.layout.tab_urgent_request_layout,null,false);
        bindingTab2=DataBindingUtil.inflate(getLayoutInflater(),R.layout.tab_urgent_request_layout,null,false);
        bindingTab3=DataBindingUtil.inflate(getLayoutInflater(),R.layout.tab_urgent_request_layout,null,false);
    }
/////////////////Handling Action Bar Color & Visibility Of It's Items /////////////////////////////////////////////////////
    public void setUpActionBar(){
        setSupportActionBar( binding.toolbar.toolbar);
        binding.toolbar.setViewmodel(new ToolbarViewModel(UrgentRequestActivity.this, ConfigurationFile.Constants.HANDLE_URGENT_REQUEST_TOOLBAR));
    }
//////////////////Handling View Pager + View Pager Adapter + Put Text Of Tabs ///////////////////////////////////////////////////
    public void initializeTabs(){
        viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager(),true);
        setUpFragments();
        binding.viewpagerUrgentRequests.setAdapter(viewPagerAdapter);
        binding.tabUrgentRequest.setupWithViewPager(binding.viewpagerUrgentRequests);
        if (getIntent()!=null){
            if (getIntent().getIntExtra(ConfigurationFile.IntentConstants.URGENT_OFFER_ID,0)!=0)
                binding.viewpagerUrgentRequests.setCurrentItem(1);
        }
        bindingTab1.tvTab1.setText(R.string.happening_now);
        bindingTab2.tvTab1.setText(R.string.in_progress);
        bindingTab3.tvTab1.setText(R.string.done);

        binding.tabUrgentRequest.getTabAt(0).setCustomView(bindingTab1.ll1);
        binding.tabUrgentRequest.getTabAt(1).setCustomView(bindingTab2.ll1);
        binding.tabUrgentRequest.getTabAt(2).setCustomView(bindingTab3.ll1);
    }

/////////////////////////Passing Fragment To View Pager Adapter Of The Tabs ////////////////////////////////////////////
    public void setUpFragments( ){
        viewPagerAdapter.addFragment(new CompletedUrgentRequestFragment(),"");
        viewPagerAdapter.addFragment(new InProgressUrgentRequestFragment(),"");
        viewPagerAdapter.addFragment(new HappenningNowUrgentRequestsFragment(),"");
    }

}
