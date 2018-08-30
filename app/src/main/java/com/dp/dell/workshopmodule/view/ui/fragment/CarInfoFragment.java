package com.dp.dell.workshopmodule.view.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dp.dell.workshopmodule.R;
import com.dp.dell.workshopmodule.databinding.FragmentCarInfoLayotBinding;
import com.dp.dell.workshopmodule.model.global.RequestData;
import com.dp.dell.workshopmodule.utils.ConfigurationFile;
import com.dp.dell.workshopmodule.viewmodel.CarInfoFragmentViewModel;
import com.google.gson.Gson;

/**
 * Created by DELL on 12/03/2018.
 */

public class CarInfoFragment extends Fragment {
    private FragmentCarInfoLayotBinding binding;
    private CarInfoFragmentViewModel viewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_car_info_layot,container,false);
        View v=binding.getRoot();
        viewModel=new CarInfoFragmentViewModel(getActivity().getApplicationContext(), getRequestObj());
        binding.setCarViewModel(viewModel);

        return v;
    }

    public RequestData getRequestObj(){
        RequestData requestData =(RequestData) getArguments().getSerializable(ConfigurationFile.SharedPrefConstants.PREF_REQUEST_OBJECT);
        if(requestData!=null){
            System.out.println("Request Data 1:"+new Gson().toJson(requestData).toString());;
        }
        return requestData;
    }


}
