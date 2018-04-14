package com.example.dell.workshopmodule.viewmodel.InProgressDetail;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.example.dell.workshopmodule.model.global.RequestData;
import com.example.dell.workshopmodule.model.global.UserData;
import com.example.dell.workshopmodule.model.response.DefaultResponse;
import com.example.dell.workshopmodule.model.response.OfferData;
import com.example.dell.workshopmodule.model.response.WorkshopOfferResponse;
import com.example.dell.workshopmodule.network.ApiClient;
import com.example.dell.workshopmodule.network.EndPoints;
import com.example.dell.workshopmodule.utils.ConfigurationFile;
import com.example.dell.workshopmodule.utils.CustomUtils;
import com.example.dell.workshopmodule.utils.NetWorkConnection;
import com.example.dell.workshopmodule.view.ui.callback.BaseInterface;
import com.example.dell.workshopmodule.view.ui.fragment.CarInfoFragment;
import com.example.dell.workshopmodule.view.ui.fragment.ClientInfo;
import com.example.dell.workshopmodule.view.ui.fragment.OfferInfo;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;
import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.banners.RemoteBanner;

/**
 * Created by DELL on 28/03/2018.
 */

public class InProgressrequestDetailViewModel extends BaseObservable {

    public ObservableList<Banner>remoteBanners;

    private Context context;
    private BaseInterface callback;
    private UserData userData;
    private int requestId;
    public InProgressrequestDetailViewModel(Context context, BaseInterface callback,int requestId) {
        remoteBanners=new ObservableArrayList<>();
        this.context=context;
        this.callback=callback;
        this.requestId=requestId;
        userData= CustomUtils.getInstance().getSaveUserObject(context);
        setRemoteBanners();
    }

    public void setRemoteBanners(){
        remoteBanners.add(new RemoteBanner(
                "https://assets.materialup.com/uploads/dcc07ea4-845a-463b-b5f0-4696574da5ed/preview.jpg"
        ));
        remoteBanners.add(new RemoteBanner(
                "https://assets.materialup.com/uploads/4b88d2c1-9f95-4c51-867b-bf977b0caa8c/preview.gif"
        ));
        remoteBanners.add(new RemoteBanner(
                "https://assets.materialup.com/uploads/76d63bbc-54a1-450a-a462-d90056be881b/preview.png"
        ));
        remoteBanners.add(new RemoteBanner(
                "https://assets.materialup.com/uploads/05e9b7d9-ade2-4aed-9cb4-9e24e5a3530d/preview.jpg"
        ));
    }


    public void completeRequest(View view){
        if(NetWorkConnection.isConnectingToInternet(context)) {
            System.out.println("Code cONNECTING cOMPLETED");
            ApiClient.getClient().create(EndPoints.class).completeRequest(ConfigurationFile.Constants.API_KEY, "EN", ConfigurationFile.Constants.Content_Type, ConfigurationFile.Constants.Content_Type, "Bearer "+userData.getToken(),requestId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Response<DefaultResponse>>() {
                        @Override
                        public void accept(Response<DefaultResponse> completeRequestResponse) throws Exception {
                            int code=completeRequestResponse.code();
                            System.out.println("Code cOMPLETED:"+code);
                            if(code==ConfigurationFile.Constants.SUCCESS_CODE){
                                callback.updateUi(ConfigurationFile.Constants.SUCCESS_CODE);
                            }else if(code==ConfigurationFile.Constants.UNAUTHENTICATED_CODE){
                                callback.updateUi(ConfigurationFile.Constants.UNAUTHENTICATED_CODE);
                            }else if(code==ConfigurationFile.Constants.CANT_COMPLETE_REQUEST_CODE ){
                                callback.updateUi(ConfigurationFile.Constants.CANT_COMPLETE_REQUEST_CODE );
                            }

                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            System.out.println("Code cOMPLETED :"+throwable.getMessage());
                        }
                    });
        }else {
            callback.updateUi(ConfigurationFile.Constants.NO_INTERNET_CONNECTION_CODE);
        }
    }







}
