package com.findandfix.workshop.ui.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.findandfix.workshop.R;
import com.findandfix.workshop.databinding.ItemMyConversationLayoutBinding;
import com.findandfix.workshop.model.global.Conv.ConversationHistory;
import com.findandfix.workshop.ui.holder.MyConversationHolder;

/**
 * Created by DELL on 09/05/2018.
 */

public class MyConversationAdapter extends RecyclerView.Adapter<MyConversationHolder> {
  private ObservableList<ConversationHistory> history;

    public MyConversationAdapter(ObservableList<ConversationHistory> history) {
        this.history=history;
        System.out.println("Adapter Basic :"+history.size());
    }

    @NonNull
    @Override
    public MyConversationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMyConversationLayoutBinding binding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_my_conversation_layout,parent,false);
        return new MyConversationHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyConversationHolder holder, int position) {
            holder.bindConversation(history.get(position));
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
