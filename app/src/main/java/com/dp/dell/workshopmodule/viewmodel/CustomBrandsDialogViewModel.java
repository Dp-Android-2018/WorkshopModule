package com.dp.dell.workshopmodule.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableInt;
import android.view.View;

import com.dp.dell.workshopmodule.model.global.BrandItem;
import com.dp.dell.workshopmodule.model.response.BrandsResponse;
import com.dp.dell.workshopmodule.utils.ConfigurationFile;
import com.dp.dell.workshopmodule.utils.ConstantsCallsUtils;
import com.dp.dell.workshopmodule.utils.CustomUtils;
import com.dp.dell.workshopmodule.utils.NetWorkConnection;
import com.dp.dell.workshopmodule.utils.SharedPrefrenceUtils;
import com.dp.dell.workshopmodule.view.ui.Application.MyApplication;
import com.dp.dell.workshopmodule.view.ui.callback.BaseInterface;
import com.dp.dell.workshopmodule.view.ui.callback.NetworkCallback;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by DELL on 24/03/2018.
 */

public class CustomBrandsDialogViewModel extends BaseObservable {

    private Context context;
    private BaseInterface navigator;
    private List<BrandItem>brandItems;
    private  BaseInterface updateViewModel;
    private SharedPrefrenceUtils prefrenceUtils;
    public ObservableInt progressBar;
    public CustomBrandsDialogViewModel(Context context, BaseInterface navigator, BaseInterface updateViewModel) {
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
               // progressBar.set(View.VISIBLE);
                CustomUtils.getInstance().showProgressDialog((Activity)context);
                ConstantsCallsUtils.getInstance().getBrands(new NetworkCallback() {
                    @Override
                    public <E> void onSuccess(Object response, int code) {
                     //   progressBar.set(View.GONE);
                        CustomUtils.getInstance().cancelDialog();
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
            setBrandItems(brandsResponse.getData());
        }
    }



    public void setBrandItems(List<BrandItem> brandItems) {
        this.brandItems = brandItems;
        notifyPropertyChanged(com.dp.dell.workshopmodule.BR.brandItems);
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
                  updateViewModel.updateUi(ConfigurationFile.Constants.UPDATE_BRANDS_DIALOG);
    }
}
