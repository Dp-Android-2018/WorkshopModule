package com.findandfix.workshop.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import com.findandfix.workshop.R;
import com.findandfix.workshop.databinding.ActivityMyConversationLayoutBinding;
import com.findandfix.workshop.utils.ConfigurationFile;
import com.findandfix.workshop.viewmodel.ConversationViewModel;
import com.findandfix.workshop.viewmodel.ToolbarViewModel;

/**
 * Created by DELL on 09/05/2018.
 */

public class MyConversationsActivity extends BaseActivity {
    private ActivityMyConversationLayoutBinding binding;
    private ConversationViewModel viewModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        setUpToolBar();
    }

    public void initBinding(){
        binding= DataBindingUtil.setContentView(MyConversationsActivity.this, R.layout.activity_my_conversation_layout);
        viewModel=new ConversationViewModel(MyConversationsActivity.this);
        binding.rvMyConversation.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
        binding.setConversationviewmodel(viewModel);
    }

    public void setUpToolBar(){
        setSupportActionBar( binding.toolbar.toolbar);
        binding.toolbar.setViewmodel(new ToolbarViewModel(MyConversationsActivity.this, ConfigurationFile.Constants.HANDLE_CONVERSATION_HISTORY_TOOLBAR));
    }
}
