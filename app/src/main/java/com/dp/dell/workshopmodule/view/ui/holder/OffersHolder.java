package com.dp.dell.workshopmodule.view.ui.holder;

import android.support.v7.widget.RecyclerView;

import com.dp.dell.workshopmodule.databinding.ItemAdvertisingBinding;
import com.dp.dell.workshopmodule.model.global.AdvData;
import com.dp.dell.workshopmodule.viewmodel.ItemOfferViewModel;


public class OffersHolder extends RecyclerView.ViewHolder {
    private ItemAdvertisingBinding binding;
    public OffersHolder(ItemAdvertisingBinding binding) {
        super(binding.getRoot());
        this.binding=binding;
    }

    public void bindOffer(AdvData advData){
        System.out.println();
       if(binding.getViewmodel()==null)
            binding.setViewmodel(new ItemOfferViewModel(itemView.getContext(),advData));
        else
            binding.getViewmodel().setAdv(advData);

    }
}
