package com.findandfix.workshop.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.findandfix.workshop.R;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ReturnMode;
import com.esafirm.imagepicker.model.Image;
import com.findandfix.workshop.model.request.RegisterRequest;
import com.findandfix.workshop.ui.adapter.WorkShopProfileAdapter;
import com.findandfix.workshop.utils.ConfigurationFile;
import com.findandfix.workshop.utils.CustomUtils;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bahaa Gabal on 13,October,2018
 */
public class WorkshopProfileActivity extends AppCompatActivity {

    private RecyclerView recyclerViewWorkshopImages;
    private RelativeLayout relativeLayoutChooseImages;
    private TextView tvNextStep;
    private WorkShopProfileAdapter mWorkShopProfileAdapter;
    private List<Image> images;
    private List<Uri> imagesUri;
    private static final int NUMBER_OF_WORKSHOP_PROFILE_IMAGES = 10;
    private StorageReference mFirebaseStorageReference;
    private List<String> storageImagesUrl;
    private RegisterRequest registerRequest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workshop_profile_layout);
        images = new ArrayList<>();
        imagesUri = new ArrayList<>();
        storageImagesUrl = new ArrayList<>();
        mFirebaseStorageReference = FirebaseStorage.getInstance().getReference().child("app_photos");
        registerRequest=(RegisterRequest) getIntent().getSerializableExtra(ConfigurationFile.SharedPrefConstants.PREF_REGISTER_OBJECT);
        initUi();
        initRecyclerViewAdapter();
    }

    public void initUi() {
        recyclerViewWorkshopImages = (RecyclerView) findViewById(R.id.rv_workshop_profile_images);
        relativeLayoutChooseImages = (RelativeLayout) findViewById(R.id.rl_choose_image);
        tvNextStep = (TextView) findViewById(R.id.tv_specialization_next_step);
        tvNextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomUtils.getInstance().showProgressDialog(WorkshopProfileActivity.this);
                uploadImagesToFirebase();
            }
        });

        relativeLayoutChooseImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mWorkShopProfileAdapter.getItemCount() < NUMBER_OF_WORKSHOP_PROFILE_IMAGES) {
                    pickImagesFromGallery();
                } else {
                    Snackbar.make(findViewById(R.id.rl_parent), getString(R.string.maximum_images_number_text), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    public void initRecyclerViewAdapter() {
        mWorkShopProfileAdapter = new WorkShopProfileAdapter(getApplicationContext(), images);
        recyclerViewWorkshopImages.setAdapter(mWorkShopProfileAdapter);
    }

    public void uploadImagesToFirebase() {
        for (int i = 0; i < imagesUri.size(); i++) {
            UploadTask photoRef = mFirebaseStorageReference.child(imagesUri.get(i).getLastPathSegment()).putFile(imagesUri.get(i));
            photoRef.addOnSuccessListener(taskSnapshot -> {
                Uri photoUrl = taskSnapshot.getDownloadUrl();
                storageImagesUrl.add(photoUrl.toString());
                if (storageImagesUrl.size() == imagesUri.size()) {
                    CustomUtils.getInstance().cancelDialog();
                    registerRequest.setWorkshopProfileImages(storageImagesUrl);
                    Intent intent = new Intent(this , SocialNetworkActivity.class);
                    intent.putExtra(ConfigurationFile.SharedPrefConstants.PREF_REGISTER_OBJECT, registerRequest);
                    startActivity(intent);
                }

            });
        }

    }

    public void pickImagesFromGallery() {
        ImagePicker imagePicker = ImagePicker.create(this)
                .multi()
                .limit(10 - mWorkShopProfileAdapter.getItemCount())
                .language("in")
                .theme(R.style.ImagePickerTheme)
                .returnMode(ReturnMode.NONE)
                .folderMode(false)
                .includeVideo(false)
                .toolbarArrowColor(Color.RED)
                .toolbarFolderTitle("Folder")
                .toolbarImageTitle("Tap to select")
                .toolbarDoneButtonText("DONE");

        imagePicker// max images can be selected (99 by default)
                .imageFullDirectory(Environment.getExternalStorageDirectory().getPath()) // can be full path
                .start(); //
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            if (resultCode == RESULT_OK) {
                images.addAll(ImagePicker.getImages(data));
                mWorkShopProfileAdapter.notifyDataSetChanged();
                for (Image image : ImagePicker.getImages(data)) {
                    imagesUri.add(Uri.fromFile(new File(image.getPath())));
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
