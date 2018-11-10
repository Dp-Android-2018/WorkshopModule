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
import com.findandfix.workshop.model.global.WenshTypes;
import com.findandfix.workshop.model.response.WinshResponse;
import com.findandfix.workshop.network.ApiClient;
import com.findandfix.workshop.network.EndPoints;
import com.findandfix.workshop.ui.Application.MyApplication;
import com.findandfix.workshop.ui.callback.BaseInterface;
import com.findandfix.workshop.utils.ConfigurationFile;
import com.findandfix.workshop.utils.CustomUtils;
import com.findandfix.workshop.utils.NetWorkConnection;
import com.findandfix.workshop.utils.SharedPrefrenceUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WenshDialogViewModel extends BaseObservable {
    private Context context;
    private List<WenshTypes> data;
    private BaseInterface navigator;
    private BaseInterface updateViewModel;
    private SharedPrefrenceUtils prefrenceUtils;
    public ObservableInt progressBar;
    public ObservableField<String> dialogTitle;
    public WenshDialogViewModel(Context context, BaseInterface navigator, BaseInterface updateViewModel) {
        this.context = context;
        this.navigator = navigator;
        this.updateViewModel = updateViewModel;
        progressBar=new ObservableInt(View.GONE);
        dialogTitle=new ObservableField<>();
        prefrenceUtils=new SharedPrefrenceUtils(context);
        data = new ArrayList<>();

            dialogTitle.set(context.getString(R.string.wensh_types));
            getWenshTypes();

    }



    public void getWenshTypes(){
        data.clear();
        final WinshResponse winshResponse=(WinshResponse) prefrenceUtils.getSavedObject(ConfigurationFile.SharedPrefConstants.PREF_WENSH_TYPES_OBJECT,WinshResponse.class);
        if(winshResponse==null) {
            if(NetWorkConnection.isConnectingToInternet(context)) {

                ApiClient.getClient().create(EndPoints.class).getWinchTypes(ConfigurationFile.Constants.API_KEY,CustomUtils.getInstance().getAppLanguage((Activity)context),ConfigurationFile.Constants.Content_Type,ConfigurationFile.Constants.Content_Type)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(winshResponseResponse -> {
                            prefrenceUtils.saveObjectToSharedPreferences(ConfigurationFile.SharedPrefConstants.PREF_WENSH_TYPES_OBJECT, winshResponseResponse);
                            setData(winshResponseResponse.body().getData());
                        }, throwable -> {
                            System.out.println("throwable :"+throwable.getMessage());
                            CustomUtils.getInstance().cancelDialog();
                        });
            }else {
                navigator.updateUi(ConfigurationFile.Constants.NO_INTERNET_CONNECTION_CODE);
            }
        }else {
            setData(winshResponse.getData());
        }
    }



    public void setData(List<WenshTypes> data) {
        this.data = data;
        notifyPropertyChanged(BR.data); }

    @Bindable
    public List<WenshTypes> getData() {
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
     /*   if (((MyApplication)(MyApplication.getAppContext())).getCustomDialogCode()==ConfigurationFile.Constants.DISPLAY_SPECIALIZATION_DIALOG)
            ((MyApplication) (MyApplication.getAppContext())).setBasicspecializations( ((MyApplication) (MyApplication.getAppContext())).getTempspecializations());
        else
            ((MyApplication) (MyApplication.getAppContext())).setBasicUrgentTypes( ((MyApplication) (MyApplication.getAppContext())).getTempUrgentTypes());
        navigator.updateUi(ConfigurationFile.Constants.SUBMIT_DIALOG_DATA);
        updateViewModel.updateUi(code==ConfigurationFile.Constants.DISPLAY_SPECIALIZATION_DIALOG ? ConfigurationFile.Constants.DISPLAY_SPECIALIZATION_DIALOG_TEXT:ConfigurationFile.Constants.DISPLAY_URGENT_TYPES_DIALOG_text);*/
    }

}
