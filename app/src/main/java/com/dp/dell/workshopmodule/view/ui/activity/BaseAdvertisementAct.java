package com.dp.dell.workshopmodule.view.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by DELL on 14/05/2018.
 */

public class BaseAdvertisementAct extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void initBinding(){

    }

    public void handleToolbar(){

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(int check) {
        /* Do something */
        finish();
    };
}
