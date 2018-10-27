package com.dp.dell.workshopmodule.view.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.dp.dell.workshopmodule.R;
import com.dp.dell.workshopmodule.databinding.ActivityChatLayoutBinding;
import com.dp.dell.workshopmodule.model.global.Conv.ConversationHistory;
import com.dp.dell.workshopmodule.utils.ConfigurationFile;
import com.dp.dell.workshopmodule.utils.CustomUtils;
import com.dp.dell.workshopmodule.view.ui.callback.BaseInterface;
import com.dp.dell.workshopmodule.view.ui.callback.CallAnotherActivityNavigator;
import com.dp.dell.workshopmodule.viewmodel.ChatViewModel;
import com.dp.dell.workshopmodule.viewmodel.ToolbarViewModel;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;

/**
 * Created by DELL on 14/03/2018.
 */

public class ChatActivity extends BaseActivity implements BaseInterface,CallAnotherActivityNavigator{

    private ActivityChatLayoutBinding binding;
    private ChatViewModel viewModel;
    private ConversationHistory history;
    public static boolean active=false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        setUpToolBar();
    }
/*We Define Static Variable To Check If Activity is Active Or Not To Push Notification Only When It is not Active */
    @Override
    protected void onStart() {
        super.onStart();
        active=true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        active=false;
    }

    /*Inflating Ui + Declaring View Model + Getting Conversation Id To get Conversation Details + Defining Recycler View
    * Property*/
    public void initBinding(){
            history=(ConversationHistory) getIntent().getSerializableExtra("Conversation_Id");
            binding= DataBindingUtil.setContentView(ChatActivity.this, R.layout.activity_chat_layout);
            viewModel=new ChatViewModel(this,this,this,history);
            binding.setViewmodel(viewModel);
                binding.rvChat.setHasFixedSize(true);
             binding.rvChat.setItemViewCacheSize(20);
             binding.rvChat.setDrawingCacheEnabled(true);
            binding.rvChat.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
            binding.rvChat.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
    }

    /* Handling Toolbar Layout + Defining It's Properties */
    public void setUpToolBar(){
        setSupportActionBar( binding.toolbar.toolbar);
        ToolbarViewModel toolbarViewModel=new ToolbarViewModel(ChatActivity.this,ConfigurationFile.Constants.HANDLE_CONVERSATION_HISTORY_TOOLBAR);
        binding.toolbar.setViewmodel(toolbarViewModel);
        toolbarViewModel.handleConversationDetailToolbar(history.getConversations().sender_name);

    }

    /*Updating Ui According To Code Sent From View Model */
    @Override
    public void updateUi(int code) {
        if(code== ConfigurationFile.Constants.SHOW_DIALOG_CODE)
            CustomUtils.getInstance().showDialog(ChatActivity.this,viewModel);
        else if(code==ConfigurationFile.Constants.PERMISSION_GRANTED_CAMERA)
           CustomUtils.getInstance().openCamera(ChatActivity.this);
        else if(code==ConfigurationFile.Constants.PERMISSION_GRANTED_GALLERY)
            CustomUtils.getInstance().openGallery(ChatActivity.this);
    }

    /*Handling On Activity Result If It take picture and pick location */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            Image image = ImagePicker.getFirstImageOrNull(data);
            viewModel.onActivityResult(requestCode,resultCode,data,image);
            return;
        }
    }

    /*Displaying Place Picker To make User Pick Up his Location */
    @Override
    public void callActivity() {
            CustomUtils.getInstance().startPlacePicker(ChatActivity.this);
    }
}
