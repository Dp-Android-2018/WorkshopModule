package com.dp.dell.workshopmodule.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.dp.dell.workshopmodule.BR;
import com.dp.dell.workshopmodule.model.global.RequestData;
import com.dp.dell.workshopmodule.model.global.UserData;
import com.dp.dell.workshopmodule.model.response.OfferData;
import com.dp.dell.workshopmodule.model.response.WorkshopOfferResponse;
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
