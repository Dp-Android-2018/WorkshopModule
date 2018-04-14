package com.example.dell.workshopmodule.view.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.dell.workshopmodule.R;
import com.example.dell.workshopmodule.view.ui.holder.CurrentRequestsHolder;

/**
 * Created by DELL on 13/03/2018.
 */

public class CurrentUrgentRequestsAdapter extends RecyclerView.Adapter<CurrentRequestsHolder>{

    public CurrentUrgentRequestsAdapter() {}


        @NonNull
        @Override
        public CurrentRequestsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CurrentRequestsHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_urgent_happing_now_layout,parent,false));}

        @Override
        public void onBindViewHolder(@NonNull CurrentRequestsHolder holder, int position) {}

        @Override
            public int getItemCount() {return 8;}

}
