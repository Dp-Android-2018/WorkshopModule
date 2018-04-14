package com.example.dell.workshopmodule.view.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.dell.workshopmodule.R;
import com.example.dell.workshopmodule.databinding.ItemCityLayoutBinding;
import com.example.dell.workshopmodule.model.global.BaseModel;
import com.example.dell.workshopmodule.view.ui.holder.CustomCitiesHolder;
import com.example.dell.workshopmodule.view.ui.holder.CustomCountriesHolder;
import com.example.dell.workshopmodule.viewmodel.CityItemViewModel;

import java.util.List;

/**
 * Created by DELL on 19/03/2018.
 */

public class CityAdapter extends RecyclerView.Adapter<CustomCitiesHolder> {

    private List<BaseModel>cities;
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
}
