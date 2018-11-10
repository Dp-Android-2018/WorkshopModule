package com.findandfix.workshop.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.findandfix.workshop.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Bahaa Gabal on 20,October,2018
 */
public class WorkshopProfileImagesAdapter extends RecyclerView.Adapter<WorkshopProfileImagesAdapter.WorkshopProfileImagesHolder> {
    private ArrayList<String> mWorkshopProfileImages;
    private Context mContext;

    public WorkshopProfileImagesAdapter(Context mContext, ArrayList<String> mWorkshopProfileImages) {
        this.mContext = mContext;
        this.mWorkshopProfileImages = mWorkshopProfileImages;
    }

    @NonNull
    @Override
    public WorkshopProfileImagesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_workshop_profile_image, parent, false);
        return new WorkshopProfileImagesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkshopProfileImagesHolder holder, int position) {
        Picasso.with(mContext).load(mWorkshopProfileImages.get(position)).into(holder.mIvWorkshopProfileImage);
    }

    @Override
    public int getItemCount() {
        return mWorkshopProfileImages.size();
    }

    public class WorkshopProfileImagesHolder extends RecyclerView.ViewHolder {
        ImageView mIvWorkshopProfileImage;

        public WorkshopProfileImagesHolder(View itemView) {
            super(itemView);
            mIvWorkshopProfileImage = itemView.findViewById(R.id.iv_workshop_profile_image);
        }
    }
}
