package com.example.dell.workshopmodule.view.ui.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.dell.workshopmodule.databinding.ItemCityLayoutBinding;
import com.example.dell.workshopmodule.model.global.BaseModel;
import com.example.dell.workshopmodule.viewmodel.CityItemViewModel;

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
