package com.example.dell.workshopmodule.view.ui.fragment;

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
import com.example.dell.workshopmodule.view.ui.adapter.CompletedRequestsAdapter;
import com.example.dell.workshopmodule.view.ui.adapter.CompletedUrgentRequestAdapter;

/**
 * Created by DELL on 13/03/2018.
 */

public class CompletedUrgentRequestFragment extends Fragment {
    private RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_completed_urgent_requests_layout,container,false);
        recyclerView=(RecyclerView)v.findViewById(R.id.rv_urgent_completed_requests);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setAdapter(new CompletedUrgentRequestAdapter());
        return v;
    }
}
