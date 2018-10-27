package com.dp.dell.workshopmodule.view.ui.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dp.dell.workshopmodule.R;
import com.esafirm.imagepicker.model.Image;

import java.util.List;

/**
 * Created by Bahaa Gabal on 13,October,2018
 */
public class WorkShopProfileAdapter extends RecyclerView.Adapter<WorkShopProfileAdapter.ViewHolder> {
    private Context mContext;
    private List<Image>images;

    public WorkShopProfileAdapter(Context mContext, List<Image>images){
        this.mContext=mContext;
        this.images=images;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.item_workshop_profile_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String imagePath=images.get(position).getPath();
        holder.image.setImageBitmap(BitmapFactory.decodeFile(imagePath));
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.iv_workshop_profile);
        }
    }
}
