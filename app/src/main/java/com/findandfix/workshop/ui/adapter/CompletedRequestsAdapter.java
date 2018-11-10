package com.findandfix.workshop.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.findandfix.workshop.R;
import com.findandfix.workshop.ui.holder.CurrentRequestsHolder;

/**
 * Created by DELL on 12/03/2018.
 */

public class CompletedRequestsAdapter extends RecyclerView.Adapter<CurrentRequestsHolder>{
    public CompletedRequestsAdapter() {
    }

    @NonNull
    @Override
    public CurrentRequestsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CurrentRequestsHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_completed_request_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CurrentRequestsHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 8;
    }
}
