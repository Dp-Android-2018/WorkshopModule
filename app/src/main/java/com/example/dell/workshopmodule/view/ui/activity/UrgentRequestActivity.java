package com.example.dell.workshopmodule.view.ui.activity;

import android.content.Context;
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
import com.example.dell.workshopmodule.view.ui.fragment.CompletedUrgentRequestFragment;
import com.example.dell.workshopmodule.view.ui.fragment.HappeningNowFragment;
import com.example.dell.workshopmodule.view.ui.fragment.HappenningNowUrgentRequestsFragment;
import com.example.dell.workshopmodule.view.ui.fragment.InProgressFragment;
import com.example.dell.workshopmodule.view.ui.fragment.InProgressUrgentRequestFragment;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by DELL on 13/03/2018.
 */

public class UrgentRequestActivity extends AppCompatActivity{

    @BindView(R.id.tab_urgent_request)
    TabLayout tabLayout;

    @BindView(R.id.viewpager_urgent_requests)
    ViewPager viewPager;
    private LinearLayout linearLayoutOne,linearLayout2,linearLayout3;
    private TextView tv1,tv2,tv3;
    private ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urgent_request_layout);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView tv=(TextView)toolbar.findViewById(R.id.tv_toolbar_title);
        tv.setText(getResources().getString(R.string.uregnt_request));
        toolbar.setBackgroundColor(getResources().getColor(R.color.redColor));
        if(Build.VERSION.SDK_INT>=21){
            Window window=this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getApplicationContext().getResources().getColor(R.color.redColor));
        }

        ButterKnife.bind(this);
        viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager(),true);
        viewPagerAdapter.addFragment(new CompletedUrgentRequestFragment(),"ONE");
        viewPagerAdapter.addFragment(new InProgressUrgentRequestFragment(), "TWO");
        viewPagerAdapter.addFragment(new HappenningNowUrgentRequestsFragment(), "THREE");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        View headerView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.tab_urgent_request_layout, null, false);

        View headerView2 = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.tab_urgent_request_layout, null, false);

        View headerView3 = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.tab_urgent_request_layout, null, false);

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
