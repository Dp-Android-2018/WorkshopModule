package com.findandfix.workshop.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.findandfix.workshop.R;
import com.findandfix.workshop.databinding.ActivityAddAdvertismentRangeLayoutBinding;
import com.findandfix.workshop.model.global.UserData;
import com.findandfix.workshop.model.request.AddAdvertisementRequest;
import com.findandfix.workshop.network.ApiClient;
import com.findandfix.workshop.network.EndPoints;
import com.findandfix.workshop.ui.adapter.DialogOffersAdapter;
import com.findandfix.workshop.utils.ConfigurationFile;
import com.findandfix.workshop.utils.CustomUtils;
import com.findandfix.workshop.utils.NetWorkConnection;
import com.findandfix.workshop.utils.SharedPrefrenceUtils;
import com.findandfix.workshop.viewmodel.ToolbarViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by DELL on 13/03/2018.
 */

public class AddAdvertismentRange extends BaseAdvertisementAct {
    private ActivityAddAdvertismentRangeLayoutBinding binding;
    private Dialog dialog=null;
    private RecyclerView spinnerRecycleView;
    private Button submitDialog;
    private List<String>offersList;
    private int from=-1;
    private int to=-1;
    private UserData userData;
    private AddAdvertisementRequest addAdvertisementRequest;
    private SharedPrefrenceUtils prefrenceUtils;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        offersList=new ArrayList<>();
        getIntentExtras();
        getUserData();
        initBinding();
        handleToolbar(); }
    //////////////////////////////Get (Extra Intents) (addAdvertisementRequest) From Previous Activity ///////////////////////////////////////////
    public void getIntentExtras(){
        addAdvertisementRequest=(AddAdvertisementRequest)getIntent().getSerializableExtra(ConfigurationFile.IntentConstants.ADD_ADV_OBJ);
    }

////////////// Get User Data From Shared Pref /////////////////////////////////////////////////////////

    public void getUserData(){

        userData=CustomUtils.getInstance().getSaveUserObject(getApplicationContext());
    }

    ///////////////////////////////Init Layout Of Activity And Check If to &  from != null (rANGE !=NULL)//////////////////////////////////
    public void initBinding(){
        binding= DataBindingUtil.setContentView(AddAdvertismentRange.this,R.layout.activity_add_advertisment_range_layout);
        binding.btnNext.setOnClickListener(v -> {
            if (to!=-1 && from!=-1 ) {
                addAdvertisementRequest.setCountFrom(from);
                addAdvertisementRequest.setCountTo(to);
                addAdv();
            }
        });

        ///////////////Set Ranges Of Data /////////////////////////////////////////
        binding.etRanges.setOnClickListener(v -> {
            offersList.add("0:99");
            offersList.add("100:999");
            offersList.add("1000:9999");
            offersList.add("10000:99999");
            setOffersList(offersList);
        });
    }

    ////////////////////////////Make Call To Add Adv //////////////////////////////////////////////////////////////
    public void addAdv(){
        if(NetWorkConnection.isConnectingToInternet(getApplicationContext())) {

           // binding.progressBar.setVisibility(View.VISIBLE);
            CustomUtils.getInstance().showProgressDialog(AddAdvertismentRange.this);
            ApiClient.getClient().create(EndPoints.class).addWorkshopAdv(ConfigurationFile.Constants.API_KEY, CustomUtils.getInstance().getAppLanguage(getApplicationContext()), ConfigurationFile.Constants.Content_Type, ConfigurationFile.Constants.Content_Type, "Bearer " + userData.getToken(),addAdvertisementRequest)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(defaultResponseResponse -> {
                        //binding.progressBar.setVisibility(View.GONE);
                        CustomUtils.getInstance().cancelDialog();
                        System.out.println("Add Offer Code :"+defaultResponseResponse.code());

                        if (defaultResponseResponse.code() == ConfigurationFile.Constants.ADV_ADDED_SUCCESSFULLY) {
                            Intent i = new Intent(getApplicationContext(), AdvertismentPublished.class);
                            startActivity(i);
                            finishAffinity();
                        } else if (defaultResponseResponse.code() == ConfigurationFile.Constants.CANT_COMPLETE_REQUEST_CODE) {
                            Snackbar.make(binding.getRoot(),getResources().getString(R.string.cant_complete_your_request),Snackbar.LENGTH_LONG).show();
                        }else if (defaultResponseResponse.code() == ConfigurationFile.Constants.UNAUTHENTICATED_CODE ||
                                defaultResponseResponse.code() == ConfigurationFile.Constants.UNSUBSCRIBE_CODE) {
                           CustomUtils.getInstance().endSession(AddAdvertismentRange.this);
                        }
                    }, throwable -> {
                        CustomUtils.getInstance().cancelDialog();
                       // binding.progressBar.setVisibility(View.GONE);
                        System.out.println("Add Offer Ex :" + throwable.getMessage());
                    });
        }else {
            Snackbar.make(binding.getRoot(),getResources().getString(R.string.internet_connection),Snackbar.LENGTH_LONG).show();
        }
    }

    //////////////////Determine Value Of From & To ////////////////////////////////////////////////////////
    private void setOffersList(List<String> data){
        DialogOffersAdapter offersAdapter = new DialogOffersAdapter(getApplicationContext(),data);
        setDialog(getString(R.string.select_range));
        spinnerRecycleView.setAdapter(offersAdapter);
        offersAdapter.setClickListener(position -> {
            binding.etRanges.setText(data.get(position));
            if (position ==0){
                from=0;
                to=99;
            } else if (position ==1){
                from=100;
                to=999;
            } else if (position ==2){
                from=1000;
                to=9999;
            }else if (position ==3){
                from=10000;
                to=99999;
            }
        });
    }

    ///////////////////////Display Dialog Of Ranges //////////////////////////////////////////////
    private void setDialog(String title){
        dialog = new Dialog(AddAdvertismentRange.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        LayoutInflater factory = LayoutInflater.from(AddAdvertismentRange.this);
        final View dialogView = factory.inflate(R.layout.dialog_adv_ranges_layout, null);
        TextView titleTextView = dialogView.findViewById(R.id.tv_spinner_title);
        titleTextView.setText(title);
        spinnerRecycleView = dialogView.findViewById(R.id.rv_spinner_collections);
        spinnerRecycleView.setLayoutManager(new LinearLayoutManager(AddAdvertismentRange.this));
        submitDialog=dialogView.findViewById(R.id.btn_ok);
        submitDialog.setOnClickListener(v -> dialog.cancel());
        dialog.setContentView(dialogView);
        dialog.show();
    }

    /////////////////Handle Toolbar ColOR AND DEFINE IT'S PROPERTIES /////////////////////////////////////////////////
    public void handleToolbar(){
        setSupportActionBar( binding.toolbar.toolbar);
        binding.toolbar.setViewmodel(new ToolbarViewModel(AddAdvertismentRange.this, ConfigurationFile.Constants.HANDLE_CUSTOM_ADD_ADVERTISING_TOOLBAR));
    }

}

