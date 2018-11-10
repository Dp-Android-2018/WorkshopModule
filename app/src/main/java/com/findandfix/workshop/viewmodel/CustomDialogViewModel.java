package com.findandfix.workshop.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.view.View;

import com.findandfix.workshop.BR;
import com.findandfix.workshop.R;
import com.findandfix.workshop.model.global.BaseModel;
import com.findandfix.workshop.model.response.SpecializationResponse;
import com.findandfix.workshop.model.response.UrgentRequestTypeResponse;
import com.findandfix.workshop.ui.Application.MyApplication;
import com.findandfix.workshop.ui.callback.BaseInterface;
import com.findandfix.workshop.ui.callback.NetworkCallback;
import com.findandfix.workshop.utils.ConfigurationFile;
import com.findandfix.workshop.utils.ConstantsCallsUtils;
import com.findandfix.workshop.utils.CustomUtils;
import com.findandfix.workshop.utils.NetWorkConnection;
import com.findandfix.workshop.utils.SharedPrefrenceUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 21/03/2018.
 */

public class CustomDialogViewModel extends BaseObservable {
    private Context context;
    private int code;
    private List<BaseModel> data;
    private BaseInterface navigator;
    private BaseInterface updateViewModel;
    private SharedPrefrenceUtils prefrenceUtils;
    public ObservableInt progressBar;
    public ObservableField<String>dialogTitle;
    public CustomDialogViewModel(Context context, int code, BaseInterface navigator, BaseInterface updateViewModel) {
        this.code = code;
        this.context = context;
        this.navigator = navigator;
        this.updateViewModel = updateViewModel;
        progressBar=new ObservableInt(View.GONE);
        dialogTitle=new ObservableField<>();
        prefrenceUtils=new SharedPrefrenceUtils(context);
        data = new ArrayList<>();
        if (code == ConfigurationFile.Constants.DISPLAY_SPECIALIZATION_DIALOG) {
            dialogTitle.set(context.getResources().getString(R.string.specialization));
            getSpecializations();
        }
        else if (code == ConfigurationFile.Constants.DISPLAY_URGENT_TYPES_DIALOG) {
            dialogTitle.set(context.getResources().getString(R.string.uregnt_request));
            getUrgentRequestTypes();
        }

        ((MyApplication)(MyApplication.getAppContext())).setCustomDialogCode(code);
    }


    /*public void getSpecializations() {
        ApiClient.getClient().create(EndPoints.class).getSpecializations(ConfigurationFile.Constants.API_KEY, "EN", ConfigurationFile.Constants.Content_Type, ConfigurationFile.Constants.Content_Type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Response<SpecializationResponse>>() {
                    @Override
                    public void accept(Response<SpecializationResponse> specializationResponseResponse) throws Exception {

                        if (specializationResponseResponse.code() == ConfigurationFile.Constants.SUCCESS_CODE) {

                            List<BaseModel> models = specializationResponseResponse.body().getData();
                            setData(models);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }*/

    public void getSpecializations(){
        data.clear();
        final SpecializationResponse specializationResponse=(SpecializationResponse) prefrenceUtils.getSavedObject(ConfigurationFile.SharedPrefConstants.PREF_SPECIALIZATIONS_OBJECT,SpecializationResponse.class);
        if(specializationResponse==null) {
            if(NetWorkConnection.isConnectingToInternet(context)) {
             //   progressBar.set(View.VISIBLE);
                CustomUtils.getInstance().showProgressDialog((Activity)context);
                ConstantsCallsUtils.getInstance().getSpecializations(new NetworkCallback() {
                    @Override
                    public <E> void onSuccess(Object response, int code) {
                       // progressBar.set(View.GONE);
                        CustomUtils.getInstance().cancelDialog();
                        if (code == ConfigurationFile.Constants.SUCCESS_CODE) {
                            System.out.println("Brand Details");
                            SpecializationResponse specializationResponse1 = (SpecializationResponse) response;
                            prefrenceUtils.saveObjectToSharedPreferences(ConfigurationFile.SharedPrefConstants.PREF_SPECIALIZATIONS_OBJECT, specializationResponse1);
                            setData(specializationResponse1.getData());
                        }
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                      //  progressBar.set(View.GONE);
                        CustomUtils.getInstance().cancelDialog();
                    }

                    @Override
                    public <E> void onUnuthenticated(int code) {
                        CustomUtils.getInstance().endSession((Activity)context);
                    }
                }, CustomUtils.getInstance().getAppLanguage(context));
            }else {
                navigator.updateUi(ConfigurationFile.Constants.NO_INTERNET_CONNECTION_CODE);
            }
        }else {
            setData(specializationResponse.getData());
        }
    }



    /*public void getUrgentRequestTypes() {

        ApiClient.getClient().create(EndPoints.class).getUrgentTypes(ConfigurationFile.Constants.API_KEY, "EN", ConfigurationFile.Constants.Content_Type, ConfigurationFile.Constants.Content_Type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Response<UrgentRequestTypeResponse>>() {
                    @Override
                    public void accept(Response<UrgentRequestTypeResponse> urgentRequestTypeResponseResponse) throws Exception {
                        if (urgentRequestTypeResponseResponse.code() == ConfigurationFile.Constants.SUCCESS_CODE) {
                            System.out.println("Urgent Request Type :" + urgentRequestTypeResponseResponse.body().getData().size());
                            List<BaseModel> urgents = urgentRequestTypeResponseResponse.body().getData();
                            setData(urgents);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }*/

    public void getUrgentRequestTypes(){
        data.clear();
        final UrgentRequestTypeResponse urgentRequestTypeResponse=(UrgentRequestTypeResponse) prefrenceUtils.getSavedObject(ConfigurationFile.SharedPrefConstants.PREF_URGNENTS_OBJECT,UrgentRequestTypeResponse.class);
        if(urgentRequestTypeResponse==null) {
            if(NetWorkConnection.isConnectingToInternet(context)) {
             //   progressBar.set(View.VISIBLE);
                CustomUtils.getInstance().showProgressDialog((Activity)context);
                ConstantsCallsUtils.getInstance().getUrgentRequestTypes(new NetworkCallback() {
                    @Override
                    public <E> void onSuccess(Object response, int code) {
                      //  progressBar.set(View.GONE);
                        CustomUtils.getInstance().cancelDialog();
                        if (code == ConfigurationFile.Constants.SUCCESS_CODE) {
                            System.out.println("Brand Details");
                            UrgentRequestTypeResponse urgentRequestTypeResponse1 = (UrgentRequestTypeResponse) response;
                            prefrenceUtils.saveObjectToSharedPreferences(ConfigurationFile.SharedPrefConstants.PREF_URGNENTS_OBJECT, urgentRequestTypeResponse1);
                            setData(urgentRequestTypeResponse1.getData());
                        }
                    }

                    @Override
                    public void onFailure(Throwable throwable) {

                        //progressBar.set(View.GONE);
                        CustomUtils.getInstance().cancelDialog();
                    }



                    @Override
                    public <E> void onUnuthenticated(int code) {
                        CustomUtils.getInstance().endSession((Activity)context);
                    }
                },CustomUtils.getInstance().getAppLanguage(context));
            }else {
                navigator.updateUi(ConfigurationFile.Constants.NO_INTERNET_CONNECTION_CODE);
            }
        }else {
            setData(urgentRequestTypeResponse.getData());
        }
    }

    public void setData(List<BaseModel> data) {
        this.data = data;
        notifyPropertyChanged(BR.data);

    }

    @Bindable
    public List<BaseModel> getData() {
        return data;
    }

    public void cancelDialog(View view) {
      if (((MyApplication)(MyApplication.getAppContext())).getCustomDialogCode()==ConfigurationFile.Constants.DISPLAY_SPECIALIZATION_DIALOG)
        ((MyApplication) (MyApplication.getAppContext())).clearSpecializations();
      else
          ((MyApplication) (MyApplication.getAppContext())).clearUrgentTypes();

        System.out.println("Specialization Size :"+ ((MyApplication) (MyApplication.getAppContext())).getBasicspecializations().size());
        navigator.updateUi(ConfigurationFile.Constants.CANCEL_DIALOG_DATA);

    }

    public void submitDialog(View view) {
        if (((MyApplication)(MyApplication.getAppContext())).getCustomDialogCode()==ConfigurationFile.Constants.DISPLAY_SPECIALIZATION_DIALOG)
        ((MyApplication) (MyApplication.getAppContext())).setBasicspecializations( ((MyApplication) (MyApplication.getAppContext())).getTempspecializations());
        else
            ((MyApplication) (MyApplication.getAppContext())).setBasicUrgentTypes( ((MyApplication) (MyApplication.getAppContext())).getTempUrgentTypes());
        navigator.updateUi(ConfigurationFile.Constants.SUBMIT_DIALOG_DATA);
        updateViewModel.updateUi(code==ConfigurationFile.Constants.DISPLAY_SPECIALIZATION_DIALOG ? ConfigurationFile.Constants.DISPLAY_SPECIALIZATION_DIALOG_TEXT:ConfigurationFile.Constants.DISPLAY_URGENT_TYPES_DIALOG_text);
    }


}
