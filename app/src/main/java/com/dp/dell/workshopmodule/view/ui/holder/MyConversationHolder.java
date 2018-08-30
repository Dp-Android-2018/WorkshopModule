package com.dp.dell.workshopmodule.view.ui.holder;

import android.support.v7.widget.RecyclerView;

import com.dp.dell.workshopmodule.databinding.ItemMyConversationLayoutBinding;
import com.dp.dell.workshopmodule.model.global.Conv.ConversationHistory;
import com.dp.dell.workshopmodule.viewmodel.ItemConversationViewModel;

/**
 * Created by DELL on 09/05/2018.
 */

public class MyConversationHolder extends RecyclerView.ViewHolder {
    private ItemMyConversationLayoutBinding binding;
    public MyConversationHolder(ItemMyConversationLayoutBinding binding) {
        super(binding.cvParent);
        this.binding=binding;
    }

    public void bindConversation(ConversationHistory conversationHistory){
        if(binding.getViewmodel()==null)
            binding.setViewmodel(new ItemConversationViewModel(itemView.getContext(),conversationHistory));
        else
            binding.getViewmodel().setConversation(conversationHistory);

    }
}
