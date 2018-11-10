package com.findandfix.workshop.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;

import com.findandfix.workshop.R;
import com.findandfix.workshop.databinding.ActivityRequestDetailLayoutBinding;
import com.findandfix.workshop.model.global.RequestData;
import com.findandfix.workshop.ui.callback.BaseInterface;
import com.findandfix.workshop.ui.dialog.AddOfferDialog;
import com.findandfix.workshop.utils.ConfigurationFile;
import com.findandfix.workshop.utils.CustomUtils;
import com.findandfix.workshop.viewmodel.PublishedRequestDetail.RequestDetailViewModel;
import com.findandfix.workshop.viewmodel.ToolbarViewModel;

/**
 * Created by DELL on 12/03/2018.
 */

public class RequestDetailActivity extends BaseActivity implements BaseInterface {

    private ActivityRequestDetailLayoutBinding binding;
    private RequestDetailViewModel viewModel=null;
    private RequestData requestData=null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        setUpToolBar();
    }

    @Override
    public void updateUi(int code) {
        if(code==ConfigurationFile.Constants.SHOW_DIALOG_CODE){
            AddOfferDialog dialog=new AddOfferDialog(RequestDetailActivity.this,getExtrasFromIntent(),getRequestType(),this,viewModel.getData());
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        }else if(code==ConfigurationFile.Constants.OFFER_ADDED_SUCCESSFULLY || code==ConfigurationFile.Constants.OFFER_UPDATED_SUCCESSFULLY ){
            showSnackBar(R.string.offer_add_successfully);
            Intent i=new Intent(this,NormalRequestActivity.class);
            startActivity(i);
            finish();
        }else if (code==ConfigurationFile.Constants.OFFER_DELETED_SUCCESSFULLY){
            showSnackBar(R.string.offer_deleted_successfully);
        }else if (code==ConfigurationFile.Constants.UNAUTHENTICATED_CODE){
            CustomUtils.getInstance().endSession(RequestDetailActivity.this);
        }
    }

    public void initBinding(){
        binding= DataBindingUtil.setContentView(RequestDetailActivity.this,R.layout.activity_request_detail_layout);
         viewModel=new RequestDetailViewModel(RequestDetailActivity.this,getExtrasFromIntent(),getRequestType(),this);
        binding.setRequestDetailViewModel(viewModel);

        binding.bannerSlider1.setOnBannerClickListener(position -> {
            requestData = getExtrasFromIntent();

            if (requestData.getVideos() != null) {
                if ((!requestData.getVideos().get(0).equals("")) && position == requestData.getImage().size()) {

                    Intent i = new Intent(getApplicationContext(), PlayingVideoActivity.class);
                    i.putExtra(ConfigurationFile.IntentConstants.VIDEO_URL, requestData.getVideos().get(0).getPath());
                    startActivity(i);

                }
            }
        });}

    public void setUpToolBar(){
        setSupportActionBar( binding.toolbar.toolbar);
        binding.toolbar.setViewmodel(new ToolbarViewModel(RequestDetailActivity.this, ConfigurationFile.Constants.HANDLE_NORMAL_REQUEST_DETAIL_TOOLBAR));

    }

    public RequestData getExtrasFromIntent(){
        RequestData requestData=(RequestData) getIntent().getSerializableExtra(ConfigurationFile.SharedPrefConstants.PREF_REQUEST_OBJECT);
        return requestData;
    }

    public int getRequestType(){
        return  getIntent().getIntExtra(ConfigurationFile.Constants.REQUEST_TYPE,0);
    }

    public void showSnackBar(int message){
        Snackbar.make(binding.svParent, message,Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        viewModel.onBackPressed();
    }
}
