package com.findandfix.workshop.ui.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.findandfix.workshop.R;
import com.findandfix.workshop.databinding.FragmentSettingLayoutBinding;
import com.findandfix.workshop.model.global.UserData;
import com.findandfix.workshop.network.ApiClient;
import com.findandfix.workshop.network.EndPoints;
import com.findandfix.workshop.ui.activity.SplashActivity;
import com.findandfix.workshop.utils.ConfigurationFile;
import com.findandfix.workshop.utils.CustomUtils;
import com.findandfix.workshop.utils.NetWorkConnection;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by DELL on 08/05/2018.
 */

public class SettingFragment extends Fragment {

  private FragmentSettingLayoutBinding binding;
  private Dialog dialog=null;
  private UserData userData;
    String url = "http://dp-itc.com/fnf/terms.html";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_setting_layout,container,false);
        View view=binding.getRoot();
        userData=CustomUtils.getInstance().getSaveUserObject(getActivity());

        binding.rlLanguage.setOnClickListener(v -> setLanguageDialog());
        binding.btnSignOut.setOnClickListener(v -> signOut());

        binding.ivSetting.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.dp-itc.com/"));
            startActivity(browserIntent);
        });
        binding.tvTermsCondition.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        });
        binding.rlDeactivate.setOnClickListener(v -> setDialog());

        if (CustomUtils.getInstance().getAppLanguage(getActivity()).equals("ar")) {
            binding.tvLangContent.setText("العربية");
            binding.rvParent.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
           /* LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            mLayoutManager.setReverseLayout(true);
            mLayoutManager.setStackFromEnd(true);*/
        }else if (CustomUtils.getInstance().getAppLanguage(getActivity()).equals("en"))
            binding.tvLangContent.setText("English");
        return view;}

    public void deActivateAccount(){

        if(NetWorkConnection.isConnectingToInternet(getActivity().getApplicationContext())) {
            CustomUtils.getInstance().showProgressDialog(getActivity());
            ApiClient.getClient().create(EndPoints.class).deactiveAccount(ConfigurationFile.Constants.API_KEY, CustomUtils.getInstance().getAppLanguage(getActivity()), ConfigurationFile.Constants.Content_Type, ConfigurationFile.Constants.Content_Type, "Bearer "+userData.getToken())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(defaultResponseResponse -> {
                        CustomUtils.getInstance().cancelDialog();
                        int code=defaultResponseResponse.code();
                        System.out.println("Code :"+code);
                        if(code==ConfigurationFile.Constants.SUCCESS_CODE){
                           // dialog.dismiss();
                            Snackbar.make(getActivity().findViewById(R.id.drawer), R.string.account_deactivated_successfully,Snackbar.LENGTH_LONG).show();
                            new Handler().postDelayed(() -> CustomUtils.getInstance().logout(getActivity()),Snackbar.LENGTH_LONG);
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
            Snackbar.make(getActivity().findViewById(R.id.ll_container),getActivity().getResources().getString(R.string.internet_connection),Snackbar.LENGTH_LONG).show();
        }
    }


    private void setDialog(){
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        LayoutInflater factory = LayoutInflater.from(getActivity());
        final View dialogView = factory.inflate(R.layout.dialog_deactivate_account_layout, null);
        Button btnCancel=dialogView.findViewById(R.id.btn_cancel);
        Button btnAccept=dialogView.findViewById(R.id.btn_deactivate);
        btnCancel.setOnClickListener(v -> dialog.dismiss());

        btnAccept.setOnClickListener(v -> {
            dialog.dismiss();
            deActivateAccount();
        });
        dialog.setContentView(dialogView);
        dialog.show();
    }





    private void setLanguageDialog(){
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        LayoutInflater factory = LayoutInflater.from(getActivity());
        final View dialogView = factory.inflate(R.layout.dialog_language_layout, null);
        Button btnCancel=dialogView.findViewById(R.id.btn_cancel);
        Button btnAccept=dialogView.findViewById(R.id.btn_deactivate);
        btnCancel.setOnClickListener(v -> dialog.dismiss());

        btnAccept.setOnClickListener(v -> {
            if (CustomUtils.getInstance().getAppLanguage(getActivity()).equals("en"))
                CustomUtils.getInstance().saveAppLanguage(getActivity(),"ar");
            else  CustomUtils.getInstance().saveAppLanguage(getActivity(),"en");

            Intent i=new Intent(getActivity(), SplashActivity.class);
            startActivity(i);
            getActivity().finish();
        });
        dialog.setContentView(dialogView);
        dialog.show();
    }


    public void signOut(){
        if (NetWorkConnection.isConnectingToInternet(getActivity())) {
            CustomUtils.getInstance().showProgressDialog(getActivity());
            ApiClient.getClient().create(EndPoints.class).logout(ConfigurationFile.Constants.API_KEY,CustomUtils.getInstance().getAppLanguage(getActivity()),ConfigurationFile.Constants.Content_Type,ConfigurationFile.Constants.Content_Type,"Bearer "+userData.getToken())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(defaultResponseResponse -> {
                        CustomUtils.getInstance().cancelDialog();
                        System.out.println("Sign out Code  :"+defaultResponseResponse.code());

                        if (defaultResponseResponse.code()==ConfigurationFile.Constants.SUCCESS_CODE){
                            CustomUtils.getInstance().logout(getActivity());

                        } else if (defaultResponseResponse.code()==ConfigurationFile.Constants.UNAUTHENTICATED_CODE ||
                                defaultResponseResponse.code()==ConfigurationFile.Constants.UNSUBSCRIBE_CODE){
                            CustomUtils.getInstance().logout(getActivity());
                        }
                        else if (defaultResponseResponse.code()==ConfigurationFile.Constants.CANT_COMPLETE_REQUEST_CODE)
                            Snackbar.make(getActivity().findViewById(R.id.drawer), R.string.cant_complete_your_request,Snackbar.LENGTH_LONG).show();
                    }, throwable -> {
                        CustomUtils.getInstance().cancelDialog();
                        //  binding.progressBar.setVisibility(View.GONE);
                        System.out.println("Ex :"+throwable.getMessage());
                    });


        }else {
            Snackbar.make(getActivity().findViewById(R.id.drawer),getResources().getString(R.string.internet_connection),Snackbar.LENGTH_LONG).show();
        }
    }



}
