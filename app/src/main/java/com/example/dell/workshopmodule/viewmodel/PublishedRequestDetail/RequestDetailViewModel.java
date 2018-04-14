package com.example.dell.workshopmodule.viewmodel.PublishedRequestDetail;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.databinding.ObservableList;
import android.net.Uri;
import android.view.View;

import com.example.dell.workshopmodule.R;
import com.example.dell.workshopmodule.model.global.BaseModel;
import com.example.dell.workshopmodule.model.global.RequestData;
import com.example.dell.workshopmodule.model.global.UserData;
import com.example.dell.workshopmodule.utils.ConfigurationFile;
import com.example.dell.workshopmodule.view.ui.callback.BaseInterface;

import java.util.ArrayList;
import java.util.List;

import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.banners.RemoteBanner;

/**
 * Created by DELL on 28/03/2018.
 */

public class RequestDetailViewModel extends BaseObservable{
    public ObservableList<Banner> remoteBanners;
    private RequestData requestData;
    private Context context;
    private BaseInterface callback;
    public ObservableInt requestDetailType;
    public RequestDetailViewModel(Context context, RequestData requestData,int requestType, BaseInterface callback) {
        remoteBanners=new ObservableArrayList<>();
        this.context=context;
        this.requestData=requestData;
        this.callback=callback;
        requestDetailType=new ObservableInt();
        requestDetailType.set(requestType);
        setUpBanners();
    }

    public void setUpBanners(){
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


    public String getSpecialization(){
        String specializationtext="";
        for (BaseModel model:requestData.getSpecializations())
            specializationtext=specializationtext+model.getName();

        return specializationtext;
    }

    public String CarInfo(){
        return requestData.getBrand()+"-"+requestData.getModel()+"-"+requestData.getYear();
    }

    public String getDesc(){
        return  requestData.getNotes();
    }

    public String getAddress(){
        return requestData.getAddress();
    }

    public String getDate(){return  requestData.getDate();
    }

    public void navigate(View view){

        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?daddr=" + requestData.getLatitude() + "," + requestData.getLongitude()));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public String needWinch(){
        String winch;
        if(requestData.getWinch()==1)
            winch=context.getString(R.string.need_winch);
        else
            winch=context.getString(R.string.dont_need_winch);
        return winch;
    }

    public void displayDialog(View view){
        callback.updateUi(ConfigurationFile.Constants.SHOW_DIALOG_CODE);
    }
}
