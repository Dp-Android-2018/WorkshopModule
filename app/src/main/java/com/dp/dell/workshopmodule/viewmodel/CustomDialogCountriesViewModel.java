package com.dp.dell.workshopmodule.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableInt;
import android.databinding.ObservableList;
import android.view.View;

import com.dp.dell.workshopmodule.model.global.CountryItem;
import com.dp.dell.workshopmodule.model.response.CountryResponse;
import com.dp.dell.workshopmodule.utils.ConfigurationFile;
import com.dp.dell.workshopmodule.utils.ConstantsCallsUtils;
import com.dp.dell.workshopmodule.utils.CustomUtils;
import com.dp.dell.workshopmodule.utils.NetWorkConnection;
import com.dp.dell.workshopmodule.utils.SharedPrefrenceUtils;
import com.dp.dell.workshopmodule.view.ui.callback.BaseInterface;
import com.dp.dell.workshopmodule.view.ui.callback.NetworkCallback;

import io.reactivex.disposables.CompositeDisposable;

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
              //  progressBar.set(View.VISIBLE);
                CustomUtils.getInstance().showProgressDialog((Activity)context);
                ConstantsCallsUtils.getInstance().getCountries(new NetworkCallback() {
                    @Override
                    public <E> void onSuccess(Object response, int code) {
                       // progressBar.set(View.GONE);
                        CustomUtils.getInstance().cancelDialog();
                        if (code == ConfigurationFile.Constants.SUCCESS_CODE) {
                            CountryResponse countryResponse = (CountryResponse) response;
                            prefrenceUtils.saveObjectToSharedPreferences(ConfigurationFile.SharedPrefConstants.PREF_COUNTRIES_OBJECT, countryResponse);
                            countries.addAll(countryResponse.getData());
                        }
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                     //   progressBar.set(View.GONE);
                        CustomUtils.getInstance().cancelDialog();
                    }

                    @Override
                    public <E> void onUnuthenticated(int code) {
                        CustomUtils.getInstance().endSession((Activity) context);
                    }
                }, CustomUtils.getInstance().getAppLanguage(context));
            }else {
                callback.updateUi(ConfigurationFile.Constants.NO_INTERNET_CONNECTION_CODE);
            }
        }else {
            countries.addAll(countryResponse.getData());
        }
    }
}
