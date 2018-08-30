package com.dp.dell.workshopmodule.view.ui.holder;

import android.support.v7.widget.RecyclerView;

import com.dp.dell.workshopmodule.databinding.ItemMoreCompletedRequestLayoutBinding;
import com.dp.dell.workshopmodule.databinding.ItemMoreSentOffersRequestsLayoutBinding;
import com.dp.dell.workshopmodule.model.global.RequestData;
import com.dp.dell.workshopmodule.viewmodel.HappeningNow.ItemRequestViewModel;

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
