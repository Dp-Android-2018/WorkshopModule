package com.findandfix.workshop.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.findandfix.workshop.BR;
import com.findandfix.workshop.model.global.RequestData;
import com.findandfix.workshop.model.global.UserData;
import com.findandfix.workshop.model.response.OfferData;
import com.findandfix.workshop.network.ApiClient;
import com.findandfix.workshop.network.EndPoints;
import com.findandfix.workshop.ui.callback.BaseInterface;
import com.findandfix.workshop.utils.ConfigurationFile;
import com.findandfix.workshop.utils.CustomUtils;
import com.findandfix.workshop.utils.NetWorkConnection;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by DELL on 27/03/2018.
 */

public class OfferInfoViewModel extends BaseObservable {
    private Context context;
    private BaseInterface callback;
    private RequestData requestData;
    private UserData userData;
    private String offerDesc;
    private String salaryRange;
    private String periodRange;
    public OfferInfoViewModel(Context context, BaseInterface callback, RequestData requestData) {
        this.callback=callback;
        this.context=context;
        this.requestData=requestData;
        userData= CustomUtils.getInstance().getSaveUserObject(context);

        getWorkshopOffer();
    }

    public void getWorkshopOffer(){
        if(NetWorkConnection.isConnectingToInternet(context)) {
            CustomUtils.getInstance().showProgressDialog((Activity)context);
            System.out.println("Code cONNECTING");
            ApiClient.getClient().create(EndPoints.class).getWorkshopOffer(ConfigurationFile.Constants.API_KEY, CustomUtils.getInstance().getAppLanguage(context), ConfigurationFile.Constants.Content_Type, ConfigurationFile.Constants.Content_Type, "Bearer "+userData.getToken(),requestData.getId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(workShopRequestResponseResponse -> {
                        CustomUtils.getInstance().cancelDialog();
                        int code=workShopRequestResponseResponse.code();
                        System.out.println("Code :"+code);
                        if(code==ConfigurationFile.Constants.SUCCESS_CODE){
                           OfferData data=workShopRequestResponseResponse.body().getData();
                            setOfferDesc(data.getDescription());
                            setPeriodRange(data.getDurationFrom()+"-"+data.getDurationTo());
                            setSalaryRange(data.getCostFrom()+"-"+data.getCostTo());
                        }else if(code==ConfigurationFile.Constants.UNAUTHENTICATED_CODE || code==ConfigurationFile.Constants.UNSUBSCRIBE_CODE){
                            callback.updateUi(ConfigurationFile.Constants.UNAUTHENTICATED_CODE);
                        }else if(code==ConfigurationFile.Constants.CANT_COMPLETE_REQUEST_CODE ){
                            callback.updateUi(ConfigurationFile.Constants.CANT_COMPLETE_REQUEST_CODE );
                        }else if(code==ConfigurationFile.Constants.UNSUBSCRIBED_CODE){

                        }

                    }, throwable -> {
                        CustomUtils.getInstance().cancelDialog();
                        System.out.println("Normat Request :"+throwable.getMessage());
                    });
        }else {
            callback.updateUi(ConfigurationFile.Constants.NO_INTERNET_CONNECTION_CODE);
        }
    }

    @Bindable
    public String getOfferDesc(){
        return offerDesc;
    }

    public void setOfferDesc(String offerDesc){
        this.offerDesc=offerDesc;
        notifyPropertyChanged(BR.offerDesc);
    }


    @Bindable
    public String getSalaryRange() {
        return salaryRange+" average Salary";

    }



    public void setSalaryRange(String salaryRange) {
        this.salaryRange = salaryRange;
        notifyPropertyChanged(BR.salaryRange);
    }

    @Bindable
    public String getPeriodRange() {
        return periodRange+" average Date";
    }

    public void setPeriodRange(String periodRange) {
        this.periodRange = periodRange;
        notifyPropertyChanged(BR.periodRange);
    }

}
