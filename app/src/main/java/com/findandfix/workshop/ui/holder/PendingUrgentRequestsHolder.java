package com.findandfix.workshop.ui.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.findandfix.workshop.R;
import com.findandfix.workshop.model.global.UrgentRequestData;
import com.google.gson.Gson;

public class PendingUrgentRequestsHolder extends RecyclerView.ViewHolder {

    private TextView tvUrgentType;
    private TextView tvCarInfo;
    private TextView tvAddress;
    private TextView tvUrgentTime;

    public PendingUrgentRequestsHolder(View itemView) {
        super(itemView);
        tvUrgentType = (TextView) itemView.findViewById(R.id.tv_urgent_request_title);
        tvCarInfo = (TextView) itemView.findViewById(R.id.tv_urgent_car_type);
        tvAddress = (TextView) itemView.findViewById(R.id.tv_urgent_request_location);
        tvUrgentTime = (TextView) itemView.findViewById(R.id.tv_request_time);
    }

    public void bindUrgentRequest(UrgentRequestData data) {
        System.out.println("Code Urgent :"+data.getId());
        System.out.println("Code Urgent  OBJ:"+new Gson().toJson(data));
        if (data.getWinchType() == null)
            tvUrgentType.setText(data.getType().getName());
        else {
            String types="";
            for (int i=0;i<data.getWinchType().size();i++){
                types=types+" "+data.getWinchType().get(i).getwName()+" ";
            }
            tvUrgentType.setText(data.getType().getName() + " - " + types);
        }
        tvCarInfo.setText(data.getBrand() + " - " + data.getModel() + " - " + data.getYear());
        tvAddress.setText(data.getDistance()+" "+itemView.getContext().getString(R.string.km));
        if (data.getDate()!=null)
        tvUrgentTime.setText(data.getDate());
        else tvUrgentTime.setText("");

    }
}
