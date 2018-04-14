package com.example.dell.workshopmodule.viewmodel.HappeningNow;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.view.View;

import com.example.dell.workshopmodule.model.global.RequestData;
import com.example.dell.workshopmodule.utils.ConfigurationFile;
import com.example.dell.workshopmodule.view.ui.activity.InProgressRequestDetail;
import com.example.dell.workshopmodule.view.ui.activity.RequestDetailActivity;

import java.util.Observable;

import retrofit2.http.PUT;

/**
 * Created by DELL on 27/03/2018.
 */

public class ItemRequestViewModel extends BaseObservable {
    private RequestData requestData;
    private Context context;
    public ItemRequestViewModel(Context context,RequestData requestData) {
        this.requestData=requestData;
        this.context=context;
    }

    public void setRequestData(RequestData requestData) {
        this.requestData = requestData;
        notifyChange();
    }

    public String getRequestTitle(){
        return requestData.getDate();
    }

    public String getCarType(){
        return requestData.getBrand()+"-"+requestData.getModel();
    }

    public String getAddress(){
        return requestData.getAddress();
    }

    public String getDate(){
        return requestData.getDate();
    }

    public void moveToInProgressDetailPage(View view){

        Intent i=new Intent(view.getContext(), InProgressRequestDetail.class);
        i.putExtra(ConfigurationFile.SharedPrefConstants.PREF_REQUEST_OBJECT,requestData);
        context.startActivity(i);
    }


    public void moveToPublishedDetailPage(View view){

        Intent i=new Intent(view.getContext(), RequestDetailActivity.class);
        i.putExtra(ConfigurationFile.Constants.REQUEST_TYPE,ConfigurationFile.Constants.PUBLISH_REQUEST_DETAIL);
        i.putExtra(ConfigurationFile.SharedPrefConstants.PREF_REQUEST_OBJECT,requestData);
        context.startActivity(i);
    }


    public void moveToOfferedDetailPage(View view){

        Intent i=new Intent(view.getContext(), RequestDetailActivity.class);
        i.putExtra(ConfigurationFile.Constants.REQUEST_TYPE,ConfigurationFile.Constants.OFFERED_REQUEST_DETAIL);
        i.putExtra(ConfigurationFile.SharedPrefConstants.PREF_REQUEST_OBJECT,requestData);
        context.startActivity(i);
    }
}
