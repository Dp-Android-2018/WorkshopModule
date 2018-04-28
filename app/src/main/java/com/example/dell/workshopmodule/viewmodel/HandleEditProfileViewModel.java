package com.example.dell.workshopmodule.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;
import android.view.View;

import com.example.dell.workshopmodule.R;
import com.example.dell.workshopmodule.model.request.EmailRequest;
import com.example.dell.workshopmodule.model.request.UpdateProfileRequest;
import com.example.dell.workshopmodule.model.response.DefaultResponse;
import com.example.dell.workshopmodule.network.ApiClient;
import com.example.dell.workshopmodule.network.EndPoints;
import com.example.dell.workshopmodule.utils.ConfigurationFile;
import com.example.dell.workshopmodule.utils.NetWorkConnection;
import com.example.dell.workshopmodule.utils.SharedPrefrenceUtils;
import com.example.dell.workshopmodule.view.ui.callback.BaseInterface;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by DELL on 22/04/2018.
 */

public class HandleEditProfileViewModel extends BaseObservable {

    public ObservableInt progressDialog=new ObservableInt(View.GONE);
    public void setEditProfileObject(final UpdateProfileRequest updateProfileRequest, final Context context, final BaseInterface navigator, String token){

        if(NetWorkConnection.isConnectingToInternet(context)) {
            progressDialog.set(View.VISIBLE);
            ApiClient.getClient().create(EndPoints.class).updateWorkshopProfile(ConfigurationFile.Constants.API_KEY, "EN", ConfigurationFile.Constants.Content_Type, ConfigurationFile.Constants.Content_Type, "Bearer " + token,updateProfileRequest)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Response<DefaultResponse>>() {
                        @Override
                        public void accept(Response<DefaultResponse> defaultResponseResponse) throws Exception {
                                 progressDialog.set(View.GONE);
                                if(defaultResponseResponse.code()==ConfigurationFile.Constants.SUCCESS_CODE){
                                    navigator.updateUi(ConfigurationFile.Constants.SUCCESS_CODE);
                                   /* SharedPrefrenceUtils pref = new SharedPrefrenceUtils(context);
                                    pref.saveObjectToSharedPreferences(ConfigurationFile.SharedPrefConstants.PREF_WORKSHOP_DATA, updateProfileRequest);*/
                                }else if(defaultResponseResponse.code()==ConfigurationFile.Constants.UNAUTHENTICATED_CODE){
                                    navigator.updateUi(ConfigurationFile.Constants.UNAUTHENTICATED_CODE);
                                }else if(defaultResponseResponse.code()==ConfigurationFile.Constants.INVALID_DATA){
                                    navigator.updateUi(ConfigurationFile.Constants.INVALID_DATA);
                                }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            progressDialog.set(View.GONE);
                            System.out.println("Update Profile Exception :"+throwable.getMessage());
                        }
                    });
        }else {
            navigator.updateUi(ConfigurationFile.Constants.NO_INTERNET_CONNECTION_CODE);
        }
    }
}
