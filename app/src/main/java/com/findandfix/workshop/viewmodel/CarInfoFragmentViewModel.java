package com.findandfix.workshop.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.net.Uri;
import android.view.View;

import com.findandfix.workshop.model.global.BaseModel;
import com.findandfix.workshop.model.global.RequestData;
import com.google.gson.Gson;

/**
 * Created by DELL on 27/03/2018.
 */

public class CarInfoFragmentViewModel extends BaseObservable {

    private Context context;
    private RequestData requestData;
    public CarInfoFragmentViewModel(Context context, RequestData requestData) {
        this.requestData=requestData;
        this.context=context;
        if(requestData!=null){
            System.out.println("Request Data :"+new Gson().toJson(requestData).toString());;
        }
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

    public String getDate(){
        return  requestData.getDate();
    }

    public void navigate(View view){

        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?daddr=" + requestData.getLatitude() + "," + requestData.getLongitude()));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
