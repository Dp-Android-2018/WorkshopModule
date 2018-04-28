package com.example.dell.workshopmodule.view.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;

import com.example.dell.workshopmodule.R;
import com.example.dell.workshopmodule.databinding.DialogCustomLayoutBinding;
import com.example.dell.workshopmodule.utils.ConfigurationFile;
import com.example.dell.workshopmodule.view.ui.callback.DisplayDialogNavigator;
import com.example.dell.workshopmodule.view.ui.callback.BaseInterface;
import com.example.dell.workshopmodule.viewmodel.CustomDialogViewModel;

/**
 * Created by DELL on 21/03/2018.
 */

public class CustomDialog extends Dialog implements BaseInterface {
    DialogCustomLayoutBinding binding;
    private Context context;
    private CustomDialogViewModel viewModel;
    private int code;
    private BaseInterface navigator;
    public CustomDialog(@NonNull Context context, int code, BaseInterface navigator) {
        super(context);
        this.context=context;
        this.code=code;
        this.navigator=navigator;
    }

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
   }

    public void initBinding(){
        binding= DataBindingUtil.inflate(this.getLayoutInflater(), R.layout. dialog_custom_layout, null, false);
        setContentView(binding.getRoot());
        viewModel=new CustomDialogViewModel(context,code,this,navigator);
        binding.setDialogViewModel(viewModel);
        binding.rvData.setLayoutManager(new LinearLayoutManager(context));
    }


    @Override
    public void updateUi(int code) {
        if(code== ConfigurationFile.Constants.NO_INTERNET_CONNECTION_CODE)
            Snackbar.make(binding.cvParent,context.getString(R.string.internet_connection),Snackbar.LENGTH_LONG).show();
        else
            this.dismiss();
    }
}
