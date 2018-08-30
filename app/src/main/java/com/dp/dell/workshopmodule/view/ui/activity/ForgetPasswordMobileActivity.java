package com.dp.dell.workshopmodule.view.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.dp.dell.workshopmodule.R;
import com.dp.dell.workshopmodule.databinding.ActivityForgotPasswordLayoutBinding;
import com.dp.dell.workshopmodule.model.request.GetMobileCode;
import com.dp.dell.workshopmodule.model.response.DefaultResponse;
import com.dp.dell.workshopmodule.network.ApiClient;
import com.dp.dell.workshopmodule.network.EndPoints;
import com.dp.dell.workshopmodule.utils.ConfigurationFile;
import com.dp.dell.workshopmodule.utils.CustomUtils;
import com.dp.dell.workshopmodule.utils.NetWorkConnection;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ForgetPasswordMobileActivity extends BaseActivity {

    ActivityForgotPasswordLayoutBinding binding;
    //////////////Checking If Mobile Not Empty Then Make a call with mobile to send code to this phone number //////////////////////
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_forgot_password_layout);
        binding.btnLoginEnter.setOnClickListener(v -> {
                if (binding.etMobile.getText().toString().trim().equals("")){
                    Snackbar.make(findViewById(R.id.rl_parent), R.string.please_enter_data, Snackbar.LENGTH_LONG).show();
                }else {
                    checkMobile();
                }
        });
    }

    public void checkMobile(){

        if (NetWorkConnection.isConnectingToInternet(ForgetPasswordMobileActivity.this)) {
            CustomUtils.getInstance().showProgressDialog(ForgetPasswordMobileActivity.this);
            GetMobileCode getMobileCode=new GetMobileCode(binding.etMobile.getText().toString().trim());
            ApiClient.getClient().create(EndPoints.class).getRestToken(ConfigurationFile.Constants.API_KEY,CustomUtils.getInstance().getAppLanguage(getApplicationContext()),ConfigurationFile.Constants.Content_Type,ConfigurationFile.Constants.Content_Type,getMobileCode)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(defaultResponseResponse -> {
                        CustomUtils.getInstance().cancelDialog();
                        System.out.println("Mobile Code  :"+defaultResponseResponse.code());

                        if (defaultResponseResponse.code()== ConfigurationFile.Constants.SUCCESS_CODE) {

                            Intent i=new Intent(ForgetPasswordMobileActivity.this,ForgetPasswordCodeActivity.class);
                            startActivity(i);
                        } else if (defaultResponseResponse.code()== ConfigurationFile.Constants.UNSUBSCRIBE_CODE ||
                                defaultResponseResponse.code()== ConfigurationFile.Constants.UNAUTHENTICATED_CODE){
                            CustomUtils.getInstance().endSession(ForgetPasswordMobileActivity.this);
                        }else if (defaultResponseResponse.code()==ConfigurationFile.Constants.CANT_COMPLETE_REQUEST_CODE)
                            Snackbar.make(findViewById(R.id.rl_parent), R.string.cant_complete_your_request, Snackbar.LENGTH_LONG).show();
                    }, throwable -> {
                        CustomUtils.getInstance().cancelDialog();
                        //  binding.progressBar.setVisibility(View.GONE);
                        System.out.println("Ex :"+throwable.getMessage());
                    });


        }else {
            Snackbar.make(findViewById(R.id.rl_parent),getResources().getString(R.string.internet_connection), Snackbar.LENGTH_LONG).show();
        }
    }

    ////////////////////////Handle On Bck Pressed ///////////////////////////
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(ForgetPasswordMobileActivity.this,LoginActivity.class);
        startActivity(i);
        finishAffinity();
    }
}
