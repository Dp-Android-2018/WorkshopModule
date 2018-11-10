package com.findandfix.workshop.ui.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.findandfix.workshop.R;
import com.findandfix.workshop.databinding.ItemChatLayoutBinding;
import com.findandfix.workshop.model.global.Conv.Message;
import com.findandfix.workshop.ui.holder.ConversationDetailHolder;

/**
 * Created by DELL on 13/05/2018.
 */

public class ConversationDetailAdapter extends RecyclerView.Adapter<ConversationDetailHolder> {
    private ObservableList<Message> history;
    public ConversationDetailAdapter(ObservableList<Message> history) {
        this.history=history;
    }

    @NonNull
    @Override
    public ConversationDetailHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemChatLayoutBinding binding=DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_chat_layout,parent,false);
        return new ConversationDetailHolder(binding);
    }



    @Override
    public void onBindViewHolder(@NonNull ConversationDetailHolder holder, int position) {
        holder.bindConversationDetail(history.get(position));
    }

    @Override
    public int getItemCount() {
        return history.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
