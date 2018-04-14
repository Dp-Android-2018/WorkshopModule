package com.example.dell.workshopmodule.view.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.dell.workshopmodule.R;
import com.example.dell.workshopmodule.view.ui.adapter.MyAdvertisementAdapter;

/**
 * Created by DELL on 14/03/2018.
 */

public class MyAdvertisementActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_advertisement_layout);

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView tv=(TextView)toolbar.findViewById(R.id.tv_toolbar_title);
        tv.setText(R.string.my_advertisement);
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorBlueLight));

        recyclerView=(RecyclerView)findViewById(R.id.rv_my_advertisment);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdvertisementAdapter());
    }
}
