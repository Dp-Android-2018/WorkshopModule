package com.findandfix.workshop.ui.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.findandfix.workshop.R;
import com.findandfix.workshop.model.global.AchievmentObj;
import com.squareup.picasso.Picasso;

public class AchievmentHolder extends RecyclerView.ViewHolder {

    private TextView tvTitle;
    private TextView tvDate;
    private ImageView ivBefore;
    private ImageView ivAfter;
    private TextView tvDesc;
    public AchievmentHolder(View itemView) {
        super(itemView);
        tvTitle=(TextView)itemView.findViewById(R.id.tv_achievment_title);
        tvDate=(TextView)itemView.findViewById(R.id.tv_achievment_date);
        tvDesc=(TextView)itemView.findViewById(R.id.tv_achievment_desc);
        ivBefore=(ImageView) itemView.findViewById(R.id.iv_before);
        ivAfter=(ImageView) itemView.findViewById(R.id.iv_after);
    }


    public void bindAchievment(AchievmentObj obj){
            tvTitle.setText(obj.getTitle());
            tvDate.setText(obj.getCreatedAt());
            tvDesc.setText(obj.getDescription());
        Picasso.with(itemView.getContext()).load(obj.getBeforeImage()).into(ivBefore);
        Picasso.with(itemView.getContext()).load(obj.getAfterImage()).into(ivAfter);
    }
}
