package com.dp.dell.workshopmodule.view.ui.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.dp.dell.workshopmodule.R;
import com.dp.dell.workshopmodule.databinding.ItemCustomLayoutBinding;
import com.dp.dell.workshopmodule.model.global.BaseModel;
import com.dp.dell.workshopmodule.view.ui.holder.CustomItemHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 21/03/2018.
 */

public class CustomDialogAdapter extends RecyclerView.Adapter<CustomItemHolder>{
    private List<BaseModel>models=new ArrayList<>();;

    public CustomDialogAdapter(List<BaseModel> models) {
        this.models = models;
    }

    @Override
    public CustomItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemCustomLayoutBinding binding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_custom_layout,parent,false);
        return new CustomItemHolder(binding);
    }

    @Override
    public void onBindViewHolder(CustomItemHolder holder, int position) {

        holder.BindUser(models.get(position));
    }


    @Override
    public int getItemCount() {
        System.out.println("Specializations Size Adapter"+models.size());
        return models.size();
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }


}

