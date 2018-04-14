package com.example.dell.workshopmodule.utils;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.ObservableList;
import android.graphics.Bitmap;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by DELL on 20/03/2018.
 */
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.dell.workshopmodule.model.global.BrandItem;
import com.example.dell.workshopmodule.model.global.BaseModel;
import com.example.dell.workshopmodule.model.global.CountryItem;
import com.example.dell.workshopmodule.model.global.RequestData;
import com.example.dell.workshopmodule.view.ui.adapter.CityAdapter;
import com.example.dell.workshopmodule.view.ui.adapter.CountriesAdapter;
import com.example.dell.workshopmodule.view.ui.adapter.CustomBrandsDialogAdapter;
import com.example.dell.workshopmodule.view.ui.adapter.CustomDialogAdapter;
import com.example.dell.workshopmodule.view.ui.adapter.RequestsAdapter;
import com.example.dell.workshopmodule.view.ui.adapter.ViewPagerAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.views.BannerSlider;

public class CustomBinder {

    /*@BindingAdapter("bind:items")
    public static void bindList(Spinner view, List<BaseModel> list) {
        CityAdapter adapter = new CityAdapter(list);
        view.setAdapter(adapter);
    }*/



    @BindingAdapter("bind:errorText")
    public static void setErrorMessage(TextInputLayout view, String errorMessage) {
        view.setError(errorMessage);
    }

    @BindingAdapter("bind:setError")
    public static void setErrorMessageEdittext(EditText view, String errorMessage) {
        view.setError(errorMessage);
    }

    @BindingAdapter("bind:data")
    public static void setRecyclerData(RecyclerView view, List<BaseModel> list) {
        System.out.println("Specialization Binding");
        CustomDialogAdapter adapter = new CustomDialogAdapter(list);
            view.setAdapter(adapter);

    }


    @BindingAdapter("bind:brands")
    public static void setRecyclerBrandsData(RecyclerView view, List<BrandItem> list) {
        System.out.println("Specialization Binding");
        CustomBrandsDialogAdapter adapter = new CustomBrandsDialogAdapter(list);
        view.setAdapter(adapter);

    }


    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String url){
        Picasso.with(imageView.getContext()).load(url).into(imageView);
    }


   @BindingAdapter("bind:imageBitmap")
        public static void setImageBitmap(ImageView imageView, Bitmap bitmap){
            imageView.setImageBitmap(bitmap);
    }


    @BindingAdapter("bind:request")
    public static void setRecyclerRequestData(RecyclerView view,ObservableList<RequestData> list) {
        System.out.println("Requests  Binding:");
        RequestsAdapter adapter = new RequestsAdapter(list,1);
        view.setAdapter(adapter);

    }

    @BindingAdapter("bind:pendingrequest")
    public static void setRecyclerPendingRequestData(RecyclerView view,ObservableList<RequestData> list) {
        System.out.println("Requests  Binding:");
        RequestsAdapter adapter = new RequestsAdapter(list,2);
        view.setAdapter(adapter);

    }


    @BindingAdapter("bind:inProgressItems")
    public static void setRecyclerInProgressRequestData(RecyclerView view,ObservableList<RequestData> list) {
        System.out.println("Requests  Binding:");
        RequestsAdapter adapter = new RequestsAdapter(list,3);
        view.setAdapter(adapter);

    }


    @BindingAdapter("bind:completedrequests")
    public static void setRecyclerCompletedRequests(RecyclerView view,ObservableList<RequestData> list) {
        System.out.println("Requests  Binding:");
        RequestsAdapter adapter = new RequestsAdapter(list,4);
        view.setAdapter(adapter);

    }


    @BindingAdapter("bind:countries")
    public static void setRecyclerCountries(RecyclerView view,ObservableList<CountryItem> list) {
        System.out.println("Requests  Binding:");
        CountriesAdapter countriesAdapter=new CountriesAdapter(list);
        view.setAdapter(countriesAdapter);

    }

    @BindingAdapter("bind:cities")
    public static void setRecyclerCities(RecyclerView view,ObservableList<BaseModel> list) {
        System.out.println("Requests  Binding:");
        CityAdapter cityAdapter=new CityAdapter(list);
        view.setAdapter(cityAdapter);

    }

    @BindingAdapter("bind:setBanners")
    public static void setBanners(BannerSlider bannerSlider, ArrayList<Banner>list) {
            bannerSlider.setBanners(list);
    }


    @BindingAdapter("bind:onFocusChange")
    public static void onFocusChange(EditText text, final View.OnFocusChangeListener listener) {
        text.setOnFocusChangeListener(listener);
    }


    @BindingAdapter("bind:onChanged")
    public static void onFocusChange(EditText text, final TextView.OnEditorActionListener listener) {
        text.setOnEditorActionListener(listener);
    }




}
