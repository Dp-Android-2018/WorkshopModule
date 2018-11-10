package com.findandfix.workshop.ui.holder;

import android.support.v7.widget.RecyclerView;

import com.findandfix.workshop.databinding.ItemMultiChoiseBrandsLayoutBinding;
import com.findandfix.workshop.model.global.BrandItem;
import com.findandfix.workshop.viewmodel.CustomDialogBrandItemViewModel;

/**
 * Created by DELL on 24/03/2018.
 */

public class CustomBranItemHolder extends RecyclerView.ViewHolder {
    ItemMultiChoiseBrandsLayoutBinding binding;
    public CustomBranItemHolder(ItemMultiChoiseBrandsLayoutBinding binding) {
        super(binding.rlParent);
        this.binding=binding;
    }

    public void BindBrand(BrandItem brandItem){
            if(binding.getItemViewModel()==null) {
                binding.setItemViewModel(new CustomDialogBrandItemViewModel(brandItem));
            }else {
                binding.getItemViewModel().setBrandItem(brandItem);
            }

    }
}
