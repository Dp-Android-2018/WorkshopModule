package com.findandfix.workshop.ui.holder;

import android.support.v7.widget.RecyclerView;

import com.findandfix.workshop.databinding.ItemMoreSentOffersRequestsLayoutBinding;
import com.findandfix.workshop.model.global.RequestData;
import com.findandfix.workshop.viewmodel.HappeningNow.ItemRequestViewModel;

/**
 * Created by DELL on 07/08/2018.
 */

public class MoreSentOffersRequestHolder extends RecyclerView.ViewHolder {
    ItemMoreSentOffersRequestsLayoutBinding binding;
    public MoreSentOffersRequestHolder(ItemMoreSentOffersRequestsLayoutBinding binding) {
        super(binding.cvParent);
        this.binding=binding;
    }

    public void BindRequest(RequestData requestData){
        if(binding.getPendingviewmodel()==null){
            binding.setPendingviewmodel(new ItemRequestViewModel(itemView.getContext(),requestData));
        }else {
            binding.getPendingviewmodel().setRequestData(requestData);
        }
    }
}
