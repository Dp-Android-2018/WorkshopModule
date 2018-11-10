package com.findandfix.workshop.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;

import com.findandfix.workshop.R;
import com.findandfix.workshop.databinding.ActivityAddAdvertismentTitleLayoutBinding;
import com.findandfix.workshop.model.request.AddAdvertisementRequest;
import com.findandfix.workshop.utils.ConfigurationFile;
import com.findandfix.workshop.utils.CustomUtils;
import com.findandfix.workshop.viewmodel.ToolbarViewModel;

/**
 * Created by DELL on 13/03/2018.
 */

public class AddAdvertisement extends BaseAdvertisementAct {
    private ActivityAddAdvertismentTitleLayoutBinding binding;

    private AddAdvertisementRequest addAdvertisementRequest;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        handleToolbar();
    }
/*Inflating Layout Of ACTIVITY  CHECKING IF ADV TITLE NOT EMPTY AND MOVING TO AddAdvertismentDescription ACTIVITY
    PUTTING THE addAdvertisementRequest IN EXTRA*/
    public void initBinding(){
        binding= DataBindingUtil.setContentView(AddAdvertisement.this,R.layout.activity_add_advertisment_title_layout);
        binding.tvWorkshopName.setText(CustomUtils.getInstance().getSaveUserObject(getApplicationContext()).getName());
        binding.btnNext.setOnClickListener(v -> {
            if(binding.etAdvTitle.getText().toString()!=null && !binding.etAdvTitle.getText().toString().equals(""))
            {
                addAdvertisementRequest=new AddAdvertisementRequest();
                addAdvertisementRequest.setTitle(binding.etAdvTitle.getText().toString());
                Intent i = new Intent(getApplicationContext(), AddAdvertismentDescription.class);
                i.putExtra(ConfigurationFile.IntentConstants.ADD_ADV_OBJ,addAdvertisementRequest);
                startActivity(i);
            }else {
                Snackbar.make(binding.rlContainer,getString(R.string.add_adv_title),Snackbar.LENGTH_LONG).show();
            }
        });

    }
///////////////////////SET ToolBar Color and define it's Properties //////////////////////////////////////////////////////////////////
    public void handleToolbar(){
        setSupportActionBar( binding.toolbar.toolbar);
        binding.toolbar.setViewmodel(new ToolbarViewModel(AddAdvertisement.this, ConfigurationFile.Constants.HANDLE_ADD_ADVERTISING_TOOLBAR));
    }




}
