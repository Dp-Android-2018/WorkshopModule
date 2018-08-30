package com.dp.dell.workshopmodule.view.ui.fragment;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chibde.visualizer.BarVisualizer;
import com.dp.dell.workshopmodule.R;
import com.dp.dell.workshopmodule.databinding.FragmentInProgressUrgentRequestBinding;
import com.dp.dell.workshopmodule.model.global.Conv.ConversationHistory;
import com.dp.dell.workshopmodule.model.global.Conv.ConversationsLocation;
import com.dp.dell.workshopmodule.model.global.Conv.WorkshopFire;
import com.dp.dell.workshopmodule.model.global.UrgentRequestData;
import com.dp.dell.workshopmodule.model.global.UserData;
import com.dp.dell.workshopmodule.model.response.DefaultResponse;
import com.dp.dell.workshopmodule.model.response.WorkShopUrgentRequestResponse;
import com.dp.dell.workshopmodule.network.ApiClient;
import com.dp.dell.workshopmodule.network.EndPoints;
import com.dp.dell.workshopmodule.utils.ConfigurationFile;
import com.dp.dell.workshopmodule.utils.CustomUtils;
import com.dp.dell.workshopmodule.utils.NetWorkConnection;
import com.dp.dell.workshopmodule.view.ui.activity.ChatActivity;
import com.dp.dell.workshopmodule.view.ui.adapter.InProgressUrgentRequestAdapter;
import com.dp.dell.workshopmodule.view.ui.callback.BaseInterface;
import com.dp.dell.workshopmodule.view.ui.callback.UrgentProgressItemClickListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by DELL on 13/03/2018.
 */

public class InProgressUrgentRequestFragment extends Fragment implements BaseInterface {
    private RecyclerView recyclerView;
    private FragmentInProgressUrgentRequestBinding binding;
    private UserData userData;
    private List<UrgentRequestData> inProgressUrgenRequests;
    private RxPermissions rxPermissions;
    private int callPosition;
    private Dialog dialog=null;
    private Dialog completeDialog=null;
    private DatabaseReference reference;
    private int chatPosition=0;
    ImageView  ivplay=null;
    private String deviceToken=null;
    private BarVisualizer barVisualizer=null;
    public int soundPos=0;

    ////////////////////////////////////////////////////
    private boolean loading=false;
    private String next=null;
    private int pageId=0;
    private int pos=0;
    ////////////////////////////////////////////////////

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_in_progress_urgent_request, container, false);
        View v = binding.getRoot();
        rxPermissions = new RxPermissions(getActivity());
        userData = CustomUtils.getInstance().getSaveUserObject(getActivity());
        inProgressUrgenRequests = new ArrayList<>();
        initializeRecycler();
        loading=true;
        getInProgressUrgentRequests();

        return v;
    }
    //////////////////////////Initializing Recycler View and Passing Adapter To them //////////////////////////////////////
    public void initializeRecycler() {
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        binding.rvInProgressUrgentRequests.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        binding.rvInProgressUrgentRequests.setItemAnimator(new DefaultItemAnimator());
        binding.rvInProgressUrgentRequests.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if ( ((LinearLayoutManager) binding.rvInProgressUrgentRequests.getLayoutManager()).findLastVisibleItemPosition()==inProgressUrgenRequests.size()-1) {
                    if (next!=null && loading ==false) {
                        loading=true;
                        System.out.println("Load More Data Success ");
                        getInProgressUrgentRequests();
                    }

                }
            }
        });
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    }
/////////////////////////Make A Call To get In Progress Requests //////////////////////////////////////////////////////////////////////
    public void getInProgressUrgentRequests() {
        if (NetWorkConnection.isConnectingToInternet(getActivity().getApplicationContext())) {
            //System.out.println("Code cONNECTING");
           // CustomUtils.getInstance().showProgressDialog(getActivity());
            ApiClient.getClient().create(EndPoints.class).getWorkshopInProgressUrgentRequests(ConfigurationFile.Constants.API_KEY, CustomUtils.getInstance().getAppLanguage(getActivity()), ConfigurationFile.Constants.Content_Type, ConfigurationFile.Constants.Content_Type, "Bearer " + userData.getToken(),pageId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(workShopUrgentRequestResponseResponse -> {
                    //    CustomUtils.getInstance().cancelDialog();
                        int code = workShopUrgentRequestResponseResponse.code();
                        System.out.println("Code :" + code);
                        loading=false;
                        if (code == ConfigurationFile.Constants.SUCCESS_CODE) {

                            pos=inProgressUrgenRequests.size();
                            inProgressUrgenRequests.addAll(workShopUrgentRequestResponseResponse.body().getData());
                            next=workShopUrgentRequestResponseResponse.body().getLinks().getNext();
                            if (next!=null)
                                pageId=Integer.parseInt(next.substring(next.length()-1));
                            binding.tvInProgressRequestNum.setText(String.valueOf(workShopUrgentRequestResponseResponse.body().getData().size()));
                            setInProgressUrgenRequestsAdapter();
                        } else if (code == ConfigurationFile.Constants.UNAUTHENTICATED_CODE) {
                            CustomUtils.getInstance().logout(getActivity());
                        } else if (code == ConfigurationFile.Constants.UNSUBSCRIBE_CODE) {
                            CustomUtils.getInstance().logout(getActivity());
                            //Snackbar.make(getActivity().findViewById(R.id.ll_container), R.string.unsubscribed, Snackbar.LENGTH_LONG).show();
                            }

                    }, throwable -> {
                       // CustomUtils.getInstance().cancelDialog();
                        System.out.println("Normat Request :" + throwable.getMessage());
                    });
        } else {
            Snackbar.make(getActivity().findViewById(R.id.ll_container), getActivity().getResources().getString(R.string.internet_connection), Snackbar.LENGTH_LONG).show();
        }
    }


//////////////////////////////Set Adapter Of Recycler View ///////////////////////////////////////////////////////////////

    public void setInProgressUrgenRequestsAdapter() {
        if (inProgressUrgenRequests.size()>0) {
            InProgressUrgentRequestAdapter adapter = new InProgressUrgentRequestAdapter(inProgressUrgenRequests);
            adapter.setInProgressItemListener((Code, Position) -> {
                if (Code == ConfigurationFile.Constants.BTN_CALL_ACTION) {
                    System.out.println("Btn Call Actions Data :" + Position);
                    CustomUtils.getInstance().requirePhonePermission(rxPermissions, InProgressUrgentRequestFragment.this);
                    callPosition = Position;
                } else if (Code == ConfigurationFile.Constants.BTN_CHAT_ACTION) {
                    chatPosition = Position;
                    addUserToFirebase();
                } else if (Code == ConfigurationFile.Constants.BTN_INFO_ACTION) {
                    setDialog(Position);
                } else if (Code == ConfigurationFile.Constants.BTN_LOCATION_ACTION) {
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://maps.google.com/maps?daddr=" + inProgressUrgenRequests.get(Position).getLatitude() + "," + inProgressUrgenRequests.get(Position).getLongitude()));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getActivity().startActivity(intent);

                } else if (Code == ConfigurationFile.Constants.COMPLETE_URGENT_REQUEST_CODE) {
                    // completeUrgentRequest(Position);
                    setCompleteDialog(Position);
                }
            });
            binding.rvInProgressUrgentRequests.setAdapter(adapter);
        }else {
            binding.tvInProgressRequestNum.setVisibility(View.GONE);
            binding.tvInProgressRequestTxt.setVisibility(View.GONE);
            binding.rvInProgressUrgentRequests.setVisibility(View.GONE);
            binding.ivNoRequests.setVisibility(View.VISIBLE);
            binding.tvNoRequests.setVisibility(View.VISIBLE);
        }
    }

    ////////////////////Update View According To Code //////////////////////////////////////////////////////////////
    @Override
    public void updateUi(int code) {
        if (code == ConfigurationFile.Constants.PERMISSION_GRANTED_PHONE_CALL)
            makeCall();
        else if (code == ConfigurationFile.Constants.PERMISSION_DENIED_PHONE_CALL  || code == ConfigurationFile.Constants.PERMISSION_DENIED_RECORD_AUDIO)
            Snackbar.make(getActivity().findViewById(R.id.ll_container), getString(R.string.permission_denied), Snackbar.LENGTH_LONG).show();

        else if (code == ConfigurationFile.Constants.PERMISSION_GRANTED_RECORD_AUDIO)
            playRecord(barVisualizer,inProgressUrgenRequests.get(soundPos).getVoiceNotes());
    }
////////////////////Make Call Action + Defining Permission //////////////////////////////////////////////////////////
    public void makeCall() {
        System.out.println("CallBack Code Calling");
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        String workShopPhone = "tel:" + inProgressUrgenRequests.get(callPosition).getCarowner().getMobile();
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

///////////////////////////Added User To Firebase + Add Conversation To It ///////////////////////////////////////////////////////////////////
    public void addUserToFirebase(){
        CustomUtils.getInstance().showProgressDialog(getActivity());
        reference= FirebaseDatabase.getInstance().getReference("users/workshop-"+userData.getId());
        WorkshopFire workshopFire=new WorkshopFire();
        workshopFire.device_token=inProgressUrgenRequests.get(chatPosition).getCarowner().getDeviceToken();
        workshopFire.location="FAF"+userData.getId()+"-"+inProgressUrgenRequests.get(chatPosition).getCarowner().getId();
        workshopFire.sender_name=inProgressUrgenRequests.get(chatPosition).getCarowner().getFirstName()+" "+inProgressUrgenRequests.get(chatPosition).getCarowner().getLastName();
        reference.child("conversations/carowner-"+inProgressUrgenRequests.get(chatPosition).getCarowner().getId()).setValue(workshopFire).addOnCompleteListener(task -> addWorkshopConv());
    }
//////////////////////////////////////Add Conversation To Specific User ////////////////////////////////////////////////////////////////
    public void addWorkshopConv(){
        reference= FirebaseDatabase.getInstance().getReference("users/carowner-"+inProgressUrgenRequests.get(chatPosition).getCarowner().getId());
        WorkshopFire workshopFire=new WorkshopFire();
        workshopFire.device_token=userData.getToken();
        workshopFire.location="FAF"+userData.getId()+"-"+inProgressUrgenRequests.get(chatPosition).getCarowner().getId();
        workshopFire.sender_name=userData.getName();
        reference.child("conversations/workshop-"+userData.getId()).setValue(workshopFire).addOnCompleteListener(task -> {
            CustomUtils.getInstance().cancelDialog();
            ConversationsLocation conversationsLocation=new ConversationsLocation();
            conversationsLocation.setSecondUserUrl("carowner-"+inProgressUrgenRequests.get(chatPosition).getCarowner().getId());
            conversationsLocation.sender_name=inProgressUrgenRequests.get(chatPosition).getCarowner().getFirstName()+" "+inProgressUrgenRequests.get(chatPosition).getCarowner().getLastName();
            conversationsLocation.location="FAF"+userData.getId()+"-"+inProgressUrgenRequests.get(chatPosition).getCarowner().getId();

            ConversationHistory conversationHistory=new ConversationHistory(null,conversationsLocation);
            Intent i=new Intent(getActivity(),ChatActivity.class);
            i.putExtra("Conversation_Id",conversationHistory);
            i.putExtra(ConfigurationFile.IntentConstants.DEVICE_TOKEN,inProgressUrgenRequests.get(chatPosition).getCarowner().getDeviceToken());
            startActivity(i);
        });
    }
/////////////////////Dialog If There is a media Like notes or Voice Notes ////////////////////////////////////////////////////////////////
    private void setDialog(int position){
        System.out.println("Btn Call Actions Data Voice Notes :"+inProgressUrgenRequests.get(position).getVoiceNotes());
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        LayoutInflater factory = LayoutInflater.from(getActivity());
        final View dialogView = factory.inflate(R.layout.dialog_urgent_info_layout, null);
        TextView tvNotes = dialogView.findViewById(R.id.tv_car_owner_desc);
        TextView tvRecord=dialogView.findViewById(R.id.tv_record);
        barVisualizer=dialogView.findViewById(R.id.visualizer);
        ivplay=dialogView.findViewById(R.id.iv_play_record);
        barVisualizer.setVisibility(View.GONE);
        ivplay.setVisibility(View.GONE);
        tvRecord.setVisibility(View.GONE);
        tvNotes.setText(inProgressUrgenRequests.get(position).getNotes()!=null? inProgressUrgenRequests.get(position).getNotes():getString(R.string.there_is_no_desc));

        ivplay.setOnClickListener(v -> {
            ivplay.setEnabled(false);
//
            soundPos=position;
            CustomUtils.getInstance().requireRecordSoundPermission(rxPermissions,InProgressUrgentRequestFragment.this);
        });
        if (inProgressUrgenRequests.get(position).getVoiceNotes()!=null){
            barVisualizer.setVisibility(View.VISIBLE);
            ivplay.setVisibility(View.VISIBLE);
            tvRecord.setVisibility(View.VISIBLE);
        }else {
            tvRecord.setVisibility(View.VISIBLE);
            tvRecord.setText(R.string.no_voice_notes);
        }
        dialog.setContentView(dialogView);
        dialog.show();
    }
///////////////////////Method To Play Voice Notes //////////////////////////////////////////////////////
    public void playRecord(BarVisualizer barVisualizer,String voice)  {

        try {
            MediaPlayer mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(voice);
            mediaPlayer.prepare(); // might take long! (for buffering, etc)
            mediaPlayer.start();

            barVisualizer.setColor(ContextCompat.getColor(getActivity(), R.color.colorYellow));

// define custom number of bars you want in the visualizer between (10 - 256).
            barVisualizer.setDensity(70);

// Set your media player to the visualizer.
            barVisualizer.setPlayer(mediaPlayer.getAudioSessionId());

            mediaPlayer.setOnCompletionListener(mp -> {
                if (ivplay!=null)
                    ivplay.setEnabled(true);
            });

        }catch (IOException ex){
            System.out.println("Ex Record :"+ex.getMessage());
            ex.printStackTrace();
        }catch (Exception ex){
            ex.getMessage();
        }
    }

//////////////////////Make Call To Complete Specific Urgent Requests //////////////////////////////////////////////////////////////
    public void completeUrgentRequest(int pos){
        System.out.println("Btn Call Actions Data Complete ID :"+inProgressUrgenRequests.get(pos).getId());
        if(NetWorkConnection.isConnectingToInternet(getActivity().getApplicationContext())) {
            System.out.println("Code cONNECTING cOMPLETED");

           CustomUtils.getInstance().showProgressDialog(getActivity());
            System.out.println("Code cOMPLETED Urgent ID "+ new Gson().toJson(inProgressUrgenRequests.get(pos)));
            ApiClient.getClient().create(EndPoints.class).completedUrgentRequest(ConfigurationFile.Constants.API_KEY, CustomUtils.getInstance().getAppLanguage(getActivity()), ConfigurationFile.Constants.Content_Type, ConfigurationFile.Constants.Content_Type, "Bearer "+userData.getToken(),inProgressUrgenRequests.get(pos).getId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(completeRequestResponse -> {
                        CustomUtils.getInstance().cancelDialog();
                        int code=completeRequestResponse.code();
                        System.out.println("Code cOMPLETED:"+code);
                        if(code==ConfigurationFile.Constants.SUCCESS_CODE || code==500){
                            Snackbar.make(getActivity().findViewById(R.id.ll_container), R.string.urgent_request_completed,Snackbar.LENGTH_LONG).show();
                            inProgressUrgenRequests.clear();
                            getInProgressUrgentRequests();
                        }else if(code==ConfigurationFile.Constants.UNAUTHENTICATED_CODE ||code==ConfigurationFile.Constants.UNSUBSCRIBE_CODE ){
                            CustomUtils.getInstance().logout(getActivity());
                        }else if(code==ConfigurationFile.Constants.CANT_COMPLETE_REQUEST_CODE ){
                            Snackbar.make(getActivity().findViewById(R.id.ll_container), R.string.cant_complete_your_request,Snackbar.LENGTH_LONG).show();
                        }

                    }, throwable -> {
                        CustomUtils.getInstance().cancelDialog();
                        System.out.println("Code cOMPLETED :"+throwable.getMessage());
                        if (throwable.getMessage().equals("Use JsonReader.setLenient(true) to accept malformed JSON at line 1 column 1 path $")){
                            Snackbar.make(getActivity().findViewById(R.id.ll_container), R.string.urgent_request_completed,Snackbar.LENGTH_LONG).show();
                            inProgressUrgenRequests.clear();
                            getInProgressUrgentRequests();
                        }
                    });
        }else {
            Snackbar.make(getActivity().findViewById(R.id.ll_container), R.string.internet_connection,Snackbar.LENGTH_LONG).show();
        }
    }

////////////////////////Display Dialog TO Ask If he really wants To Complete This Urgent ///////////////////////////////////////////
    private void setCompleteDialog(int position){
        completeDialog = new Dialog(getActivity());
        completeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        completeDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        LayoutInflater factory = LayoutInflater.from(getActivity());
        final View dialogView = factory.inflate(R.layout.dialog_urgent_request_layout, null);
        TextView tvTitle=dialogView.findViewById(R.id.tv_car_owner_desc);
        tvTitle.setText(R.string.complete_urgent_request);
        Button buttonAccept = dialogView.findViewById(R.id.btn_accept);
        buttonAccept.setOnClickListener(v -> {
            completeDialog.dismiss();
            completeUrgentRequest(position);
        });

        Button buttonCancel=dialogView.findViewById(R.id.btn_cancel);
        buttonCancel.setOnClickListener(v -> completeDialog.dismiss());
        completeDialog.setContentView(dialogView);
        completeDialog.show();
    }

    @Override
    public void onPause() {
        super.onPause();
        inProgressUrgenRequests.clear();
    }
}

