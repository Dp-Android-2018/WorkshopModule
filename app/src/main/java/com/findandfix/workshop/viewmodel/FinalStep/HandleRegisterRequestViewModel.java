package com.findandfix.workshop.viewmodel.FinalStep;

import android.app.Activity;
import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;

import com.findandfix.workshop.model.global.Conv.User;
import com.findandfix.workshop.model.global.UserData;
import com.findandfix.workshop.model.request.RegisterRequest;
import com.findandfix.workshop.model.response.LoginResponse;
import com.findandfix.workshop.network.ApiClient;
import com.findandfix.workshop.network.EndPoints;
import com.findandfix.workshop.notification.MyFirebaseInstanceIdService;
import com.findandfix.workshop.ui.Application.MyApplication;
import com.findandfix.workshop.ui.callback.BaseInterface;
import com.findandfix.workshop.utils.ConfigurationFile;
import com.findandfix.workshop.utils.CustomUtils;
import com.findandfix.workshop.utils.NetWorkConnection;
import com.findandfix.workshop.utils.SharedPrefrenceUtils;
import com.google.android.gms.internal.zzahn;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
