package com.findandfix.workshop.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;

import com.findandfix.workshop.R;
import com.findandfix.workshop.databinding.DialogCitiesLayoutBinding;
import com.findandfix.workshop.model.global.BaseModel;
import com.findandfix.workshop.ui.callback.BaseInterface;
import com.findandfix.workshop.utils.ConfigurationFile;
import com.findandfix.workshop.viewmodel.CustomDialogCitiesViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by DELL on 05/04/2018.
 */

public class CitiesDialog extends Dialog implements BaseInterface {

    private DialogCitiesLayoutBinding binding;
    private CustomDialogCitiesViewModel viewModel;
    private Context context;
    private int countryId;
    public CitiesDialog(@NonNull Context context,int countryId) {
        super(context);
        this.context=context;
        this.countryId=countryId;
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent( BaseModel baseModel){
        this.dismiss();
    }

    @Override
    public void updateUi(int code) {

                 if(code== ConfigurationFile.Constants.NO_INTERNET_CONNECTION_CODE)
                    Snackbar.make(binding.cvParent, R.string.internet_connection,Snackbar.LENGTH_LONG).show();
    }

    public void initBinding(){
        binding= DataBindingUtil.inflate(this.getLayoutInflater(), R.layout.dialog_cities_layout, null, false);
        setContentView(binding.getRoot());
        viewModel=new CustomDialogCitiesViewModel(context,countryId,this);
        binding.setDialogViewModel(viewModel);
        binding.rvCities.setLayoutManager(new LinearLayoutManager(context));
    }


}
