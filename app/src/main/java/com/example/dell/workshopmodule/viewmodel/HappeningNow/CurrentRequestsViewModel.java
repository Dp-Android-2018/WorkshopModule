package com.example.dell.workshopmodule.viewmodel.HappeningNow;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.example.dell.workshopmodule.model.global.RequestData;
import com.example.dell.workshopmodule.model.global.UserData;
import com.example.dell.workshopmodule.model.response.WorkShopRequestResponse;
import com.example.dell.workshopmodule.network.ApiClient;
import com.example.dell.workshopmodule.network.EndPoints;
import com.example.dell.workshopmodule.utils.ConfigurationFile;
import com.example.dell.workshopmodule.utils.CustomUtils;
import com.example.dell.workshopmodule.utils.NetWorkConnection;
import com.example.dell.workshopmodule.utils.SharedPrefrenceUtils;
import com.example.dell.workshopmodule.view.ui.callback.BaseInterface;

import java.lang.ref.PhantomReference;
import java.util.ArrayList;
import java.util.List;

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
    private Context context;
    private BaseInterface callback;
    private  UserData userData;
    public CurrentRequestsViewModel(Context context, BaseInterface callback) {
        normalRequest=new ObservableArrayList<>();
        pendingRequest=new ObservableArrayList<>();
        this.context=context;
        this.callback=callback;
        userData= CustomUtils.getInstance().getSaveUserObject(context);
        getNormalRequests();
        getPendingRequestCall();
    }

    public void getNormalRequests(){
        if(NetWorkConnection.isConnectingToInternet(context)) {
            System.out.println("Code cONNECTING");
            ApiClient.getClient().create(EndPoints.class).getWorkshopNormalRequests(ConfigurationFile.Constants.API_KEY, "EN", ConfigurationFile.Constants.Content_Type, ConfigurationFile.Constants.Content_Type, "Bearer "+userData.getToken())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Response<WorkShopRequestResponse>>() {
                        @Override
                        public void accept(Response<WorkShopRequestResponse> workShopRequestResponseResponse) throws Exception {
                            int code=workShopRequestResponseResponse.code();
                            System.out.println("Code :"+code);
                            if(code==ConfigurationFile.Constants.SUCCESS_CODE){
                                System.out.println("Code :"+workShopRequestResponseResponse.body().getData().size());
                                normalRequest.addAll(workShopRequestResponseResponse.body().getData());
                            }else if(code==ConfigurationFile.Constants.UNAUTHENTICATED_CODE){
                                            callback.updateUi(ConfigurationFile.Constants.UNAUTHENTICATED_CODE);
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
        }
    }

    public void getPendingRequestCall(){
        if(NetWorkConnection.isConnectingToInternet(context)) {

            System.out.println("Token :"+userData.getToken());
            ApiClient.getClient().create(EndPoints.class).getWorkshopPendingNormalRequests(ConfigurationFile.Constants.API_KEY, "EN", ConfigurationFile.Constants.Content_Type, ConfigurationFile.Constants.Content_Type,"Bearer "+userData.getToken() )
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Response<WorkShopRequestResponse>>() {
                        @Override
                        public void accept(Response<WorkShopRequestResponse> workShopRequestResponseResponse) throws Exception {
                            int code=workShopRequestResponseResponse.code();
                            if(code==ConfigurationFile.Constants.SUCCESS_CODE){
                                pendingRequest.addAll(workShopRequestResponseResponse.body().getData());
                            }else if(code==ConfigurationFile.Constants.UNAUTHENTICATED_CODE){
                                callback.updateUi(ConfigurationFile.Constants.UNAUTHENTICATED_CODE);
                            }

                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            System.out.println("Pending Request :"+throwable.getMessage());
                        }
                    });
        }else {
            callback.updateUi(ConfigurationFile.Constants.NO_INTERNET_CONNECTION_CODE);
        }
    }


}
