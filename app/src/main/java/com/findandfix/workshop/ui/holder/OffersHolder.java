package com.findandfix.workshop.ui.holder;

import android.support.v7.widget.RecyclerView;

import com.findandfix.workshop.databinding.ItemAdvertisingBinding;
import com.findandfix.workshop.model.global.AdvData;
import com.findandfix.workshop.viewmodel.ItemOfferViewModel;


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
