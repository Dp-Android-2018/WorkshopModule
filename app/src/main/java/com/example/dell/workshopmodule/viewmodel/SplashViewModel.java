package com.example.dell.workshopmodule.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.os.Handler;

import com.example.dell.workshopmodule.model.global.UserData;
import com.example.dell.workshopmodule.model.response.DefaultResponse;
import com.example.dell.workshopmodule.network.ApiClient;
import com.example.dell.workshopmodule.network.EndPoints;
import com.example.dell.workshopmodule.utils.ConfigurationFile;
import com.example.dell.workshopmodule.utils.CustomUtils;
import com.example.dell.workshopmodule.view.ui.callback.BaseInterface;
import com.google.gson.Gson;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;


/**
 * Created by DELL on 04/04/2018.
 */

public class SplashViewModel extends BaseObservable {

    private Context context;
    private BaseInterface callback;
    public SplashViewModel(Context context, BaseInterface callback) {
            this.context=context;
            this.callback=callback;
            delay();
    }

    public void delay(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                    moveTonextActivity();
            }
        }, ConfigurationFile.Constants.SPLASH_TIME_OUT);
    }

    public void moveTonextActivity(){
        if(CustomUtils.getInstance().getSaveUserObject(context)!=null){
            isSubScribed();
        }else {
                callback.updateUi(ConfigurationFile.Constants.MOVE_TO_LOGIN_ACT);
        }

    }


    public void isSubScribed(){
        UserData userData= CustomUtils.getInstance().getSaveUserObject(context);
        System.out.println("SubScribe Status Token :"+userData.getToken());
        ApiClient.getClient().create(EndPoints.class).isWorkshopSubScribed(ConfigurationFile.Constants.API_KEY, "EN", ConfigurationFile.Constants.Content_Type, ConfigurationFile.Constants.Content_Type,"Bearer "+ userData.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Response<DefaultResponse>>() {
                    @Override
                    public void accept(Response<DefaultResponse> defaultResponseResponse) throws Exception {
                        System.out.println("SubScribe Status :"+defaultResponseResponse.code());
                                if (defaultResponseResponse.code()==ConfigurationFile.Constants.SUCCESS_CODE){
                                    System.out.println("SubScribe Status cODE:"+defaultResponseResponse.body().getMessage());
                                    if (Integer.parseInt(defaultResponseResponse.body().getMessage())==0)
                                        callback.updateUi(ConfigurationFile.Constants.MOVE_TO_SUBSCRIBTION_ACTIVITY);
                                    else  callback.updateUi(ConfigurationFile.Constants.MOVE_TO_MAIN_ACT);
                                }else  if (defaultResponseResponse.code()==ConfigurationFile.Constants.CANT_COMPLETE_REQUEST_CODE)
                                         callback.updateUi(ConfigurationFile.Constants.CANT_COMPLETE_REQUEST_CODE);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        System.out.println("SubScribtion Exception :"+throwable.getMessage());
                    }
                });
    }
}
