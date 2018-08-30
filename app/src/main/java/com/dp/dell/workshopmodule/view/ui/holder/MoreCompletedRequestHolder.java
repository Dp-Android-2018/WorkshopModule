package com.dp.dell.workshopmodule.view.ui.holder;

import android.support.v7.widget.RecyclerView;

import com.dp.dell.workshopmodule.databinding.ItemCompletedRequestLayoutBinding;
import com.dp.dell.workshopmodule.databinding.ItemMoreCompletedRequestLayoutBinding;
import com.dp.dell.workshopmodule.model.global.RequestData;
import com.dp.dell.workshopmodule.viewmodel.HappeningNow.ItemRequestViewModel;

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
