package com.findandfix.workshop.ui.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.findandfix.workshop.R;
import com.findandfix.workshop.databinding.ItemCityLayoutBinding;
import com.findandfix.workshop.model.global.BaseModel;
import com.findandfix.workshop.ui.callback.RecycleItemClickListener;
import com.findandfix.workshop.ui.holder.CustomCitiesHolder;

import java.util.List;

/**
 * Created by DELL on 19/03/2018.
 */

public class CityAdapter extends RecyclerView.Adapter<CustomCitiesHolder> {

    private List<BaseModel>cities;
    private RecycleItemClickListener recycleItemClickListener;
    public CityAdapter(List<BaseModel>cities) {
        this.cities=cities;
    }

    @NonNull
    @Override
    public CustomCitiesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCityLayoutBinding binding=DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.item_city_layout,parent,false);
        return new CustomCitiesHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomCitiesHolder holder, int position) {
        holder.bindCity(cities.get(position));

    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public  void setClickListener(RecycleItemClickListener recycleItemClickListener) {
        this.recycleItemClickListener = recycleItemClickListener;
    }
}
