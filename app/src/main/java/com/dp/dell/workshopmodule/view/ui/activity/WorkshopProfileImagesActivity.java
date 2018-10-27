package com.dp.dell.workshopmodule.view.ui.activity;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.dp.dell.workshopmodule.R;
import com.dp.dell.workshopmodule.model.global.UserData;
import com.dp.dell.workshopmodule.utils.ConfigurationFile;
import com.dp.dell.workshopmodule.utils.SharedPrefrenceUtils;
import com.dp.dell.workshopmodule.view.ui.Application.MyApplication;
import com.dp.dell.workshopmodule.view.ui.adapter.WorkshopProfileImagesAdapter;

import java.util.ArrayList;

/**
 * Created by Bahaa Gabal on 20,October,2018
 */
public class WorkshopProfileImagesActivity extends AppCompatActivity {

    private RecyclerView mRecyclerViewWorkshopProfileImages;
    private Toolbar mToolbar;
    private WorkshopProfileImagesAdapter mWorkshopProfileImagesAdapter;
    private final int NUMBER_OF_RECYCLER_VIEW_COLOUMNS = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workshop_profile_images);
        initUi();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void initUi() {
        handleStatusBarColor();
        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.profile_pictures);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.whitebasecolor));
        mRecyclerViewWorkshopProfileImages = findViewById(R.id.recycler_view_workshop_images);
        mRecyclerViewWorkshopProfileImages.setLayoutManager(new GridLayoutManager(this, NUMBER_OF_RECYCLER_VIEW_COLOUMNS));
        mRecyclerViewWorkshopProfileImages.setHasFixedSize(true);
        mWorkshopProfileImagesAdapter = new WorkshopProfileImagesAdapter(this, getWorkshopProfileImages());
        mRecyclerViewWorkshopProfileImages.setAdapter(mWorkshopProfileImagesAdapter);
    }

    public ArrayList<String> getWorkshopProfileImages() {
        SharedPrefrenceUtils pref = MyApplication.getSharedprefUtilsComponent().getSharedPrefUtils();
        UserData userData = (UserData) pref.getSavedObject(ConfigurationFile.SharedPrefConstants.PREF_WORKSHOP_DATA, UserData.class);
        return userData.getWorkshopImageProfile();
    }

    public void handleStatusBarColor() {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.orangecolor));
        }
    }
}
