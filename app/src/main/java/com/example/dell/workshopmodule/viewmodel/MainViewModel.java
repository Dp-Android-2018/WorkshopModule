package com.example.dell.workshopmodule.viewmodel;

import android.animation.Animator;
import android.app.Activity;
import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import com.example.dell.workshopmodule.utils.ConfigurationFile;
import com.example.dell.workshopmodule.view.ui.callback.BaseInterface;

/**
 * Created by DELL on 17/04/2018.
 */

public class MainViewModel extends BaseObservable {

    public ObservableBoolean isTextVisible;
    public ObservableBoolean isImageVisible;
    private BaseInterface callback;
    private Activity activity;
    private int width,height;
    public MainViewModel(Activity activity, BaseInterface callback) {
        isTextVisible=new ObservableBoolean(false);
        isImageVisible=new ObservableBoolean(false);
        this.callback=callback;
        this.activity=activity;
        calculateMetrics();
    }

    public void calculateMetrics(){
             DisplayMetrics displayMetrics = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            height = displayMetrics.heightPixels;
            width = (displayMetrics.widthPixels);
    }

    public Animator.AnimatorListener onAnimationEnd(){
        return new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (!isTextVisible.get())
                    isTextVisible.set(true);
                else {
                    isTextVisible.set(false);
                    isImageVisible.set(true);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        };
    }
    public View.OnTouchListener onTouchEvent(){

            return new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    if (isImageVisible.get()) {
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            if ((int) event.getX() < ((width - 24) / 2) && (int) event.getY() < ((height - 24) / 4)) {
                                callback.updateUi(ConfigurationFile.Constants.URGENT_REQUEST_ACTIVITY);
                            } else if ((int) event.getX() < ((width - 24) / 2) && (int) event.getY() > ((height - 24) / 4)) {
                                callback.updateUi(ConfigurationFile.Constants.ADD_ADVERTISEMENT_ACTIVITY);
                            } else if ((int) event.getX() > ((width - 24) / 2) && (int) event.getY() < ((height - 24) / 4)) {
                                callback.updateUi(ConfigurationFile.Constants.NORMAL_REQUEST_ACTIVITY);
                            } else if ((int) event.getX() > ((width - 24) / 2) && (int) event.getY() > ((height - 24) / 4)) {
                                callback.updateUi(ConfigurationFile.Constants.EDIT_PROFILE_ACTIVITY);
                            }
                        }
                    }
                        return true;

                }
            };

    }

    public void startEngine(View view){
        callback.updateUi(ConfigurationFile.Constants.COMPLETE_ANIMATION_CODE);
    }







}
