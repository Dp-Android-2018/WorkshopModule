package com.findandfix.workshop.ui.holder;

import android.support.v7.widget.RecyclerView;

import com.findandfix.workshop.databinding.ItemCountryLayoutBinding;
import com.findandfix.workshop.model.global.CountryItem;
import com.findandfix.workshop.viewmodel.CountryItemViewModel;

/**
 * Created by DELL on 03/04/2018.
 */

public class CustomCountriesHolder extends RecyclerView.ViewHolder {
    private ItemCountryLayoutBinding binding;
    public CustomCountriesHolder(ItemCountryLayoutBinding binding) {
        super(binding.rlParent);
        this.binding=binding;
    }

    public void bidCountry(CountryItem countryItem){
            if(binding.getViewmodel()==null){
                binding.setViewmodel(new CountryItemViewModel(itemView.getContext(),countryItem));
            }else {
                binding.getViewmodel().setCountryData(countryItem);
            }
    }
}
