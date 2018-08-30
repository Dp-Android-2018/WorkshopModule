package com.dp.dell.workshopmodule.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.dp.dell.workshopmodule.R;
import com.dp.dell.workshopmodule.utils.ConfigurationFile;
import com.dp.dell.workshopmodule.utils.CustomUtils;
import com.dp.dell.workshopmodule.view.ui.activity.MainActivity;
import com.dp.dell.workshopmodule.view.ui.activity.MyConversationsActivity;
import com.dp.dell.workshopmodule.view.ui.activity.NormalRequestActivity;

/**
 * Created by DELL on 14/05/2018.
 */

public class ToolbarViewModel extends BaseObservable {

    public ObservableInt imageVisibility;
    public ObservableInt convVisibility;
    public ObservableInt backimageVisibility;
    public ObservableInt rotationValue;
    public ObservableField<String>toolbarTitle;
    public ObservableInt toolbarColor;
    private Context context;
    private int code=0;
    public ToolbarViewModel(Context context,int checker) {
        this.context=context;
        imageVisibility=new ObservableInt();
        convVisibility=new ObservableInt();
        backimageVisibility=new ObservableInt(View.GONE);
        toolbarTitle=new ObservableField<>();
        toolbarColor=new ObservableInt();
        rotationValue=new ObservableInt();
        if (CustomUtils.getInstance().getAppLanguage(context).equals("ar"))
            rotationValue.set(180);


        if (checker== ConfigurationFile.Constants.HANDLE_MAIN_TOOLBAR)
            handleMainToolBar();

        else if (checker==ConfigurationFile.Constants.HANDLE_MORE_COMPLETED_URHENT_REQUEST_TOOLBAR)
            handleMoreCompletedUrgentRequestToolbar(1);

        else if (checker==ConfigurationFile.Constants.HANDLE_MORE_PENDING_URHENT_REQUEST_TOOLBAR)
            handleMoreCompletedUrgentRequestToolbar(2);

        else if (checker==ConfigurationFile.Constants.HANDLE_MORE_COMPLETED_URHENT_REQUEST_TOOLBAR)
            handleMoreCompletedUrgentRequestToolbar(2);

        else if (checker== ConfigurationFile.Constants.HANDLE_NORMAL_REQUEST_TOOLBAR)
            handleNormalRequestToolbar();

        else if (checker== ConfigurationFile.Constants.HANDLE_MORE_COMPLETED_NORMAL_REQUEST_TOOLBAR)
            handleMoreCompletedNormalRequestToolbar(1);

        else if (checker== ConfigurationFile.Constants.HANDLE_MORE_PENDING_NORMAL_REQUEST_TOOLBAR)
            handleMoreCompletedNormalRequestToolbar(2);

        else if (checker== ConfigurationFile.Constants.HANDLE_MORE_NORMAL_REQUEST_TOOLBAR)
            handleMoreCompletedNormalRequestToolbar(3);

        else if (checker== ConfigurationFile.Constants.HANDLE_URGENT_REQUEST_TOOLBAR)
            handleUrgentRequestToolbar();

        else if (checker== ConfigurationFile.Constants.HANDLE_EDIT_PROFILE_TOOLBAR)
            handleEditProfileToolbar();

        else if (checker== ConfigurationFile.Constants.HANDLE_CONVERSATION_HISTORY_TOOLBAR)
            handleConversationHistory();

        else if (checker== ConfigurationFile.Constants.HANDLE_ADD_ADVERTISING_TOOLBAR)
            handleAddAdvertise();

        else if (checker== ConfigurationFile.Constants.HANDLE_ADD_ACHIEVMENT_TOOLBAR)
            handleAddAchievment();

        else if (checker== ConfigurationFile.Constants.HANDLE_CHANGE_PASSWORD_TOOLBAR)
            handleChangePasswordToolbar();

        else if (checker== ConfigurationFile.Constants.HANDLE_CUSTOM_ADD_ADVERTISING_TOOLBAR)
            handleAddAdvertisingToolbar();
        else if (checker== ConfigurationFile.Constants.HANDLE_NORMAL_REQUEST_DETAIL_TOOLBAR) {
            handleNormalRequestDetailToolbar();
            code=ConfigurationFile.Constants.HANDLE_NORMAL_REQUEST_DETAIL_TOOLBAR;
        }
    }

    public void handleMainToolBar(){
        imageVisibility.set(View.GONE);
        handleToolbarBackground(context.getString(R.string.find_fix),R.color.colorBlueLight);
        handleStatusBar(R.color.colorBlueLight);
    }

    public void handleNormalRequestToolbar(){
        handleToolbarBackground(context.getString(R.string.normal_request),R.color.bluecolor);
        handleStatusBar(R.color.bluecolor);
    }

    public void handleMoreCompletedNormalRequestToolbar(int checker){
        imageVisibility.set(View.GONE);
        backimageVisibility.set(View.VISIBLE);
       
        handleToolbarBackground(context.getString(R.string.more_requests),R.color.bluecolor);
       
        handleStatusBar(R.color.bluecolor);
    }

    public void handleMoreCompletedUrgentRequestToolbar(int checker){
        imageVisibility.set(View.GONE);
        backimageVisibility.set(View.VISIBLE);
        if (checker==1)
            handleToolbarBackground(context.getString(R.string.completed_request),R.color.redColor);
        else   handleToolbarBackground(context.getString(R.string.pending_requests),R.color.redColor);
        handleStatusBar(R.color.redColor);
    }

    public void handleUrgentRequestToolbar(){
        handleToolbarBackground(context.getString(R.string.uregnt_request),R.color.redColor);
        handleStatusBar(R.color.redColor);
    }

    public void handleEditProfileToolbar(){
        handleToolbarBackground(context.getString(R.string.edit_profile),R.color.orangecolor);
        handleStatusBar(R.color.orangecolor);
    }

    public void handleConversationHistory(){
        imageVisibility.set(View.GONE);
        backimageVisibility.set(View.VISIBLE);
        convVisibility.set(View.GONE);
        handleToolbarBackground(context.getString(R.string.chat),R.color.colorBlueLight);
        handleStatusBar(R.color.colorBlueLight);}

        public void handleConversationDetailToolbar(String name){
            imageVisibility.set(View.GONE);
            backimageVisibility.set(View.VISIBLE);
            handleToolbarBackground(name,R.color.colorBlueLight);
            handleStatusBar(R.color.colorBlueLight);
        }

    public void handleAddAdvertise(){
        handleToolbarBackground(context.getString(R.string.add_advertisment),R.color.advertisment_toolbar_color);
        handleStatusBar(R.color.advertisment_toolbar_color);
    }

    public void handleAddAchievment(){
        handleToolbarBackground(context.getString(R.string.add_achievment),R.color.colorBlueLight);
        handleStatusBar(R.color.advertisment_toolbar_color);
    }

    public void handleChangePasswordToolbar(){
        imageVisibility.set(View.GONE);
        backimageVisibility.set(View.VISIBLE);
        handleToolbarBackground(context.getString(R.string.change_password),R.color.orangecolor);
        handleStatusBar(R.color.orangecolor);
    }

    public void handleAddAdvertisingToolbar(){
        imageVisibility.set(View.GONE);
        backimageVisibility.set(View.VISIBLE);
        handleToolbarBackground(context.getString(R.string.add_advertisment),R.color.advertisment_toolbar_color);
        handleStatusBar(R.color.advertisment_toolbar_color);
    }

    public void  handleNormalRequestDetailToolbar(){
        imageVisibility.set(View.GONE);
        backimageVisibility.set(View.VISIBLE);
        handleToolbarBackground(context.getString(R.string.normal_request),R.color.bluecolor);
        handleStatusBar(R.color.bluecolor);
    }

    public void handleStatusBar(int color){
        if(Build.VERSION.SDK_INT>=21){
            Window window=((Activity)context).getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(context.getResources().getColor(color));
        }}
    public void openChatAct(View view){
        Intent i=new Intent(context,MyConversationsActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);}

    public void backToMainAct(View view){
        Intent i=new Intent(context,MainActivity.class);
        ((Activity)context).startActivity(i);
        ((Activity)context).finish();
    }
    public void handleAddAdvertiseDetail(String title){
        imageVisibility.set(View.GONE);
        backimageVisibility.set(View.VISIBLE);
        handleToolbarBackground(title,R.color.advertisment_toolbar_color);
        handleStatusBar(R.color.advertisment_toolbar_color);
    }
    public void finishAct(View view){
        if (code==ConfigurationFile.Constants.HANDLE_NORMAL_REQUEST_DETAIL_TOOLBAR){
            Intent i=new Intent((Activity)context, NormalRequestActivity.class);
            ((Activity)context).startActivity(i);
            ((Activity)context).finish();
        } else
        ((Activity)context).finish();
    }
    public void handleToolbarBackground(String toolbarTitleText , int color){
        toolbarTitle.set(toolbarTitleText);
        toolbarColor.set(color);}



}
