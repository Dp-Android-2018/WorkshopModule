package com.dp.dell.workshopmodule.view.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dp.dell.workshopmodule.R;
import com.dp.dell.workshopmodule.model.global.BrandItem;
import com.dp.dell.workshopmodule.view.ui.callback.RecycleItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by DELL on 31/07/2018.
 */

public class BrandsAdapter extends RecyclerView.Adapter<BrandsAdapter.ViewHolder> {

    private List<BrandItem> brandItems;
    private LayoutInflater mInflater;
    private RecycleItemClickListener recycleItemClickListener;
    Context context;

    // data is passed into the constructor
    public BrandsAdapter(Context context, List<BrandItem> brandItems) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.brandItems = brandItems;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_spinner, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BrandItem cityItem = brandItems.get(position);
        holder.title.setText(cityItem.getName());
        System.out.println("Brand Image Path :"+"http://new.findandfix.com/"+cityItem.getImage());
        Picasso.with(context).load("http://new.findandfix.com"+cityItem.getImage()).into(holder.image);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return brandItems.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        ImageView image;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_spinner_title);
            image = itemView.findViewById(R.id.iv_spinner_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (recycleItemClickListener != null) recycleItemClickListener.onItemClick(getAdapterPosition());
        }
    }



    public  void setClickListener(RecycleItemClickListener recycleItemClickListener) {
        this.recycleItemClickListener = recycleItemClickListener;
    }


}
