package com.findandfix.workshop.utils;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.databinding.BaseObservable;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.view.Window;

import com.findandfix.workshop.R;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ReturnMode;
import com.findandfix.workshop.model.global.BaseModel;
import com.findandfix.workshop.model.global.BrandItem;
import com.findandfix.workshop.model.global.UserData;
import com.findandfix.workshop.ui.Application.MyApplication;
import com.findandfix.workshop.ui.activity.LoginActivity;
import com.findandfix.workshop.ui.callback.BaseInterface;
import com.findandfix.workshop.ui.callback.TaskMonitor;
import com.findandfix.workshop.ui.callback.UpdateTimeListener;
import com.findandfix.workshop.viewmodel.AddAchievmentImageViewModel;
import com.findandfix.workshop.viewmodel.AddAdvImageViewModel;
import com.findandfix.workshop.viewmodel.ChatViewModel;
import com.findandfix.workshop.viewmodel.FinalStep.FinalStepRegisterViewModel;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.storage.StorageReference;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by DELL on 28/03/2018.
 */

public class CustomUtils {

    private static CustomUtils customUtils = null;
    private static String selectedTime;
    public static String imageFilePath = "";
    private Dialog dialog = null;

    private CustomUtils() {
    }

    public static CustomUtils getInstance() {
        if (customUtils == null)
            customUtils = new CustomUtils();

        return customUtils;
    }


    public void requireRecordSoundPermission(RxPermissions rxPermissions, BaseInterface callback) {
        rxPermissions
                .request(Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) { // Always true pre-M

                        callback.updateUi(ConfigurationFile.Constants.PERMISSION_GRANTED_RECORD_AUDIO);

                    } else {
                        // Oups permission denied
                        callback.updateUi(ConfigurationFile.Constants.PERMISSION_DENIED_RECORD_AUDIO);
                    }
                });
    }

    public void showTimePickerDialog(Context context, final UpdateTimeListener listener, final int code) {

        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(context, (timePicker, selectedHour, selectedMinute) -> {
            selectedTime = (selectedHour < 10 ? "0" + selectedHour : selectedHour) + ":" + (selectedMinute < 10 ? "0" + selectedMinute : selectedMinute);
            listener.updateTime(selectedTime, code);
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle(context.getString(R.string.select_time));
        mTimePicker.show();

    }

    public String encodeImage(Bitmap bm) {
        int nh = (int) (bm.getHeight() * (512.0 / bm.getWidth()));
        bm = Bitmap.createScaledBitmap(bm, 512, nh, true);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);
        return encImage;
    }

    public Bitmap modifyOrientation(Bitmap bitmap, String image_absolute_path) throws IOException {
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

    public Bitmap rotate(Bitmap bitmap, float degrees) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public Bitmap flip(Bitmap bitmap, boolean horizontal, boolean vertical) {
        Matrix matrix = new Matrix();
        matrix.preScale(horizontal ? -1 : 1, vertical ? -1 : 1);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public String uriToFilename(Uri uri, Context context) {

        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        String picturePath = "";
        if (uri != null) {
            Cursor cursor = context.getContentResolver().query(uri, filePathColumn, null, null, null);
            assert cursor != null;
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
        }
        return picturePath;
    }


    public boolean checkIfAlreadyhavePermission(Context context, String permission) {
        if (ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    public void requestForSpecificPermission(Context context, String[] permissions, int requestCode) {
        ActivityCompat.requestPermissions((Activity) context, permissions, requestCode);
    }

    public void uploadFireBasePic(StorageReference storageReference, Uri selectedImageUri, TaskMonitor callback) {

        final StorageReference photoRef = storageReference.child(selectedImageUri.getLastPathSegment());
        photoRef.putFile(selectedImageUri).addOnSuccessListener(taskSnapshot -> {
            Uri photourl = taskSnapshot.getDownloadUrl();
            callback.taskCompleted(photourl.toString());
        });
    }

    public void requirePermission(RxPermissions rxPermissions, int checker, BaseInterface callback) {
        rxPermissions
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
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

    public void requireLocationPermission(RxPermissions rxPermissions, BaseInterface callback) {
        rxPermissions
                .request(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe(granted -> {
                    if (granted) { // Always true pre-M

                        callback.updateUi(ConfigurationFile.Constants.PERMISSION_GRANTED_LOCATION);

                    } else {
                        // Oups permission denied
                        callback.updateUi(ConfigurationFile.Constants.PERMISSION_DENIED);
                    }
                });
    }


    public void requirePhonePermission(RxPermissions rxPermissions, BaseInterface callback) {
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


    public UserData getSaveUserObject(Context context) {
        SharedPrefrenceUtils prefrenceUtils = new SharedPrefrenceUtils(context);
        UserData userData = (UserData) prefrenceUtils.getSavedObject(ConfigurationFile.SharedPrefConstants.PREF_WORKSHOP_DATA, UserData.class);
        return userData;
    }


    public void showDialog(Context context, final BaseObservable viewModel) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose Pictures")
                .setItems(R.array.media, (dialog, which) -> {
                    if (viewModel instanceof ChatViewModel)
                        ((ChatViewModel) viewModel).askForPermission(which);

                    else if (viewModel instanceof FinalStepRegisterViewModel)
                        ((FinalStepRegisterViewModel) viewModel).askForPermission(which);

                    else if (viewModel instanceof AddAdvImageViewModel)
                        ((AddAdvImageViewModel) viewModel).askForPermission(which);

                    else if (viewModel instanceof AddAchievmentImageViewModel)
                        ((AddAchievmentImageViewModel) viewModel).askForPermission(which);
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void startPlacePicker(Activity activity) {
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

    public boolean getDarkModeVal(Context context) {
        SharedPrefrenceUtils sharedPrefrenceUtils = new SharedPrefrenceUtils(context);
        return sharedPrefrenceUtils.getBooleanFromSharedPrederances(ConfigurationFile.SharedPrefConstants.DARK_MODE_PARAM);
    }

    public Boolean isValidMobileNumber(String s) {
        Pattern p = Pattern.compile("(0/1)?[0-9]{9}");

        // Pattern class contains matcher() method
        // to find matching between given number
        // and regular expression
        Matcher m = p.matcher(s);
        return (m.find() && m.group().equals(s));
    }

    public void logout(Activity activity) {
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

    public void clearSharedPref(Context context) {
        SharedPrefrenceUtils prefrenceUtils = new SharedPrefrenceUtils(context);
        prefrenceUtils.clearToken();
    }

    public String setSpecializationText() {
        String specializationText = "";

        List<BaseModel> baseModels = ((MyApplication) MyApplication.getAppContext()).getBasicspecializations();
        for (BaseModel model : baseModels)
            specializationText = specializationText + model.getName() + " - ";
        return specializationText;
    }


    public String setBrandsText() {
        String brandsText = "";

        List<BrandItem> baseModels = ((MyApplication) MyApplication.getAppContext()).getBasicBrands();
        for (BrandItem brandItem : baseModels)
            brandsText = brandsText + brandItem.getName() + " - ";
        return brandsText;
    }

    public String setUrgentText() {
        String urgentText = "";
        List<BaseModel> baseModels = ((MyApplication) MyApplication.getAppContext()).getBasicUrgentTypes();
        for (BaseModel model : baseModels)
            urgentText = urgentText + model.getName() + " - ";
        return urgentText;
    }


    public List<Integer> getSpecializationIds() {

        List<Integer> ids = new ArrayList();
        List<BaseModel> baseModels = ((MyApplication) MyApplication.getAppContext()).getBasicspecializations();
        for (BaseModel model : baseModels)
            ids.add(model.getId());

        return ids;
    }


    public List<Integer> getBrandsIds() {

        List<Integer> ids = new ArrayList();
        List<BrandItem> baseModels = ((MyApplication) MyApplication.getAppContext()).getBasicBrands();
        for (BrandItem brandItem : baseModels)
            ids.add(brandItem.getId());
        return ids;
    }

    public List<Integer> getUrgentIds() {

        List<Integer> ids = new ArrayList();
        List<BaseModel> baseModels = ((MyApplication) MyApplication.getAppContext()).getBasicUrgentTypes();
        for (BaseModel model : baseModels)
            ids.add(model.getId());
        return ids;
    }

    public void endSession(Activity activity) {

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

            if (date1.before(date2)) {
                return true;
            } else {

                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void openCamera(Activity activity) {
        ImagePicker.cameraOnly().start(activity);
    }

    public void openGallery(Activity activity) {
        ImagePicker imagePicker = ImagePicker.create(activity)
                .single()
                .language("in") // Set image picker language
                .theme(R.style.ImagePickerTheme)
                .returnMode(ReturnMode.ALL) // set whether pick action or camera action should return immediate result or not. Only works in single mode for image picker
                .folderMode(false) // set folder mode (false by default)
                .includeVideo(false) // include video (false by default)
                .toolbarArrowColor(Color.RED) // set toolbar arrow up color
                .toolbarFolderTitle("Folder") // folder selection title
                .toolbarImageTitle("Tap to select") // image selection title
                .toolbarDoneButtonText("DONE");// done button text

        imagePicker// max images can be selected (99 by default)
                .imageFullDirectory(Environment.getExternalStorageDirectory().getPath()) // can be full path
                .start(); //
    }


    private File createImageFile(Activity activity) throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        imageFilePath = image.getAbsolutePath();
        return image;
    }


    public String firstCharacters(String name) {
        String[] splited = name.split("\\s+");
        String workshoptitle = "";
        for (int i = 0; i < splited.length; i++)
            workshoptitle = workshoptitle + splited[i].toUpperCase().charAt(0);

        return workshoptitle;
    }


    public void showProgressDialog(Context activity) {
        if (dialog == null || !dialog.isShowing()) {
            dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.custom_dialog_layout);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(false);
            dialog.show();
        }

    }

    public String getAppLanguage(Context context) {
        SharedPrefrenceUtils sharedPrefrenceUtils = new SharedPrefrenceUtils(context);
        String lang = sharedPrefrenceUtils.getStringFromSharedPrederances(ConfigurationFile.SharedPrefConstants.APP_LANGUAGE);
        return lang;
    }

    public void saveAppLanguage(Context context, String lang) {
        SharedPrefrenceUtils sharedPrefrenceUtils = new SharedPrefrenceUtils(context);
        sharedPrefrenceUtils.addStringToSharedPrederances(ConfigurationFile.SharedPrefConstants.APP_LANGUAGE, lang);
    }

    public void cancelDialog() {
        dialog.dismiss();
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String getPathFromUri(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }
}
