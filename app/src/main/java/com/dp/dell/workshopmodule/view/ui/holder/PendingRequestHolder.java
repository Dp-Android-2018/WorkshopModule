package com.dp.dell.workshopmodule.view.ui.holder;

import android.support.v7.widget.RecyclerView;

import com.dp.dell.workshopmodule.databinding.ItemCurrentRequestsLayoutBinding;
import com.dp.dell.workshopmodule.databinding.ItemSentOffersRequestsLayoutBinding;
import com.dp.dell.workshopmodule.model.global.RequestData;
import com.dp.dell.workshopmodule.viewmodel.HappeningNow.ItemRequestViewModel;

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
