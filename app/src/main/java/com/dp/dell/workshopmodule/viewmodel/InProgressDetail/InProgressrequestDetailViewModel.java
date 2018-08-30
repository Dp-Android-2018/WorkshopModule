package com.dp.dell.workshopmodule.viewmodel.InProgressDetail;

import android.app.Activity;
import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.view.View;

import com.dp.dell.workshopmodule.R;
import com.dp.dell.workshopmodule.model.global.CompleteNotification;
import com.dp.dell.workshopmodule.model.global.CompletePayload;
import com.dp.dell.workshopmodule.model.global.Media;
import com.dp.dell.workshopmodule.model.global.RequestData;
import com.dp.dell.workshopmodule.model.global.UserData;
import com.dp.dell.workshopmodule.model.request.CompleteRequestNotification;
import com.dp.dell.workshopmodule.model.response.DefaultResponse;
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
    private RequestData requestData;
    public InProgressrequestDetailViewModel(Context context, BaseInterface callback,RequestData requestData) {
        remoteBanners=new ObservableArrayList<>();
        this.context=context;
        this.callback=callback;
        this.requestData=requestData;
        userData= CustomUtils.getInstance().getSaveUserObject(context);
        setRemoteBanners();
    }


    public void setRemoteBanners(){
   /*     remoteBanners.add(new RemoteBanner(
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
        ));*/

        for (Media media:requestData.getImage()) {
            remoteBanners.add(new RemoteBanner(media.getPath()));
        }
        if (requestData.getVideos()!=null && requestData.getVideos().size()!=0){
           if ((!requestData.getVideos().get(0).equals("")))
            remoteBanners.add(new RemoteBanner(null).setPlaceHolder(context.getResources().getDrawable(R.drawable.ic_video_symbol)));
        }


    }


    public void completeRequest(View view){
        if(NetWorkConnection.isConnectingToInternet(context)) {
            CustomUtils.getInstance().showProgressDialog((Activity)context);
            System.out.println("Code cONNECTING cOMPLETED");
            CompleteNotification completeNotification=new CompleteNotification();
            completeNotification.setKey(4);
            completeNotification.setDeviceToken(requestData.getCarowner().getDeviceToken());
            CompletePayload completePayload=new CompletePayload();
            completePayload.setNotificationTitle("complete_fixing");
            completePayload.setWorkshopId(String.valueOf(CustomUtils.getInstance().getSaveUserObject(context).getId()));
            completePayload.setWorkShopName(userData.getName());
            completePayload.setKey(4);
            completeNotification.setData(completePayload);
            CompleteRequestNotification completeRequestNotification=new CompleteRequestNotification();
            completeRequestNotification.setNotification(completeNotification);
            ApiClient.getClient().create(EndPoints.class).completeRequest(ConfigurationFile.Constants.API_KEY, CustomUtils.getInstance().getAppLanguage(context), ConfigurationFile.Constants.Content_Type, ConfigurationFile.Constants.Content_Type, "Bearer "+userData.getToken(),requestData.getId(),completeRequestNotification)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(completeRequestResponse -> {
                        CustomUtils.getInstance().cancelDialog();
                        int code=completeRequestResponse.code();
                        System.out.println("Code cOMPLETED:"+code);
                        if(code==ConfigurationFile.Constants.SUCCESS_CODE){
                            callback.updateUi(ConfigurationFile.Constants.SUCCESS_CODE);
                        }else if(code==ConfigurationFile.Constants.UNAUTHENTICATED_CODE || code==ConfigurationFile.Constants.UNSUBSCRIBE_CODE){
                            callback.updateUi(ConfigurationFile.Constants.UNAUTHENTICATED_CODE);
                        }else if(code==ConfigurationFile.Constants.CANT_COMPLETE_REQUEST_CODE ){
                            callback.updateUi(ConfigurationFile.Constants.CANT_COMPLETE_REQUEST_CODE );
                        }

                    }, throwable -> {
                        CustomUtils.getInstance().cancelDialog();
                        System.out.println("Code cOMPLETED :"+throwable.getMessage());
                    });
        }else {
            callback.updateUi(ConfigurationFile.Constants.NO_INTERNET_CONNECTION_CODE);
        }
    }







}
