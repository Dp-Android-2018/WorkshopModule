package com.findandfix.workshop.ui.holder;

import android.support.v7.widget.RecyclerView;

import com.findandfix.workshop.databinding.ItemCityLayoutBinding;
import com.findandfix.workshop.model.global.BaseModel;
import com.findandfix.workshop.viewmodel.CityItemViewModel;

/**
 * Created by DELL on 05/04/2018.
 */

public class CustomCitiesHolder extends RecyclerView.ViewHolder {
    private ItemCityLayoutBinding binding;
    public CustomCitiesHolder(ItemCityLayoutBinding binding) {
        super(binding.rlParent);
        this.binding=binding;
    }

    public void bindCity(BaseModel city){
        if(binding.getCityViewModel()==null){
            binding.setCityViewModel(new CityItemViewModel(city));
        }else {
                binding.getCityViewModel().setCity(city);
        }
    }
}
