package com.dp.dell.workshopmodule.view.ui.activity;

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
import android.widget.TextView;

import com.dp.dell.workshopmodule.R;
import com.dp.dell.workshopmodule.databinding.ActivityAddAdvertismentCountryLayoutBinding;
import com.dp.dell.workshopmodule.model.global.BaseModel;
import com.dp.dell.workshopmodule.model.global.CountryItem;
import com.dp.dell.workshopmodule.model.request.AddAdvertisementRequest;
import com.dp.dell.workshopmodule.model.response.CityResponse;
import com.dp.dell.workshopmodule.model.response.CountryResponse;
import com.dp.dell.workshopmodule.network.ApiClient;
import com.dp.dell.workshopmodule.network.EndPoints;
import com.dp.dell.workshopmodule.utils.ConfigurationFile;
import com.dp.dell.workshopmodule.utils.ConstantsCallsUtils;
import com.dp.dell.workshopmodule.utils.CustomUtils;
import com.dp.dell.workshopmodule.utils.NetWorkConnection;
import com.dp.dell.workshopmodule.utils.SharedPrefrenceUtils;
import com.dp.dell.workshopmodule.view.ui.adapter.DialogCityAdapter;
import com.dp.dell.workshopmodule.view.ui.adapter.DialogCountriesAdapter;
import com.dp.dell.workshopmodule.view.ui.callback.NetworkCallback;
import com.dp.dell.workshopmodule.view.ui.callback.RecycleItemClickListener;
import com.dp.dell.workshopmodule.viewmodel.ToolbarViewModel;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by DELL on 13/03/2018.
 */

public class AddAdvertismentCountry extends BaseAdvertisementAct {

    private ActivityAddAdvertismentCountryLayoutBinding binding;
    private SharedPrefrenceUtils prefrenceUtils;
    private RecyclerView spinnerRecycleView;
    private int selectedCountryId=-1,selectedCityId=-1;
    private Dialog dialog=null;
    private AddAdvertisementRequest addAdvertisementRequest;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefrenceUtils=new SharedPrefrenceUtils(this);
        getIntentExtra();
        initBinding();
        handleToolbar();
    }
//////////////////////Get Extra  Of Previous Act //////////////////////////////////////////////////////////////////////////////
    public void getIntentExtra(){
        addAdvertisementRequest=(AddAdvertisementRequest)getIntent().getSerializableExtra(ConfigurationFile.IntentConstants.ADD_ADV_OBJ);
    }

//////////////////////////////////Init Layout Of Activity And Check If Countries != null & CITY !=NULL AND SELECTED TO MOVE TO NEXT ACT /////////////////////////////////////
    public void initBinding(){
        binding= DataBindingUtil.setContentView(AddAdvertismentCountry.this,R.layout.activity_add_advertisment_country_layout);
        binding.btnNext.setOnClickListener(v -> {
            if (selectedCountryId!=-1 && selectedCityId!=-1) {
                addAdvertisementRequest.setCityId(selectedCityId);
                addAdvertisementRequest.setCountryId(selectedCountryId);
                Intent i = new Intent(getApplicationContext(), AddAdvertismentRange.class);
                i.putExtra(ConfigurationFile.IntentConstants.ADD_ADV_OBJ,addAdvertisementRequest);
                startActivity(i);
            }else {
                Snackbar.make(binding.getRoot(), R.string.select_country_city,Snackbar.LENGTH_LONG).show();
            }
        });

        ////////////Make an Action To get Countries From Web Service /////////////////////////////////////////////
        binding.etCountries.setOnClickListener(v -> {

            if ((binding.progressBar.getVisibility()==View.INVISIBLE)|| (binding.progressBar.getVisibility()==View.GONE))
                     getCountries();
        });

        ////////////Make an Action To get Cities From Web Service /////////////////////////////////////////////
        binding.etCity.setOnClickListener(v -> {
            if(selectedCountryId!=-1){
                if ((binding.progressBar.getVisibility()==View.INVISIBLE)|| (binding.progressBar.getVisibility()==View.GONE))
                        getCitiesCall();
            }else   Snackbar.make(binding.getRoot(), R.string.choose_country_for_city,Snackbar.LENGTH_LONG).show();
        });

    }
/////////////////////////Make Call To Get Countries //////////////////////////////////////////////////////////////////////////////////////

    /*Check If Countries Not Saved In Shared Preference Before If it's Saved
    IT WILL GET iT fROM Shared Preference Other wise It will make a call To get Data */


    public void getCountries(){

        CountryResponse countryResponse=(CountryResponse) prefrenceUtils.getSavedObject(ConfigurationFile.SharedPrefConstants.PREF_COUNTRIES_OBJECT,CountryResponse.class);
        if(countryResponse==null) {
            if(NetWorkConnection.isConnectingToInternet(AddAdvertismentCountry.this)) {
                //progressBar.set(View.VISIBLE);
               // binding.progressBar.setVisibility(View.VISIBLE);
                CustomUtils.getInstance().showProgressDialog(AddAdvertismentCountry.this);
                ConstantsCallsUtils.getInstance().getCountries(new NetworkCallback() {
                    @Override
                    public <E> void onSuccess(Object response, int code) {
                      //  binding.progressBar.setVisibility(View.GONE);
                        CustomUtils.getInstance().cancelDialog();
                        if (code == ConfigurationFile.Constants.SUCCESS_CODE) {
                            CountryResponse countryResponse = (CountryResponse) response;
                            prefrenceUtils.saveObjectToSharedPreferences(ConfigurationFile.SharedPrefConstants.PREF_COUNTRIES_OBJECT, countryResponse);
                            setCountriesList(countryResponse.getData());
                        }
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        CustomUtils.getInstance().cancelDialog();
                      //  binding.progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public <E> void onUnuthenticated(int code) {
                        CustomUtils.getInstance().endSession(AddAdvertismentCountry.this);
                    }
                }, CustomUtils.getInstance().getAppLanguage(getApplicationContext()));
            }else {
                Snackbar.make(binding.getRoot(),R.string.internet_connection,Snackbar.LENGTH_LONG).show();
            }
        }else {
            setCountriesList(countryResponse.getData());
        }
    }
////////////////////////Make Call To Get Cities ////////////////////////////////////////////////////////////////////////////////////



    /*GET CITIES ACCOARDING TO COUNTRY ID  */
    public void getCitiesCall() {
        if(NetWorkConnection.isConnectingToInternet(getApplicationContext())) {
           // binding.progressBar.setVisibility(View.VISIBLE);
            CustomUtils.getInstance().showProgressDialog(AddAdvertismentCountry.this);
             ApiClient.getClient().create(EndPoints.class).getCities(ConfigurationFile.Constants.API_KEY,CustomUtils.getInstance().getAppLanguage(getApplicationContext()), ConfigurationFile.Constants.Content_Type, ConfigurationFile.Constants.Content_Type, selectedCountryId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(cityResponseResponse -> {
                      //  binding.progressBar.setVisibility(View.GONE);
                        CustomUtils.getInstance().cancelDialog();
                        if (cityResponseResponse.code() == ConfigurationFile.Constants.SUCCESS_CODE) {
                         //   cities.clear();
                            //cities.addAll(cityResponseResponse.body().getData());
                            setCitiesList(cityResponseResponse.body().getData());
                        }else if(cityResponseResponse.code() == ConfigurationFile.Constants.UNSUBSCRIBE_CODE ||
                                cityResponseResponse.code() == ConfigurationFile.Constants.UNAUTHENTICATED_CODE){
                            CustomUtils.getInstance().endSession(AddAdvertismentCountry.this);
                        }
                    }, throwable -> {
                       // binding.progressBar.setVisibility(View.GONE);
                        CustomUtils.getInstance().cancelDialog();
                    });
        }else {
            Snackbar.make(binding.getRoot(),R.string.internet_connection,Snackbar.LENGTH_LONG).show();
        }

    }


    //////////////////Set Adapter Of Countries Recycler View /////////////////////////////////////

    //////////Set Country Data To Adapter Of Recycler View/////////////////////////
    private void setCountriesList(List<CountryItem> data){
        DialogCountriesAdapter countriesAdapter = new DialogCountriesAdapter(getApplicationContext(),data);
        setDialog(getResources().getString(R.string.choose_country));
        spinnerRecycleView.setAdapter(countriesAdapter);
        countriesAdapter.setClickListener(position -> {
            selectedCountryId = data.get(position).getId();
            binding.etCountries.setText(data.get(position).getName());
            selectedCityId =-1;
            binding.etCity.setText("");
            dialog.cancel();
        });
    }
    //////////////////Set Adapter Of Cities Recycler View /////////////////////////////////////
    private void setCitiesList(List<BaseModel> data){

        DialogCityAdapter citiesAdapter = new DialogCityAdapter(getApplicationContext(),data);
        setDialog(getString(R.string.choose_city));
        spinnerRecycleView.setAdapter(citiesAdapter);
        citiesAdapter.setClickListener(position -> {
            selectedCityId = data.get(position).getId();
            binding.etCity.setText(data.get(position).getName());
            dialog.cancel();
        });
    }

/////////////////////////////Display Dialog /////////////////////////////////////////
    private void setDialog(String title){
        dialog = new Dialog(AddAdvertismentCountry.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        LayoutInflater factory = LayoutInflater.from(AddAdvertismentCountry.this);
        final View dialogView = factory.inflate(R.layout.dialog_spinner, null);
        TextView titleTextView = dialogView.findViewById(R.id.tv_spinner_title);
        titleTextView.setText(title);
        spinnerRecycleView = dialogView.findViewById(R.id.rv_spinner_collections);
        spinnerRecycleView.setLayoutManager(new LinearLayoutManager(AddAdvertismentCountry.this));
        dialog.setContentView(dialogView);
        dialog.show();
    }
    ///////////////////Handle Toolbar ColOR AND DEFINE IT'S PROPERTIES /////////////////////////////////////////////////
    public void handleToolbar(){
        setSupportActionBar( binding.toolbar.toolbar);
        binding.toolbar.setViewmodel(new ToolbarViewModel(AddAdvertismentCountry.this, ConfigurationFile.Constants.HANDLE_CUSTOM_ADD_ADVERTISING_TOOLBAR));
    }
}
