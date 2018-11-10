package com.findandfix.workshop.ui.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.findandfix.workshop.R;
import com.findandfix.workshop.databinding.ItemAdvertisingBinding;
import com.findandfix.workshop.model.global.AdvData;
import com.findandfix.workshop.ui.holder.OffersHolder;

import java.util.List;


public class OffersAdapter extends RecyclerView.Adapter<OffersHolder> {

    private List<AdvData> data;

    public OffersAdapter(List<AdvData> data) {
        System.out.println("Offers CODE SIZE 2:"+data.size());
        this.data=data;
    }

    @NonNull
    @Override
    public OffersHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAdvertisingBinding binding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_advertising,parent,false);
        return new OffersHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OffersHolder holder, int position) {
            holder.bindOffer(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
