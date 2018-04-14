package com.example.dell.workshopmodule.view.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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
import com.example.dell.workshopmodule.view.ui.adapter.ViewPagerAdapter;
import com.example.dell.workshopmodule.view.ui.fragment.CompletedRequestsFragment;
import com.example.dell.workshopmodule.view.ui.fragment.HappeningNowFragment;
import com.example.dell.workshopmodule.view.ui.fragment.InProgressFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by DELL on 14/03/2018.
 */

public class NormalRequestActivity extends AppCompatActivity {

       @BindView(R.id.tab_normal_request)
       TabLayout tabLayout;

       @BindView(R.id.viewpager_normal_requests)
       ViewPager viewPager;
       private LinearLayout linearLayoutOne,linearLayout2,linearLayout3;
       private TextView tv1,tv2,tv3;
       private ViewPagerAdapter viewPagerAdapter;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_requests_layout);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(Build.VERSION.SDK_INT>=21){
            Window window=this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getApplicationContext().getResources().getColor(R.color.bluecolor));
        }

        ///////////////////////////////////Declaring Tabs //////////////////////////////////////////////////////
        ButterKnife.bind(this);
        viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager(),true);
        viewPagerAdapter.addFragment(new CompletedRequestsFragment(),"ONE");
        viewPagerAdapter.addFragment(new InProgressFragment(), "TWO");
        viewPagerAdapter.addFragment(new HappeningNowFragment(), "THREE");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        View headerView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.tab_normal_requests_layout, null, false);

        View headerView2 = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.tab_normal_requests_layout, null, false);

        View headerView3 = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.tab_normal_requests_layout, null, false);

        linearLayoutOne = (LinearLayout) headerView.findViewById(R.id.ll1);
        tv1=(TextView)headerView.findViewById(R.id.tvTab1) ;
        linearLayout2 = (LinearLayout) headerView2.findViewById(R.id.ll1);
        tv2=(TextView)headerView2.findViewById(R.id.tvTab1) ;
        linearLayout3 = (LinearLayout) headerView3.findViewById(R.id.ll1);
        tv3=(TextView)headerView3.findViewById(R.id.tvTab1) ;

        tv1.setText(R.string.happening_now);
        tv2.setText(R.string.in_progress);
        tv3.setText(R.string.done);


        tabLayout.getTabAt(0).setCustomView(linearLayoutOne);
        tabLayout.getTabAt(1).setCustomView(linearLayout2);
        tabLayout.getTabAt(2).setCustomView(linearLayout3);




    }

}

