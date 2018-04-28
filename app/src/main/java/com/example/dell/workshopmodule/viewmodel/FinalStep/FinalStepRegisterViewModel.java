package com.example.dell.workshopmodule.viewmodel.FinalStep;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.View;

import com.example.dell.workshopmodule.BR;
import com.example.dell.workshopmodule.R;
import com.example.dell.workshopmodule.model.request.RegisterRequest;
import com.example.dell.workshopmodule.utils.ConfigurationFile;
import com.example.dell.workshopmodule.utils.CustomUtils;
import com.example.dell.workshopmodule.utils.ValidationUtils;
import com.example.dell.workshopmodule.view.ui.callback.BaseInterface;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

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
    private String picUrl=null;
    Uri selectedImageUri=null;
    public FinalStepRegisterViewModel(Context context, BaseInterface callback, RegisterRequest registerRequest,HandleRegisterRequestViewModel registerRequestViewModel) {
        this.context = context;
        this.callback = callback;
        this.registerRequest=registerRequest;
        this.registerRequestViewModel=registerRequestViewModel;
        setPicBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_find_fix_logo));
        papers = new ArrayList<>();
        bitmaps = new ArrayList<>();
        storageReference= FirebaseStorage.getInstance().getReference().child("app_photos");
    }

    //////////////////////Dialog To Choose Image From Camera Or Gallery /////////////////////////////
    public void displayDialog(View view) {
        callback.updateUi(ConfigurationFile.Constants.SHOW_DIALOG_CODE);
    }

    ///////////////////////////////////Set Image To Image View and Convert it to Base 54 ////////////////////////////////
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("Image View Modle");

        if (resultCode == RESULT_OK) {
            convertImageToBase64(data.getData());
            selectedImageUri=data.getData();
           /* final StorageReference photoRef=storageReference.child(selectedImageUri.getLastPathSegment());
            photoRef.putFile(selectedImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri photourl=taskSnapshot.getDownloadUrl();
                    System.out.println("Firebase Url :"+photourl.toString());
                    picUrl=photourl.toString();
                }
            });*/
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
        this.checker = checker;
        String[] permissions = {ConfigurationFile.PermissionsClass.WRITE_EXTERNAL_STORAGE};
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (!CustomUtils.getInstance().checkIfAlreadyhavePermission(context, ConfigurationFile.PermissionsClass.WRITE_EXTERNAL_STORAGE)) {
                CustomUtils.getInstance().requestForSpecificPermission(context, permissions, ConfigurationFile.PermissionsClass.REQUEST_CODE_WRITE_EXTERNAL_STORAGE);
            } else {
                openMedia(checker);
            }
        } else {
            openMedia(checker);
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case ConfigurationFile.PermissionsClass.REQUEST_CODE_WRITE_EXTERNAL_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openMedia(checker);
                } else {
                    callback.updateUi(ConfigurationFile.Constants.PERMISSION_DENIED);
                }
                break;

            default:
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////Check If Permission Denied Or Granted //////////////////////////////////////////////
    public void openMedia(int checker) {
        if (checker == 0)
            callback.updateUi(ConfigurationFile.Constants.PERMISSION_GRANTED_CAMERA);
        else
            callback.updateUi(ConfigurationFile.Constants.PERMISSION_GRANTED_GALLERY);
    }

    /////////////////////////////Move To next Step //////////////////////////////////////////////////////////////////
    public void validate(View view) {
        if (selectedImageUri!=null && picUrl ==null) {
            uploadFireBasePic();

        }else if (selectedImageUri ==null && picUrl !=null){
            picUrl=null;
            setPicBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_find_fix_logo));
            setStep(2);
        }else {
            callback.updateUi(ConfigurationFile.Constants.CHOOSE_IMAGE_FROM_GALLERY);
        }
    }


    public void uploadFireBasePic(){
        final StorageReference photoRef=storageReference.child(selectedImageUri.getLastPathSegment());
        photoRef.putFile(selectedImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri photourl=taskSnapshot.getDownloadUrl();
                System.out.println("Firebase Url :"+photourl.toString());
                picUrl=photourl.toString();
                if (step == 1) {
                    reset();
                } else if (step == 2) {
                    papers.add(picUrl);
                    registerRequestViewModel.handleRegister(registerRequest,papers,callback);
                }
            }
        });
    }

    ///////////////////////////////Reset Data Of First View ////////////////////////////////////////////////////////////
    public void reset() {
        bitmaps.clear();
        papers.clear();
        bitmaps.add(selectedImageBitmap);
        papers.add(picUrl);
        encodedImage = "";
        selectedImageUri=null;
        picUrl=null;
        setPicBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_find_fix_logo));
        setStep(2);
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
            if(!papers.isEmpty()) papers.remove(0);
            callback.updateUi(ConfigurationFile.Constants.MOVE_TO_PREVIOUS_ACTIVITY);
        } else if (step == 2) {
            if(papers.size()==2) papers.remove(1);
            selectedImageBitmap = bitmaps.get(0);
            encodedImage = papers.get(0);
            setPicBitmap(bitmaps.get(0));
            picUrl=papers.get(0);
            setStep(1);
        }
    }

}
