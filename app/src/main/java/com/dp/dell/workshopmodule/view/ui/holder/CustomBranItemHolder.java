package com.dp.dell.workshopmodule.view.ui.holder;

import android.support.v7.widget.RecyclerView;

import com.dp.dell.workshopmodule.databinding.ItemMultiChoiseBrandsLayoutBinding;
import com.dp.dell.workshopmodule.model.global.BrandItem;
import com.dp.dell.workshopmodule.viewmodel.CustomDialogBrandItemViewModel;

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
