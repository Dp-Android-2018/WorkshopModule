package com.example.dell.workshopmodule.view.ui.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.dell.workshopmodule.databinding.ItemCountryLayoutBinding;
import com.example.dell.workshopmodule.model.global.CountryItem;
import com.example.dell.workshopmodule.viewmodel.CountryItemViewModel;

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
