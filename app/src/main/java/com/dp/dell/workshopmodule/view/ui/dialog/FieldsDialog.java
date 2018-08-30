package com.dp.dell.workshopmodule.view.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;

import com.dp.dell.workshopmodule.R;
import com.dp.dell.workshopmodule.databinding.DialogCitiesLayoutBinding;
import com.dp.dell.workshopmodule.databinding.DialogFieldsLayoutBinding;
import com.dp.dell.workshopmodule.model.global.BaseModel;
import com.dp.dell.workshopmodule.viewmodel.CustomDialogCitiesViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by DELL on 05/04/2018.
 */

public class FieldsDialog extends Dialog {

    private DialogFieldsLayoutBinding binding;
    private CustomDialogCitiesViewModel viewModel;
    private Context context;

    public FieldsDialog(@NonNull Context context) {
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent( BaseModel baseModel){
        this.dismiss();
    }


    public void initBinding(){
        binding= DataBindingUtil.inflate(this.getLayoutInflater(), R.layout.dialog_fields_layout, null, false);
        setContentView(binding.getRoot());
        viewModel=new CustomDialogCitiesViewModel(context,-1,null);
        binding.setDialogViewModel(viewModel);
        binding.rvCities.setLayoutManager(new LinearLayoutManager(context));
    }
}
