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
import com.findandfix.workshop.ui.callback.RecycleItemClickListener;

import java.util.List;

public class DialogOffersAdapter extends RecyclerView.Adapter<DialogOffersAdapter.ViewHolder> {

    private List<String> offersItems;
    private LayoutInflater mInflater;
    private int row_index=-1;
    private RecycleItemClickListener recycleItemClickListener;
    Context context;

    // data is passed into the constructor
    public DialogOffersAdapter(Context context, List<String> offersItems) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.offersItems = offersItems;
    }

    // inflates the row layout from xml when needed
    @Override
    public DialogOffersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_spinner, parent, false);
        return new DialogOffersAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DialogOffersAdapter.ViewHolder holder, int position) {

        holder.title.setText(offersItems.get(position));
        holder.image.setVisibility(View.INVISIBLE);
        holder.relativeLayout.setOnClickListener(v -> {
            System.out.println("Clicked :"+position);
            row_index=position;
            notifyDataSetChanged();
            if (recycleItemClickListener != null) recycleItemClickListener.onItemClick(position);
        });
       if(row_index==position){
            holder.relativeLayout.setBackgroundColor(context.getResources().getColor(R.color.blackcolor));
        } else
        {
            holder.relativeLayout.setBackgroundColor(context.getResources().getColor(R.color.whitebasecolor));
        }
    }



    // total number of rows
    @Override
    public int getItemCount() {
        return offersItems.size();
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
        }
    }



    public  void setClickListener(RecycleItemClickListener recycleItemClickListener) {
        this.recycleItemClickListener = recycleItemClickListener;
    }


}

