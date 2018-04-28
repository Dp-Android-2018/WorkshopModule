package com.example.dell.workshopmodule.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.example.dell.workshopmodule.R;
import com.example.dell.workshopmodule.model.request.LoginRequest;
import com.example.dell.workshopmodule.model.global.UserData;
import com.example.dell.workshopmodule.model.response.LoginResponse;
import com.example.dell.workshopmodule.network.ApiClient;
import com.example.dell.workshopmodule.network.EndPoints;
import com.example.dell.workshopmodule.notification.MyFirebaseInstanceIdService;
import com.example.dell.workshopmodule.utils.ConfigurationFile;
import com.example.dell.workshopmodule.utils.NetWorkConnection;
import com.example.dell.workshopmodule.utils.SharedPrefrenceUtils;
import com.example.dell.workshopmodule.utils.ValidationUtils;
import com.example.dell.workshopmodule.view.ui.activity.FirstStepRegisterActivity;
import com.example.dell.workshopmodule.view.ui.callback.BaseInterface;
import com.example.dell.workshopmodule.view.ui.callback.NetworkCallback;
import com.google.android.gms.internal.zzahn;
import com.google.firebase.FirebaseApp;
import com.google.gson.Gson;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.Observable;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Response;

/**
 * Created by DELL on 17/03/2018.
 */

public class LoginViewModel extends Observable {

    public ObservableField<String> email;
    public  ObservableField<String> password;
    public ObservableField<String> phone;
    public ObservableInt progressDialog;
    private   CompositeDisposable compositeDisposable;
    private Context context;
    private BaseInterface callback;
    private SharedPrefrenceUtils pref;
    public LoginViewModel(Context context, BaseInterface callback) {
        this.context=context;
        this.callback=callback;
        initializeVariables();
        getFirebaseToken();
    }

    public void initializeVariables(){
        email=new ObservableField<>();
        password=new ObservableField<>();
        phone=new ObservableField<>();
        progressDialog=new ObservableInt(View.GONE);
        compositeDisposable=new CompositeDisposable();
    }

    public String getFirebaseToken(){
        final MyFirebaseInstanceIdService mfs=new MyFirebaseInstanceIdService();
        FirebaseApp.initializeApp(context);
        zzahn.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mfs.onTokenRefresh();
            }
        });
        System.out.println("User Token :"+ MyFirebaseInstanceIdService.TOKEN);
        return MyFirebaseInstanceIdService.TOKEN;
    }

    public void checkLogin(final View view){


        System.out.println("Login Data :"+email.get() +password.get());
        if(ValidationUtils.isEmpty(email.get()) || ValidationUtils.isEmpty(password.get())){
            callback.updateUi(ConfigurationFile.Constants.FILL_ALL_DATTA);
        }else {
            if(NetWorkConnection.isConnectingToInternet(context)) {
                if (!ValidationUtils.isMail(email.get()) && ValidationUtils.isPhone(email.get())) {
                    phone.set(email.get());
                    email.set(null);
                }

                progressDialog.set(View.VISIBLE);
                final LoginRequest loginRequest = new LoginRequest(email.get(), phone.get(), password.get(), null);
                Disposable disposable = ApiClient.getClient().create(EndPoints.class).login(ConfigurationFile.Constants.API_KEY, "EN", ConfigurationFile.Constants.Content_Type, ConfigurationFile.Constants.Content_Type, loginRequest)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Response<LoginResponse>>() {
                            @Override
                            public void accept(Response<LoginResponse> loginResponseResponse) throws Exception {
                                progressDialog.set(View.GONE);
                                if (loginResponseResponse.code() == ConfigurationFile.Constants.SUCCESS_CODE) {
                                    LoginResponse loginResponse = loginResponseResponse.body();
                                    System.out.println("Country Id : "+loginResponse.getData().getCountry().getId());
                                    System.out.println("Country Id 2: "+loginResponse.getData().getCity().getId());
                                    System.out.println("User Data :"+new Gson().toJson(loginResponse.getData()));
                                    saveDataToPrefs(loginResponse.getData());
                                    setChanged();
                                    if (loginResponse.getData().isSubscribed())
                                                notifyObservers();
                                    else
                                        callback.updateUi(ConfigurationFile.Constants.MOVE_TO_SUBSCRIBTION_ACTIVITY);
                                } else if (loginResponseResponse.code() == ConfigurationFile.Constants.INVALID_USERNAME_PASSWORD_LOGIN_CODE) {
                                    callback.updateUi(ConfigurationFile.Constants.INVALID_USERNAME_PASSWORD_LOGIN_CODE);
                                } else if (loginResponseResponse.code() == ConfigurationFile.Constants.INVALID_DATA) {
                                    callback.updateUi(ConfigurationFile.Constants.INVALID_DATA);
                                }
                            }

                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                progressDialog.set(View.GONE);
                                System.out.println(throwable.toString());
                            }
                        });

                compositeDisposable.add(disposable);
            }else {
                callback.updateUi(ConfigurationFile.Constants.NO_INTERNET_CONNECTION_CODE);
            }
        }
    }

    public void moveToRegisterActivity( View view){
        callback.updateUi(ConfigurationFile.Constants.MOVE_TO_REGISTER_ACTIVITY);
    }


    public void reset(){
        unSubScribe();
        compositeDisposable=null;
        context=null;
    }

    public void saveDataToPrefs(UserData data){
        pref=new SharedPrefrenceUtils(context);
        pref.saveObjectToSharedPreferences(ConfigurationFile.SharedPrefConstants.PREF_WORKSHOP_DATA,data);
    }

    public void unSubScribe(){
        if(compositeDisposable!=null && ! compositeDisposable.isDisposed())
            compositeDisposable.dispose();
    }



}
