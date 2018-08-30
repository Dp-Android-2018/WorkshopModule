package com.dp.dell.workshopmodule.view.ui.holder;

import android.support.v7.widget.RecyclerView;

import com.dp.dell.workshopmodule.databinding.ItemChatLayoutBinding;
import com.dp.dell.workshopmodule.databinding.ItemMyConversationLayoutBinding;
import com.dp.dell.workshopmodule.model.global.Conv.Message;
import com.dp.dell.workshopmodule.viewmodel.ItemChatDetailViewModel;

/**
 * Created by DELL on 13/05/2018.
 */

public class ConversationDetailHolder  extends RecyclerView.ViewHolder {
    private ItemChatLayoutBinding binding;
    public ConversationDetailHolder(ItemChatLayoutBinding binding) {
        super(binding.rvParent);
        this.binding=binding;
    }

    public void bindConversationDetail(Message message){
        if(binding.getViewmodel()==null)
            binding.setViewmodel(new ItemChatDetailViewModel(itemView.getContext(),message));
        else
            binding.getViewmodel().setConversationDetail(message);

    }
}

