package com.example.dell.workshopmodule.view.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.dell.workshopmodule.R;
import com.example.dell.workshopmodule.view.ui.dialog.AdvertismentRangeDialog;
import com.example.dell.workshopmodule.view.ui.dialog.UrgentRequestDialog;

/**
 * Created by DELL on 13/03/2018.
 */

public class AddAdvertismentRange extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_advertisment_range_layout);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView tv=(TextView)toolbar.findViewById(R.id.tv_toolbar_title);
        tv.setText(R.string.add_advertisment);
        toolbar.setBackgroundColor(getResources().getColor(R.color.advertisment_toolbar_color));
        if(Build.VERSION.SDK_INT>=21){
            Window window=this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getApplicationContext().getResources().getColor(R.color.advertisment_toolbar_color));
        }


        Button b= (Button)findViewById(R.id.btn_next);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent i=new Intent(AddAdvertismentRange.this,AdvertismentPublished.class);
                startActivity(i);
            }
        });
    }

}

