package com.dp.dell.workshopmodule.view.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.dp.dell.workshopmodule.R;
import com.dp.dell.workshopmodule.view.ui.holder.CurrentRequestsHolder;

/**
 * Created by DELL on 14/03/2018.
 */

public class MyAdvertisementAdapter extends RecyclerView.Adapter<CurrentRequestsHolder> {

    public MyAdvertisementAdapter() {}

    @NonNull
    @Override
    public CurrentRequestsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CurrentRequestsHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_advertisment_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CurrentRequestsHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 8;
    }
}
