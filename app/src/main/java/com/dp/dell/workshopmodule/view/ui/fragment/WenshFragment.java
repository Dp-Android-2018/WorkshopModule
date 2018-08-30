package com.dp.dell.workshopmodule.view.ui.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dp.dell.workshopmodule.R;
import com.dp.dell.workshopmodule.databinding.FragmentWenshLayoutBinding;
import com.dp.dell.workshopmodule.model.global.UserData;
import com.dp.dell.workshopmodule.model.response.WorkShopCountResponse;
import com.dp.dell.workshopmodule.network.ApiClient;
import com.dp.dell.workshopmodule.network.EndPoints;
import com.dp.dell.workshopmodule.utils.ConfigurationFile;
import com.dp.dell.workshopmodule.utils.CustomUtils;
import com.dp.dell.workshopmodule.utils.NetWorkConnection;
import com.dp.dell.workshopmodule.view.ui.activity.AddAdvertisement;
import com.dp.dell.workshopmodule.view.ui.activity.EditProfileInfoActivity;
import com.dp.dell.workshopmodule.view.ui.activity.NormalRequestActivity;
import com.dp.dell.workshopmodule.view.ui.activity.UrgentRequestActivity;
import com.dp.dell.workshopmodule.view.ui.callback.BaseInterface;
import com.dp.dell.workshopmodule.viewmodel.MainFragViewModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by DELL on 07/05/2018.
 */

public class WenshFragment extends Fragment implements BaseInterface{
    FragmentWenshLayoutBinding binding;
    private MainFragViewModel viewModel;
    private UserData userData;
    MediaPlayer mp ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_wensh_layout,container,false);
        View v=binding.getRoot();
        mp=MediaPlayer.create(getActivity(),R.raw.a1);
        viewModel=new MainFragViewModel(getActivity(),this);
        userData=CustomUtils.getInstance().getSaveUserObject(getActivity().getApplicationContext());
        binding.setViewModel(viewModel);
        handlAnimation();
        getUiData();
        return v;}

    @Override
    public void updateUi(int code) {
        if(code== ConfigurationFile.Constants.COMPLETE_ANIMATION_CODE) {
            completeImage();
            try {
                if (mp.isPlaying()) {
                    mp.stop();
                    mp.release();
                    mp = MediaPlayer.create(getActivity(), R.raw.a1);
                } mp.start();
            } catch(Exception e) { e.printStackTrace(); }
        } else if(code== ConfigurationFile.Constants.NORMAL_REQUEST_ACTIVITY)
            startActivity(new Intent(getActivity(),UrgentRequestActivity.class));
        else if(code== ConfigurationFile.Constants.EDIT_PROFILE_ACTIVITY)
            startActivity(new Intent(getActivity(),EditProfileInfoActivity.class));
        else if(code== ConfigurationFile.Constants.ADD_ADVERTISEMENT_ACTIVITY)
            startActivity(new Intent(getActivity(),AddAdvertisement.class));
    }

    public void handlAnimation(){
        binding.animationView.playAnimation();
        binding.animationView.setMaxProgress(0.3F);}

    public void completeImage(){
        binding.animationView.playAnimation();
        binding.animationView.setMinProgress(0.3F);
        binding.animationView.setMaxProgress(1.0F);}



    public void getUiData(){
        if(NetWorkConnection.isConnectingToInternet(getActivity())) {
            CustomUtils.getInstance().showProgressDialog(getActivity());
            ApiClient.getClient().create(EndPoints.class).getCounts(ConfigurationFile.Constants.API_KEY, CustomUtils.getInstance().getAppLanguage(getActivity()), ConfigurationFile.Constants.Content_Type, ConfigurationFile.Constants.Content_Type, "Bearer "+userData.getToken())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(defaultResponseResponse -> {
                        CustomUtils.getInstance().cancelDialog();
                        int code=defaultResponseResponse.code();
                        System.out.println("Code :"+code);
                        if(code==ConfigurationFile.Constants.SUCCESS_CODE){
                            WorkShopCountResponse response=defaultResponseResponse.body();
                        //    binding.tvUrgentRequestNum.setText(String.valueOf(response.getUrgentRequestsCount()));
                            binding.tvNormalRequestNum.setText(String.valueOf(response.getUrgentRequestsCount()));
                            binding.tvAdvNum.setText(String.valueOf(response.getAdvsCount()));

                        }else if(code==ConfigurationFile.Constants.UNAUTHENTICATED_CODE){
                            CustomUtils.getInstance().logout(getActivity());
                        }else if(code==ConfigurationFile.Constants.UNSUBSCRIBE_CODE){
                            Snackbar.make(getActivity().findViewById(R.id.drawer), R.string.unsubscribed,Snackbar.LENGTH_LONG).show();
                        }

                    }, throwable -> {
                        CustomUtils.getInstance().cancelDialog();
                        System.out.println("Normat Request :"+throwable.getMessage());
                    });
        }else {
            Snackbar.make(getActivity().findViewById(R.id.drawer),getActivity().getResources().getString(R.string.internet_connection),Snackbar.LENGTH_LONG).show();
        }
    }
}
