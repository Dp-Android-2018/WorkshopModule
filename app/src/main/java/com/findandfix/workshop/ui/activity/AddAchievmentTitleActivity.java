package com.findandfix.workshop.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;

import com.findandfix.workshop.R;
import com.findandfix.workshop.databinding.ActivityAchievmentTitleLayoutBinding;
import com.findandfix.workshop.model.global.UserData;
import com.findandfix.workshop.model.request.AddAchievmentRequest;
import com.findandfix.workshop.utils.ConfigurationFile;
import com.findandfix.workshop.utils.CustomUtils;
import com.findandfix.workshop.viewmodel.ToolbarViewModel;

public class AddAchievmentTitleActivity extends BaseActivity {

    private ActivityAchievmentTitleLayoutBinding binding;
    private AddAchievmentRequest addAchievmentRequest;
    private UserData userData;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        handleToolbar();
    }

    /*Inflating Ui
   checking If Achievment Title Not Empty and moving to AddAchievmentDescriptionActivity Activity */
    public void initBinding(){

        binding= DataBindingUtil.setContentView(AddAchievmentTitleActivity.this, R.layout.activity_achievment_title_layout);
        userData= CustomUtils.getInstance().getSaveUserObject(getApplicationContext());
        binding.tvWorkshopName.setText(userData.getName());
        binding.btnNext.setOnClickListener(v -> {
            if(binding.etAdvTitle.getText().toString()!=null && !binding.etAdvTitle.getText().toString().equals(""))
            {
                addAchievmentRequest=new AddAchievmentRequest();
                addAchievmentRequest.setTitle(binding.etAdvTitle.getText().toString());
                Intent i = new Intent(getApplicationContext(), AddAchievmentDescriptionActivity.class);
                i.putExtra(ConfigurationFile.IntentConstants.ADD_ACHIEVMENT_OBJ,addAchievmentRequest);
                startActivity(i);
            }else {
                Snackbar.make(binding.rlContainer,getString(R.string.add_adv_title),Snackbar.LENGTH_LONG).show();
            }
        });

    }
    /* Specify Layout and properties Of Toolbar According To Code Which I Sent */
    public void handleToolbar(){
        setSupportActionBar( binding.toolbar.toolbar);
        binding.toolbar.setViewmodel(new ToolbarViewModel(AddAchievmentTitleActivity.this, ConfigurationFile.Constants.HANDLE_ADD_ACHIEVMENT_TOOLBAR));
    }


}
