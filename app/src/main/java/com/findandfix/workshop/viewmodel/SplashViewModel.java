package com.findandfix.workshop.viewmodel;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.databinding.BaseObservable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.findandfix.workshop.R;
import com.findandfix.workshop.model.global.UserData;
import com.findandfix.workshop.model.global.VersionChecker;
import com.findandfix.workshop.ui.Application.MyApplication;
import com.findandfix.workshop.ui.activity.ChatActivity;
import com.findandfix.workshop.ui.activity.MyConversationsActivity;
import com.findandfix.workshop.ui.activity.NormalRequestActivity;
import com.findandfix.workshop.ui.activity.UrgentRequestActivity;
import com.findandfix.workshop.ui.callback.BaseInterface;
import com.findandfix.workshop.utils.ConfigurationFile;
import com.findandfix.workshop.utils.CustomUtils;
import com.findandfix.workshop.utils.SharedPrefrenceUtils;

import java.util.concurrent.ExecutionException;


/**
 * Created by DELL on 04/04/2018.
 */

public class SplashViewModel extends BaseObservable {

    private Context context;
    private BaseInterface callback;
    private Dialog dialog=null;
    public SplashViewModel(Context context, BaseInterface callback) {
            this.context=context;
            this.callback=callback;
            secondaryDelay();
           // delay();
    }

    public void checkVersionUpdate(){
        VersionChecker versionChecker = new VersionChecker();
        try {
            String latestVersion = versionChecker.execute().get();
            System.out.println("Latest Version Data :"+latestVersion);
            getCurrentVersion(latestVersion);
        } catch (InterruptedException e) {
            e.printStackTrace();
            delay();
        } catch (ExecutionException e) {
            e.printStackTrace();
            delay();
        }
    }

    public void getCurrentVersion(String playStoreVersion){
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            String version = pInfo.versionName;
            if (version.equalsIgnoreCase(playStoreVersion))
                delay();
            else showDialog();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void showDialog(){
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        LayoutInflater factory = LayoutInflater.from(context);
        final View dialogView = factory.inflate(R.layout.dialog_update_available_layout, null);
        Button btnCancel=dialogView.findViewById(R.id.btn_cancel);
        Button btnAccept=dialogView.findViewById(R.id.btn_deactivate);
        btnAccept.setOnClickListener(v -> {
            Uri uri = Uri.parse("market://details?id="+context.getPackageName());
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            try {
                context.startActivity(goToMarket);
            } catch (ActivityNotFoundException e) {
                context.startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id="+context.getPackageName())));
            }
        });
        dialog.setContentView(dialogView);
        dialog.show();
    }

    public void secondaryDelay(){
        new Handler().postDelayed(() -> checkVersionUpdate(), 500);
    }
    ////////////////Make Delay For 5 Seconds Before Moving To Next Act ///////////////////////////////////
    public void delay(){
        new Handler().postDelayed(() -> moveTonextActivity(), ConfigurationFile.Constants.SPLASH_TIME_OUT);
    }

    ///////////////////////////Handle Notifications in Background and Determine Which Act You Should Move To////////////////////
    public void moveTonextActivity(){


        if (((Activity)context).getIntent().getExtras()!=null) {
            Intent i=null;

            for (String key : ((Activity)context).getIntent().getExtras().keySet())
            {
                System.out.println(("Bundle Debug" + key +"  "+ ((Activity)context).getIntent().getExtras().get(key) ));
            }
            System.out.println("Bundle Debug 2 :"+ ((Activity)context).getIntent().getExtras().get("key"));
            ///////////////////If There Is New Urgent Request //////////////////////////////////////////////
           if(((Activity)context).getIntent().getExtras().containsKey("urgent_request_id")){
                if (String.valueOf(((Activity) context).getIntent().getExtras().get("urgent_request_id"))!=null) {
                    i = new Intent(context, UrgentRequestActivity.class);
                    context.startActivity(i);
                    ((Activity) context).finish();
                    ((Activity) context).finishAffinity();
                }
                ((Activity)context).getIntent().getExtras().clear();

            }else if (((Activity)context).getIntent().getExtras().containsKey("noti_title")){
               /////////////////////////If There is New Message /////////////////////////////////////////
                if (String.valueOf(((Activity) context).getIntent().getExtras().get("noti_title")).equals("new_message")){
                    if (ChatActivity.active){

                    }else {
                         i = new Intent(context, MyConversationsActivity.class);
                          context.startActivity(i);
                          ((Activity) context).finish();
                          ((Activity) context).finishAffinity();
                    }
                    /////////////////////////If Sent Offer For Normal Request Accepted ////////////////////////////////////////
                } else if (String.valueOf(((Activity) context).getIntent().getExtras().get("noti_title")).equals("offer_accepted")) {
                   i = new Intent(context, NormalRequestActivity.class);
                   i.putExtra(ConfigurationFile.IntentConstants.OFFER_ID, Integer.parseInt(String.valueOf(((Activity) context).getIntent().getExtras().get("offer_id"))));
                   context.startActivity(i);
                   ((Activity) context).finish();
                   ((Activity) context).finishAffinity();
                    /////////////////////////If Sent Offer For Urgent Request Accepted ////////////////////////////////////////
               }  else if (String.valueOf(((Activity) context).getIntent().getExtras().get("noti_title")).equals("offer_accepted_urgent") ) {
                   i = new Intent(context, UrgentRequestActivity.class);
                   i.putExtra(ConfigurationFile.IntentConstants.URGENT_OFFER_ID, Integer.parseInt(String.valueOf(((Activity) context).getIntent().getExtras().get("offer_id"))));
                   context.startActivity(i);
                   ((Activity) context).finish();
                   ((Activity) context).finishAffinity();
               }
               ((Activity)context).getIntent().getExtras().clear();

            }else if (((Activity)context).getIntent().getExtras().containsKey("title")){
               /////////////////////////If Subscribtion has been approved ////////////////////////////////////////
               if (String.valueOf(((Activity) context).getIntent().getExtras().get("title"))!=null &&
                       String.valueOf(((Activity) context).getIntent().getExtras().get("title")).equals("subscription-approved") ) {
                        UserData userData=CustomUtils.getInstance().getSaveUserObject(context);
                        userData.setSubscribed(true);
                        saveDataToPrefs(userData);
                   if (CustomUtils.getInstance().getSaveUserObject(context) != null) {
                       callback.updateUi(ConfigurationFile.Constants.MOVE_TO_MAIN_ACT);
                   } else {
                       callback.updateUi(ConfigurationFile.Constants.MOVE_TO_LOGIN_ACT);
                   }
               }
           }else {
                if (CustomUtils.getInstance().getSaveUserObject(context) != null) {
                    callback.updateUi(ConfigurationFile.Constants.MOVE_TO_MAIN_ACT);
                } else {
                    callback.updateUi(ConfigurationFile.Constants.MOVE_TO_LOGIN_ACT);
                }
            }

        }else {
            if (CustomUtils.getInstance().getSaveUserObject(context) != null) {
                callback.updateUi(ConfigurationFile.Constants.MOVE_TO_MAIN_ACT);
            } else {
                callback.updateUi(ConfigurationFile.Constants.MOVE_TO_LOGIN_ACT);
            }
        }

    }

    /////////////////// Save Data Of User To Shared Prefrence/////////////////////////////////////////
    public void saveDataToPrefs(UserData data){
        SharedPrefrenceUtils pref=null;
        pref= MyApplication.getSharedprefUtilsComponent().getSharedPrefUtils();
        pref.saveObjectToSharedPreferences(ConfigurationFile.SharedPrefConstants.PREF_WORKSHOP_DATA,data);
    }

}
