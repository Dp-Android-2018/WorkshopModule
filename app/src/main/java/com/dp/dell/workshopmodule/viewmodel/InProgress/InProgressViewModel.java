package com.dp.dell.workshopmodule.viewmodel.InProgress;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dp.dell.workshopmodule.model.global.RequestData;
import com.dp.dell.workshopmodule.model.global.UserData;
import com.dp.dell.workshopmodule.model.response.WorkShopRequestResponse;
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

/**
 * Created by DELL on 27/03/2018.
 */

public class InProgressViewModel extends BaseObservable{
    private Context context;
    private BaseInterface callback;
    private  UserData userData;
    public ObservableField<String> total;
    public ObservableList<RequestData>inProgressRequest;
    /////////////////////////////////////////////////////////////////////////
    private boolean loading=false;
    private String next=null;
    private int pageId=0;
    private int pos=0;

    ////////////////////////////////////////////////////////////////////////
    public InProgressViewModel(Context context, BaseInterface callback) {
        this.context=context;
        this.callback=callback;
        inProgressRequest=new ObservableArrayList<>();
        userData= CustomUtils.getInstance().getSaveUserObject(context);
        total=new ObservableField<>();
        loading=true;

    }
/////////////////////////Handling Load More Of Recycler View ///////////////////////////////////////////////////////////////
    public RecyclerView.OnScrollListener onScroll(){
        return new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if ( ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition()==inProgressRequest.size()) {
                    if (next!=null && loading ==false) {
                        loading=true;
                        System.out.println("Load More Data Success ");
                        getInProgressRequests();
                    }
                }
            }
        };
    }

    public void onResume(){
        getInProgressRequests();
    }


    /////////////////////////////Make A Call To Get In Progress Requests ///////////////////////////////////////////////
    public void getInProgressRequests(){
        if(NetWorkConnection.isConnectingToInternet(context)) {
         //   CustomUtils.getInstance().showProgressDialog((Activity)context);
            System.out.println("Code cONNECTING");
            ApiClient.getClient().create(EndPoints.class).getWorkshopInProgressRequests(ConfigurationFile.Constants.API_KEY, CustomUtils.getInstance().getAppLanguage(context), ConfigurationFile.Constants.Content_Type, ConfigurationFile.Constants.Content_Type, "Bearer "+userData.getToken(),pageId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(workShopRequestResponseResponse -> {
       //                 CustomUtils.getInstance().cancelDialog();
                        int code=workShopRequestResponseResponse.code();
                        System.out.println("Code Progress:"+code);
                        loading=false;
                        if(code==ConfigurationFile.Constants.SUCCESS_CODE){
                            System.out.println("Code IN pROGRESS:"+workShopRequestResponseResponse.body().getData().size());
                            pos=inProgressRequest.size();
                            inProgressRequest.addAll(workShopRequestResponseResponse.body().getData());
                            if (inProgressRequest.size()==0){
                                callback.updateUi(ConfigurationFile.Constants.NO_DATA);}
                            next=workShopRequestResponseResponse.body().getLinks().getNext();
                            if (next!=null)
                                pageId=Integer.parseInt(next.substring(next.length()-1));
                            total.set(String.valueOf(workShopRequestResponseResponse.body().getMeta().getTotal()));
                            if (inProgressRequest.size()==0)
                                callback.updateUi(ConfigurationFile.Constants.NO_DATA);
                        }else if(code==ConfigurationFile.Constants.UNAUTHENTICATED_CODE || code==ConfigurationFile.Constants.UNSUBSCRIBE_CODE){
                            callback.updateUi(ConfigurationFile.Constants.UNAUTHENTICATED_CODE);
                        }else if(code==ConfigurationFile.Constants.UNSUBSCRIBED_CODE){

                        }

                    }, throwable -> {
                   //     CustomUtils.getInstance().cancelDialog();
                        System.out.println("Normat Request :"+throwable.getMessage());
                    });
        }else {
            callback.updateUi(ConfigurationFile.Constants.NO_INTERNET_CONNECTION_CODE);
        }
    }

    public void onpause(){
        inProgressRequest.clear();
    }
}
