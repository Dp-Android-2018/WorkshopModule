package com.example.dell.workshopmodule.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableInt;
import android.databinding.ObservableList;
import android.view.View;

import com.example.dell.workshopmodule.BR;
import com.example.dell.workshopmodule.R;
import com.example.dell.workshopmodule.model.global.BaseModel;
import com.example.dell.workshopmodule.model.global.CountryItem;
import com.example.dell.workshopmodule.model.response.CityResponse;
import com.example.dell.workshopmodule.model.response.CountryResponse;
import com.example.dell.workshopmodule.network.ApiClient;
import com.example.dell.workshopmodule.network.EndPoints;
import com.example.dell.workshopmodule.utils.ConfigurationFile;
import com.example.dell.workshopmodule.utils.ConstantsCallsUtils;
import com.example.dell.workshopmodule.utils.NetWorkConnection;
import com.example.dell.workshopmodule.utils.SharedPrefrenceUtils;
import com.example.dell.workshopmodule.view.ui.callback.BaseInterface;
import com.example.dell.workshopmodule.view.ui.callback.NetworkCallback;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by DELL on 05/04/2018.
 */

public class CustomDialogCitiesViewModel extends BaseObservable {

    private Context context;
    private CompositeDisposable compositeDisposable;
    public ObservableInt progressDialog;
    public ObservableList<BaseModel> cities;
    private int countryId;
    private BaseInterface callback;
    private SharedPrefrenceUtils prefrenceUtils;
    public CustomDialogCitiesViewModel(Context context, int countryId, BaseInterface callback) {
        this.context=context;
        cities=new ObservableArrayList<>();
        compositeDisposable=new CompositeDisposable();
        progressDialog=new ObservableInt(View.GONE);
        this.countryId=countryId;
        this.callback=callback;
        prefrenceUtils=new SharedPrefrenceUtils(context);
        if(countryId!=-1)
            getCitiesCall();
        else
            initializeServiceProviderType();

    }

    public void getCitiesCall() {
        if(NetWorkConnection.isConnectingToInternet(context)) {
            progressDialog.set(View.VISIBLE);
            Disposable disposable = ApiClient.getClient().create(EndPoints.class).getCities(ConfigurationFile.Constants.API_KEY, "EN", ConfigurationFile.Constants.Content_Type, ConfigurationFile.Constants.Content_Type, countryId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Response<CityResponse>>() {
                        @Override
                        public void accept(Response<CityResponse> cityResponseResponse) throws Exception {
                            progressDialog.set(View.GONE);
                            if (cityResponseResponse.code() == ConfigurationFile.Constants.SUCCESS_CODE) {
                                cities.clear();
                                cities.addAll(cityResponseResponse.body().getData());
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            progressDialog.set(View.GONE);
                        }
                    });

            compositeDisposable.add(disposable);
        }else {
            callback.updateUi(ConfigurationFile.Constants.NO_INTERNET_CONNECTION_CODE);
        }

    }


    public void initializeServiceProviderType(){
        cities.clear();

        BaseModel model1 =new BaseModel();
        model1.setId(2);
        model1.setName(context.getResources().getString(R.string.wensh));

        BaseModel model2 =new BaseModel();
        model2.setId(3);
        model2.setName(context.getResources().getString(R.string.service_provider));

        cities.add(model1);
        cities.add(model2);
    }
}
