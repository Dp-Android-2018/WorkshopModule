package com.findandfix.workshop.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.findandfix.workshop.R;
import com.findandfix.workshop.model.global.UrgentRequestData;
import com.findandfix.workshop.ui.callback.RecycleItemClickListener;
import com.findandfix.workshop.ui.holder.UrgentRequestHolder;

import java.util.List;

/**
 * Created by DELL on 13/03/2018.
 */

public class CurrentUrgentRequestsAdapter extends RecyclerView.Adapter<UrgentRequestHolder>{
    private List<UrgentRequestData>data;
    private RecycleItemClickListener listener=null;
    public CurrentUrgentRequestsAdapter(List<UrgentRequestData>data) {
        this.data=data;
    }


        @NonNull
        @Override
        public UrgentRequestHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UrgentRequestHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_urgent_happing_now_layout,parent,false));}

        @Override
        public void onBindViewHolder(@NonNull UrgentRequestHolder holder, int position) {
                holder.bindUrgentRequest(data.get(position),listener);
                }

        @Override
            public int getItemCount() {return data.size();}

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public void setClickListener(RecycleItemClickListener listener){
        this.listener=listener;
    }
}
