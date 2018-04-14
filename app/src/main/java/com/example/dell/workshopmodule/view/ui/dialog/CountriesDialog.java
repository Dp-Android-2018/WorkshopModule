package com.example.dell.workshopmodule.view.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.example.dell.workshopmodule.R;
import com.example.dell.workshopmodule.databinding.DialogCountriesLayoutBinding;
import com.example.dell.workshopmodule.model.global.CountryItem;
import com.example.dell.workshopmodule.model.global.MessageEvent;
import com.example.dell.workshopmodule.utils.ConfigurationFile;
import com.example.dell.workshopmodule.view.ui.callback.BaseInterface;
import com.example.dell.workshopmodule.viewmodel.CustomBrandsDialogViewModel;
import com.example.dell.workshopmodule.viewmodel.CustomDialogCountriesViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
//import com.example.dell.workshopmodule.databinding.DialogCountriesLayoutBinding;

/**
 * Created by DELL on 10/03/2018.
 */

public class CountriesDialog extends Dialog implements BaseInterface{

        private DialogCountriesLayoutBinding binding;
        private CustomDialogCountriesViewModel viewModel;
        private Context context;
    public CountriesDialog(@NonNull Context context) {
        super(context);
        this.context=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    public void initBinding(){
        binding= DataBindingUtil.inflate(this.getLayoutInflater(), R.layout.dialog_countries_layout, null, false);
        setContentView(binding.getRoot());
        viewModel=new CustomDialogCountriesViewModel(context,this);
        binding.setDialogViewModel(viewModel);
        binding.rvCountries.setLayoutManager(new LinearLayoutManager(context));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent( CountryItem  countryItem){
        this.dismiss();
    }

    @Override
    public void updateUi(int code) {
                      if(code== ConfigurationFile.Constants.NO_INTERNET_CONNECTION_CODE)
                Snackbar.make(binding.cvParent,context.getString(R.string.internet_connection),Snackbar.LENGTH_LONG).show();
    }
}
