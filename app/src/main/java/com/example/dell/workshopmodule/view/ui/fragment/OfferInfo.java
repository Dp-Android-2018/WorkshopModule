package com.example.dell.workshopmodule.view.ui.fragment;

import android.databinding.BaseObservable;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dell.workshopmodule.R;
import com.example.dell.workshopmodule.databinding.FragmentCarInfoLayotBinding;
import com.example.dell.workshopmodule.databinding.FragmentOfferInfoAndroidBinding;
import com.example.dell.workshopmodule.model.global.RequestData;
import com.example.dell.workshopmodule.utils.ConfigurationFile;
import com.example.dell.workshopmodule.utils.CustomUtils;
import com.example.dell.workshopmodule.view.ui.callback.BaseInterface;
import com.example.dell.workshopmodule.viewmodel.OfferInfoViewModel;
import com.google.gson.Gson;

/**
 * Created by DELL on 12/03/2018.
 */

public class OfferInfo extends Fragment implements BaseInterface{
    FragmentOfferInfoAndroidBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_offer_info_android,container,false);
        View v=binding.getRoot();
        OfferInfoViewModel offerInfoViewModel=new OfferInfoViewModel(getActivity().getApplicationContext(),this,getRequestObj());
        binding.setOfferViewModel(offerInfoViewModel);
        return v;
    }

    @Override
    public void updateUi(int code) {
                     if(code== ConfigurationFile.Constants.UNAUTHENTICATED_CODE)
                             CustomUtils.getInstance().logout(getActivity());
                     else if(code== ConfigurationFile.Constants.CANT_COMPLETE_REQUEST_CODE){
                         Snackbar.make(binding.rlParent, R.string.cant_add_offer,Snackbar.LENGTH_LONG).show();
                     }
    }

    public RequestData getRequestObj(){
        RequestData requestData =(RequestData) getArguments().getSerializable(ConfigurationFile.SharedPrefConstants.PREF_REQUEST_OBJECT);
        if(requestData!=null){
            System.out.println("Request Data 1:"+new Gson().toJson(requestData).toString());;
        }
        return requestData;
    }
}
