package com.example.dell.workshopmodule.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableInt;
import android.databinding.ObservableList;
import android.view.View;

import com.example.dell.workshopmodule.model.global.CountryItem;
import com.example.dell.workshopmodule.model.response.CountryResponse;
import com.example.dell.workshopmodule.network.ApiClient;
import com.example.dell.workshopmodule.network.EndPoints;
import com.example.dell.workshopmodule.utils.ConfigurationFile;
import com.example.dell.workshopmodule.utils.ConstantsCallsUtils;
import com.example.dell.workshopmodule.utils.NetWorkConnection;
import com.example.dell.workshopmodule.utils.SharedPrefrenceUtils;
import com.example.dell.workshopmodule.view.ui.callback.BaseInterface;
import com.example.dell.workshopmodule.view.ui.callback.NetworkCallback;
import com.google.gson.Gson;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by DELL on 03/04/2018.
 */

public class CustomDialogCountriesViewModel extends BaseObservable {
    private Context context;
    private CompositeDisposable compositeDisposable;
    public ObservableList<CountryItem>countries;
    private  SharedPrefrenceUtils prefrenceUtils;
    public ObservableInt progressBar;
    private BaseInterface callback;
    public CustomDialogCountriesViewModel(Context context,BaseInterface callback) {
        this.context=context;
        compositeDisposable=new CompositeDisposable();
        countries=new ObservableArrayList<>();
         prefrenceUtils=new SharedPrefrenceUtils(context);
         progressBar=new ObservableInt(View.GONE);
         this.callback=callback;
        getCountries();
    }

    public void getCountries(){
        countries.clear();
        CountryResponse countryResponse=(CountryResponse) prefrenceUtils.getSavedObject(ConfigurationFile.SharedPrefConstants.PREF_COUNTRIES_OBJECT,CountryResponse.class);
        if(countryResponse==null) {
            if(NetWorkConnection.isConnectingToInternet(context)) {
                progressBar.set(View.VISIBLE);
                ConstantsCallsUtils.getInstance().getCountries(new NetworkCallback() {
                    @Override
                    public <E> void onSuccess(Object response, int code) {
                        progressBar.set(View.GONE);
                        if (code == ConfigurationFile.Constants.SUCCESS_CODE) {
                            CountryResponse countryResponse = (CountryResponse) response;
                            prefrenceUtils.saveObjectToSharedPreferences(ConfigurationFile.SharedPrefConstants.PREF_COUNTRIES_OBJECT, countryResponse);
                            countries.addAll(countryResponse.getData());
                        }
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        progressBar.set(View.GONE);
                    }
                });
            }else {
                callback.updateUi(ConfigurationFile.Constants.NO_INTERNET_CONNECTION_CODE);
            }
        }else {
            countries.addAll(countryResponse.getData());
        }
    }
}
