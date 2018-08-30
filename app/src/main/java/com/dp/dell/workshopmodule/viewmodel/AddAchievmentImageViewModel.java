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
import android.net.Uri;
import android.view.View;

import com.dp.dell.workshopmodule.BR;
import com.dp.dell.workshopmodule.model.global.UserData;
import com.dp.dell.workshopmodule.model.request.AddAchievmentRequest;
import com.dp.dell.workshopmodule.model.response.DefaultResponse;
import com.dp.dell.workshopmodule.network.ApiClient;
import com.dp.dell.workshopmodule.network.EndPoints;
import com.dp.dell.workshopmodule.utils.ConfigurationFile;
import com.dp.dell.workshopmodule.utils.CustomUtils;
import com.dp.dell.workshopmodule.utils.NetWorkConnection;
import com.dp.dell.workshopmodule.view.ui.activity.AddAchievmentAfterFixing;
import com.dp.dell.workshopmodule.view.ui.callback.BaseInterface;
import com.dp.dell.workshopmodule.view.ui.callback.TaskMonitor;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class AddAchievmentImageViewModel extends BaseObservable {
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
    private AddAchievmentRequest addAchievmentRequest;
    public ObservableBoolean visibility;
    private int checker;
    private UserData userData=null;
    public AddAchievmentImageViewModel(Context context,BaseInterface callback,int checker) {
        this.context=context;
        this.callback=callback;
        this.checker=checker;
        rxPermissions=new RxPermissions((Activity) context);
        visibility=new ObservableBoolean(false);
        loading=new ObservableBoolean(false);
        userData=CustomUtils.getInstance().getSaveUserObject(context);
        progress=new ObservableFloat(0.3f);
        storageReference= FirebaseStorage.getInstance().getReference().child("app_photos");
        getIntentExtra();
    }

    public void getIntentExtra(){
        addAchievmentRequest=(AddAchievmentRequest) (((Activity)context).getIntent().getSerializableExtra(ConfigurationFile.IntentConstants.ADD_ACHIEVMENT_OBJ));
    }
    //////////////////////Dialog To Choose Image From Camera Or Gallery /////////////////////////////
    public void displayDialog(View view) {
        callback.updateUi(ConfigurationFile.Constants.SHOW_DIALOG_CODE);}

    ///////////////////////////////////Set Image To Image View and Convert it to Base 54 ////////////////////////////////
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("Image View Modle");

        if (resultCode == RESULT_OK) {
            convertImageToBase64(data.getData());
            selectedImageUri=data.getData();}}

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
           callback.updateUi(ConfigurationFile.Constants.FILL_ALL_DATTA);
        } }


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
        if (checker==1) {
            addAchievmentRequest.setBeforeImage(picUrl != null ? picUrl : null);
            Intent i = new Intent(context, AddAchievmentAfterFixing.class);
            i.putExtra(ConfigurationFile.IntentConstants.ADD_ACHIEVMENT_OBJ, addAchievmentRequest);
            context.startActivity(i);
        }else if (checker==2){
            addAchievmentRequest.setAfterImage(picUrl != null ? picUrl : null);
            addAchievment();
        }
    }


    public void addAchievment(){
        if(NetWorkConnection.isConnectingToInternet(context)) {

         //   visibility.set(true);
            CustomUtils.getInstance().showProgressDialog((Activity)context);
            ApiClient.getClient().create(EndPoints.class).addAchievment(ConfigurationFile.Constants.API_KEY, CustomUtils.getInstance().getAppLanguage(context), ConfigurationFile.Constants.Content_Type, ConfigurationFile.Constants.Content_Type, "Bearer " + userData.getToken(),addAchievmentRequest)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(defaultResponseResponse -> {
                        CustomUtils.getInstance().cancelDialog();
                      //  visibility.set(false);
                        System.out.println("Add Achievment Code Code :"+defaultResponseResponse.code());

                        if (defaultResponseResponse.code() == ConfigurationFile.Constants.ADV_ADDED_SUCCESSFULLY) {
                           callback.updateUi(ConfigurationFile.Constants.SUCCESS_CODE);
                        } else if (defaultResponseResponse.code() == ConfigurationFile.Constants.CANT_COMPLETE_REQUEST_CODE) {
                            callback.updateUi(ConfigurationFile.Constants.CANT_COMPLETE_REQUEST_CODE);
                        }else if (defaultResponseResponse.code() == ConfigurationFile.Constants.UNAUTHENTICATED_CODE ||
                                defaultResponseResponse.code() == ConfigurationFile.Constants.UNSUBSCRIBE_CODE) {
                            callback.updateUi(ConfigurationFile.Constants.UNAUTHENTICATED_CODE);
                        }
                    }, throwable -> {
                        CustomUtils.getInstance().cancelDialog();
                       // visibility.set(false);
                        System.out.println("Add Achievment Ex :" + throwable.getMessage());
                    });
        }else {
            callback.updateUi(ConfigurationFile.Constants.NO_INTERNET_CONNECTION_CODE);
        }
    }
}
