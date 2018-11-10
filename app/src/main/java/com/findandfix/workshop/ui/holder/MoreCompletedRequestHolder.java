package com.findandfix.workshop.ui.holder;

import android.support.v7.widget.RecyclerView;

import com.findandfix.workshop.databinding.ItemMoreCompletedRequestLayoutBinding;
import com.findandfix.workshop.model.global.RequestData;
import com.findandfix.workshop.viewmodel.HappeningNow.ItemRequestViewModel;

/**
 * Created by DELL on 07/08/2018.
 */

public class MoreCompletedRequestHolder extends RecyclerView.ViewHolder {

    ItemMoreCompletedRequestLayoutBinding binding;
    public MoreCompletedRequestHolder(ItemMoreCompletedRequestLayoutBinding binding) {
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
