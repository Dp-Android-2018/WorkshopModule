package com.findandfix.workshop.utils;

import com.findandfix.workshop.model.response.BrandsResponse;
import com.findandfix.workshop.model.response.CityResponse;
import com.findandfix.workshop.model.response.CountryResponse;
import com.findandfix.workshop.model.response.SpecializationResponse;
import com.findandfix.workshop.model.response.UrgentRequestTypeResponse;
import com.findandfix.workshop.network.ApiClient;
import com.findandfix.workshop.network.EndPoints;
import com.findandfix.workshop.ui.callback.NetworkCallback;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by DELL on 04/04/2018.
 */

public class ConstantsCallsUtils {

    private static ConstantsCallsUtils constantsCallsUtils;
    private CountryResponse countryResponse=null;
    private BrandsResponse brandsResponse=null;
    private SpecializationResponse specializationResponse=null;
    private UrgentRequestTypeResponse urgentRequestTypeResponse=null;
    private CityResponse cityResponse=null;
    private ConstantsCallsUtils(){}
    public static ConstantsCallsUtils getInstance(){
        if (constantsCallsUtils==null)
            constantsCallsUtils=new ConstantsCallsUtils();
        return constantsCallsUtils;
    }


    public void getCountries(final NetworkCallback callback, String lang){
        System.out.println("Countries Connecting");
         ApiClient.getClient().create(EndPoints.class).getCountries(ConfigurationFile.Constants.API_KEY,lang,ConfigurationFile.Constants.Content_Type,ConfigurationFile.Constants.Content_Type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(countryResponseResponse -> {
                    System.out.println("Countries CODE :"+countryResponseResponse.code());
                    if (countryResponseResponse.code()==ConfigurationFile.Constants.SUCCESS_CODE) {
                        CountryResponse response=countryResponseResponse.body();
                        callback.onSuccess(response,ConfigurationFile.Constants.SUCCESS_CODE);
                    }else if (countryResponseResponse.code()==ConfigurationFile.Constants.UNAUTHENTICATED_CODE ||
                            countryResponseResponse.code()==ConfigurationFile.Constants.UNSUBSCRIBE_CODE){
                        callback.onUnuthenticated(countryResponseResponse.code());
                    }
                }, throwable -> {
                    System.out.println("Ex :"+throwable.getMessage());
                    callback.onFailure(throwable);
                });

    }


    public void getBrands(final NetworkCallback callback, String lang) {

        ApiClient.getClient().create(EndPoints.class).getBrands(ConfigurationFile.Constants.API_KEY,lang, ConfigurationFile.Constants.Content_Type, ConfigurationFile.Constants.Content_Type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(brandsResponseResponse -> {
                    if (brandsResponseResponse.code() == ConfigurationFile.Constants.SUCCESS_CODE) {
                        brandsResponse = brandsResponseResponse.body();
                        callback.onSuccess(brandsResponse, ConfigurationFile.Constants.SUCCESS_CODE);
                    }else if (brandsResponseResponse.code()==ConfigurationFile.Constants.UNAUTHENTICATED_CODE ||
                            brandsResponseResponse.code()==ConfigurationFile.Constants.UNSUBSCRIBE_CODE){
                        callback.onUnuthenticated(brandsResponseResponse.code());
                    }
                }, throwable -> callback.onFailure(throwable));
    }

    public void getSpecializations(final NetworkCallback callback, String lang) {
        ApiClient.getClient().create(EndPoints.class).getSpecializations(ConfigurationFile.Constants.API_KEY,lang, ConfigurationFile.Constants.Content_Type, ConfigurationFile.Constants.Content_Type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(specializationResponseResponse -> {

                    if (specializationResponseResponse.code() == ConfigurationFile.Constants.SUCCESS_CODE) {
                        specializationResponse = specializationResponseResponse.body();
                        callback.onSuccess(specializationResponse, ConfigurationFile.Constants.SUCCESS_CODE);
                    }else if (specializationResponseResponse.code()==ConfigurationFile.Constants.UNAUTHENTICATED_CODE ||
                            specializationResponseResponse.code()==ConfigurationFile.Constants.UNSUBSCRIBE_CODE){
                        callback.onUnuthenticated(specializationResponseResponse.code());
                    }

                }, throwable -> callback.onFailure(throwable));


    }


    public void getUrgentRequestTypes(final NetworkCallback callback, String lang) {

        ApiClient.getClient().create(EndPoints.class).getUrgentTypes(ConfigurationFile.Constants.API_KEY,lang, ConfigurationFile.Constants.Content_Type, ConfigurationFile.Constants.Content_Type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(urgentRequestTypeResponseResponse -> {
                    if (urgentRequestTypeResponseResponse.code() == ConfigurationFile.Constants.SUCCESS_CODE) {
                        urgentRequestTypeResponse = urgentRequestTypeResponseResponse.body();
                        callback.onSuccess(urgentRequestTypeResponse, ConfigurationFile.Constants.SUCCESS_CODE);
                    }else if (urgentRequestTypeResponseResponse.code()==ConfigurationFile.Constants.UNAUTHENTICATED_CODE ||
                            urgentRequestTypeResponseResponse.code()==ConfigurationFile.Constants.UNSUBSCRIBE_CODE){
                        callback.onUnuthenticated(urgentRequestTypeResponseResponse.code());
                    }

                }, throwable -> callback.onFailure(throwable));

    }



}
