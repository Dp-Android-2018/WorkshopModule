package com.findandfix.workshop.viewmodel.CompletedRequest;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.databinding.ObservableList;
import android.view.View;

import com.findandfix.workshop.model.global.RequestData;
import com.findandfix.workshop.model.global.UserData;
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

public class CompletedRequestsViewModel extends BaseObservable {

    private Context context;
    private BaseInterface callback;
    private UserData userData;
    public ObservableInt visibility;
    public ObservableField<String> total;
    public ObservableList<RequestData>completedRequests;
    public CompletedRequestsViewModel(Context context, BaseInterface callback) {
        this.callback=callback;
        this.context=context;
        userData= CustomUtils.getInstance().getSaveUserObject(context);
        completedRequests=new ObservableArrayList<>();
        total=new ObservableField<>();
        visibility=new ObservableInt(View.VISIBLE);

    }

    public void onResume(){
        getCompletedRequests();
    }
//////////////////////////////Make A Call To Get Completed Requests ///////////////////////////////////////////////////////////
    public void getCompletedRequests(){
        if(NetWorkConnection.isConnectingToInternet(context)) {
            System.out.println("Code cONNECTING");
          //  CustomUtils.getInstance().showProgressDialog((Activity)context);
            ApiClient.getClient().create(EndPoints.class).getWorkshopCompletedRequests(ConfigurationFile.Constants.API_KEY, CustomUtils.getInstance().getAppLanguage(context), ConfigurationFile.Constants.Content_Type, ConfigurationFile.Constants.Content_Type, "Bearer "+userData.getToken(),0)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(workShopRequestResponseResponse -> {
                   //     CustomUtils.getInstance().cancelDialog();
                        int code=workShopRequestResponseResponse.code();
                        System.out.println("Code :"+code);

                        if(code==ConfigurationFile.Constants.SUCCESS_CODE){
                            completedRequests.clear();
                            System.out.println("Code :"+workShopRequestResponseResponse.body().getData().size());
                            completedRequests.addAll(workShopRequestResponseResponse.body().getData());
                            total.set(String.valueOf(workShopRequestResponseResponse.body().getMeta().getTotal()));
                            if (completedRequests.size()==0) {

                                callback.updateUi(ConfigurationFile.Constants.NO_DATA);
                                visibility.set(View.GONE);
                            }

                        }else if(code==ConfigurationFile.Constants.UNAUTHENTICATED_CODE ||code==ConfigurationFile.Constants.UNSUBSCRIBE_CODE ){
                            callback.updateUi(ConfigurationFile.Constants.UNAUTHENTICATED_CODE);
                        }else if(code==ConfigurationFile.Constants.UNSUBSCRIBED_CODE){

                        }

                    }, throwable -> {
                     //   CustomUtils.getInstance().cancelDialog();
                        System.out.println("Normat Request :"+throwable.getMessage());
                    });
        }else {
            callback.updateUi(ConfigurationFile.Constants.NO_INTERNET_CONNECTION_CODE);
        }
    }
}
