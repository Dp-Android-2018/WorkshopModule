package com.findandfix.workshop.ui.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.findandfix.workshop.R;
import com.findandfix.workshop.databinding.FragmentClientInfoLayoutBinding;
import com.findandfix.workshop.model.global.Conv.ConversationHistory;
import com.findandfix.workshop.model.global.Conv.ConversationsLocation;
import com.findandfix.workshop.model.global.Conv.WorkshopFire;
import com.findandfix.workshop.model.global.RequestData;
import com.findandfix.workshop.model.global.UserData;
import com.findandfix.workshop.ui.activity.ChatActivity;
import com.findandfix.workshop.ui.callback.BaseInterface;
import com.findandfix.workshop.utils.ConfigurationFile;
import com.findandfix.workshop.utils.CustomUtils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.tbruyelle.rxpermissions2.RxPermissions;

/**
 * Created by DELL on 12/03/2018.
 */

public class ClientInfo extends Fragment implements BaseInterface {
    FragmentClientInfoLayoutBinding binding;
    private RxPermissions rxPermissions;
    private DatabaseReference reference;
    private UserData userData;
    private String deviceToken=null;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_client_info_layout,container,false);
        View v=binding.getRoot();
        userData=CustomUtils.getInstance().getSaveUserObject(getActivity());
        rxPermissions=new RxPermissions(getActivity());
        binding.tvCarOwnerName.setText(getRequestObj().getCarowner().getFirstName()+" "+getRequestObj().getCarowner().getLastName());
        deviceToken=getRequestObj().getCarowner().getDeviceToken();
        binding.btnCall.setOnClickListener(v12 -> CustomUtils.getInstance().requirePhonePermission(rxPermissions, ClientInfo.this));

        binding.btnChat.setOnClickListener(v1 -> addUserToFirebase());


        return v;
    }


    public void addUserToFirebase(){
        CustomUtils.getInstance().showProgressDialog(getActivity());
        reference= FirebaseDatabase.getInstance().getReference("users/workshop-"+userData.getId());
        WorkshopFire workshopFire=new WorkshopFire();
        workshopFire.device_token=getRequestObj().getCarowner().getDeviceToken();
        workshopFire.location="FAF"+userData.getId()+"-"+getRequestObj().getCarowner().getId();
        workshopFire.sender_name=getRequestObj().getCarowner().getFirstName()+" "+getRequestObj().getCarowner().getLastName();
        reference.child("conversations/carowner-"+getRequestObj().getCarowner().getId()).setValue(workshopFire).addOnCompleteListener(task -> addWorkshopConv());
    }

    public void addWorkshopConv(){
        reference= FirebaseDatabase.getInstance().getReference("users/carowner-"+getRequestObj().getCarowner().getId());
        WorkshopFire workshopFire=new WorkshopFire();
        workshopFire.device_token=userData.getToken();
        workshopFire.location="FAF"+userData.getId()+"-"+getRequestObj().getCarowner().getId();
        workshopFire.sender_name=userData.getName();
        reference.child("conversations/workshop-"+userData.getId()).setValue(workshopFire).addOnCompleteListener(task -> {
            CustomUtils.getInstance().cancelDialog();
            ConversationsLocation conversationsLocation=new ConversationsLocation();
            conversationsLocation.setSecondUserUrl("carowner-"+getRequestObj().getCarowner().getId());
            conversationsLocation.sender_name=getRequestObj().getCarowner().getFirstName()+" "+getRequestObj().getCarowner().getLastName();
            conversationsLocation.location="FAF"+userData.getId()+"-"+getRequestObj().getCarowner().getId();

            ConversationHistory conversationHistory=new ConversationHistory(null,conversationsLocation);
            Intent i=new Intent(getActivity(),ChatActivity.class);
            i.putExtra("Conversation_Id",conversationHistory);
            i.putExtra(ConfigurationFile.IntentConstants.DEVICE_TOKEN,deviceToken);
            startActivity(i);
        });
    }
    public RequestData getRequestObj(){
        RequestData requestData =(RequestData) getArguments().getSerializable(ConfigurationFile.SharedPrefConstants.PREF_REQUEST_OBJECT);
        if(requestData!=null){
            System.out.println("Request Data 1:"+new Gson().toJson(requestData).toString());;
        }
        return requestData;
    }

    @Override
    public void updateUi(int code) {
        System.out.println("CallBack Code :"+code);
        if (code == ConfigurationFile.Constants.PERMISSION_GRANTED_PHONE_CALL)
            makeCall();
        else if (code == ConfigurationFile.Constants.PERMISSION_DENIED_PHONE_CALL)
            Snackbar.make(getActivity().findViewById(R.id.rl_parent), getString(R.string.permission_denied), Snackbar.LENGTH_LONG).show();
    }

    public void makeCall() {
        System.out.println("CallBack Code Calling");
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        String workShopPhone = "tel:" + getRequestObj().getCarowner().getMobile();
        callIntent.setData(Uri.parse(workShopPhone));
        /*if (ActivityCompat.checkSelfPermission(RequestDetailActivity.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }*/
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            return;
        }
        startActivity(callIntent);

    }
}
