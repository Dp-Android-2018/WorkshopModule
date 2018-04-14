package com.example.dell.workshopmodule.view.ui.holder;

import android.support.v7.widget.RecyclerView;

import com.example.dell.workshopmodule.databinding.ItemCurrentRequestsLayoutBinding;
import com.example.dell.workshopmodule.databinding.ItemSentOffersRequestsLayoutBinding;
import com.example.dell.workshopmodule.model.global.RequestData;
import com.example.dell.workshopmodule.viewmodel.HappeningNow.ItemRequestViewModel;

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
