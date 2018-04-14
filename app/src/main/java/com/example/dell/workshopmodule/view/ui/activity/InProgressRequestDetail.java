package com.example.dell.workshopmodule.view.ui.activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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
import com.example.dell.workshopmodule.databinding.ActivityInProgressRequestDetailLayoutBinding;
import com.example.dell.workshopmodule.databinding.TabNormalRequestsLayoutBinding;
import com.example.dell.workshopmodule.model.global.RequestData;
import com.example.dell.workshopmodule.utils.ConfigurationFile;
import com.example.dell.workshopmodule.utils.CustomUtils;
import com.example.dell.workshopmodule.view.ui.adapter.ViewPagerAdapter;
import com.example.dell.workshopmodule.view.ui.callback.BaseInterface;
import com.example.dell.workshopmodule.view.ui.fragment.CarInfoFragment;
import com.example.dell.workshopmodule.view.ui.fragment.ClientInfo;
import com.example.dell.workshopmodule.view.ui.fragment.CompletedRequestsFragment;
import com.example.dell.workshopmodule.view.ui.fragment.HappeningNowFragment;
import com.example.dell.workshopmodule.view.ui.fragment.InProgressFragment;
import com.example.dell.workshopmodule.view.ui.fragment.OfferInfo;
import com.example.dell.workshopmodule.viewmodel.InProgressDetail.InProgressrequestDetailViewModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.banners.RemoteBanner;
import ss.com.bannerslider.views.BannerSlider;

/**
 * Created by DELL on 12/03/2018.
 */

public class InProgressRequestDetail extends AppCompatActivity implements BaseInterface{
    ///////////////////////////////////////////////////////////////////////////////////////////
            ActivityInProgressRequestDetailLayoutBinding binding;
            TabNormalRequestsLayoutBinding bindingtab1;
              TabNormalRequestsLayoutBinding bindingtab2;
             TabNormalRequestsLayoutBinding bindingtab3;
             InProgressrequestDetailViewModel viewModel;
             ViewPagerAdapter viewPagerAdapter;
    //////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initBinding();
        setUpActionBar();
        initializeTabs();
    }

    @Override
    public void updateUi(int code) {
        if(code==ConfigurationFile.Constants.SUCCESS_CODE) {
            Snackbar.make(binding.rlParent, R.string.request_completed,Snackbar.LENGTH_LONG).show();
            finish();
        }else if(code==ConfigurationFile.Constants.NO_INTERNET_CONNECTION_CODE)
            Snackbar.make(binding.rlParent, R.string.internet_connection,Snackbar.LENGTH_LONG).show();

        else if(code== ConfigurationFile.Constants.UNAUTHENTICATED_CODE)
            CustomUtils.getInstance().logout(InProgressRequestDetail.this);

        else if(code==ConfigurationFile.Constants.CANT_COMPLETE_REQUEST_CODE)
            Snackbar.make(binding.rlParent, R.string.cant_complete_offer,Snackbar.LENGTH_LONG).show();
    }

    public void initBinding(){
        binding= DataBindingUtil.setContentView(InProgressRequestDetail.this,R.layout.activity_in_progress_request_detail_layout);
        bindingtab1=DataBindingUtil.inflate(getLayoutInflater(),R.layout.tab_normal_requests_layout,null,false);
        bindingtab2=DataBindingUtil.inflate(getLayoutInflater(),R.layout.tab_normal_requests_layout,null,false);
        bindingtab3=DataBindingUtil.inflate(getLayoutInflater(),R.layout.tab_normal_requests_layout,null,false);
        viewModel=new  InProgressrequestDetailViewModel(getApplicationContext(),this,getExtrasFromIntent().getId());
        binding.setInprogressdetailviewmodel(viewModel);

    }

    public void setUpActionBar(){
        setSupportActionBar((Toolbar) binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
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


        bindingtab1.tvTab1.setText(R.string.car_info);
        bindingtab2.tvTab1.setText(R.string.offer_info);
        bindingtab3.tvTab1.setText(R.string.client_info);


        binding.tabNormalRequest.getTabAt(0).setCustomView(bindingtab1.ll1);
        binding.tabNormalRequest.getTabAt(1).setCustomView(bindingtab2.ll1);
        binding.tabNormalRequest.getTabAt(2).setCustomView(bindingtab3.ll1);
    }

    public RequestData getExtrasFromIntent(){
        RequestData requestData=(RequestData) getIntent().getSerializableExtra(ConfigurationFile.SharedPrefConstants.PREF_REQUEST_OBJECT);
        return requestData;
    }


    public void setUpFragments( ){

        Bundle bundle=new Bundle();
        bundle.putSerializable(ConfigurationFile.SharedPrefConstants.PREF_REQUEST_OBJECT,getExtrasFromIntent());

        ClientInfo clientInfo=new ClientInfo();
        clientInfo.setArguments(bundle);

        CarInfoFragment carInfoFragment=new CarInfoFragment();
        carInfoFragment.setArguments(bundle);

        OfferInfo offerInfo=new OfferInfo();
        offerInfo.setArguments(bundle);

        viewPagerAdapter.addFragment(clientInfo,"");
        viewPagerAdapter.addFragment(offerInfo,"");
        viewPagerAdapter.addFragment(carInfoFragment,"");
    }


}
