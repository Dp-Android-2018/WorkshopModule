package com.example.dell.workshopmodule.view.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;

import com.example.dell.workshopmodule.R;
import com.example.dell.workshopmodule.databinding.ActivityRequestDetailLayoutBinding;
import com.example.dell.workshopmodule.model.global.RequestData;
import com.example.dell.workshopmodule.utils.ConfigurationFile;
import com.example.dell.workshopmodule.view.ui.callback.BaseInterface;
import com.example.dell.workshopmodule.view.ui.dialog.AddOfferDialog;
import com.example.dell.workshopmodule.viewmodel.PublishedRequestDetail.RequestDetailViewModel;

import java.util.ArrayList;
import java.util.List;

import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.banners.RemoteBanner;
import ss.com.bannerslider.views.BannerSlider;

/**
 * Created by DELL on 12/03/2018.
 */

public class RequestDetailActivity extends AppCompatActivity implements BaseInterface{

    private ActivityRequestDetailLayoutBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        setUpToolBar();
    }

    @Override
    public void updateUi(int code) {
        if(code==ConfigurationFile.Constants.SHOW_DIALOG_CODE){
            AddOfferDialog dialog=new AddOfferDialog(RequestDetailActivity.this,getExtrasFromIntent().getId(),getRequestType(),this);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        }else if(code==ConfigurationFile.Constants.OFFER_ADDED_SUCCESSFULLY){
            Snackbar.make(binding.svParent, R.string.offer_add_successfully,Snackbar.LENGTH_LONG).show();
            Intent i=new Intent(this,NormalRequestActivity.class);
            startActivity(i);
            finish();
        }
    }

    public void initBinding(){
        binding= DataBindingUtil.setContentView(RequestDetailActivity.this,R.layout.activity_request_detail_layout);
        RequestDetailViewModel viewModel=new RequestDetailViewModel(getApplicationContext(),getExtrasFromIntent(),getRequestType(),this);
        binding.setRequestDetailViewModel(viewModel);
    }



    public void setUpToolBar(){

        setSupportActionBar( binding.toolbar.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        if(Build.VERSION.SDK_INT>=21){
            Window window=this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getApplicationContext().getResources().getColor(R.color.bluecolor));
        }
    }

    public RequestData getExtrasFromIntent(){
        RequestData requestData=(RequestData) getIntent().getSerializableExtra(ConfigurationFile.SharedPrefConstants.PREF_REQUEST_OBJECT);
        return requestData;
    }

    public int getRequestType(){
        return  getIntent().getIntExtra(ConfigurationFile.Constants.REQUEST_TYPE,0);
    }


}
