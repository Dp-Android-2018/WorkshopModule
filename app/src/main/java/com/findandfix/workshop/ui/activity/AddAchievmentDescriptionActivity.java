package com.findandfix.workshop.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;

import com.findandfix.workshop.R;
import com.findandfix.workshop.databinding.ActivityAchievmentDescriptionLayoutBinding;
import com.findandfix.workshop.model.request.AddAchievmentRequest;
import com.findandfix.workshop.utils.ConfigurationFile;
import com.findandfix.workshop.viewmodel.ToolbarViewModel;

public class AddAchievmentDescriptionActivity extends BaseActivity {

    private ActivityAchievmentDescriptionLayoutBinding binding;
    private AddAchievmentRequest addAchievmentRequest;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getIntents();
        initBinding();
        handleToolbar();
    }

    /*Inflating Ui
    checking If Description Not Empty and moving to AddAchievmentBeforeFixing Activity */
    public void initBinding(){
        binding= DataBindingUtil.setContentView(AddAchievmentDescriptionActivity.this, R.layout.activity_achievment_description_layout);
        binding.btnNext.setOnClickListener(v -> {
            if(binding.etAdvDesc.getText().toString()!=null && !binding.etAdvDesc.getText().toString().equals("")) {
                addAchievmentRequest.setDescription(binding.etAdvDesc.getText().toString().trim());
                Intent i = new Intent(getApplicationContext(), AddAchievmentBeforeFixing.class);
                i.putExtra(ConfigurationFile.IntentConstants.ADD_ACHIEVMENT_OBJ, addAchievmentRequest);
                startActivity(i);
            }else {
                Snackbar.make(binding.getRoot(),getString(R.string.add_achievment_description),Snackbar.LENGTH_LONG).show();
            }
        });
    }
    /* Specify Layout and properties Of Toolbar According To Code Which I Sent */
    public void handleToolbar(){
        setSupportActionBar( binding.toolbar.toolbar);
        binding.toolbar.setViewmodel(new ToolbarViewModel(AddAchievmentDescriptionActivity.this, ConfigurationFile.Constants.HANDLE_ADD_ACHIEVMENT_TOOLBAR));
    }

    /* get Data Which Comes From Previous Activity */
    public void getIntents(){
        addAchievmentRequest=(AddAchievmentRequest) getIntent().getSerializableExtra(ConfigurationFile.IntentConstants.ADD_ACHIEVMENT_OBJ);
    }
}
