package com.findandfix.workshop.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.findandfix.workshop.R;
import com.findandfix.workshop.model.global.BaseModel;
import com.findandfix.workshop.ui.callback.RecycleItemClickListener;

import java.util.List;


public class SpecializeAdapter extends RecyclerView.Adapter<SpecializeAdapter.ViewHolder> {

    private List<BaseModel> specializeItems;
    private LayoutInflater mInflater;
    private RecycleItemClickListener recycleItemClickListener;
    Context context;

    // data is passed into the constructor
    public SpecializeAdapter(Context context, List<BaseModel> specializeItems) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.specializeItems = specializeItems;
    }

    // inflates the row layout from xml when needed
    @Override
    public SpecializeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_spinner, parent, false);
        return new SpecializeAdapter.ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(SpecializeAdapter.ViewHolder holder, int position) {
        BaseModel baseModel = specializeItems.get(position);
        holder.title.setText(baseModel.getName());
        holder.image.setVisibility(View.GONE);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return specializeItems.size();
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
            if (recycleItemClickListener != null)
                recycleItemClickListener.onItemClick(getAdapterPosition());
        }
    }


    public void setClickListener(RecycleItemClickListener recycleItemClickListener) {
        this.recycleItemClickListener = recycleItemClickListener;
    }


}
