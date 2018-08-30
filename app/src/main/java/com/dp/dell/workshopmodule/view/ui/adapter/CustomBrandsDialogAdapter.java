package com.dp.dell.workshopmodule.view.ui.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.dp.dell.workshopmodule.R;
import com.dp.dell.workshopmodule.databinding.ItemMultiChoiseBrandsLayoutBinding;
import com.dp.dell.workshopmodule.model.global.BrandItem;
import com.dp.dell.workshopmodule.view.ui.holder.CustomBranItemHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 24/03/2018.
 */

public class CustomBrandsDialogAdapter extends RecyclerView.Adapter<CustomBranItemHolder> {

    private List<BrandItem> brandItems=new ArrayList<>();;

    public CustomBrandsDialogAdapter(List<BrandItem> brandItems) {
        this.brandItems = brandItems;
    }
    @NonNull
    @Override
    public CustomBranItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMultiChoiseBrandsLayoutBinding binding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_multi_choise_brands_layout,parent,false);
        return new CustomBranItemHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomBranItemHolder holder, int position) {
        holder.BindBrand(brandItems.get(position));
    }

    @Override
    public int getItemCount() {
        return brandItems.size();
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
