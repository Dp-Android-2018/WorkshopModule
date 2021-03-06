package com.findandfix.workshop.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.findandfix.workshop.R;
import com.findandfix.workshop.databinding.ActivityAddAdvertismentDescriptionLayoutBinding;
import com.findandfix.workshop.model.request.AddAdvertisementRequest;
import com.findandfix.workshop.utils.ConfigurationFile;
import com.findandfix.workshop.viewmodel.ToolbarViewModel;

/**
 * Created by DELL on 13/03/2018.
 */

public class AddAdvertismentDescription extends BaseAdvertisementAct {
    private ActivityAddAdvertismentDescriptionLayoutBinding binding;
    private AddAdvertisementRequest addAdvertisementRequest;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getIntents();
        initBinding();
        handleToolbar();
    }
    /*Inflating Layout Of ACTIVITY
    * Checking If etAdvDesc not Empty and move to next activity */
    public void initBinding(){
        binding= DataBindingUtil.setContentView(AddAdvertismentDescription.this,R.layout.activity_add_advertisment_description_layout);
        binding.btnNext.setOnClickListener(v -> {
           addAdvertisementRequest.setDescription(binding.etAdvDesc.getText().toString().trim().equals("")?null:binding.etAdvDesc.getText().toString().trim());
            Intent i=new Intent(getApplicationContext(),AddAdvertisementImage.class);
            i.putExtra(ConfigurationFile.IntentConstants.ADD_ADV_OBJ,addAdvertisementRequest);
            startActivity(i);
        });
    }
    //////////////////////SET ToolBar Color and define it's Properties //////////////////////////////////////////////////////////////////
    public void handleToolbar(){
        setSupportActionBar( binding.toolbar.toolbar);
        binding.toolbar.setViewmodel(new ToolbarViewModel(AddAdvertismentDescription.this, ConfigurationFile.Constants.HANDLE_CUSTOM_ADD_ADVERTISING_TOOLBAR));
    }
//////////////////////////////Get (Extra Intents) (addAdvertisementRequest) From Previous Activity ////////////////////////////////////////////////
    public void getIntents(){
        addAdvertisementRequest=(AddAdvertisementRequest) getIntent().getSerializableExtra(ConfigurationFile.IntentConstants.ADD_ADV_OBJ);
    }

}
