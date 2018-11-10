package com.findandfix.workshop.ui.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.findandfix.workshop.R;
import com.findandfix.workshop.model.global.UrgentRequestData;
import com.findandfix.workshop.ui.callback.UrgentProgressItemClickListener;
import com.findandfix.workshop.utils.ConfigurationFile;

public class InProgressUrgentHolder extends RecyclerView.ViewHolder {

    private TextView tvUrgentType;
    private TextView tvCarInfo;
    private TextView tvAddress;
    private TextView tvUrgentTime;
    private Button btnCall;
    private Button btnChat;
    private Button btnInfo;
    private Button btnLocation;
    public InProgressUrgentHolder(View itemView) {
        super(itemView);
        tvUrgentType = (TextView) itemView.findViewById(R.id.tv_urgent_request_title);
        tvCarInfo = (TextView) itemView.findViewById(R.id.tv_urgent_car_type);
        tvAddress = (TextView) itemView.findViewById(R.id.tv_urgent_request_location);
        tvUrgentTime = (TextView) itemView.findViewById(R.id.tv_urgent_request_time);
        btnCall=(Button) itemView.findViewById(R.id.btn_call);
        btnChat=(Button) itemView.findViewById(R.id.btn_chat);
        btnInfo=(Button) itemView.findViewById(R.id.btn_info);
        btnLocation=(Button) itemView.findViewById(R.id.btn_location);
    }

    public void bindUrgentRequest(UrgentRequestData data, UrgentProgressItemClickListener listener){
        if (data.getWinchType()==null)
            tvUrgentType.setText(data.getType().getName());
        else {
            String types="";
            for (int i=0;i<data.getWinchType().size();i++){
                types=types+" "+data.getWinchType().get(i).getwName()+" ";
            }
            tvUrgentType.setText(data.getType().getName() + " - " + types);
        }
        tvCarInfo.setText(data.getBrand()+" - "+data.getModel()+" - "+data.getYear());
        tvAddress.setText(data.getDistance()+itemView.getContext().getString(R.string.km));
        tvUrgentTime.setText(data.getDate());
        btnCall.setOnClickListener(v -> listener.makeProgressAction(ConfigurationFile.Constants.BTN_CALL_ACTION,getAdapterPosition()));


        btnChat.setOnClickListener(v -> listener.makeProgressAction(ConfigurationFile.Constants.BTN_CHAT_ACTION,getAdapterPosition()));

        btnLocation.setOnClickListener(v -> listener.makeProgressAction(ConfigurationFile.Constants.BTN_LOCATION_ACTION,getAdapterPosition()));

        btnInfo.setOnClickListener(v -> listener.makeProgressAction(ConfigurationFile.Constants.BTN_INFO_ACTION,getAdapterPosition()));

        itemView.setOnClickListener(v -> listener.makeProgressAction(ConfigurationFile.Constants.COMPLETE_URGENT_REQUEST_CODE,getAdapterPosition()));

    }
}
