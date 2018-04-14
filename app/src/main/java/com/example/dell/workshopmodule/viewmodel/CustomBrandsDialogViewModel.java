package com.example.dell.workshopmodule.viewmodel;

import android.app.ProgressDialog;
import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableInt;
import android.view.View;

import com.example.dell.workshopmodule.model.global.BrandItem;
import com.example.dell.workshopmodule.model.response.BrandsResponse;
import com.example.dell.workshopmodule.model.response.CountryResponse;
import com.example.dell.workshopmodule.network.ApiClient;
import com.example.dell.workshopmodule.network.EndPoints;
import com.example.dell.workshopmodule.utils.ConfigurationFile;
import com.example.dell.workshopmodule.utils.ConstantsCallsUtils;
import com.example.dell.workshopmodule.utils.NetWorkConnection;
import com.example.dell.workshopmodule.utils.SharedPrefrenceUtils;
import com.example.dell.workshopmodule.view.ui.Application.MyApplication;
import com.example.dell.workshopmodule.view.ui.callback.DisplayDialogNavigator;
import com.example.dell.workshopmodule.view.ui.callback.BaseInterface;
import com.example.dell.workshopmodule.view.ui.callback.NetworkCallback;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;



/**
 * Created by DELL on 24/03/2018.
 */

public class CustomBrandsDialogViewModel extends BaseObservable {

    private Context context;
    private BaseInterface navigator;
    private List<BrandItem>brandItems;
    private  DisplayDialogNavigator updateViewModel;
    private SharedPrefrenceUtils prefrenceUtils;
    public ObservableInt progressBar;
    public CustomBrandsDialogViewModel(Context context, BaseInterface navigator, DisplayDialogNavigator updateViewModel) {
        this.context=context;
        this.navigator=navigator;
        brandItems=new ArrayList<>();
        prefrenceUtils=new SharedPrefrenceUtils(context);
        this.updateViewModel=updateViewModel;
        progressBar=new ObservableInt(View.GONE);
        getBrands();
    }



    public void getBrands(){
        brandItems.clear();
        final BrandsResponse brandsResponse=(BrandsResponse) prefrenceUtils.getSavedObject(ConfigurationFile.SharedPrefConstants.PREF_BRANDS_OBJECT,BrandsResponse.class);
        if(brandsResponse==null) {
            if(NetWorkConnection.isConnectingToInternet(context)) {
                progressBar.set(View.VISIBLE);
                ConstantsCallsUtils.getInstance().getBrands(new NetworkCallback() {
                    @Override
                    public <E> void onSuccess(Object response, int code) {
                        progressBar.set(View.GONE);
                        System.out.println("Brands Code :"+code);
                        System.out.println("Brand Size :"+((BrandsResponse)response).getData().size());
                        if (code == ConfigurationFile.Constants.SUCCESS_CODE) {
                            System.out.println("Brand Details");
                            BrandsResponse brandsResponse1 = (BrandsResponse) response;
                            prefrenceUtils.saveObjectToSharedPreferences(ConfigurationFile.SharedPrefConstants.PREF_BRANDS_OBJECT, brandsResponse1);
                            setBrandItems(brandsResponse1.getData());
                            System.out.println("Brand Size 2:"+brandItems.size());
                        }
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        progressBar.set(View.GONE);
                    }
                });
            }else {
                navigator.updateUi(ConfigurationFile.Constants.NO_INTERNET_CONNECTION_CODE);
            }
        }else {
            setBrandItems(brandsResponse.getData());
        }
    }



    public void setBrandItems(List<BrandItem> brandItems) {
        this.brandItems = brandItems;
        notifyPropertyChanged(com.example.dell.workshopmodule.BR.brandItems);
    }

    @Bindable
    public List<BrandItem> getBrandItems() {
        return brandItems;
    }


    public void cancelDialog(View view) {

            ((MyApplication) (MyApplication.getAppContext())).clearTempBrands();
                navigator.updateUi(ConfigurationFile.Constants.CANCEL_DIALOG_DATA);

    }

    public void submitDialog(View view) {
            ((MyApplication) (MyApplication.getAppContext())).setBasicBrands( ((MyApplication) (MyApplication.getAppContext())).getTempBrands());
                navigator.updateUi(ConfigurationFile.Constants.SUBMIT_DIALOG_DATA);
                  updateViewModel.updateWorkshopData(ConfigurationFile.Constants.UPDATE_BRANDS_DIALOG);
    }
}
