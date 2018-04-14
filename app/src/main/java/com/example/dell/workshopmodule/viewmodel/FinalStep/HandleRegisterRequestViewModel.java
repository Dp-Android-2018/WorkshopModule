package com.example.dell.workshopmodule.viewmodel.FinalStep;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.dell.workshopmodule.BR;
import com.example.dell.workshopmodule.model.global.UserData;
import com.example.dell.workshopmodule.model.request.RegisterRequest;
import com.example.dell.workshopmodule.model.response.LoginResponse;
import com.example.dell.workshopmodule.network.ApiClient;
import com.example.dell.workshopmodule.network.EndPoints;
import com.example.dell.workshopmodule.utils.ConfigurationFile;
import com.example.dell.workshopmodule.utils.NetWorkConnection;
import com.example.dell.workshopmodule.utils.SharedPrefrenceUtils;
import com.example.dell.workshopmodule.view.ui.callback.BaseInterface;
import com.example.dell.workshopmodule.view.ui.callback.NetworkCallback;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by DELL on 26/03/2018.
 */

public class HandleRegisterRequestViewModel extends BaseObservable {

    private Context context;
    private  Boolean progressDialog;
    private BaseInterface callback;
    public HandleRegisterRequestViewModel(Context context,BaseInterface callback) {
        this.context=context;
        this.callback=callback;

    }

    public void handleRegister(RegisterRequest registerRequest, List<String>papers, final BaseInterface callback){

        if(NetWorkConnection.isConnectingToInternet(context)) {
            registerRequest.setPapers(papers);
            setProgressDialog(true);
            ApiClient.getClient().create(EndPoints.class).register(ConfigurationFile.Constants.API_KEY, "EN", ConfigurationFile.Constants.Content_Type, ConfigurationFile.Constants.Content_Type, registerRequest)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Response<LoginResponse>>() {
                        @Override
                        public void accept(Response<LoginResponse> loginResponseResponse) throws Exception {
                            setProgressDialog(false);
                            if (loginResponseResponse.code() == ConfigurationFile.Constants.SUCCESS_CODE) {
                                System.out.println("Handle Register :" + loginResponseResponse.code());
                                LoginResponse loginResponse = loginResponseResponse.body();
                                UserData data = loginResponse.getData();
                                SharedPrefrenceUtils pref = new SharedPrefrenceUtils(context);
                                pref.saveObjectToSharedPreferences(ConfigurationFile.SharedPrefConstants.PREF_WORKSHOP_DATA, data);
                                callback.updateUi(ConfigurationFile.Constants.MOVE_TO_NEXT_ACTIVITY);
                            } else if (loginResponseResponse.code() == ConfigurationFile.Constants.INVALID_DATA) {
                                System.out.println("Handle Register :" + loginResponseResponse.code());
                                callback.updateUi(ConfigurationFile.Constants.INVALID_DATA);
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            setProgressDialog(false);
                            System.out.println("Handle Register :" + throwable.getMessage());
                        }
                    });
        }else {
            callback.updateUi(ConfigurationFile.Constants.NO_INTERNET_CONNECTION_CODE);
        }
    }



    public void setProgressDialog(boolean progressDialog) {
        System.out.println("Changed:"+progressDialog);
        this.progressDialog = progressDialog;
        notifyPropertyChanged(BR.progressDialog);
    }

    @Bindable
    public Boolean getProgressDialog() {
        return progressDialog;
    }
}
