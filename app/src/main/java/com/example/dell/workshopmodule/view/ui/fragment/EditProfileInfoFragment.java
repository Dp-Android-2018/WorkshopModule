package com.example.dell.workshopmodule.view.ui.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.dell.workshopmodule.R;
import com.example.dell.workshopmodule.databinding.FragmentEditProfileInfoLayoutBinding;
import com.example.dell.workshopmodule.model.global.BaseModel;
import com.example.dell.workshopmodule.model.global.CountryItem;
import com.example.dell.workshopmodule.utils.ConfigurationFile;
import com.example.dell.workshopmodule.view.ui.activity.AddAdvertismentRange;
import com.example.dell.workshopmodule.view.ui.activity.AdvertismentPublished;
import com.example.dell.workshopmodule.view.ui.activity.FirstStepRegisterActivity;
import com.example.dell.workshopmodule.view.ui.adapter.CurrentRequestsAdapter;
import com.example.dell.workshopmodule.view.ui.adapter.OfferSentAdapter;
import com.example.dell.workshopmodule.view.ui.callback.BaseInterface;
import com.example.dell.workshopmodule.view.ui.dialog.CitiesDialog;
import com.example.dell.workshopmodule.view.ui.dialog.CountriesDialog;
import com.example.dell.workshopmodule.view.ui.dialog.SubmitEditProfileDialog;
import com.example.dell.workshopmodule.viewmodel.EditProfileInfoViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by DELL on 14/03/2018.
 */

public class EditProfileInfoFragment extends Fragment implements BaseInterface{

    FragmentEditProfileInfoLayoutBinding binding;
    EditProfileInfoViewModel viewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_edit_profile_info_layout,container,false);
        View v=binding.getRoot();
        viewModel=new EditProfileInfoViewModel(getActivity(),this,this);
        binding.setViewmodel(viewModel);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.onResume();
    }

    @Override
    public void updateUi(int code) {
        if(code== ConfigurationFile.Constants.SHOW_DIALOG_CODE){
            CountriesDialog dialog=new CountriesDialog(getActivity());
            showDialog(dialog);
        }else if(code== ConfigurationFile.Constants.SHOW_CITIES_DIALOG_CODE){
            CitiesDialog dialog=new CitiesDialog(getActivity(),2);
            showDialog(dialog);
        } else if (code == ConfigurationFile.Constants.FILL_ALL_DATTA)
            showSnackBar(getResources().getString(R.string.fill_data));
        else if (code == ConfigurationFile.Constants.INVALID_EMAIL_FORMAT)
            showSnackBar(getResources().getString(R.string.fill_data));
    }

    @Override
    public void onPause() {
        super.onPause();
        viewModel.onPause();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent( CountryItem countryItem){
        viewModel.setCountryData(countryItem);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCityMessageEvent( BaseModel baseModel){
        if(!(baseModel instanceof CountryItem))
            viewModel.setCityData(baseModel);
    }

    public void showDialog(Dialog dialog){
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    public void showSnackBar(String message){
        Snackbar.make(getView(),message,Snackbar.LENGTH_LONG).show();
    }
}