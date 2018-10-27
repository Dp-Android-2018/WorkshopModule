package com.dp.dell.workshopmodule.viewmodel.FinalStep;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableFloat;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;


import com.dp.dell.workshopmodule.R;
import com.dp.dell.workshopmodule.BR;
import com.dp.dell.workshopmodule.model.request.RegisterRequest;
import com.dp.dell.workshopmodule.utils.ConfigurationFile;
import com.dp.dell.workshopmodule.utils.CustomUtils;
import com.dp.dell.workshopmodule.view.ui.activity.SocialNetworkActivity;
import com.dp.dell.workshopmodule.view.ui.callback.BaseInterface;
import com.dp.dell.workshopmodule.view.ui.callback.TaskMonitor;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import butterknife.internal.Utils;

import static android.app.Activity.RESULT_OK;

/**
 * Created by DELL on 26/03/2018.
 */

public class FinalStepRegisterViewModel extends BaseObservable {

    private Context context;
    private BaseInterface callback;
    private Bitmap picBitmap;
    private int checker;
    String encodedImage = "";
    private int step = 1;
    private ArrayList<String> papers;
    private ArrayList<Bitmap> bitmaps;
    private Bitmap selectedImageBitmap;
    private RegisterRequest registerRequest;
    private HandleRegisterRequestViewModel registerRequestViewModel;
    StorageReference storageReference;
    private String picUrl = null;
    public ObservableBoolean loading;
    Uri selectedImageUri = null;
    public ObservableFloat progress;
    private RxPermissions rxPermissions;
    public ObservableField<String> messageText = new ObservableField<>();

    public FinalStepRegisterViewModel(Context context, BaseInterface callback, RegisterRequest registerRequest) {
        this.context = context;
        this.callback = callback;
        this.registerRequest = registerRequest;
        rxPermissions = new RxPermissions((Activity) context);
        this.registerRequestViewModel = registerRequestViewModel;
        papers = new ArrayList<>();
        bitmaps = new ArrayList<>();
        loading = new ObservableBoolean(false);
        progress = new ObservableFloat(0.3f);
        storageReference = FirebaseStorage.getInstance().getReference().child("app_photos");
        messageText.set(context.getResources().getString(R.string.upload_commercial_register));

    }

    //////////////////////Dialog To Choose Image From Camera Or Gallery /////////////////////////////
    public void displayDialog(View view) {
        callback.updateUi(ConfigurationFile.Constants.SHOW_DIALOG_CODE);
    }

    ///////////////////////////////////Set Image To Image View and Convert it to Base 54 ////////////////////////////////
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onActivityResult(int requestCode, int resultCode, Intent data,Image image) {
        if (resultCode == RESULT_OK) {
                selectedImageBitmap = BitmapFactory.decodeFile(image.getPath());
                setPicBitmap(selectedImageBitmap);
                selectedImageUri = Uri.fromFile(new File(image.getPath()));
        }
    }

    //////////////////////////////////Update IMage View //////////////////////////////////////////
    public void setPicBitmap(Bitmap picBitmap) {
        System.out.println("Bitmap Conversion ");
        this.picBitmap = picBitmap;
        notifyPropertyChanged(BR.picBitmap);
    }


    @Bindable
    public Bitmap getPicBitmap() {
        return picBitmap;
    }
    //////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////Converting Image To Base64////////////////////////////////////////

    public void convertImageToBase64(Uri ImageUri) {
        try {
            final InputStream imageStream = context.getContentResolver().openInputStream(ImageUri);
            selectedImageBitmap = BitmapFactory.decodeStream(imageStream);
            String RealPicturePath = CustomUtils.getInstance().uriToFilename(ImageUri, context);
            selectedImageBitmap = CustomUtils.getInstance().modifyOrientation(selectedImageBitmap, RealPicturePath);
            setPicBitmap(selectedImageBitmap);
            encodedImage = CustomUtils.getInstance().encodeImage(selectedImageBitmap);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////

    ///////////////////Ask For Permission if Sdk > lollipop //////////////////////////////////////////////
    public void askForPermission(int checker) {
        CustomUtils.getInstance().requirePermission(rxPermissions, checker, callback);
    }


    /////////////////////////////Move To next Step //////////////////////////////////////////////////////////////////
    public void validate(View view) {


        if (selectedImageUri != null) {
            System.out.println("Image Data 1:" + selectedImageUri.toString());
            uploadFireBasePic();
        } else if (selectedImageUri == null && picUrl != null) {
            System.out.println("Image Data 1 2 3:" + selectedImageUri.toString());
            picUrl = null;
            setPicBitmap(null);
            setStep(2);
            messageText.set(context.getString(R.string.please_upload_id));

        } else {
            callback.updateUi(ConfigurationFile.Constants.CHOOSE_IMAGE_FROM_GALLERY);
        }
    }


    public void uploadFireBasePic() {
        loading.set(true);
        CustomUtils.getInstance().uploadFireBasePic(storageReference, selectedImageUri, photoUrl -> {
            picUrl = photoUrl;
            progress.set(1.0f);
        });
    }

    ///////////////////////////////Reset Data Of First View ////////////////////////////////////////////////////////////
    public void reset() {
        bitmaps.clear();
        papers.clear();
        bitmaps.add(selectedImageBitmap);
        papers.add(picUrl);
        encodedImage = "";
        selectedImageUri = null;
        picUrl = null;
        setPicBitmap(null);
        setStep(2);
        messageText.set(context.getString(R.string.please_upload_id));
    }

    @Bindable
    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
        notifyPropertyChanged(BR.step);
    }

    //////////////////////////////////////////Handle Back Action ////////////////////////////////////////////////////////////////////
    public void handleBackAction(View view) {
        if (step == 1) {
            if (!papers.isEmpty()) papers.remove(0);
            callback.updateUi(ConfigurationFile.Constants.MOVE_TO_PREVIOUS_ACTIVITY);
        } else if (step == 2) {
            if (papers.size() == 2)
                papers.remove(1);
            selectedImageBitmap = bitmaps.get(0);
            encodedImage = papers.get(0);
            setPicBitmap(bitmaps.get(0));
            picUrl = papers.get(0);
            setStep(1);
            messageText.set(context.getString(R.string.upload_commercial_register));
        }
    }


    public Animator.AnimatorListener onAnimationEnd() {
        return new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                System.out.println("Animation End :" + (progress.get()));
                if (progress.get() == 1.0f) {
                    progress.set(0.3f);
                    if (step == 1) {
                        reset();
                    } else if (step == 2) {
                        papers.add(picUrl);
                        registerRequest.setPapers(papers);
                        callback.updateUi(ConfigurationFile.Constants.MOVE_TO_NEXT_ACTIVITY);
                        //registerRequestViewModel.handleRegister(registerRequest,papers,callback);
                    }
                    loading.set(false);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        };
    }

}
