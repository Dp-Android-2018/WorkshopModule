package com.findandfix.workshop.ui.holder;

import android.support.v7.widget.RecyclerView;

import com.findandfix.workshop.databinding.ItemSentOffersRequestsLayoutBinding;
import com.findandfix.workshop.model.global.RequestData;
import com.findandfix.workshop.viewmodel.HappeningNow.ItemRequestViewModel;

/**
 * Created by DELL on 27/03/2018.
 */

public class PendingRequestHolder extends RecyclerView.ViewHolder{
    private ItemSentOffersRequestsLayoutBinding binding;
    public PendingRequestHolder(ItemSentOffersRequestsLayoutBinding binding) {
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
