package com.dp.dell.workshopmodule.viewmodel.FinalStep;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;


import com.dp.dell.workshopmodule.model.global.Conv.User;
import com.dp.dell.workshopmodule.model.global.UserData;
import com.dp.dell.workshopmodule.model.request.RegisterRequest;
import com.dp.dell.workshopmodule.model.response.LoginResponse;
import com.dp.dell.workshopmodule.network.ApiClient;
import com.dp.dell.workshopmodule.network.EndPoints;
import com.dp.dell.workshopmodule.notification.MyFirebaseInstanceIdService;
import com.dp.dell.workshopmodule.utils.ConfigurationFile;
import com.dp.dell.workshopmodule.utils.CustomUtils;
import com.dp.dell.workshopmodule.utils.NetWorkConnection;
import com.dp.dell.workshopmodule.utils.SharedPrefrenceUtils;
import com.dp.dell.workshopmodule.view.ui.Application.MyApplication;
import com.dp.dell.workshopmodule.view.ui.callback.BaseInterface;
import com.google.android.gms.internal.zzahn;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by DELL on 26/03/2018.
 */

public class HandleRegisterRequestViewModel extends BaseObservable {

    private Context context;
    public ObservableBoolean progressDialog;
    private BaseInterface callback;
    private DatabaseReference reference;
    private String token;
    public HandleRegisterRequestViewModel(Context context,BaseInterface callback) {
        this.context=context;
        this.callback=callback;
        progressDialog=new ObservableBoolean();
        token=getFirebaseToken();
    }

    public String getFirebaseToken(){
        final MyFirebaseInstanceIdService mfs= MyApplication.getFirebaseComponent().getFirebaseService();
        FirebaseApp.initializeApp(context);
        zzahn.runOnUiThread(() -> mfs.onTokenRefresh());
        System.out.println("User Token :"+ MyFirebaseInstanceIdService.TOKEN);
        return MyFirebaseInstanceIdService.TOKEN;
    }

    public void handleRegister(RegisterRequest registerRequest, final BaseInterface callback){
        registerRequest.setDeviceToken(token);
        if(NetWorkConnection.isConnectingToInternet(context)) {
            System.out.println("Register Request :"+new Gson().toJson(registerRequest));
           // progressDialog.set(true);
            CustomUtils.getInstance().showProgressDialog((Activity)context);
            ApiClient.getClient().create(EndPoints.class).register(ConfigurationFile.Constants.API_KEY, CustomUtils.getInstance().getAppLanguage(context), ConfigurationFile.Constants.Content_Type, ConfigurationFile.Constants.Content_Type, registerRequest)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(loginResponseResponse -> {
                        //progressDialog.set(false);
                        CustomUtils.getInstance().cancelDialog();
                        System.out.println("Handle Register BASE:" + loginResponseResponse.code());
                        if (loginResponseResponse.code() == ConfigurationFile.Constants.SUCCESS_REGISTER_CODE) {
                            System.out.println("Handle Register :" + loginResponseResponse.code());
                            LoginResponse loginResponse = loginResponseResponse.body();
                            UserData data = loginResponse.getData();
                            addUserToFirebase(data);
                            SharedPrefrenceUtils pref = new SharedPrefrenceUtils(context);
                            pref.saveObjectToSharedPreferences(ConfigurationFile.SharedPrefConstants.PREF_WORKSHOP_DATA, data);
                            callback.updateUi(ConfigurationFile.Constants.MOVE_TO_NEXT_ACTIVITY);

                        } else if (loginResponseResponse.code() == ConfigurationFile.Constants.INVALID_DATA) {
                            System.out.println("Handle Register :" + loginResponseResponse.code()+loginResponseResponse.message());
                            callback.updateUi(ConfigurationFile.Constants.INVALID_DATA);
                        }
                    }, throwable -> {
                        CustomUtils.getInstance().cancelDialog();
                     //   progressDialog.set(false);
                        System.out.println("Handle Register :" + throwable.getMessage());
                    });
        }else {
            callback.updateUi(ConfigurationFile.Constants.NO_INTERNET_CONNECTION_CODE);
        }
    }




    public void addUserToFirebase(UserData data){
        reference= FirebaseDatabase.getInstance().getReference("users/workshop-"+data.getId());
        User user=new User();
        user.email=data.getEmail();
        user.name=data.getName();
        user.profilePicLink="";
        reference.child("credentials").setValue(user).addOnCompleteListener(task -> {
           /* callback.updateUi(ConfigurationFile.Constants.MOVE_TO_MAIN_ACT);
            (context).finishAffinity();*/
        });
    }

}
