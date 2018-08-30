package com.dp.dell.workshopmodule.view.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;

import com.dp.dell.workshopmodule.R;
import com.dp.dell.workshopmodule.databinding.ActivityInProgressRequestDetailLayoutBinding;
import com.dp.dell.workshopmodule.databinding.TabNormalRequestsLayoutBinding;
import com.dp.dell.workshopmodule.model.global.RequestData;
import com.dp.dell.workshopmodule.utils.ConfigurationFile;
import com.dp.dell.workshopmodule.utils.CustomUtils;
import com.dp.dell.workshopmodule.view.ui.adapter.ViewPagerAdapter;
import com.dp.dell.workshopmodule.view.ui.callback.BaseInterface;
import com.dp.dell.workshopmodule.view.ui.fragment.CarInfoFragment;
import com.dp.dell.workshopmodule.view.ui.fragment.ClientInfo;
import com.dp.dell.workshopmodule.view.ui.fragment.OfferInfo;
import com.dp.dell.workshopmodule.viewmodel.InProgressDetail.InProgressrequestDetailViewModel;
import com.dp.dell.workshopmodule.viewmodel.ToolbarViewModel;

import ss.com.bannerslider.events.OnBannerClickListener;

/**
 * Created by DELL on 12/03/2018.
 */

public class InProgressRequestDetail extends BaseActivity implements BaseInterface{
    ///////////////////////////////////////////////////////////////////////////////////////////
            ActivityInProgressRequestDetailLayoutBinding binding;
            TabNormalRequestsLayoutBinding bindingtab1;
              TabNormalRequestsLayoutBinding bindingtab2;
             TabNormalRequestsLayoutBinding bindingtab3;
             InProgressrequestDetailViewModel viewModel;
             ViewPagerAdapter viewPagerAdapter;
            RequestData requestData=null;
    //////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initBinding();
        setUpActionBar();
        initializeTabs();
    }

    ///////////////////////Update Ui According To Code Which Sent From View Model ////////////////////////////////////
    @Override
    public void updateUi(int code) {
        if(code==ConfigurationFile.Constants.SUCCESS_CODE) {
            Snackbar.make(binding.rlParent, R.string.request_completed,Snackbar.LENGTH_LONG).show();
            Intent I=new Intent(InProgressRequestDetail.this,MainActivity.class);
            startActivity(I);
            finishAffinity();
        }else if(code==ConfigurationFile.Constants.NO_INTERNET_CONNECTION_CODE)
            Snackbar.make(binding.rlParent, R.string.internet_connection,Snackbar.LENGTH_LONG).show();

        else if(code== ConfigurationFile.Constants.UNAUTHENTICATED_CODE)
            CustomUtils.getInstance().logout(InProgressRequestDetail.this);

        else if(code==ConfigurationFile.Constants.CANT_COMPLETE_REQUEST_CODE)
            Snackbar.make(binding.rlParent, R.string.cant_complete_offer,Snackbar.LENGTH_LONG).show();
    }

    /////////////////Set Custom View For Tab Layout in Android and Checking If There is a video To play it in another Activity ////////////////

    public void initBinding(){
        binding= DataBindingUtil.setContentView(InProgressRequestDetail.this,R.layout.activity_in_progress_request_detail_layout);
        bindingtab1=DataBindingUtil.inflate(getLayoutInflater(),R.layout.tab_normal_requests_layout,null,false);
        bindingtab2=DataBindingUtil.inflate(getLayoutInflater(),R.layout.tab_normal_requests_layout,null,false);
        bindingtab3=DataBindingUtil.inflate(getLayoutInflater(),R.layout.tab_normal_requests_layout,null,false);
        viewModel=new  InProgressrequestDetailViewModel(InProgressRequestDetail.this,this,getExtrasFromIntent());
        binding.setInprogressdetailviewmodel(viewModel);
        binding.bannerSlider1.setOnBannerClickListener(position -> {
            requestData = getExtrasFromIntent();
            if (requestData.getVideos() != null) {
                if ((!requestData.getVideos().get(0).equals("")) && position == requestData.getImage().size()) {

                    Intent i = new Intent(getApplicationContext(), PlayingVideoActivity.class);
                    i.putExtra(ConfigurationFile.IntentConstants.VIDEO_URL, requestData.getVideos().get(0).getPath());
                    startActivity(i);

                }
            }
        });

    }
////////////Handle Action Bar Layout & Define it's properties ////////////////////////////////////////////////
    public void setUpActionBar(){
        setSupportActionBar( binding.toolbar.toolbar);
        binding.toolbar.setViewmodel(new ToolbarViewModel(InProgressRequestDetail.this, ConfigurationFile.Constants.HANDLE_NORMAL_REQUEST_DETAIL_TOOLBAR));
    }

    //////////////////Initialize View Pager and Put Tabs in it ///////////////////////////////////////////
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
////////////// GET Extra Object From Intent ////////////////////////////////////////////////////////////////////////////////////
    public RequestData getExtrasFromIntent(){
         requestData=(RequestData) getIntent().getSerializableExtra(ConfigurationFile.SharedPrefConstants.PREF_REQUEST_OBJECT);
        return requestData;
    }

////////////////Add Fragments of Tab layout In View Pager Adapter ///////////////////////////////////////
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
