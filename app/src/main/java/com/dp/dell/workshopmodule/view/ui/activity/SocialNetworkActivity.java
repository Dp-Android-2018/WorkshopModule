package com.dp.dell.workshopmodule.view.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.dp.dell.workshopmodule.R;
import com.dp.dell.workshopmodule.databinding.ActivitySocialNetworkLayoutBinding;
import com.dp.dell.workshopmodule.model.global.Social;
import com.dp.dell.workshopmodule.model.request.RegisterRequest;
import com.dp.dell.workshopmodule.utils.ConfigurationFile;
import com.dp.dell.workshopmodule.utils.CustomUtils;
import com.dp.dell.workshopmodule.utils.ValidationUtils;
import com.dp.dell.workshopmodule.view.ui.callback.BaseInterface;
import com.dp.dell.workshopmodule.viewmodel.FinalStep.HandleRegisterRequestViewModel;

import butterknife.internal.Utils;

/**
 * Created by DELL on 25/07/2018.
 */

public class SocialNetworkActivity extends AppCompatActivity implements BaseInterface{

    ActivitySocialNetworkLayoutBinding binding;
    RegisterRequest registerRequest;
    Social social;
    HandleRegisterRequestViewModel registerRequestViewModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_social_network_layout);
        social=new Social();
        registerRequest=(RegisterRequest) getIntent().getSerializableExtra(ConfigurationFile.SharedPrefConstants.PREF_REGISTER_OBJECT);
        registerRequestViewModel=new HandleRegisterRequestViewModel(SocialNetworkActivity.this,this);
        binding.tvSpecializationBack.setOnClickListener(v -> finish());

        binding.tvSpecializationNextStep.setOnClickListener(v -> {
            if (checkMedia()==true) {
                registerRequest.setSocial(social);
                registerRequestViewModel.handleRegister(registerRequest, SocialNetworkActivity.this);
            }
        });
    }

    public boolean checkMedia(){
        if (!ValidationUtils.isEmpty(binding.etFacebook.getText().toString())) {
            if (binding.etFacebook.getText().toString().trim().contains("facebook")) {
                social.setFacebook(binding.etFacebook.getText().toString().trim());
            } else {
                Snackbar.make(binding.rlParent, R.string.adD_valid_link, Snackbar.LENGTH_LONG).show();
                return false;
            }
        } if (!ValidationUtils.isEmpty(binding.etTwitter.getText().toString())){
            if (binding.etTwitter.getText().toString().trim().contains("twitter")){
                social.setTwitter(binding.etTwitter.getText().toString().trim());
            }else {
                Snackbar.make(binding.rlParent, R.string.adD_valid_link,Snackbar.LENGTH_LONG).show();
                return false;
            }
        } if (!ValidationUtils.isEmpty(binding.etYoutube.getText().toString())){
            if (binding.etYoutube.getText().toString().contains("youtube")){
                social.setYoutube(binding.etYoutube.getText().toString());
            }else {
                Snackbar.make(binding.rlParent, R.string.adD_valid_link,Snackbar.LENGTH_LONG).show();
                return false;
            }
        }if (!ValidationUtils.isEmpty(binding.etInstagram.getText().toString())){
            if (binding.etInstagram.getText().toString().contains("instagram")){
                social.setInstagram(binding.etYoutube.getText().toString());
            }else {
                Snackbar.make(binding.rlParent, R.string.adD_valid_link,Snackbar.LENGTH_LONG).show();
                return false;
            }
        }

        return true;
    }

    @Override
    public void updateUi(int code) {
        if(code== ConfigurationFile.Constants.MOVE_TO_PREVIOUS_ACTIVITY)
            moveToPreviousActivity();

        else if(code== ConfigurationFile.Constants.INVALID_DATA)
            showSnackBar(R.string.invalid_data);
        else if(code== ConfigurationFile.Constants.MOVE_TO_NEXT_ACTIVITY)
            MoveToNextActivity(new Intent(this,MainActivity.class));


        else if(code==ConfigurationFile.Constants.NO_INTERNET_CONNECTION_CODE)
            showSnackBar(R.string.internet_connection);
    }

    public void showSnackBar(int message){
        Snackbar.make(binding.rlParent,message,Snackbar.LENGTH_LONG).show();
    }

    public void moveToPreviousActivity(){finish();}

    public void MoveToNextActivity(Intent I){
        sendBrodcast();
        I.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(I);
        finishAffinity();
    }

    public void sendBrodcast(){

        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("com.package.ACTION_LOGOUT");
       sendBroadcast(broadcastIntent);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
