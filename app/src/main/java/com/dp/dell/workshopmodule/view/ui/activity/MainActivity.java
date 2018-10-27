package com.dp.dell.workshopmodule.view.ui.activity;

import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.dp.dell.workshopmodule.R;
import com.dp.dell.workshopmodule.databinding.ActivityMainBinding;
import com.dp.dell.workshopmodule.databinding.HeaderMenuLayoutBinding;
import com.dp.dell.workshopmodule.model.global.UserData;
import com.dp.dell.workshopmodule.utils.ConfigurationFile;
import com.dp.dell.workshopmodule.utils.CustomUtils;
import com.dp.dell.workshopmodule.utils.SharedPrefrenceUtils;
import com.dp.dell.workshopmodule.view.ui.Application.MyApplication;
import com.dp.dell.workshopmodule.view.ui.callback.BaseInterface;
import com.dp.dell.workshopmodule.view.ui.fragment.AboutFragment;
import com.dp.dell.workshopmodule.view.ui.fragment.AchievmentFragment;
import com.dp.dell.workshopmodule.view.ui.fragment.MainFragment;
import com.dp.dell.workshopmodule.view.ui.fragment.OffersFragment;
import com.dp.dell.workshopmodule.view.ui.fragment.SettingFragment;
import com.dp.dell.workshopmodule.view.ui.fragment.SubscribitonFragment;
import com.dp.dell.workshopmodule.view.ui.fragment.WenshFragment;
import com.dp.dell.workshopmodule.viewmodel.MainActViewModel;
import com.dp.dell.workshopmodule.viewmodel.ToolbarViewModel;
import com.pusher.client.AuthorizationFailureException;
import com.pusher.client.Authorizer;
import com.pusher.client.Pusher;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.PrivateChannelEventListener;
import com.pusher.client.connection.ConnectionEventListener;
import com.pusher.client.connection.ConnectionStateChange;
import com.pusher.client.util.HttpAuthorizer;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends BaseActivity implements BaseInterface  {

         ActivityMainBinding binding;
        private MainActViewModel mainViewModel;
        private ToolbarViewModel toolbarViewModel;
        private String SOCKET_ID;
        private Channel channel;
        private  HttpAuthorizer authorizer;
        private Pusher pusher;
        private BroadcastReceiver broadcastReceiver=null;
    private ConnectionFactory mConnectionFactory = null;
    private SharedPrefrenceUtils pref;
    boolean doubleBackToExitPressedOnce = false;
   HashMap<String, String> authHeader;
    String url="http://new.findandfix.com/workshop/broadcast/auth";
    private  URL endPoint;

    ////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////
    private UserData userData;
    ////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //////////////////////////////////////////////////////////////////////
            initBinding();
            setUpToolBar();
            handleNavigation();
            handleNavigationHeader();
            navigateBetweenFragments(null);
            registerReciever();}

            // Make Broad Cast Reciever To Change Status Of Activity When Account Activated an Notification Comes //
    public void registerReciever(){
        System.out.println("Notification Log 3");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("CLOSE_ALL");
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                System.out.println("Notification Log 4");
                UserData userData=CustomUtils.getInstance().getSaveUserObject(getApplicationContext());
                userData.setSubscribed(true);
                saveDataToPrefs(userData);
                activeAccount();
            }
        };
        registerReceiver(broadcastReceiver, intentFilter);
    }
    /////////Init Layout and Determine View Model Of Activity //////////////////////
    public void initBinding(){
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main);
        mainViewModel=new MainActViewModel(MainActivity.this,this);
        binding.setViewModel(mainViewModel);}

        // Method To Determine Toolbar Color and Visibility Of it's Item //
    public void setUpToolBar(){
            setSupportActionBar(binding.toolbar.toolbar);
            toolbarViewModel=new ToolbarViewModel(MainActivity.this,ConfigurationFile.Constants.HANDLE_MAIN_TOOLBAR);
            binding.toolbar.setViewmodel(toolbarViewModel);
        if (CustomUtils.getInstance().getAppLanguage(MainActivity.this).equals("ar")){
            binding.toolbar.toolbar.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
    }


    /* Get Callback From View Model and Update Ui  + Navigate Between Fragments*/
    @Override
    public void updateUi(int code) {
        if(code== ConfigurationFile.Constants.CLOSE_MENU_DRAWER)
            binding.drawer.closeDrawers();
        else if(code== ConfigurationFile.Constants.MOVE_TO_MAIN_FRAG) {
            MainFragment mainFragment=new MainFragment();
            toolbarViewModel.handleToolbarBackground(getString(R.string.find_fix),R.color.colorBlueLight);
            navigateBetweenFragments(mainFragment);
        }  else if(code== ConfigurationFile.Constants.MOVE_TO_WINCH_FRAG) {
            WenshFragment wenshFragment=new WenshFragment();
            navigateBetweenFragments(wenshFragment);
        } else if(code== ConfigurationFile.Constants.MOVE_TO_ABOUT_FRAG) {
            AboutFragment aboutFragment=new AboutFragment();
            toolbarViewModel.handleToolbarBackground(getString(R.string.about),R.color.colorBlueLight);
            navigateBetweenFragments(aboutFragment);
        } else if(code== ConfigurationFile.Constants.MOVE_TO_SUBSCRIBTION_ACTIVITY){
            SubscribitonFragment subscribitonFragment=new SubscribitonFragment();
            navigateBetweenFragments(subscribitonFragment);
        }else if(code== ConfigurationFile.Constants.MOVE_TO_SETTING_FRAG){
            SettingFragment settingFragment=new SettingFragment();
            navigateBetweenFragments(settingFragment);
        }else if(code== ConfigurationFile.Constants.MOVE_TO_ACHIEVMENT_FRAG){
            AchievmentFragment achievmentFragment=new AchievmentFragment();
            toolbarViewModel.handleToolbarBackground(getString(R.string.achievments),R.color.colorBlueLight);
            navigateBetweenFragments(achievmentFragment);
        }else if(code== ConfigurationFile.Constants.MOVE_TO_ADV_FRAG){
            OffersFragment offersFragment=new OffersFragment();
            toolbarViewModel.handleToolbarBackground(getString(R.string.my_adv),R.color.colorBlueLight);
            navigateBetweenFragments(offersFragment);
        }else if(code== ConfigurationFile.Constants.HANDLE_APP_RATE){
          rateApp();
        }
    }

/////////////////Navigate Between Fragmentts ///////////////////////////////
    private void navigateBetweenFragments(Fragment fragment){
        System.out.println("Notification Log 5");
        if(fragment==null) {
           if (mainViewModel.isSubScribed()) {
               if(mainViewModel.isWinch())
                   fragment = new WenshFragment();
               else
                 fragment = new MainFragment();
           }
           else
               fragment = new SubscribitonFragment();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();}

        public void activeAccount(){
        Fragment fragment=null;
            if(mainViewModel.isWinch())
                fragment = new WenshFragment();
            else
                fragment = new MainFragment();

            if (! isFinishing()) {
                try {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    //fragmentManager.popBackStack();
                    android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frame, fragment);
                    fragmentTransaction.commitAllowingStateLoss();
                }catch (IllegalStateException ignored) {
                    // There's no way to avoid getting this if saveInstanceState has already been called.
                }
            }
        }
    /////////////////// Save Data Of User To Shared Prefrence/////////////////////////////////////////
    public void saveDataToPrefs(UserData data){
        pref= MyApplication.getSharedprefUtilsComponent().getSharedPrefUtils();
        pref.saveObjectToSharedPreferences(ConfigurationFile.SharedPrefConstants.PREF_WORKSHOP_DATA,data);
    }

    //////////////Declaring Drawer Toggle //////////////////////////////////////////////////////////////////////
    public void handleNavigation(){

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,binding.drawer,binding.toolbar.toolbar,R.string.add_offer, R.string.add_offer_desc){
            @Override
            public void onDrawerClosed(View drawerView) {super.onDrawerClosed(drawerView);}
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }


        };
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.whitebasecolor));

        binding.drawer.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();



    }



    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    ///////////////Declaring Navigation View Header and Set Data To It ////////////////////////////////
    public void handleNavigationHeader(){
        binding.navigationView.setItemIconTintList(null);
        View headerview=binding.navigationView.getHeaderView(0);
        HeaderMenuLayoutBinding headerMenuLayoutBinding=HeaderMenuLayoutBinding.bind(headerview);
        headerMenuLayoutBinding.tvWorkshopName.setText(mainViewModel.workshopname.get());
        headerMenuLayoutBinding.tvWorkshopMail.setText(mainViewModel.workshopmail.get());

        String workshoptitle=CustomUtils.getInstance().firstCharacters(mainViewModel.workshopname.get());
        headerMenuLayoutBinding.tvWorkshopTitle.setText(workshoptitle);

    }

   ////////////////Navigate To Our App in a Store To Rate It //////////////////////////////////////////////

    public void rateApp(){
        Uri uri = Uri.parse("market://details?id=" + getApplicationContext().getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName())));
        }
    }


    /////////////Handle On Back Action and Display Snackbar To User ///////////////////////////////
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Snackbar.make(findViewById(R.id.drawer), R.string.press_again_to_exit, Snackbar.LENGTH_LONG).show();

        new Handler().postDelayed(() -> doubleBackToExitPressedOnce=false, 2000);
    }

    ////////////////////////UnRegister BroadCast Reciever Which You Declared //////////////////////////////////////////////
    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(broadcastReceiver);

    }
}
