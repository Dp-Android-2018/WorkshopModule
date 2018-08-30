package com.dp.dell.workshopmodule.view.ui.holder;

import android.support.v7.widget.RecyclerView;

import com.dp.dell.workshopmodule.databinding.ItemCustomLayoutBinding;
import com.dp.dell.workshopmodule.model.global.BaseModel;
import com.dp.dell.workshopmodule.viewmodel.CustomDialogItemViewModel;

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
