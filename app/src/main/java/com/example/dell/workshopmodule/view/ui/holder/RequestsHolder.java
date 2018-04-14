package com.example.dell.workshopmodule.view.ui.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.dell.workshopmodule.databinding.ItemCurrentRequestsLayoutBinding;
import com.example.dell.workshopmodule.model.global.RequestData;
import com.example.dell.workshopmodule.viewmodel.HappeningNow.ItemRequestViewModel;

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
