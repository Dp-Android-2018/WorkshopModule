package com.example.dell.workshopmodule.view.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;

import com.example.dell.workshopmodule.R;
import com.example.dell.workshopmodule.databinding.DialogCustomBrandsLayoutBinding;
import com.example.dell.workshopmodule.utils.ConfigurationFile;
import com.example.dell.workshopmodule.view.ui.callback.DisplayDialogNavigator;
import com.example.dell.workshopmodule.view.ui.callback.BaseInterface;
import com.example.dell.workshopmodule.viewmodel.CustomBrandsDialogViewModel;

/**
 * Created by DELL on 24/03/2018.
 */

public class CustomBrandsDialog extends Dialog implements BaseInterface {

    private DialogCustomBrandsLayoutBinding binding;
    private CustomBrandsDialogViewModel viewModel;
    private Context context;
    private DisplayDialogNavigator updateModel;
    public CustomBrandsDialog(@NonNull Context context,DisplayDialogNavigator updatemodel) {
        super(context);
        this.context=context;
        this.updateModel=updatemodel;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
    }

    public void initBinding(){
        binding= DataBindingUtil.inflate(this.getLayoutInflater(), R.layout. dialog_custom_brands_layout, null, false);
        setContentView(binding.getRoot());
        viewModel=new CustomBrandsDialogViewModel(context,this,updateModel);
        binding.setDialogBrandsViewModel(viewModel);
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
