package com.dp.dell.workshopmodule.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.view.View;

import com.dp.dell.workshopmodule.R;
import com.dp.dell.workshopmodule.model.global.Notification;
import com.dp.dell.workshopmodule.model.global.Payload;
import com.dp.dell.workshopmodule.model.global.RequestData;
import com.dp.dell.workshopmodule.model.global.UserData;
import com.dp.dell.workshopmodule.model.request.AddOfferRequest;
import com.dp.dell.workshopmodule.model.response.DefaultResponse;
import com.dp.dell.workshopmodule.model.response.OfferData;
import com.dp.dell.workshopmodule.network.ApiClient;
import com.dp.dell.workshopmodule.network.EndPoints;
import com.dp.dell.workshopmodule.utils.ConfigurationFile;
import com.dp.dell.workshopmodule.utils.CustomUtils;
import com.dp.dell.workshopmodule.utils.NetWorkConnection;
import com.dp.dell.workshopmodule.utils.ValidationUtils;
import com.dp.dell.workshopmodule.view.ui.callback.BaseInterface;
import com.google.gson.Gson;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by DELL on 28/03/2018.
 */

public class AddOfferViewModel extends BaseObservable {

    public ObservableInt selectorPosition;
    public ObservableInt selectorDate;
    public ObservableField<String>description;
    private Context context;
    private UserData userData;
    private RequestData requestData;
    private int priceFrom;
    private int priceTo;
    private int dateFrom;
    private int dateTo;
    private BaseInterface callback;
    public ObservableInt requestTypeObservable;
    private int offerId;
    private OfferData data;
    public AddOfferViewModel(Context context, RequestData requestData, int requestType, BaseInterface callback, OfferData data) {
        this.context=context;
        description=new ObservableField<>();
        selectorPosition=new ObservableInt(0);
        selectorDate=new ObservableInt(0);
        this.requestData=requestData;
        requestTypeObservable=new ObservableInt();
        requestTypeObservable.set(requestType);
        this.callback=callback;
        this.data=data;
        userData= CustomUtils.getInstance().getSaveUserObject(context);
        if (requestType==ConfigurationFile.Constants.OFFERED_REQUEST_DETAIL)
            getWorkshopOffer();
    }

    public void priceSelector(View view){
        if(view.getId()== R.id.rl_price_1){
            setPrice(1,0,99);
        }else if(view.getId()== R.id.rl_price_2){
            setPrice(2,100,499);
        }else if(view.getId()== R.id.rl_price_3){
            setPrice(3,500,999);
        }else if (view.getId()== R.id.rl_price_4){
            setPrice(4,1000,10000);
        }
    }

    public void dateSelector(View view){
        if(view.getId()== R.id.rl_date_1){
            setDate(1,1,6);
        }else if(view.getId()== R.id.rl_date_2){
            setDate(2,7,14);
        }else if(view.getId()== R.id.rl_date_3){
            setDate(3,15,29);
        }else if (view.getId()== R.id.rl_date_4){
            setDate(4,30,60);
        }
    }

    public void addOfferAction(View view){
        if(description.get()!=null && !ValidationUtils.isEmpty(description.get())){
            if (selectorPosition.get()!=0){
                if (selectorDate.get()!=0){
                    if (requestTypeObservable.get()==ConfigurationFile.Constants.OFFERED_REQUEST_DETAIL)
                        updateOffer();
                    else
                        addOffer();
                }else {
                    callback.updateUi(ConfigurationFile.Constants.EMPTY_OFFER_DATE_CODE);
                }
            }else {
                callback.updateUi(ConfigurationFile.Constants.EMPTY_OFFER_PRICE_CODE);
            }

        }else {
            callback.updateUi(ConfigurationFile.Constants.EMPTY_OFFER_DESCRIBTION_CODE);
        }
    }

    public void addOffer(){
        if(NetWorkConnection.isConnectingToInternet(context)) {
            CustomUtils.getInstance().showProgressDialog((Activity)context);
            System.out.println("Add Offer Tokenh :"+userData.getToken());
            System.out.println("Add Offer id :"+requestData.getId());
            Notification notification=new Notification();
            notification.setKey(2);
            System.out.println("Add Offer request Device Token  :"+requestData.getCarowner().getDeviceToken());
            notification.setDeviceToken(requestData.getCarowner().getDeviceToken());
            Payload payload=new Payload();
            payload.setRequestId(String.valueOf(requestData.getId()));
            payload.setNotificationTitle("new_offer");
            payload.setKey(2);
            notification.setData(payload);
            //NotificationExp notificationExp=new NotificationExp("Yarb","Yarb");
           // notification.setNotificationExp(notificationExp);
            AddOfferRequest addOfferRequest=new AddOfferRequest(description.get(),dateFrom,dateTo,priceFrom,priceTo,notification);
            System.out.println("Add Offer request :"+new Gson().toJson(addOfferRequest));
            ApiClient.getClient().create(EndPoints.class).addWorkshopOffer(ConfigurationFile.Constants.API_KEY, CustomUtils.getInstance().getAppLanguage(context), ConfigurationFile.Constants.Content_Type, ConfigurationFile.Constants.Content_Type, "Bearer " + userData.getToken(),addOfferRequest, requestData.getId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(defaultResponseResponse -> {
                        System.out.println("Add Offer Code :"+defaultResponseResponse.code());
                        CustomUtils.getInstance().cancelDialog();
                        if (defaultResponseResponse.code() == ConfigurationFile.Constants.OFFER_ADDED_SUCCESSFULLY) {
                            callback.updateUi(ConfigurationFile.Constants.OFFER_ADDED_SUCCESSFULLY);
                        } else if (defaultResponseResponse.code() == ConfigurationFile.Constants.UNAUTHENTICATED_CODE ||
                                defaultResponseResponse.code() == ConfigurationFile.Constants.UNSUBSCRIBE_CODE) {
                            callback.updateUi(ConfigurationFile.Constants.UNAUTHENTICATED_CODE);

                        } else if (defaultResponseResponse.code() == ConfigurationFile.Constants.CANT_COMPLETE_REQUEST_CODE) {
                            callback.updateUi(ConfigurationFile.Constants.CANT_COMPLETE_REQUEST_CODE);
                        }
                    }, throwable -> {
                        CustomUtils.getInstance().cancelDialog();
                        System.out.println("Add Offer Ex :" + throwable.getMessage());
                    });
        }else {
            callback.updateUi(ConfigurationFile.Constants.NO_INTERNET_CONNECTION_CODE);
        }
    }



    public void updateOffer(){
        if(NetWorkConnection.isConnectingToInternet(context)) {
            System.out.println("Update Offer Id :"+offerId);
            CustomUtils.getInstance().showProgressDialog((Activity)context);
            AddOfferRequest addOfferRequest=new AddOfferRequest(description.get(),dateFrom,dateTo,priceFrom,priceTo,null);
            ApiClient.getClient().create(EndPoints.class).updateWorkshopOffer(ConfigurationFile.Constants.API_KEY, CustomUtils.getInstance().getAppLanguage(context), ConfigurationFile.Constants.Content_Type, ConfigurationFile.Constants.Content_Type, "Bearer " + userData.getToken(),addOfferRequest, offerId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(defaultResponseResponse -> {
                        CustomUtils.getInstance().cancelDialog();
                        System.out.println("Update Offer Code :"+defaultResponseResponse.code());
                        if (defaultResponseResponse.code() == ConfigurationFile.Constants.SUCCESS_CODE) {
                            callback.updateUi(ConfigurationFile.Constants.SUCCESS_CODE);
                        } else if (defaultResponseResponse.code() == ConfigurationFile.Constants.UNAUTHENTICATED_CODE ||
                                defaultResponseResponse.code() == ConfigurationFile.Constants.UNSUBSCRIBE_CODE ) {
                            callback.updateUi(ConfigurationFile.Constants.UNAUTHENTICATED_CODE);

                        } else if (defaultResponseResponse.code() == ConfigurationFile.Constants.CANT_COMPLETE_REQUEST_CODE) {
                            callback.updateUi(ConfigurationFile.Constants.CANT_COMPLETE_REQUEST_CODE);
                        }
                    }, throwable -> {
                        CustomUtils.getInstance().cancelDialog();
                        System.out.println("Update Offer Ex :" + throwable.getMessage());
                    });
        }else {
            callback.updateUi(ConfigurationFile.Constants.NO_INTERNET_CONNECTION_CODE);
        }
    }




    public void getWorkshopOffer(){
       /* if(NetWorkConnection.isConnectingToInternet(context)) {
            System.out.println("Code cONNECTING");
            ApiClient.getClient().create(EndPoints.class).getWorkshopOffer(ConfigurationFile.Constants.API_KEY, "EN", ConfigurationFile.Constants.Content_Type, ConfigurationFile.Constants.Content_Type, "Bearer "+userData.getToken(),requestId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Response<WorkshopOfferResponse>>() {
                        @Override
                        public void accept(Response<WorkshopOfferResponse> workShopRequestResponseResponse) throws Exception {
                            int code=workShopRequestResponseResponse.code();
                            System.out.println("Code :"+code);
                            if(code==ConfigurationFile.Constants.SUCCESS_CODE){
                                OfferData data=workShopRequestResponseResponse.body().getData();
                                offerId=data.getId();
                                description.set(data.getDescription());
                                ///////////////////////////////////////
                                   if(data.getDurationFrom()==1){
                                       setDate(1,1,6);
                                    }else if(data.getDurationFrom()==7){
                                       setDate(2,7,14);
                                    }else if(data.getDurationFrom()==15){
                                       setDate(3,15,29);
                                    }else if(data.getDurationFrom()==30){
                                       setDate(4,30,60);
                                    }
                                    ////////////////////////////////////////////////
                                //////////////////////////////////////////////////////
                                if (data.getCostFrom()==0){
                                    setPrice(1,0,99);
                                } else if (data.getCostFrom()==100){
                                    setPrice(2,100,499);
                                } else if (data.getCostFrom()==500){
                                    setPrice(3,500,999);
                                } else if (data.getCostFrom()==1000){
                                    setPrice(4,1000,10000);
                                }
                            }else if(code==ConfigurationFile.Constants.UNAUTHENTICATED_CODE){
                                    callback.updateUi(ConfigurationFile.Constants.UNAUTHENTICATED_CODE);
                            }else if(code==ConfigurationFile.Constants.CANT_COMPLETE_REQUEST_CODE ){
                                callback.updateUi(ConfigurationFile.Constants.CANT_COMPLETE_REQUEST_CODE );
                            }else if(code==ConfigurationFile.Constants.UNSUBSCRIBED_CODE){

                            }

                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            System.out.println("Normat Request :"+throwable.getMessage());
                        }
                    });
        }else {
            callback.updateUi(ConfigurationFile.Constants.NO_INTERNET_CONNECTION_CODE);
        }*/
        description.set(data.getDescription());
        ///////////////////////////////////////
        if(data.getDurationFrom()==1){
            setDate(1,1,6);
        }else if(data.getDurationFrom()==7){
            setDate(2,7,14);
        }else if(data.getDurationFrom()==15){
            setDate(3,15,29);
        }else if(data.getDurationFrom()==30){
            setDate(4,30,60);
        }
        ////////////////////////////////////////////////
        //////////////////////////////////////////////////////
        if (data.getCostFrom()==0){
            setPrice(1,0,99);
        } else if (data.getCostFrom()==100){
            setPrice(2,100,499);
        } else if (data.getCostFrom()==500){
            setPrice(3,500,999);
        } else if (data.getCostFrom()==1000){
            setPrice(4,1000,10000);
        }
    }


    public void setPrice(int selection , int priceFrom ,int priceTo){
        selectorPosition.set(selection);
        this.priceFrom=priceFrom;
        this.priceTo=priceTo;
    }

    public void setDate(int selection , int dateFrom ,int dateTo){
        selectorDate.set(selection);
        this.dateFrom=dateFrom;
        this.dateTo=dateTo;
    }


}
