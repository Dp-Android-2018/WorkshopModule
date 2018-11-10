package com.findandfix.workshop.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.findandfix.workshop.R;
import com.findandfix.workshop.databinding.ActivityAdvertisingDetailBinding;
import com.findandfix.workshop.model.global.AdvData;
import com.findandfix.workshop.utils.ConfigurationFile;
import com.findandfix.workshop.viewmodel.ToolbarViewModel;
import com.squareup.picasso.Picasso;


public class OffersDetailActivity extends BaseActivity {

    private AdvData advData;
    private ActivityAdvertisingDetailBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(OffersDetailActivity.this, R.layout.activity_advertising_detail);
        getInentExtras();
        setUpToolBar();
        setDatatoUi();
    }

    public void setUpToolBar(){
        setSupportActionBar( binding.toolbar.toolbar);
        binding.toolbar.setViewmodel(new ToolbarViewModel(OffersDetailActivity.this,-1));
        binding.toolbar.getViewmodel().handleAddAdvertiseDetail(advData.getTitle());
    }

    public void getInentExtras(){
        advData=(AdvData)getIntent().getSerializableExtra(ConfigurationFile.IntentConstants.WORKSHOP_ADV_OBJ);
    }
    public void setDatatoUi(){
        binding.textView.setText(advData.getTitle());
        binding.textView2.setText((advData.getDescription()==null || advData.getDescription().equals("")) ?getString(R.string.no_desc):advData.getDescription());
        if (advData.getImage()!=null && !(advData.getImage().equals("")))
        Picasso.with(getApplicationContext()).load(advData.getImage()).into(binding.ivAdv);
    }
}
