package com.dp.dell.workshopmodule.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableInt;
import android.view.View;

import com.dp.dell.workshopmodule.model.global.UserData;
import com.dp.dell.workshopmodule.model.request.UpdateProfileRequest;
import com.dp.dell.workshopmodule.model.response.DefaultResponse;
import com.dp.dell.workshopmodule.network.ApiClient;
import com.dp.dell.workshopmodule.network.EndPoints;
import com.dp.dell.workshopmodule.utils.ConfigurationFile;
import com.dp.dell.workshopmodule.utils.CustomUtils;
import com.dp.dell.workshopmodule.utils.NetWorkConnection;
import com.dp.dell.workshopmodule.view.ui.callback.BaseInterface;
import com.google.gson.Gson;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by DELL on 22/04/2018.
 */

public class HandleEditProfileViewModel extends BaseObservable {
    private  EditProfileInfoViewModel infoViewModel;
    private EditProfileSpecializationViewModel specializationViewModel;
    private UpdateProfileRequest updateProfileRequest;
    private UserData userData;
    private Context context;
    private BaseInterface navigator;
    public HandleEditProfileViewModel(EditProfileInfoViewModel infoViewModel,EditProfileSpecializationViewModel specializationViewModel,Context context,BaseInterface navigator) {
            this.infoViewModel=infoViewModel;
            this.specializationViewModel=specializationViewModel;
            this.context=context;
            this.navigator=navigator;
             userData= CustomUtils.getInstance().getSaveUserObject(context);
    }

    public void completeEditProfileRequest(View view){

        System.out.println(" eDIT pROFILE vALIDATE Info Model :"+infoViewModel.validate());
        System.out.println(" eDIT pROFILE vALIDATE sPECIALIZE Model :"+specializationViewModel.validate());
        if (infoViewModel.validate() && specializationViewModel.validate()) {
            updateProfileRequest=infoViewModel.getProfileObject();
            specializationViewModel.setProfileObject(updateProfileRequest);
            updateProfileRequest=specializationViewModel.getProfileObject();
            setEditProfileObject();
        }

    }

    public ObservableInt progressDialog=new ObservableInt(View.GONE);
    public void setEditProfileObject(){

        if(NetWorkConnection.isConnectingToInternet(context)) {
            System.out.println("Edit Profile User Token :"+userData.getToken());
            System.out.println("Edit Profile obj:"+new Gson().toJson(updateProfileRequest));
            //progressDialog.set(View.VISIBLE);
            CustomUtils.getInstance().showProgressDialog((Activity)context);
            ApiClient.getClient().create(EndPoints.class).updateWorkshopProfile(ConfigurationFile.Constants.API_KEY, CustomUtils.getInstance().getAppLanguage(context), ConfigurationFile.Constants.Content_Type, ConfigurationFile.Constants.Content_Type, "Bearer " + userData.getToken(),updateProfileRequest)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(defaultResponseResponse -> {
                             //progressDialog.set(View.GONE);
                        CustomUtils.getInstance().cancelDialog();
                        System.out.println("Edit Profile Code :"+defaultResponseResponse.code());
                            if(defaultResponseResponse.code()==ConfigurationFile.Constants.SUCCESS_CODE){
                                navigator.updateUi(ConfigurationFile.Constants.SUCCESS_CODE);
                            }else if(defaultResponseResponse.code()==ConfigurationFile.Constants.UNAUTHENTICATED_CODE ||
                                    defaultResponseResponse.code()==ConfigurationFile.Constants.UNSUBSCRIBE_CODE ){
                                navigator.updateUi(ConfigurationFile.Constants.UNAUTHENTICATED_CODE);
                            }else if(defaultResponseResponse.code()==ConfigurationFile.Constants.INVALID_DATA){
                                navigator.updateUi(ConfigurationFile.Constants.INVALID_DATA);
                            }
                    }, throwable -> {
                        CustomUtils.getInstance().cancelDialog();
                      //  progressDialog.set(View.GONE);
                        System.out.println("Update Profile Exception :"+throwable.getMessage());
                    });
        }else {
            navigator.updateUi(ConfigurationFile.Constants.NO_INTERNET_CONNECTION_CODE);
        }
    }
}
