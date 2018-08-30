package com.dp.dell.workshopmodule.utils;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.databinding.BaseObservable;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.view.Window;
import android.widget.TimePicker;


import com.dp.dell.workshopmodule.R;
import com.dp.dell.workshopmodule.model.global.BaseModel;
import com.dp.dell.workshopmodule.model.global.BrandItem;
import com.dp.dell.workshopmodule.model.global.UserData;
import com.dp.dell.workshopmodule.view.ui.Application.MyApplication;
import com.dp.dell.workshopmodule.view.ui.activity.LoginActivity;
import com.dp.dell.workshopmodule.view.ui.callback.BaseInterface;
import com.dp.dell.workshopmodule.view.ui.callback.TaskMonitor;
import com.dp.dell.workshopmodule.view.ui.callback.UpdateTimeListener;
import com.dp.dell.workshopmodule.viewmodel.AddAchievmentImageViewModel;
import com.dp.dell.workshopmodule.viewmodel.AddAdvImageViewModel;
import com.dp.dell.workshopmodule.viewmodel.ChatViewModel;
import com.dp.dell.workshopmodule.viewmodel.FinalStep.FinalStepRegisterViewModel;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by DELL on 28/03/2018.
 */

public class CustomUtils {

    private static  CustomUtils customUtils=null;
    private static String selectedTime;
    private Dialog dialog=null;
    private CustomUtils(){}
    public static CustomUtils getInstance(){
        if(customUtils==null)
            customUtils=new CustomUtils();

        return customUtils;
    }


    public void requireRecordSoundPermission(RxPermissions rxPermissions,BaseInterface callback){
        rxPermissions
                .request(Manifest.permission.RECORD_AUDIO,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) { // Always true pre-M

                        callback.updateUi(ConfigurationFile.Constants.PERMISSION_GRANTED_RECORD_AUDIO);

                    } else {
                        // Oups permission denied
                        callback.updateUi(ConfigurationFile.Constants.PERMISSION_DENIED_RECORD_AUDIO);
                    }
                });
    }

    public  void showTimePickerDialog(Context context, final UpdateTimeListener listener, final int code){

        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(context, (timePicker, selectedHour, selectedMinute) -> {
            selectedTime=(selectedHour < 10 ? "0" + selectedHour : selectedHour) + ":" + (selectedMinute < 10 ? "0" + selectedMinute : selectedMinute);
            listener.updateTime(selectedTime,code);
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle(context.getString(R.string.select_time));
        mTimePicker.show();

    }

    public  String encodeImage(Bitmap bm)
    {
        int nh = (int) (bm.getHeight() * (512.0 / bm.getWidth()));
        bm = Bitmap.createScaledBitmap(bm, 512, nh, true);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);
        return encImage;
    }

    public  Bitmap modifyOrientation(Bitmap bitmap, String image_absolute_path) throws IOException {
        ExifInterface ei = new ExifInterface(image_absolute_path);
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotate(bitmap, 90);

            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotate(bitmap, 180);

            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotate(bitmap, 270);

            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                return flip(bitmap, true, false);

            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                return flip(bitmap, false, true);

            default:
                return bitmap;
        }
    }

    public  Bitmap rotate(Bitmap bitmap, float degrees) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public  Bitmap flip(Bitmap bitmap, boolean horizontal, boolean vertical) {
        Matrix matrix = new Matrix();
        matrix.preScale(horizontal ? -1 : 1, vertical ? -1 : 1);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public  String uriToFilename(Uri uri, Context context) {

        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        String picturePath="";
        if (uri != null) {
            Cursor cursor = context.getContentResolver().query(uri, filePathColumn, null, null, null);
            assert cursor != null;
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
        }
        return picturePath;
    }



    public  boolean checkIfAlreadyhavePermission(Context context,String permission) {
        if (ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    public  void requestForSpecificPermission(Context context,String[]permissions,int requestCode) {
        ActivityCompat.requestPermissions((Activity)context, permissions, requestCode);
    }

    public void uploadFireBasePic(StorageReference storageReference, Uri selectedImageUri , TaskMonitor callback){

        final StorageReference photoRef=storageReference.child(selectedImageUri.getLastPathSegment());
        photoRef.putFile(selectedImageUri).addOnSuccessListener(taskSnapshot -> {
            Uri photourl=taskSnapshot.getDownloadUrl();
            callback.taskCompleted(photourl.toString());});
    }

    public void requirePermission(RxPermissions rxPermissions, int checker, BaseInterface callback){
        rxPermissions
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA)
                .subscribe(granted -> {
                    if (granted) { // Always true pre-M
                        if (checker == 0)
                            callback.updateUi(ConfigurationFile.Constants.PERMISSION_GRANTED_CAMERA);
                        else
                            callback.updateUi(ConfigurationFile.Constants.PERMISSION_GRANTED_GALLERY);

                    } else {
                        // Oups permission denied
                        callback.updateUi(ConfigurationFile.Constants.PERMISSION_DENIED);
                    }
                });
    }

    public void requireLocationPermission(RxPermissions rxPermissions, BaseInterface callback){
        rxPermissions
                .request(Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe(granted -> {
                    if (granted) { // Always true pre-M

                            callback.updateUi(ConfigurationFile.Constants.PERMISSION_GRANTED_LOCATION);

                    } else {
                        // Oups permission denied
                        callback.updateUi(ConfigurationFile.Constants.PERMISSION_DENIED);
                    }
                });
    }


    public void requirePhonePermission(RxPermissions rxPermissions,BaseInterface callback){
        rxPermissions
                .request(Manifest.permission.CALL_PHONE)
                .subscribe(granted -> {
                    if (granted) { // Always true pre-M

                        callback.updateUi(ConfigurationFile.Constants.PERMISSION_GRANTED_PHONE_CALL);

                    } else {
                        // Oups permission denied
                        callback.updateUi(ConfigurationFile.Constants.PERMISSION_DENIED_PHONE_CALL);
                    }
                });
    }


    public UserData getSaveUserObject(Context context){
        SharedPrefrenceUtils prefrenceUtils=new SharedPrefrenceUtils(context);
        UserData userData=(UserData) prefrenceUtils.getSavedObject(ConfigurationFile.SharedPrefConstants.PREF_WORKSHOP_DATA, UserData.class);
        return userData;
    }


    public void showDialog(Context context, final BaseObservable viewModel){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose Pictures")
                .setItems(R.array.media, (dialog, which) -> {
                    if (viewModel instanceof ChatViewModel)
                    ((ChatViewModel)viewModel).askForPermission(which);

                    else if (viewModel instanceof FinalStepRegisterViewModel)
                        ((FinalStepRegisterViewModel)viewModel).askForPermission(which);

                    else if (viewModel instanceof AddAdvImageViewModel)
                        ((AddAdvImageViewModel)viewModel).askForPermission(which);

                    else if (viewModel instanceof AddAchievmentImageViewModel)
                        ((AddAchievmentImageViewModel)viewModel).askForPermission(which);
                });

        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }

    public void startPlacePicker(Activity activity){
        try {
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            activity.startActivityForResult(builder.build(activity),
                    ConfigurationFile.Constants.PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    public boolean getDarkModeVal(Context context){
        SharedPrefrenceUtils sharedPrefrenceUtils=new SharedPrefrenceUtils(context);
        return sharedPrefrenceUtils.getBooleanFromSharedPrederances(ConfigurationFile.SharedPrefConstants.DARK_MODE_PARAM);
    }

    public Boolean isValidMobileNumber(String s){
        Pattern p = Pattern.compile("(0/1)?[0-9]{9}");

        // Pattern class contains matcher() method
        // to find matching between given number
        // and regular expression
        Matcher m = p.matcher(s);
        return (m.find() && m.group().equals(s));
    }

    public void logout(Activity activity){
       /* String lang=CustomUtils.getInstance().getAppLanguage(activity);

        Intent i=new Intent(activity, LoginActivity.class);
        activity.finishAffinity();
        clearSharedPref((Context)activity);
        CustomUtils.getInstance().saveAppLanguage(activity,lang);
        activity.startActivity(i);*/

            SharedPrefrenceUtils utils = new SharedPrefrenceUtils(activity);
            String lang = utils.getStringFromSharedPrederances(ConfigurationFile.SharedPrefConstants.APP_LANGUAGE);
            utils.clearToken();
            saveAppLanguage(activity, lang);
            Intent i = new Intent(activity, LoginActivity.class);
            activity.startActivity(i);
            activity.finish();
            activity.finishAffinity();

    }

    public void clearSharedPref(Context context){
        SharedPrefrenceUtils prefrenceUtils=new SharedPrefrenceUtils(context);
        prefrenceUtils.clearToken();
    }

    public String setSpecializationText(){
        String specializationText="";

        List<BaseModel> baseModels=((MyApplication)MyApplication.getAppContext()).getBasicspecializations();
        for(BaseModel model:baseModels)
            specializationText=specializationText+model.getName()+" - ";
        return specializationText;
    }


    public String setBrandsText(){
        String brandsText="";

        List<BrandItem> baseModels=((MyApplication)MyApplication.getAppContext()).getBasicBrands();
        for(BrandItem brandItem:baseModels)
            brandsText=brandsText+brandItem.getName()+" - ";
        return brandsText;
    }

    public String setUrgentText(){
        String urgentText="";
        List<BaseModel> baseModels=((MyApplication)MyApplication.getAppContext()).getBasicUrgentTypes();
        for(BaseModel model:baseModels)
            urgentText=urgentText+model.getName()+" - ";
        return urgentText;
    }


    public List<Integer> getSpecializationIds(){

        List<Integer>ids=new ArrayList();
        List<BaseModel> baseModels=((MyApplication)MyApplication.getAppContext()).getBasicspecializations();
        for(BaseModel model:baseModels)
            ids.add(model.getId());

        return ids;
    }


    public List<Integer> getBrandsIds(){

        List<Integer>ids=new ArrayList();
        List<BrandItem> baseModels=((MyApplication)MyApplication.getAppContext()).getBasicBrands();
        for(BrandItem brandItem:baseModels)
            ids.add(brandItem.getId());
        return ids;
    }

    public List<Integer> getUrgentIds(){

        List<Integer>ids=new ArrayList();
        List<BaseModel> baseModels=((MyApplication)MyApplication.getAppContext()).getBasicUrgentTypes();
        for(BaseModel model:baseModels)
            ids.add(model.getId());
        return ids;
    }

    public void endSession(Activity activity){

            SharedPrefrenceUtils utils = new SharedPrefrenceUtils(activity);
            String lang = utils.getStringFromSharedPrederances(ConfigurationFile.SharedPrefConstants.APP_LANGUAGE);
            utils.clearToken();
            saveAppLanguage(activity, lang);
            Intent i = new Intent(activity, LoginActivity.class);
            activity.startActivity(i);
            activity.finish();
            activity.finishAffinity();

    }


    public boolean checktimings(String time, String endtime) {

        String pattern = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        try {
            Date date1 = sdf.parse(time);
            Date date2 = sdf.parse(endtime);

            if(date1.before(date2)) {
                return true;
            } else {

                return false;
            }
        } catch (ParseException e){
            e.printStackTrace();
        }
        return false;
    }

    public void openCamera(Activity activity){
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        activity.startActivityForResult(cameraIntent, ConfigurationFile.Constants.CAMERA_REQUEST);
    }

    public void openGallery(Activity activity){
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(pickPhoto , ConfigurationFile.Constants.GALLERY_REQUEST);
    }

    public String firstCharacters(String name){
        String[] splited = name.split("\\s+");
        String workshoptitle="";
        for (int i=0;i<splited.length;i++)
            workshoptitle=workshoptitle+splited[i].toUpperCase().charAt(0);

        return workshoptitle;
    }


    public void showProgressDialog(Context activity){
        if (dialog==null || !dialog.isShowing()) {
            dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.custom_dialog_layout);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.setCancelable(false);
            dialog.show();
        }

    }

    public String getAppLanguage(Context context){
        SharedPrefrenceUtils sharedPrefrenceUtils=new SharedPrefrenceUtils(context);
        String lang=sharedPrefrenceUtils.getStringFromSharedPrederances(ConfigurationFile.SharedPrefConstants.APP_LANGUAGE);
        return lang;
    }

    public void saveAppLanguage(Context context,String lang){
        SharedPrefrenceUtils sharedPrefrenceUtils=new SharedPrefrenceUtils(context);
        sharedPrefrenceUtils.addStringToSharedPrederances(ConfigurationFile.SharedPrefConstants.APP_LANGUAGE,lang);
    }

    public void cancelDialog(){
        dialog.dismiss();
    }
}
