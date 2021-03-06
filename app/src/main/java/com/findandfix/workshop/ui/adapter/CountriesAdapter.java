package com.findandfix.workshop.ui.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.findandfix.workshop.R;
import com.findandfix.workshop.databinding.ItemCountryLayoutBinding;
import com.findandfix.workshop.model.global.CountryItem;
import com.findandfix.workshop.ui.callback.RecycleItemClickListener;
import com.findandfix.workshop.ui.holder.CustomCountriesHolder;

import java.util.List;

/**
 * Created by DELL on 03/04/2018.
 */

public class CountriesAdapter extends RecyclerView.Adapter<CustomCountriesHolder> {

    private RecycleItemClickListener recycleItemClickListener;
    private List<CountryItem>countryItems;
    public CountriesAdapter(List<CountryItem> countryItems) {
        this.countryItems=countryItems;
    }

    @NonNull
    @Override
    public CustomCountriesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCountryLayoutBinding binding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_country_layout,parent,false);
        return new CustomCountriesHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomCountriesHolder holder, int position) {
        holder.bidCountry(countryItems.get(position));
    }

    @Override
    public int getItemCount() {
        return countryItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public  void setClickListener(RecycleItemClickListener recycleItemClickListener) {
        this.recycleItemClickListener = recycleItemClickListener;
    }
}
