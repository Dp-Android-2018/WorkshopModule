package com.example.dell.workshopmodule.view.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import com.example.dell.workshopmodule.databinding.TabEditProfileLayoutBinding;
import com.example.dell.workshopmodule.model.request.UpdateProfileRequest;
import com.example.dell.workshopmodule.utils.ConfigurationFile;
import com.example.dell.workshopmodule.view.ui.adapter.ViewPagerAdapter;
import com.example.dell.workshopmodule.view.ui.fragment.CarInfoFragment;
import com.example.dell.workshopmodule.view.ui.fragment.ClientInfo;
import com.example.dell.workshopmodule.view.ui.fragment.EditProfileInfoFragment;
import com.example.dell.workshopmodule.view.ui.fragment.EditProfileSpecializationFragment;
import com.example.dell.workshopmodule.view.ui.fragment.OfferInfo;
import com.example.dell.workshopmodule.viewmodel.InProgressDetail.InProgressrequestDetailViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by DELL on 14/03/2018.
 */

public class EditProfileInfoActivity extends AppCompatActivity {

    private ActivityEditProfileLayoutBinding binding;
    private TabEditProfileLayoutBinding bindingTab1;
    private TabEditProfileLayoutBinding bindingTab2;
    private  ViewPagerAdapter viewPagerAdapter;
    private EditProfileSpecializationFragment specializationFragment;
    private EditProfileInfoFragment infoFragment;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        setUpActionBar();
        initializeTabs();
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(UpdateProfileRequest updateProfileRequest){
        binding.viewpagerEditProfile.setCurrentItem(1);
        specializationFragment.setProfileObject(updateProfileRequest);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }



    public void initBinding(){
        binding= DataBindingUtil.setContentView(EditProfileInfoActivity.this,R.layout.activity_edit_profile_layout);
        bindingTab1=DataBindingUtil.inflate(getLayoutInflater(),R.layout.tab_edit_profile_layout,null,false);
        bindingTab2=DataBindingUtil.inflate(getLayoutInflater(),R.layout.tab_edit_profile_layout,null,false);
    }

    public void setUpActionBar(){
        setSupportActionBar( binding.toolbar.toolbar);
        binding.toolbar.tvToolbarTitle.setText(R.string.edit_profile);
        binding.toolbar.toolbar.setBackgroundColor(getResources().getColor(R.color.orangecolor));
        if(Build.VERSION.SDK_INT>=21){
            Window window=this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getApplicationContext().getResources().getColor(R.color.orangecolor));
        }
    }

    public void initializeTabs(){
        viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager(),true);
        setUpFragments();
        binding.viewpagerEditProfile.setAdapter(viewPagerAdapter);
        binding.tabEditProfile.setupWithViewPager(binding.viewpagerEditProfile);


        bindingTab1.tvTab1.setText(R.string.information);
        bindingTab2.tvTab1.setText(R.string.specialization);



        binding.tabEditProfile.getTabAt(0).setCustomView(bindingTab1.ll1);
        binding.tabEditProfile.getTabAt(1).setCustomView(bindingTab2.ll1);
    }


    public void setUpFragments(){
        specializationFragment=new EditProfileSpecializationFragment();
        infoFragment=new EditProfileInfoFragment();
        viewPagerAdapter.addFragment(specializationFragment,"");
        viewPagerAdapter.addFragment(infoFragment ,"");
    }
}
