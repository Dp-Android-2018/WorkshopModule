package com.dp.dell.workshopmodule.view.ui.holder;

import android.support.v7.widget.RecyclerView;

import com.dp.dell.workshopmodule.databinding.ItemCompletedRequestLayoutBinding;
import com.dp.dell.workshopmodule.databinding.ItemInProgressRequestsLayoutBinding;
import com.dp.dell.workshopmodule.model.global.RequestData;
import com.dp.dell.workshopmodule.viewmodel.HappeningNow.ItemRequestViewModel;

/**
 * Created by DELL on 27/03/2018.
 */

public class CompletedRequestHolder extends RecyclerView.ViewHolder {

    ItemCompletedRequestLayoutBinding binding;
    public CompletedRequestHolder(ItemCompletedRequestLayoutBinding binding) {
        super(binding.cvParent);
        this.binding=binding;
    }

    public void BindRequest(RequestData requestData){
        if(binding.getCompletedViewMOdel()==null){
            binding.setCompletedViewMOdel(new ItemRequestViewModel(itemView.getContext(),requestData));
        }else {
            binding.getCompletedViewMOdel().setRequestData(requestData);
        }
    }
}
