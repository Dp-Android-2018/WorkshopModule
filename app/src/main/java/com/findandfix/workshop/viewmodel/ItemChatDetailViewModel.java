package com.findandfix.workshop.viewmodel;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import com.findandfix.workshop.R;
import com.findandfix.workshop.model.global.Conv.Message;
import com.findandfix.workshop.model.global.UserData;
import com.findandfix.workshop.ui.Application.MyApplication;
import com.findandfix.workshop.utils.CustomUtils;
import com.github.chrisbanes.photoview.PhotoView;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by DELL on 13/05/2018.
 */

public class ItemChatDetailViewModel extends BaseObservable {

    private Message message;
    private Context context;
    private String secondUser;
    private UserData userData;
    public ItemChatDetailViewModel(Context context,Message message) {
        this.message=message;
        this.context=context;
        this.secondUser=secondUser;
        userData=CustomUtils.getInstance().getSaveUserObject(context);
    }

    public void setConversationDetail(Message message){
        this.message=message;
        this.secondUser=secondUser;
        notifyChange();
    }


    public String getContent(){
        return message.content;
    }

    public String fromId(){
        return message.fromID;
    }

    public String toId(){
        return message.toID;
    }

    public boolean isRead(){
        return message.isRead;
    }

    public String getType(){
        return message.type;
    }

    public String getTime(){
        Timestamp stamp = new Timestamp(message.timestamp);
        Date date = new Date(stamp.getTime());
        return String.valueOf(date);
    }

    public boolean getSender(){
        if (message.fromID.equals("workshop-"+userData.getId()))
            return true;
        else
            return false;
    }

    public boolean isText(){
        if (getType().equals("text"))
            return true;
        else return false;
    }

    public boolean isImage(){
        if (getType().equals("image"))
            return true;
        else return false;
    }

    public boolean isLoc(){
        if (getType().equals("location"))
            return true;
        else return false;
    }

    public void navigate(View view){
        System.out.println("Content Location :"+getContent());
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?daddr=" + getContent()));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public void showZoomAbleImage(View view){
        Dialog dialog = new Dialog(context, R.style.AppTheme);
        dialog.setContentView(R.layout.dialog_zoomable_image);
        PhotoView zoomablImage =  dialog.findViewById(R.id.zoom_able_image);
        zoomablImage.setImageBitmap(((BitmapDrawable)((ImageView)view).getDrawable()).getBitmap());
        try {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.parseColor("#45000000")));
        }catch (NullPointerException e){
            e.getStackTrace();
        }
        dialog.show();
    }

    public String secondUserName(){
        String secondUser= CustomUtils.getInstance().firstCharacters( ((MyApplication)(MyApplication.getAppContext())).getSecondUserName());
        return secondUser;
    }



    public String firstUserName(){

        UserData userData=CustomUtils.getInstance().getSaveUserObject(context);
        String firstUser= CustomUtils.getInstance().firstCharacters( (userData.getName()));
        return firstUser;
    }

}
