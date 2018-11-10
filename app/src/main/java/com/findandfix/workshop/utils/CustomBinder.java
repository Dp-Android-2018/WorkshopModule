package com.findandfix.workshop.utils;

import android.animation.Animator;
import android.databinding.BindingAdapter;
import android.databinding.ObservableList;
import android.graphics.Bitmap;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.findandfix.workshop.model.global.BaseModel;
import com.findandfix.workshop.model.global.BrandItem;
import com.findandfix.workshop.model.global.Conv.ConversationHistory;
import com.findandfix.workshop.model.global.Conv.Message;
import com.findandfix.workshop.model.global.CountryItem;
import com.findandfix.workshop.model.global.RequestData;
import com.findandfix.workshop.ui.Application.MyApplication;
import com.findandfix.workshop.ui.adapter.CityAdapter;
import com.findandfix.workshop.ui.adapter.ConversationDetailAdapter;
import com.findandfix.workshop.ui.adapter.CountriesAdapter;
import com.findandfix.workshop.ui.adapter.CustomBrandsDialogAdapter;
import com.findandfix.workshop.ui.adapter.CustomDialogAdapter;
import com.findandfix.workshop.ui.adapter.MyConversationAdapter;
import com.findandfix.workshop.ui.adapter.RequestsAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.views.BannerSlider;

/**
 * Created by DELL on 20/03/2018.
 */

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

    @BindingAdapter("bind:animation")
    public static void onAnimationEnd(LottieAnimationView animationView, Animator.AnimatorListener animatorListener) {
       animationView.addAnimatorListener(animatorListener);
    }

    @BindingAdapter("bind:ontouch")
    public static void onTouch(LottieAnimationView animationView, View.OnTouchListener touchListener) {
        animationView.setOnTouchListener(touchListener);
    }

    @BindingAdapter("bind:setprogress")
    public static void progress(LottieAnimationView animationView,float progress) {



                        if (progress==0.3f) {
                            animationView.playAnimation();
                            animationView.setMaxProgress(progress);
                        }else if (progress == 1.0f) {
                            animationView.resumeAnimation();
                            animationView.setMaxProgress(1.0f);
                        }



    }




    @BindingAdapter("bind:navigationItem")
    public static void navigationEvent(NavigationView navigationView, NavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener) {
                            navigationView.setNavigationItemSelectedListener(onNavigationItemSelectedListener);
    }

    @BindingAdapter("bind:scroll")
    public static void ScrollEvent(TextView textView, ScrollingMovementMethod scrollingMovementMethod) {
            textView.setMovementMethod(scrollingMovementMethod);
    }


    @BindingAdapter("bind:conversationhistory")
    public static void setRecyclerConversation(RecyclerView view,ObservableList<ConversationHistory> list) {
        System.out.println("Adapter Data Fire:"+list.size());
        MyConversationAdapter myConversationAdapter=new MyConversationAdapter(list);
        view.setAdapter(myConversationAdapter);
    }

    @BindingAdapter("bind:conversationDetails")
    public static void setRecyclerConversationDetails(RecyclerView view,ObservableList<Message> list) {
        System.out.println("Adapter Data Fire:"+list.size());
        ConversationDetailAdapter adapter=new ConversationDetailAdapter(list);
        view.setAdapter(adapter);
        view.scrollToPosition(list.size()-1);
    }

    @BindingAdapter("bind:toolbarBackground")
    public static void setToolBarBackground(android.support.v7.widget.Toolbar toolBarBackground,int color) {
            toolBarBackground.setBackgroundColor(MyApplication.getAppContext().getResources().getColor(color));
    }


    @BindingAdapter("bind:imageConv")
    public static void setImageConv(ImageView imageView,String url) {
            if (url!=null && !url.equals(""))
       Picasso.with(MyApplication.getAppContext()).load(url).fit().into(imageView);
    }




    @BindingAdapter("bind:scroll")
    public static void setRecyclerScroll(RecyclerView recyclerView, RecyclerView.OnScrollListener listener) {
       recyclerView.setOnScrollListener(listener);
    }



}
