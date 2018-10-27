package com.dp.dell.workshopmodule.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.dp.dell.workshopmodule.model.request.LoginRequest;
import com.dp.dell.workshopmodule.model.global.UserData;
import com.dp.dell.workshopmodule.model.response.DefaultResponse;
import com.dp.dell.workshopmodule.model.response.LoginResponse;
import com.dp.dell.workshopmodule.network.ApiClient;
import com.dp.dell.workshopmodule.network.EndPoints;
import com.dp.dell.workshopmodule.notification.MyFirebaseInstanceIdService;
import com.dp.dell.workshopmodule.utils.ConfigurationFile;
import com.dp.dell.workshopmodule.utils.CustomUtils;
import com.dp.dell.workshopmodule.utils.NetWorkConnection;
import com.dp.dell.workshopmodule.utils.SharedPrefrenceUtils;
import com.dp.dell.workshopmodule.utils.ValidationUtils;
import com.dp.dell.workshopmodule.view.ui.Application.MyApplication;
import com.dp.dell.workshopmodule.view.ui.callback.BaseInterface;
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
    private String token;
    private Activity activity;
    private UserData userData=null;
    public LoginViewModel(Context context, BaseInterface callback,Activity activity) {
        this.context=context;
        this.callback=callback;
        this.activity=activity;
        initializeVariables();
        token=getFirebaseToken();
        MyApplication.getObservableComponent();
    }

    public void initializeVariables(){
        email= MyApplication.getObservableComponent().getObservable();
        password=MyApplication.getObservableComponent().getObservable();
        phone=MyApplication.getObservableComponent().getObservable();
        progressDialog=MyApplication.getObservableComponent().getObservableInt();
        compositeDisposable=MyApplication.getObservableComponent().getComposite();
    }

    public String getFirebaseToken(){
        final MyFirebaseInstanceIdService mfs=MyApplication.getFirebaseComponent().getFirebaseService();
        FirebaseApp.initializeApp(context);
        zzahn.runOnUiThread(() -> mfs.onTokenRefresh());
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
               // progressDialog.set(View.VISIBLE);
                CustomUtils.getInstance().showProgressDialog((Activity)context);
                final LoginRequest loginRequest =MyApplication.getLoginRequestComponent(email.get(), phone.get(), password.get(), token).getLoginRequest();
                Disposable disposable = ApiClient.getClient().create(EndPoints.class).login(ConfigurationFile.Constants.API_KEY, CustomUtils.getInstance().getAppLanguage(context), ConfigurationFile.Constants.Content_Type, ConfigurationFile.Constants.Content_Type, loginRequest)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(loginResponseResponse -> {
                            System.out.println("Login Response Code :"+loginResponseResponse.code());
                            CustomUtils.getInstance().cancelDialog();
                           // progressDialog.set(View.GONE);
                            if (loginResponseResponse.code() == ConfigurationFile.Constants.SUCCESS_CODE) {
                                LoginResponse loginResponse = loginResponseResponse.body();
                                userData=loginResponse.getData();
                                if (loginResponse.getData().isActive()) {
                                    if (loginResponse.getData().getSuspended()==0) {
                                        System.out.println("Json Data 2 :"+new Gson().toJson(loginResponse.getData()));
                                        saveDataToPrefs(loginResponse.getData());
                                        setChanged();
                                        notifyObservers();
                                    }else  callback.updateUi(ConfigurationFile.Constants.UNSUBSCRIBE_CODE);
                                } else activeAccount(loginResponse.getData());
                            } else if (loginResponseResponse.code() == ConfigurationFile.Constants.INVALID_USERNAME_PASSWORD_LOGIN_CODE) {
                                callback.updateUi(ConfigurationFile.Constants.INVALID_USERNAME_PASSWORD_LOGIN_CODE);
                            } else if (loginResponseResponse.code() == ConfigurationFile.Constants.INVALID_DATA) {
                                callback.updateUi(ConfigurationFile.Constants.INVALID_DATA);
                            }else if (loginResponseResponse.code() == ConfigurationFile.Constants.UNSUBSCRIBE_CODE) {
                                callback.updateUi(ConfigurationFile.Constants.UNSUBSCRIBE_CODE);
                            }
                        }, throwable -> {
                            System.out.println("Login Ex:"+throwable.getMessage());
                            CustomUtils.getInstance().cancelDialog();
                           // progressDialog.set(View.GONE);
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
        pref=MyApplication.getSharedprefUtilsComponent().getSharedPrefUtils();
        pref.saveObjectToSharedPreferences(ConfigurationFile.SharedPrefConstants.PREF_WORKSHOP_DATA,data);
    }

    public void unSubScribe(){
        if(compositeDisposable!=null && ! compositeDisposable.isDisposed())
            compositeDisposable.dispose();
    }


    public void activeAccount(UserData userData){

        if(NetWorkConnection.isConnectingToInternet(context)) {
            CustomUtils.getInstance().showProgressDialog(activity);
            ApiClient.getClient().create(EndPoints.class).activeAccount(ConfigurationFile.Constants.API_KEY, CustomUtils.getInstance().getAppLanguage(context), ConfigurationFile.Constants.Content_Type, ConfigurationFile.Constants.Content_Type, "Bearer "+userData.getToken())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(defaultResponseResponse -> {
                        CustomUtils.getInstance().cancelDialog();
                        int code=defaultResponseResponse.code();
                        System.out.println("Code :"+code);
                        if(code==ConfigurationFile.Constants.SUCCESS_CODE){
                            // dialog.dismiss();
                         //   Snackbar.make(activity.findViewById(R.id.drawer), R.string.account_activated,Snackbar.LENGTH_LONG).show();
                            callback.updateUi(ConfigurationFile.Constants.ACCOUNT_ACTIVATED_SUCCESSFULLY);
                            System.out.println("Json Data 2:"+new Gson().toJson(userData));
                            saveDataToPrefs(userData);
                            setChanged();
                            notifyObservers();
                            new Handler().postDelayed(() -> notifyObservers(),Snackbar.LENGTH_LONG);
                        }else if(code==ConfigurationFile.Constants.UNAUTHENTICATED_CODE || code==ConfigurationFile.Constants.UNSUBSCRIBE_CODE){
                            CustomUtils.getInstance().logout(activity);
                        }

                    }, throwable -> {
                        CustomUtils.getInstance().cancelDialog();
                        System.out.println("Normat Request :"+throwable.getMessage());
                    });
        }else {
          //  Snackbar.make(activity.findViewById(R.id.ll_container),context.getResources().getString(R.string.internet_connection),Snackbar.LENGTH_LONG).show();
            callback.updateUi(ConfigurationFile.Constants.NO_INTERNET_CONNECTION_CODE);
        }
    }





}
