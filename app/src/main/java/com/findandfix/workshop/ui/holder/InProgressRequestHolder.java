package com.findandfix.workshop.ui.holder;

import android.support.v7.widget.RecyclerView;

import com.findandfix.workshop.databinding.ItemInProgressRequestsLayoutBinding;
import com.findandfix.workshop.model.global.RequestData;
import com.findandfix.workshop.viewmodel.HappeningNow.ItemRequestViewModel;

/**
 * Created by DELL on 27/03/2018.
 */

public class InProgressRequestHolder extends RecyclerView.ViewHolder {
    ItemInProgressRequestsLayoutBinding binding;
    public InProgressRequestHolder(ItemInProgressRequestsLayoutBinding binding) {
        super(binding.cvParent);
        this.binding=binding;
    }

    public void BindRequest(RequestData requestData){
        if(binding.getProgressViewModel()==null){
            binding.setProgressViewModel(new ItemRequestViewModel(itemView.getContext(),requestData));
        }else {
            binding.getProgressViewModel().setRequestData(requestData);
        }
    }
}
