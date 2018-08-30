package com.dp.dell.workshopmodule.viewmodel.HappeningNow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.DataBindingUtil;
import android.view.View;

import com.dp.dell.workshopmodule.R;
import com.dp.dell.workshopmodule.model.global.RequestData;
import com.dp.dell.workshopmodule.utils.ConfigurationFile;
import com.dp.dell.workshopmodule.view.ui.activity.InProgressRequestDetail;
import com.dp.dell.workshopmodule.view.ui.activity.RequestDetailActivity;
import com.github.thunder413.datetimeutils.DateTimeFormat;
import com.github.thunder413.datetimeutils.DateTimeStyle;
import com.github.thunder413.datetimeutils.DateTimeUtils;

import java.util.Date;
import java.util.Locale;

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
        String specialize="";
        for (int i=0;i<requestData.getSpecializations().size();i++){
            specialize=specialize+" "+requestData.getSpecializations().get(i).getName();
        }
        return specialize;
    }

    public int getPrecentage(){
        return (int)requestData.getPercentage();
    }

    public String precentageText(){
        return requestData.getPercentage()+"%";
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

    public String getImage(){
        if (requestData.getImage().size()!=0)
        return requestData.getImage().get(0).getPath();
    else return "";}

    public void moveToInProgressDetailPage(View view){

        Intent i=new Intent(view.getContext(), InProgressRequestDetail.class);
        i.putExtra(ConfigurationFile.SharedPrefConstants.PREF_REQUEST_OBJECT,requestData);
        context.startActivity(i);
        ((Activity)context).finish();


    }


    public void moveToPublishedDetailPage(View view){

        Intent i=new Intent(view.getContext(), RequestDetailActivity.class);
        i.putExtra(ConfigurationFile.Constants.REQUEST_TYPE,ConfigurationFile.Constants.PUBLISH_REQUEST_DETAIL);
        i.putExtra(ConfigurationFile.SharedPrefConstants.PREF_REQUEST_OBJECT,requestData);
        context.startActivity(i);
        ((Activity)context).finish();

    }


    public void moveToOfferedDetailPage(View view){

        Intent i=new Intent(view.getContext(), RequestDetailActivity.class);
        i.putExtra(ConfigurationFile.Constants.REQUEST_TYPE,ConfigurationFile.Constants.OFFERED_REQUEST_DETAIL);
        i.putExtra(ConfigurationFile.SharedPrefConstants.PREF_REQUEST_OBJECT,requestData);
        context.startActivity(i);
        ((Activity)context).finish();
    }
}
