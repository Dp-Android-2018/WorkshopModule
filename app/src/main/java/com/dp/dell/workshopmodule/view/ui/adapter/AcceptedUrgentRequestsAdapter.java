package com.dp.dell.workshopmodule.view.ui.adapter;

import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.dp.dell.workshopmodule.R;
import com.dp.dell.workshopmodule.model.global.UrgentRequestData;
import com.dp.dell.workshopmodule.view.ui.holder.PendingUrgentRequestsHolder;

import java.util.List;

/**
 * Created by DELL on 13/03/2018.
 */

public class AcceptedUrgentRequestsAdapter extends RecyclerView.Adapter<PendingUrgentRequestsHolder>{

    private List<UrgentRequestData>data;
    private int checker;
    public AcceptedUrgentRequestsAdapter(ObservableList<UrgentRequestData> data,int checker) {
        System.out.println("Code Urgent Testing:"+data.size());
        this.checker=checker;
        this.data=data;
    }


    @NonNull
    @Override
    public PendingUrgentRequestsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (checker==1)
        return new PendingUrgentRequestsHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_accepted_urgent_requests,parent,false));
        else if (checker==2)
            return new PendingUrgentRequestsHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_more_accepted_urgent_request_layout,parent,false));
        else return null;
        }

    @Override
    public void onBindViewHolder(@NonNull PendingUrgentRequestsHolder holder, int position) {
        holder.bindUrgentRequest(data.get(position));
    }

    @Override
    public int getItemCount() {return data.size();}

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }


}
