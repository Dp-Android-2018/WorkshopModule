package com.example.dell.workshopmodule.view.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.example.dell.workshopmodule.R;
import com.example.dell.workshopmodule.databinding.DialogAddOfferLayoutBinding;
import com.example.dell.workshopmodule.utils.ConfigurationFile;
import com.example.dell.workshopmodule.utils.CustomUtils;
import com.example.dell.workshopmodule.view.ui.callback.BaseInterface;
import com.example.dell.workshopmodule.viewmodel.AddOfferViewModel;

/**
 * Created by DELL on 14/03/2018.
 */

public class AddOfferDialog extends Dialog implements BaseInterface{
    DialogAddOfferLayoutBinding binding;
    private Context context;
    private int requestId;
    private BaseInterface callback;
    private int requestType;
    public AddOfferDialog(@NonNull Context context,int requestId,int requestType,BaseInterface callback) {
        super(context);
        this.context=context;
        this.requestId=requestId;
        this.callback=callback;
        this.requestType=requestType;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
    }

    @Override
    public void updateUi(int code) {
        if(code== ConfigurationFile.Constants.NO_INTERNET_CONNECTION_CODE)
            Snackbar.make(binding.cvParent,context.getString(R.string.internet_connection),Snackbar.LENGTH_LONG).show();
        else if(code== ConfigurationFile.Constants.UNAUTHENTICATED_CODE){
            CustomUtils.getInstance().logout((Activity)context);
        }else if(code== ConfigurationFile.Constants.CANT_COMPLETE_REQUEST_CODE){
            Snackbar.make(binding.cvParent, R.string.cant_add_offer,Snackbar.LENGTH_LONG).show();
        }else if(code== ConfigurationFile.Constants.EMPTY_OFFER_DESCRIBTION_CODE){
            Snackbar.make(binding.cvParent, R.string.add_offer_desc,Snackbar.LENGTH_LONG).show();
        }else if(code== ConfigurationFile.Constants.EMPTY_OFFER_PRICE_CODE){
            Snackbar.make(binding.cvParent, R.string.add_offer_price,Snackbar.LENGTH_LONG).show();
        }else if(code== ConfigurationFile.Constants.EMPTY_OFFER_DATE_CODE){
            Snackbar.make(binding.cvParent, R.string.add_offer_date,Snackbar.LENGTH_LONG).show();
        }else if(code== ConfigurationFile.Constants.OFFER_ADDED_SUCCESSFULLY){
            this.dismiss();
        }else if(code== ConfigurationFile.Constants.SUCCESS_CODE){
            this.dismiss();
        }

    }

    public void initBinding(){
        binding= DataBindingUtil.inflate(this.getLayoutInflater(),R.layout.dialog_add_offer_layout,null,false);
        setContentView(binding.getRoot());
        AddOfferViewModel addOfferViewModel=new AddOfferViewModel(context,requestId,requestType,this);
        binding.setAddOfferViewModel(addOfferViewModel);
    }


}
