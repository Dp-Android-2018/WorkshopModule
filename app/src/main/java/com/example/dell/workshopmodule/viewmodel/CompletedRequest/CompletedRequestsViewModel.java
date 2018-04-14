package com.example.dell.workshopmodule.viewmodel.CompletedRequest;

import android.content.Context;
import android.databinding.BaseObservable;
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

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by DELL on 27/03/2018.
 */

public class CompletedRequestsViewModel extends BaseObservable {

    private Context context;
    private BaseInterface callback;
    private UserData userData;
    public ObservableList<RequestData>completedRequests;
    public CompletedRequestsViewModel(Context context, BaseInterface callback) {
        this.callback=callback;
        this.context=context;
        userData= CustomUtils.getInstance().getSaveUserObject(context);
        completedRequests=new ObservableArrayList<>();
        getCompletedRequests();
    }

    public void getCompletedRequests(){
        if(NetWorkConnection.isConnectingToInternet(context)) {
            System.out.println("Code cONNECTING");
            ApiClient.getClient().create(EndPoints.class).getWorkshopCompletedRequests(ConfigurationFile.Constants.API_KEY, "EN", ConfigurationFile.Constants.Content_Type, ConfigurationFile.Constants.Content_Type, "Bearer "+userData.getToken())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Response<WorkShopRequestResponse>>() {
                        @Override
                        public void accept(Response<WorkShopRequestResponse> workShopRequestResponseResponse) throws Exception {
                            int code=workShopRequestResponseResponse.code();
                            System.out.println("Code :"+code);
                            if(code==ConfigurationFile.Constants.SUCCESS_CODE){
                                System.out.println("Code :"+workShopRequestResponseResponse.body().getData().size());
                                completedRequests.addAll(workShopRequestResponseResponse.body().getData());
                            }else if(code==ConfigurationFile.Constants.UNAUTHENTICATED_CODE){
                                callback.updateUi(ConfigurationFile.Constants.UNAUTHENTICATED_CODE);
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
        }
    }
}
