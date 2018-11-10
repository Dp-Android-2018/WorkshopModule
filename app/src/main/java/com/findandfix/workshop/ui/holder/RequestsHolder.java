package com.findandfix.workshop.ui.holder;

import android.support.v7.widget.RecyclerView;

import com.findandfix.workshop.databinding.ItemCurrentRequestsLayoutBinding;
import com.findandfix.workshop.model.global.RequestData;
import com.findandfix.workshop.viewmodel.HappeningNow.ItemRequestViewModel;

/**
 * Created by DELL on 27/03/2018.
 */

public class RequestsHolder extends RecyclerView.ViewHolder {
    private ItemCurrentRequestsLayoutBinding binding;
    public RequestsHolder(ItemCurrentRequestsLayoutBinding binding) {
        super(binding.cvParent);
        this.binding=binding;
    }

    public void BindRequest(RequestData requestData){
            if(binding.getRequestitemviewmodel()==null){
                binding.setRequestitemviewmodel(new ItemRequestViewModel(itemView.getContext(),requestData));
            }else {
                binding.getRequestitemviewmodel().setRequestData(requestData);
            }
    }
}
