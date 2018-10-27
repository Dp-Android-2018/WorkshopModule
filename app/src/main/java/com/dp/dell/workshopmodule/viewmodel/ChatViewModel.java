package com.dp.dell.workshopmodule.viewmodel;

import android.app.Activity;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.View;

import com.dp.dell.workshopmodule.model.global.Conv.ConversationHistory;
import com.dp.dell.workshopmodule.model.global.Conv.Message;
import com.dp.dell.workshopmodule.model.global.MessageData;
import com.dp.dell.workshopmodule.model.global.MessageNotification;
import com.dp.dell.workshopmodule.model.global.MsgRequest;
import com.dp.dell.workshopmodule.model.global.UserData;
import com.dp.dell.workshopmodule.model.response.DefaultResponse;
import com.dp.dell.workshopmodule.network.ApiClient;
import com.dp.dell.workshopmodule.network.EndPoints;
import com.dp.dell.workshopmodule.utils.ConfigurationFile;
import com.dp.dell.workshopmodule.utils.CustomUtils;
import com.dp.dell.workshopmodule.utils.NetWorkConnection;
import com.dp.dell.workshopmodule.view.ui.Application.MyApplication;
import com.dp.dell.workshopmodule.view.ui.callback.BaseInterface;
import com.dp.dell.workshopmodule.view.ui.callback.CallAnotherActivityNavigator;
import com.dp.dell.workshopmodule.view.ui.callback.TaskMonitor;
import com.esafirm.imagepicker.model.Image;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.sql.Timestamp;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

/**
 * Created by DELL on 13/05/2018.
 */

public class ChatViewModel extends BaseObservable  {
    private DatabaseReference reference;
    public ObservableList<Message>messages;
    public ObservableField<String> message;
    private BaseInterface callback;
    private  ConversationHistory history;
    private Activity activity;
    private int checker;
    Uri selectedImageUri=null;
    StorageReference storageReference;
    RxPermissions rxPermissions;
    private String deviceToken=null;
    private UserData userData;
    private CallAnotherActivityNavigator navigator;
    MessageNotification notification;
    public ChatViewModel(Activity activity, BaseInterface callback, CallAnotherActivityNavigator navigator,ConversationHistory history) {
        this.callback=callback;
        this.activity=activity;
        this.navigator=navigator;
         rxPermissions = new RxPermissions(activity);
        messages=new ObservableArrayList<>();
        message=new ObservableField<String>("");
        this.history=history;

        userData=CustomUtils.getInstance().getSaveUserObject(activity);
        deviceToken=activity.getIntent().getStringExtra(ConfigurationFile.IntentConstants.DEVICE_TOKEN);
        System.out.println("Device Token Data :"+deviceToken);

        ////////////////////////////////////////////////////////////////////////////////////////////////////
        notification=new MessageNotification();
        notification.setDeviceToken(deviceToken);
        notification.setKey(5);
        MessageData messageData=new MessageData();
        messageData.setNotificationTitle("new_message");
        notification.setData(messageData);
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
        storageReference= FirebaseStorage.getInstance().getReference().child("app_photos");
        ((MyApplication)(MyApplication.getAppContext())).setSecondUserName(history.getConversations().sender_name);
        getConversationDetails();
    }

    public void getConversationDetails(){
        System.out.println("Conversation Location :"+history.getConversations().location);
       reference= FirebaseDatabase.getInstance().getReference("conversations/"+history.getConversations().location);
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(!dataSnapshot.getKey().equals("last_message")) {
                    Message message = dataSnapshot.getValue(Message.class);
                    messages.add(message);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }});
    }

    public void sendNotification(){

        if (NetWorkConnection.isConnectingToInternet(activity)) {
            CustomUtils.getInstance().showProgressDialog(activity);
            MsgRequest msgRequest=new MsgRequest();
            msgRequest.setNotification(notification);
            System.out.println("Mobile Code  Notification Request :"+new Gson().toJson(msgRequest));
            ApiClient.getClient().create(EndPoints.class).sendNotification(ConfigurationFile.Constants.API_KEY,CustomUtils.getInstance().getAppLanguage(activity),ConfigurationFile.Constants.Content_Type,ConfigurationFile.Constants.Content_Type,msgRequest)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(defaultResponseResponse -> {
                        CustomUtils.getInstance().cancelDialog();
                        System.out.println("Mobile Code  Notification:"+defaultResponseResponse.code());
                        System.out.println("Mobile Code  Notification Msg :"+defaultResponseResponse.body().getMessage());
                        if (defaultResponseResponse.code()==ConfigurationFile.Constants.SUCCESS_CODE) {

                        } else if (defaultResponseResponse.code()==ConfigurationFile.Constants.CANT_COMPLETE_REQUEST_CODE){

                        }else if (defaultResponseResponse.code()==ConfigurationFile.Constants.UNAUTHENTICATED_CODE ||
                                defaultResponseResponse.code()==ConfigurationFile.Constants.UNSUBSCRIBE_CODE){
                                        CustomUtils.getInstance().endSession(activity);
                        }

                    }, throwable -> {
                        CustomUtils.getInstance().cancelDialog();
                        //  binding.progressBar.setVisibility(View.GONE);
                        System.out.println("Ex :"+throwable.getMessage());
                    });


        }else {
            callback.updateUi(ConfigurationFile.Constants.NO_INTERNET_CONNECTION_CODE);
        }
    }

    public void addMessageAction(View view){
            if (!message.get().equals(""))
                addMessage(message.get(),"text");
    }

    //////////////////////Dialog To Choose Image From Camera Or Gallery /////////////////////////////
    public void displayDialog(View view) {
        callback.updateUi(ConfigurationFile.Constants.SHOW_DIALOG_CODE);}
///////////////////////////////////////////////////////////////////////////////////
public void shareLocation(View view) {
    navigator.callActivity();}
    //////////////////////////////////////////////////////////////////////////
    public void addMessage(String content,String type){

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        final Message newMessage=new Message(content,"workshop-"+userData.getId(),history.getConversations().getSecondUserUrl(),false,type,(timestamp.getTime())/1000);
        reference.push().setValue(newMessage).addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                message.set("");
                sendNotification();
            }
        });

       reference.child("last_message").setValue(newMessage);
    }

    ///////////////////Ask For Permission if Sdk > lollipop //////////////////////////////////////////////
    public void askForPermission(int checker) {
        CustomUtils.getInstance().requirePermission(rxPermissions,checker,callback);
    }
    ///////////////////////////////////Set Image To Image View and Convert it to Base 54 ////////////////////////////////
    public void onActivityResult(int requestCode, int resultCode, Intent data, Image image) {
        if (requestCode == ConfigurationFile.Constants.CAMERA_REQUEST || requestCode == ConfigurationFile.Constants.GALLERY_REQUEST) {
            if (resultCode == RESULT_OK) {
                selectedImageUri = Uri.fromFile(new File(image.getPath()));
                uploadFireBasePic();
            }
        } else if (requestCode == ConfigurationFile.Constants.PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, activity);
                LatLng lng = place.getLatLng();
                addMessage(lng.latitude+","+lng.longitude, "location");
            }
        }
    }

    public void uploadFireBasePic(){
       CustomUtils.getInstance().uploadFireBasePic(storageReference, selectedImageUri, photoUrl -> addMessage(photoUrl,"image"));
    }


}
