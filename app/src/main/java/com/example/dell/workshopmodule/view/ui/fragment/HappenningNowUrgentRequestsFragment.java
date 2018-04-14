package com.example.dell.workshopmodule.view.ui.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dell.workshopmodule.R;
import com.example.dell.workshopmodule.view.ui.adapter.AcceptedUrgentRequestsAdapter;
import com.example.dell.workshopmodule.view.ui.adapter.CurrentRequestsAdapter;
import com.example.dell.workshopmodule.view.ui.adapter.CurrentUrgentRequestsAdapter;
import com.example.dell.workshopmodule.view.ui.adapter.OfferSentAdapter;
import com.example.dell.workshopmodule.view.ui.dialog.UrgentRequestDialog;

/**
 * Created by DELL on 13/03/2018.
 */

public class HappenningNowUrgentRequestsFragment extends Fragment {
    private RecyclerView recyclerView,recyclerView2;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_happening_now_urgent_request_layout,container,false);
        recyclerView=(RecyclerView)v.findViewById(R.id.rv_current_urgent_requests);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerView.setAdapter(new CurrentUrgentRequestsAdapter());


        recyclerView2=(RecyclerView)v.findViewById(R.id.rv_accepted_urgent_requests);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
        recyclerView2.setAdapter(new AcceptedUrgentRequestsAdapter());

       /* UrgentRequestDialog urgentRequestDialog=new UrgentRequestDialog(getContext());
        urgentRequestDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        urgentRequestDialog.show();*/
        return v;
    }
}
