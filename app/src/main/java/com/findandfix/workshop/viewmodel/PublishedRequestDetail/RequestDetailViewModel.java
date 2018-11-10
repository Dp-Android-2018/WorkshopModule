package com.findandfix.workshop.viewmodel.PublishedRequestDetail;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableInt;
import android.databinding.ObservableList;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.findandfix.workshop.R;
import com.findandfix.workshop.model.global.BaseModel;
import com.findandfix.workshop.model.global.Media;
import com.findandfix.workshop.model.global.RequestData;
import com.findandfix.workshop.model.global.UserData;
import com.findandfix.workshop.model.response.OfferData;
import com.findandfix.workshop.network.ApiClient;
import com.findandfix.workshop.network.EndPoints;
import com.findandfix.workshop.ui.activity.NormalRequestActivity;
import com.findandfix.workshop.ui.callback.BaseInterface;
import com.findandfix.workshop.utils.ConfigurationFile;
import com.findandfix.workshop.utils.CustomUtils;
import com.findandfix.workshop.utils.NetWorkConnection;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
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
    private UserData userData;
    private Dialog dialog=null;
    private int offerId;
    public ObservableInt progressVisibility;


    private OfferData data;
    public RequestDetailViewModel(Context context, RequestData requestData, int requestType, BaseInterface callback) {
        remoteBanners=new ObservableArrayList<>();
        this.context=context;
        this.requestData=requestData;
        this.callback=callback;
        requestDetailType=new ObservableInt();
        progressVisibility=new ObservableInt(View.GONE);
        requestDetailType.set(requestType);
        userData= CustomUtils.getInstance().getSaveUserObject(context);
        setUpBanners();
        if (requestType==215)
           getWorkshopOffer();
    }

    public void getWorkshopOffer(){
        if(NetWorkConnection.isConnectingToInternet(context)) {
            System.out.println("Code cONNECTING");
            //progressVisibility.set(View.VISIBLE);
            CustomUtils.getInstance().showProgressDialog((Activity)context);
            ApiClient.getClient().create(EndPoints.class).getWorkshopOffer(ConfigurationFile.Constants.API_KEY, CustomUtils.getInstance().getAppLanguage(context), ConfigurationFile.Constants.Content_Type, ConfigurationFile.Constants.Content_Type, "Bearer "+userData.getToken(),requestData.getId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(workShopRequestResponseResponse -> {
                        CustomUtils.getInstance().cancelDialog();
                      //  progressVisibility.set(View.GONE);
                        int code=workShopRequestResponseResponse.code();
                        System.out.println("Code Offer:"+code);
                        if(code==ConfigurationFile.Constants.SUCCESS_CODE){
                            OfferData data=workShopRequestResponseResponse.body().getData();
                            setData(data);
                            offerId=data.getId();
                            System.out.println("Code Offer Id:"+offerId);
                        }else if(code==ConfigurationFile.Constants.UNAUTHENTICATED_CODE || code==ConfigurationFile.Constants.UNSUBSCRIBE_CODE){
                            callback.updateUi(ConfigurationFile.Constants.UNAUTHENTICATED_CODE);
                        }else if(code==ConfigurationFile.Constants.CANT_COMPLETE_REQUEST_CODE ){
                            callback.updateUi(ConfigurationFile.Constants.CANT_COMPLETE_REQUEST_CODE );
                        }else if(code==ConfigurationFile.Constants.UNSUBSCRIBED_CODE){

                        }

                    }, throwable -> {
                        CustomUtils.getInstance().cancelDialog();
                       // progressVisibility.set(View.GONE);
                        System.out.println("Normat Request :"+throwable.getMessage());
                    });
        }else {
            callback.updateUi(ConfigurationFile.Constants.NO_INTERNET_CONNECTION_CODE);
        }
    }

    public void setUpBanners(){
       for (Media media:requestData.getImage()) {
           remoteBanners.add(new RemoteBanner(media.getPath()));

       }

        if (requestData.getVideos()!=null && requestData.getVideos().size()!=0){
           if (!requestData.getVideos().get(0).equals(""))
                remoteBanners.add(new RemoteBanner(null).setPlaceHolder(context.getResources().getDrawable(R.drawable.ic_video_symbol)));
            }



    }


    public String getSpecialization(){
        String specializationtext="";
        for (BaseModel model:requestData.getSpecializations())
            specializationtext=specializationtext+model.getName()+" ";
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

        Intent intent = new Intent(Intent.ACTION_VIEW,
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

    public void DeleteOffer(View view){
        setDialog(); }

    private void setDialog(){

            dialog = new Dialog((Activity)context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            LayoutInflater factory = LayoutInflater.from(context);
            final View dialogView = factory.inflate(R.layout.dialog_delete_normal_request_offer_layout, null);
            Button btnCancel = dialogView.findViewById(R.id.btn_cancel);
            Button btnDeleteOffer = dialogView.findViewById(R.id.btn_delete_offer);
            btnCancel.setOnClickListener(v -> dialog.cancel());
            btnDeleteOffer.setOnClickListener(v -> deleteOfferData());
            dialog.setContentView(dialogView);
            dialog.show();

    }

    public void deleteOfferData(){
        System.out.println("Delete Offer Code Token:"+userData.getToken()+" Offer Id:"+offerId);
        if(NetWorkConnection.isConnectingToInternet(context)) {
            CustomUtils.getInstance().showProgressDialog((Activity)context);
            ApiClient.getClient().create(EndPoints.class).deleteWorkshopOffer(ConfigurationFile.Constants.API_KEY, CustomUtils.getInstance().getAppLanguage(context), ConfigurationFile.Constants.Content_Type, ConfigurationFile.Constants.Content_Type, "Bearer "+ userData.getToken(),offerId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(defaultResponseResponse -> {
                        dialog.cancel();
                        CustomUtils.getInstance().cancelDialog();
                        System.out.println("Delete Offer Code :"+defaultResponseResponse.code());
                        if (defaultResponseResponse.code() == ConfigurationFile.Constants.SUCCESS_CODE) {
                            callback.updateUi(ConfigurationFile.Constants.OFFER_DELETED_SUCCESSFULLY);
                        } else if (defaultResponseResponse.code() == ConfigurationFile.Constants.UNAUTHENTICATED_CODE  ||
                                defaultResponseResponse.code() == ConfigurationFile.Constants.UNSUBSCRIBE_CODE ) {
                            callback.updateUi(ConfigurationFile.Constants.UNAUTHENTICATED_CODE);

                        } else if (defaultResponseResponse.code() == ConfigurationFile.Constants.CANT_COMPLETE_REQUEST_CODE) {
                            callback.updateUi(ConfigurationFile.Constants.CANT_COMPLETE_REQUEST_CODE);
                        }
                    }, throwable -> {
                        dialog.cancel();
                        CustomUtils.getInstance().cancelDialog();
                        System.out.println("Delete Offer Ex :" + throwable.getMessage());
                    });
        }else {
            callback.updateUi(ConfigurationFile.Constants.NO_INTERNET_CONNECTION_CODE);
        }
    }

    public OfferData getData() {
        return data;
    }

    public void setData(OfferData data) {
        this.data = data;
    }

    public void onBackPressed(){
        Intent i=new Intent(context, NormalRequestActivity.class);
        context.startActivity(i);
        ((Activity)context).finish();
    }

}
