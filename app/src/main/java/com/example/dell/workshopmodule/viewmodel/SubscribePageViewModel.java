package com.example.dell.workshopmodule.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.net.Uri;
import android.view.View;

/**
 * Created by DELL on 22/04/2018.
 */

public class SubscribePageViewModel extends BaseObservable {
    private Context context;
    public SubscribePageViewModel(Context context) {
        this.context=context;
    }

    public void moveTOFindFixWebsite(View view){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://findandfix.com/en"));
        Intent browserChooserIntent = Intent.createChooser(browserIntent , "Choose browser of your choice");
        context.startActivity(browserChooserIntent);
    }
}
