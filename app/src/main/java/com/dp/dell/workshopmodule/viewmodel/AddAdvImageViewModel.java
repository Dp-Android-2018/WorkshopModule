package com.dp.dell.workshopmodule.viewmodel;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableFloat;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.view.View;

import com.dp.dell.workshopmodule.BR;
import com.dp.dell.workshopmodule.model.request.AddAdvertisementRequest;
import com.dp.dell.workshopmodule.utils.ConfigurationFile;
import com.dp.dell.workshopmodule.utils.CustomUtils;
import com.dp.dell.workshopmodule.view.ui.activity.AddAdvertismentCountry;
import com.dp.dell.workshopmodule.view.ui.callback.BaseInterface;
import com.dp.dell.workshopmodule.view.ui.callback.TaskMonitor;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import static android.app.Activity.RESULT_OK;

public class AddAdvImageViewModel extends BaseObservable{

    private Context context;
    private BaseInterface callback;
    Uri selectedImageUri=null;
    private RxPermissions rxPermissions;
    private Bitmap picBitmap;
    private Bitmap selectedImageBitmap;
    public ObservableBoolean loading;

    public ObservableFloat progress;
    private String picUrl=null;
    private StorageReference storageReference;
    private AddAdvertisementRequest addAdvertisementRequest;
    public AddAdvImageViewModel(Context context,BaseInterface callback) {
        this.context=context;
        this.callback=callback;
        rxPermissions=new RxPermissions((Activity) context);
        loading=new ObservableBoolean(false);

        progress=new ObservableFloat(0.3f);
        storageReference= FirebaseStorage.getInstance().getReference().child("app_photos");
        getIntentExtra();
    }
//////////////////////Get Intent From Previous Act ///////////////////////////////////////////////////////////////////
    public void getIntentExtra(){
        addAdvertisementRequest=(AddAdvertisementRequest) (((Activity)context).getIntent().getSerializableExtra(ConfigurationFile.IntentConstants.ADD_ADV_OBJ));
    }
    //////////////////////Dialog To Choose Image From Camera Or Gallery /////////////////////////////
    public void displayDialog(View view) {
        callback.updateUi(ConfigurationFile.Constants.SHOW_DIALOG_CODE);}

    ///////////////////////////////////Set Image To Image View and Convert it to Base 54 ////////////////////////////////
    public void onActivityResult(int requestCode, int resultCode, Intent data, com.esafirm.imagepicker.model.Image image) {
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
        notifyPropertyChanged(BR.picBitmap);}


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
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////

    ///////////////////Ask For Permission if Sdk > lollipop //////////////////////////////////////////////
    public void askForPermission(int checker) {
        CustomUtils.getInstance().requirePermission(rxPermissions,checker,callback);
    }

    //////////////Upload Image To FireBase //////////////////////////////////////////////////////////
    public void uploadFireBasePic(){
        loading.set(true);
        CustomUtils.getInstance().uploadFireBasePic(storageReference, selectedImageUri, photoUrl -> {
            picUrl=photoUrl;
            progress.set(1.0f);
        });
    }


    public void validate(View view) {

        if (selectedImageUri!=null) {
            uploadFireBasePic();
        }else {
            moveTonextAct();
        } }

/////////////////////////cOMPLETING ANIMATION OF UPLOADING IMAGE ///////////////////////////////////
    public Animator.AnimatorListener onAnimationEnd(){
        return new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                System.out.println("Animation End :"+(progress.get()));
                if (progress.get()==1.0f){
                    progress.set(0.3f);
                    loading.set(false);
                    moveTonextAct();
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

    public void moveTonextAct(){
        addAdvertisementRequest.setImage(picUrl!=null? picUrl:null);
        Intent i=new Intent(context, AddAdvertismentCountry.class);
        i.putExtra(ConfigurationFile.IntentConstants.ADD_ADV_OBJ,addAdvertisementRequest);
        context.startActivity(i);
    }
}
