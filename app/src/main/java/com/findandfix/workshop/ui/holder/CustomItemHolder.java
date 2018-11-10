package com.findandfix.workshop.ui.holder;

import android.support.v7.widget.RecyclerView;

import com.findandfix.workshop.databinding.ItemCustomLayoutBinding;
import com.findandfix.workshop.model.global.BaseModel;
import com.findandfix.workshop.viewmodel.CustomDialogItemViewModel;

/**
 * Created by DELL on 21/03/2018.
 */

public class CustomItemHolder extends RecyclerView.ViewHolder {
    private ItemCustomLayoutBinding itemCustomLayoutBinding;
    public CustomItemHolder(ItemCustomLayoutBinding binding) {
        super(binding.rlParent);
        this.itemCustomLayoutBinding=binding;
    }

    public void BindUser(BaseModel model){

            if (itemCustomLayoutBinding.getItemViewModel() == null) {
                itemCustomLayoutBinding.setItemViewModel(new CustomDialogItemViewModel(model));
            } else {
                itemCustomLayoutBinding.getItemViewModel().setModel(model);
            }

    }
}
