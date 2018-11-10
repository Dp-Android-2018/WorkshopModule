package com.findandfix.workshop.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.findandfix.workshop.R;
import com.findandfix.workshop.model.global.UrgentRequestData;
import com.findandfix.workshop.ui.holder.CompletedUrgentRequestHolder;

import java.util.List;

/**
 * Created by DELL on 13/03/2018.
 */

public class CompletedUrgentRequestAdapter extends RecyclerView.Adapter<CompletedUrgentRequestHolder>{
    private List<UrgentRequestData> data;
    private int checker;
    public CompletedUrgentRequestAdapter(List<UrgentRequestData>data, int checker) {
        this.data=data;
        this.checker=checker;
    }

    @NonNull
    @Override
    public CompletedUrgentRequestHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (checker==1)
        return new CompletedUrgentRequestHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_completed_urgent_request_layout,parent,false));
        else  return new CompletedUrgentRequestHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_more_completed_urgent_request,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CompletedUrgentRequestHolder holder, int position) {
        holder.bindUrgentRequest(data.get(position));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
