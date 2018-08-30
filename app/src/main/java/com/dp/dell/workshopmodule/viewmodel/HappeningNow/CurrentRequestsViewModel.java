package com.dp.dell.workshopmodule.viewmodel.HappeningNow;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableInt;
import android.databinding.ObservableList;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dp.dell.workshopmodule.model.global.RequestData;
import com.dp.dell.workshopmodule.model.global.UserData;
import com.dp.dell.workshopmodule.model.response.WorkShopRequestResponse;
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

public class CurrentRequestsViewModel extends BaseObservable {

    public ObservableList<RequestData> normalRequest;
    public ObservableList<RequestData>pendingRequest;
    public ObservableInt visibility;
    public ObservableInt visibility2;
    private Context context;
    private BaseInterface callback;
    private  UserData userData;


    public CurrentRequestsViewModel(Context context, BaseInterface callback) {
        normalRequest=new ObservableArrayList<>();
        pendingRequest=new ObservableArrayList<>();
        visibility=new ObservableInt(View.VISIBLE);
        visibility2=new ObservableInt(View.VISIBLE);
        this.context=context;
        this.callback=callback;
        userData= CustomUtils.getInstance().getSaveUserObject(context);
    }

    public void onResume(){
        getNormalRequests();
        getPendingRequestCall();
    }


///////////////////////////Make A call To get Normal Requests That is near From Workshop ////////////////////////////////////////////////////
    public void getNormalRequests(){
        if(NetWorkConnection.isConnectingToInternet(context)) {
            System.out.println("Code Login cONNECTING");
            CustomUtils.getInstance().showProgressDialog((Activity)context);
            ApiClient.getClient().create(EndPoints.class).getWorkshopNormalRequests(ConfigurationFile.Constants.API_KEY, CustomUtils.getInstance().getAppLanguage(context), ConfigurationFile.Constants.Content_Type, ConfigurationFile.Constants.Content_Type, "Bearer "+userData.getToken(),0)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(workShopRequestResponseResponse -> {
                        CustomUtils.getInstance().cancelDialog();
                        int code=workShopRequestResponseResponse.code();
                        System.out.println("Code Login:"+code);
                       // loading=false;
                        if(code==ConfigurationFile.Constants.SUCCESS_CODE){
                          //  normalRequest.clear();
                          //  pos=normalRequest.size();
                            System.out.println("Code Login Data:"+workShopRequestResponseResponse.body().getData().size());
                            normalRequest.addAll(workShopRequestResponseResponse.body().getData());
                            if (normalRequest.size()==0){
                                visibility2.set(View.GONE);
                                callback.updateUi(ConfigurationFile.Constants.NO_DATA);
                            }
                          /*  next=workShopRequestResponseResponse.body().getLinks().getNext();
                            if (next!=null)
                                pageId=Integer.parseInt(next.substring(next.length()-1));*/
                        }else if(code==ConfigurationFile.Constants.UNAUTHENTICATED_CODE || code==ConfigurationFile.Constants.UNSUBSCRIBE_CODE){
                                        callback.updateUi(ConfigurationFile.Constants.UNAUTHENTICATED_CODE);
                        }

                    }, throwable -> {
                        CustomUtils.getInstance().cancelDialog();
                        System.out.println("Normat Request :"+throwable.getMessage());
                    });
        }else {
            callback.updateUi(ConfigurationFile.Constants.NO_INTERNET_CONNECTION_CODE);
        }
    }
    ///////////////////////Make A call To get Pending Requests  ////////////////////////////////////////////////////
    public void getPendingRequestCall(){
        if(NetWorkConnection.isConnectingToInternet(context)) {
        //    CustomUtils.getInstance().showProgressDialog((Activity)context);
            System.out.println("Token :"+userData.getToken());
            ApiClient.getClient().create(EndPoints.class).getWorkshopPendingNormalRequests(ConfigurationFile.Constants.API_KEY, CustomUtils.getInstance().getAppLanguage(context), ConfigurationFile.Constants.Content_Type, ConfigurationFile.Constants.Content_Type,"Bearer "+userData.getToken() )
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(workShopRequestResponseResponse -> {
      //                  CustomUtils.getInstance().cancelDialog();
                        int code=workShopRequestResponseResponse.code();

                        if(code==ConfigurationFile.Constants.SUCCESS_CODE){
                            pendingRequest.clear();
                            pendingRequest.addAll(workShopRequestResponseResponse.body().getData());
                            if (pendingRequest.size()==0){
                                visibility.set(View.GONE);
                                callback.updateUi(ConfigurationFile.Constants.NO_DATA_2);
                            }


                        }else if(code==ConfigurationFile.Constants.UNAUTHENTICATED_CODE || code==ConfigurationFile.Constants.UNSUBSCRIBE_CODE){
                            callback.updateUi(ConfigurationFile.Constants.UNAUTHENTICATED_CODE);
                        }

                    }, throwable -> {
                      //  CustomUtils.getInstance().cancelDialog();
                        System.out.println("Pending Request :"+throwable.getMessage());
                    });
        }else {
            callback.updateUi(ConfigurationFile.Constants.NO_INTERNET_CONNECTION_CODE);
        }
    }

    public void onPause(){
        normalRequest.clear();
    }


}
