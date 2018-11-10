package com.findandfix.workshop.ui.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.findandfix.workshop.R;
import com.findandfix.workshop.model.global.CountryItem;
import com.findandfix.workshop.ui.callback.RecycleItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DialogCountriesAdapter extends RecyclerView.Adapter<DialogCountriesAdapter.ViewHolder> {

    private List<CountryItem> countryItems;
    private LayoutInflater mInflater;
    private int row_index=-1;
    private RecycleItemClickListener recycleItemClickListener;
    Context context;

    // data is passed into the constructor
    public DialogCountriesAdapter(Context context, List<CountryItem> countryItems) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.countryItems = countryItems;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_spinner, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CountryItem countryItem = countryItems.get(position);
        holder.title.setText(countryItem.getName());
        Picasso.with(context).load("http://new.findandfix.com/"+countryItem.getImage()).into(holder.image);
        holder.relativeLayout.setOnClickListener(v -> {
            System.out.println("Clicked :"+position);
            row_index=position;
            notifyDataSetChanged();
            if (recycleItemClickListener != null) recycleItemClickListener.onItemClick(position);
        });
     /*   if(row_index==position){
            holder.relativeLayout.setBackgroundColor(context.getResources().getColor(R.color.blackcolor));
        } else
        {
            holder.relativeLayout.setBackgroundColor(context.getResources().getColor(R.color.whitebasecolor));
        }*/
    }



    // total number of rows
    @Override
    public int getItemCount() {
        return countryItems.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView image;
        RelativeLayout relativeLayout;
        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_spinner_title);
            image = itemView.findViewById(R.id.iv_spinner_image);
            relativeLayout=(RelativeLayout) itemView.findViewById(R.id.rl_container);
          //  itemView.setOnClickListener(this);
        }

       /* @Override
        public void onClick(View view) {
         //   view.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bg_dialog_selector));
            row_index=getAdapterPosition();
            notifyDataSetChanged();
            if (recycleItemClickListener != null) recycleItemClickListener.onItemClick(getAdapterPosition());
        }*/
    }



    public  void setClickListener(RecycleItemClickListener recycleItemClickListener) {
        this.recycleItemClickListener = recycleItemClickListener;
    }


}
