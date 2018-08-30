package com.dp.dell.workshopmodule.view.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.dp.dell.workshopmodule.R;
import com.dp.dell.workshopmodule.model.global.UrgentRequestData;
import com.dp.dell.workshopmodule.view.ui.callback.UrgentProgressItemClickListener;
import com.dp.dell.workshopmodule.view.ui.holder.InProgressUrgentHolder;

import java.util.List;

/**
 * Created by DELL on 13/03/2018.
 */

public class InProgressUrgentRequestAdapter  extends RecyclerView.Adapter<InProgressUrgentHolder>{

    private List<UrgentRequestData>data;
    private UrgentProgressItemClickListener listener;
    public InProgressUrgentRequestAdapter(List<UrgentRequestData>data) {
        this.data=data;

    }


    @NonNull
    @Override
    public InProgressUrgentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new InProgressUrgentHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_urgent_in_progress_layout,parent,false));}

    @Override
    public void onBindViewHolder(@NonNull InProgressUrgentHolder holder, int position) {
        holder.bindUrgentRequest(data.get(position),listener);
    }

    public void setInProgressItemListener(UrgentProgressItemClickListener inProgressItemListener){
        this.listener=inProgressItemListener;
    }

    @Override
    public int getItemCount() {return data.size();}

}