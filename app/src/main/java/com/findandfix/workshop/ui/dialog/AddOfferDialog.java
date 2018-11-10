package com.findandfix.workshop.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;

import com.findandfix.workshop.R;
import com.findandfix.workshop.databinding.DialogAddOfferLayoutBinding;
import com.findandfix.workshop.model.global.RequestData;
import com.findandfix.workshop.model.response.OfferData;
import com.findandfix.workshop.ui.callback.BaseInterface;
import com.findandfix.workshop.utils.ConfigurationFile;
import com.findandfix.workshop.utils.CustomUtils;
import com.findandfix.workshop.viewmodel.AddOfferViewModel;

/**
 * Created by DELL on 14/03/2018.
 */

public class AddOfferDialog extends Dialog implements BaseInterface {
    DialogAddOfferLayoutBinding binding;
    private Context context;
    private RequestData requestData;
    private BaseInterface callback;
    private int requestType;
    private OfferData offerData;
    public AddOfferDialog(@NonNull Context context, RequestData requestData, int requestType, BaseInterface callback, OfferData offerData) {
        super(context);
        this.context=context;
        this.requestData=requestData;
        this.callback=callback;
        this.requestType=requestType;
        this.offerData=offerData;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
    }

    @Override
    public void updateUi(int code) {
        if(code== ConfigurationFile.Constants.NO_INTERNET_CONNECTION_CODE)
            showSnackBar(R.string.internet_connection);
        else if(code== ConfigurationFile.Constants.UNAUTHENTICATED_CODE){
            CustomUtils.getInstance().logout((Activity)context);
        }else if(code== ConfigurationFile.Constants.CANT_COMPLETE_REQUEST_CODE){
            showSnackBar(R.string.cant_add_offer);
        }else if(code== ConfigurationFile.Constants.EMPTY_OFFER_DESCRIBTION_CODE){
            showSnackBar(R.string.add_offer_desc);
        }else if(code== ConfigurationFile.Constants.EMPTY_OFFER_PRICE_CODE){
            showSnackBar(R.string.add_offer_price);
        }else if(code== ConfigurationFile.Constants.EMPTY_OFFER_DATE_CODE){
            showSnackBar(R.string.add_offer_date);
        }else if(code== ConfigurationFile.Constants.OFFER_ADDED_SUCCESSFULLY){
            this.dismiss();
            callback.updateUi(ConfigurationFile.Constants.OFFER_ADDED_SUCCESSFULLY);
        }else if(code== ConfigurationFile.Constants.SUCCESS_CODE){
            this.dismiss();
            callback.updateUi(ConfigurationFile.Constants.OFFER_UPDATED_SUCCESSFULLY);
        }

    }

    public void initBinding(){
        binding= DataBindingUtil.inflate(this.getLayoutInflater(),R.layout.dialog_add_offer_layout,null,false);
        setContentView(binding.getRoot());
        AddOfferViewModel addOfferViewModel=new AddOfferViewModel(context,requestData,requestType,this,offerData);
        binding.setAddOfferViewModel(addOfferViewModel);
    }


    public void showSnackBar(int message){
        Snackbar.make(binding.cvParent,message,Snackbar.LENGTH_LONG).show();
    }


}
