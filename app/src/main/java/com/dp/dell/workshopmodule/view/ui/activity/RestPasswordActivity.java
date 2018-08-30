package com.dp.dell.workshopmodule.view.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.text.InputType;
import android.view.View;

import com.dp.dell.workshopmodule.R;
import com.dp.dell.workshopmodule.databinding.ActivityForgotPasswordLayoutBinding;
import com.dp.dell.workshopmodule.model.request.ForgetPasswordRequest;
import com.dp.dell.workshopmodule.model.response.DefaultResponse;
import com.dp.dell.workshopmodule.network.ApiClient;
import com.dp.dell.workshopmodule.network.EndPoints;
import com.dp.dell.workshopmodule.utils.ConfigurationFile;
import com.dp.dell.workshopmodule.utils.CustomUtils;
import com.dp.dell.workshopmodule.utils.NetWorkConnection;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class RestPasswordActivity extends BaseActivity {
    ActivityForgotPasswordLayoutBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_forgot_password_layout);
        binding.etMobile.setHint(R.string.reset_your_password);
        binding.etMobile.setInputType(InputType.TYPE_CLASS_TEXT);
        binding.btnLoginEnter.setOnClickListener(v -> {
            if (binding.etMobile.getText().toString().trim().equals("")){
                Snackbar.make(findViewById(R.id.rl_parent), R.string.please_enter_data, Snackbar.LENGTH_LONG).show();
            }else {
                resetPassword();
            }
        });
    }

    public void resetPassword(){

        if (NetWorkConnection.isConnectingToInternet(RestPasswordActivity.this)) {
            CustomUtils.getInstance().showProgressDialog(RestPasswordActivity.this);
            ForgetPasswordRequest forgetPasswordRequest=new ForgetPasswordRequest(getIntent().getStringExtra(ConfigurationFile.IntentConstants.USER_CODE),binding.etMobile.getText().toString().trim());
            ApiClient.getClient().create(EndPoints.class).resetPassword(ConfigurationFile.Constants.API_KEY,CustomUtils.getInstance().getAppLanguage(getApplicationContext()),ConfigurationFile.Constants.Content_Type,ConfigurationFile.Constants.Content_Type,forgetPasswordRequest)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(defaultResponseResponse -> {
                        CustomUtils.getInstance().cancelDialog();
                        System.out.println("Mobile Code  :"+defaultResponseResponse.code());

                        if (defaultResponseResponse.code()==ConfigurationFile.Constants.SUCCESS_CODE) {
                            Snackbar.make(findViewById(R.id.rl_parent), R.string.password_reset, Snackbar.LENGTH_LONG).show();


                            new Handler().postDelayed(() -> {
                                Intent i=new Intent(RestPasswordActivity.this,LoginActivity.class);
                                startActivity(i);
                                finishAffinity();
                            }, Snackbar.LENGTH_LONG);
                        } else if (defaultResponseResponse.code()==ConfigurationFile.Constants.CANT_COMPLETE_REQUEST_CODE)
                            Snackbar.make(findViewById(R.id.rl_parent), R.string.cant_complete_your_request, Snackbar.LENGTH_LONG).show();
                        else if (defaultResponseResponse.code()==ConfigurationFile.Constants.UNSUBSCRIBE_CODE ||
                                defaultResponseResponse.code()==ConfigurationFile.Constants.UNAUTHENTICATED_CODE)
                            CustomUtils.getInstance().endSession(RestPasswordActivity.this);
                           // Snackbar.make(findViewById(R.id.rl_parent), R.string.cant_complete_your_request, Snackbar.LENGTH_LONG).show();
                    }, throwable -> {
                        CustomUtils.getInstance().cancelDialog();
                        //  binding.progressBar.setVisibility(View.GONE);
                        System.out.println("Ex :"+throwable.getMessage());
                    });


        }else {
            Snackbar.make(findViewById(R.id.rl_parent),getResources().getString(R.string.internet_connection), Snackbar.LENGTH_LONG).show();
        }
    }


}
