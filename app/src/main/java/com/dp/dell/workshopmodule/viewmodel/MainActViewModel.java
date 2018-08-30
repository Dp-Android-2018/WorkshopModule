package com.dp.dell.workshopmodule.viewmodel;

import android.app.Activity;
import android.content.Intent;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.view.MenuItem;

import com.dp.dell.workshopmodule.R;
import com.dp.dell.workshopmodule.model.global.UserData;
import com.dp.dell.workshopmodule.utils.ConfigurationFile;
import com.dp.dell.workshopmodule.utils.CustomUtils;
import com.dp.dell.workshopmodule.utils.SharedPrefrenceUtils;
import com.dp.dell.workshopmodule.view.ui.callback.BaseInterface;

/**
 * Created by DELL on 17/04/2018.
 */

public class MainActViewModel  {
    public ObservableField<String>workshopname;
    public ObservableField<String>workshopmail;
    private BaseInterface callback;
    private Activity activity;
    private UserData userData;
    private SharedPrefrenceUtils sharedPrefrenceUtils;
    public MainActViewModel(Activity activity, BaseInterface callback) {
        sharedPrefrenceUtils=new SharedPrefrenceUtils(activity);
        userData= CustomUtils.getInstance().getSaveUserObject(activity);
        workshopname=new ObservableField<>();
        workshopmail=new ObservableField<>();
        workshopname.set(userData.getName());
        workshopmail.set(userData.getEmail());
        this.callback=callback;
        this.activity=activity;}

        //////////////////Handle On Navigation View Item Selected ////////////////////////////
    public NavigationView.OnNavigationItemSelectedListener handleNavigation(){
        return menuItem -> {
            menuItem.setChecked(true) ;
            //Closing drawer on item click
            callback.updateUi(ConfigurationFile.Constants.CLOSE_MENU_DRAWER);
            switch (menuItem.getItemId()){


                case R.id.home:

                {
                    if (isSubScribed()) {
                        if (isWinch())
                            callback.updateUi(ConfigurationFile.Constants.MOVE_TO_WINCH_FRAG);
                        else
                             callback.updateUi(ConfigurationFile.Constants.MOVE_TO_MAIN_FRAG);
                    }
                    else
                        callback.updateUi(ConfigurationFile.Constants.MOVE_TO_SUBSCRIBTION_ACTIVITY);
                    return true;
                }

                case R.id.setting:
                {
                    callback.updateUi(ConfigurationFile.Constants.MOVE_TO_SETTING_FRAG);
                    return true;
                }


                case R.id.share: {
                    shareApp();
                    return true;
                }

                case R.id.rate: {
                    callback.updateUi(ConfigurationFile.Constants.HANDLE_APP_RATE);
                    return true;
                }

                case R.id.about: {
                    callback.updateUi(ConfigurationFile.Constants.MOVE_TO_ABOUT_FRAG);
                    return true;
                }

                case R.id.achievment: {
                    callback.updateUi(ConfigurationFile.Constants.MOVE_TO_ACHIEVMENT_FRAG);
                    return true;
                }

                case R.id.adv: {
                    callback.updateUi(ConfigurationFile.Constants.MOVE_TO_ADV_FRAG);
                    return true;
                }




                default:
                    System.out.println("Something Wrong");
                    return true;

            }
        };
    }

/////////////////////////Method To Share Our App /////////////////////////////////////////
    public void shareApp(){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.example.dell.workshopmodule");
        sendIntent.setType("text/plain");
        activity.startActivity(Intent.createChooser(sendIntent, activity.getResources().getText(R.string.send_to)));}


//////////////////////Check If User SubScribed Or Not //////////////////////////////////////////////////////////
  public boolean isSubScribed(){
            return userData.isSubscribed();}

            //////////////////Check If Account For Winch Or Service Provider ///////////////////////////////////
    public boolean isWinch(){
        if(userData.getWinch()==0)
            return false;
        else return true;
    }




}
