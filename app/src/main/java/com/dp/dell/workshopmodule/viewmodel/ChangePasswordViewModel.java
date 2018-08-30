package com.dp.dell.workshopmodule.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.view.View;

import com.dp.dell.workshopmodule.model.global.UserData;
import com.dp.dell.workshopmodule.model.request.ChangePasswordRequest;
import com.dp.dell.workshopmodule.model.response.DefaultResponse;
import com.dp.dell.workshopmodule.network.ApiClient;
import com.dp.dell.workshopmodule.network.EndPoints;
import com.dp.dell.workshopmodule.utils.ConfigurationFile;
import com.dp.dell.workshopmodule.utils.CustomUtils;
import com.dp.dell.workshopmodule.utils.NetWorkConnection;
import com.dp.dell.workshopmodule.view.ui.callback.BaseInterface;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by DELL on 06/05/2018.
 */

public class ChangePasswordViewModel extends BaseObservable {
    public ObservableField<String> currentpassword;
    public ObservableField<String> newPassword;
    public ObservableField<String> cofirmNewPassword;
    public ObservableInt progress;
    private UserData userData;
    private Context context;
    private BaseInterface callback;
    public ChangePasswordViewModel(Context context, BaseInterface callback) {
        currentpassword=new ObservableField<>();
        newPassword=new ObservableField<>();
        cofirmNewPassword=new ObservableField<>();
        progress=new ObservableInt(View.GONE);
        this.context=context;
        this.callback=callback;
        userData= CustomUtils.getInstance().getSaveUserObject(context);
    }

    public void changePasswordAction(View view){
        System.out.println("Password Request New :"+newPassword.get());
        System.out.println("Password Request Confirm :"+cofirmNewPassword.get());
        if (newPassword.get()!=null && cofirmNewPassword.get()!=null && currentpassword.get()!=null) {
            if (newPassword.get().equals(cofirmNewPassword.get())) {
                if (newPassword.get().length() >= 8 && cofirmNewPassword.get().length() >= 8) {
                    handleChangePassword();
                } else
                    callback.updateUi(ConfigurationFile.Constants.INVALID_PASSWORD_FORMAT);
            } else {
                callback.updateUi(ConfigurationFile.Constants.INVALID_PASSWORD_CONFIRMATION);
            }
        }else {
            callback.updateUi(ConfigurationFile.Constants.FILL_ALL_DATTA);
        }

    }

    public void handleChangePassword(){
        if(NetWorkConnection.isConnectingToInternet(context)) {
            ChangePasswordRequest changePasswordRequest=new ChangePasswordRequest(newPassword.get(),cofirmNewPassword.get(),currentpassword.get());
            //progress.set(View.VISIBLE);
            CustomUtils.getInstance().showProgressDialog((Activity)context);
            ApiClient.getClient().create(EndPoints.class).changePasswordApi(ConfigurationFile.Constants.API_KEY, CustomUtils.getInstance().getAppLanguage(context), ConfigurationFile.Constants.Content_Type, ConfigurationFile.Constants.Content_Type, "Bearer "+ userData.getToken(),changePasswordRequest)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(changePasswordResponse -> {
                     //   progress.set(View.GONE);
                        CustomUtils.getInstance().cancelDialog();
                        System.out.println("Password Request Error 2:"+changePasswordResponse.code());
                        System.out.println("Handle Register BASE:" + changePasswordResponse.code());
                        if (changePasswordResponse.code() ==ConfigurationFile.Constants.SUCCESS_CODE){
                            currentpassword.set("");
                            newPassword.set("");
                            cofirmNewPassword.set("");
                            callback.updateUi(ConfigurationFile.Constants.SUCCESS_CODE);
                        }else if (changePasswordResponse.code() ==ConfigurationFile.Constants.INVALID_DATA){
                            callback.updateUi(ConfigurationFile.Constants.INVALID_DATA);
                        }else if (changePasswordResponse.code() ==ConfigurationFile.Constants.CANT_COMPLETE_REQUEST_CODE){
                            callback.updateUi(ConfigurationFile.Constants.CANT_COMPLETE_REQUEST_CODE);
                        }else if (changePasswordResponse.code() ==ConfigurationFile.Constants.UNSUBSCRIBE_CODE ||
                                changePasswordResponse.code() ==ConfigurationFile.Constants.UNAUTHENTICATED_CODE){
                            CustomUtils.getInstance().endSession((Activity)context);
                        }
                    }, throwable -> {
                        CustomUtils.getInstance().cancelDialog();
                        System.out.println("Password Request Error :" + throwable.getMessage());
                        // progress.set(View.GONE);}
                    });
        }else {
            callback.updateUi(ConfigurationFile.Constants.NO_INTERNET_CONNECTION_CODE);
        }
    }
}
