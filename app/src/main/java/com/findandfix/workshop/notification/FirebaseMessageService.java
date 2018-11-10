package com.findandfix.workshop.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.findandfix.workshop.R;
import com.findandfix.workshop.ui.activity.ChatActivity;
import com.findandfix.workshop.ui.activity.MyConversationsActivity;
import com.findandfix.workshop.ui.activity.NormalRequestActivity;
import com.findandfix.workshop.ui.activity.UrgentRequestActivity;
import com.findandfix.workshop.utils.ConfigurationFile;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;


public class FirebaseMessageService extends FirebaseMessagingService {

    Context context;

    // Handler handler =null;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        try {
            context = this;
            System.out.println("Notification Log Title:"+remoteMessage.getNotification().getTitle());
            System.out.println("Notification Log Body:"+remoteMessage.getNotification().getBody());
            System.out.println("Notification Log Data :"+remoteMessage.getData());
            Map<String , String> map=remoteMessage.getData();
          /*  for (Map.Entry<String, String> entry : map.entrySet())
            {
                System.out.println("Notification Log Data :"+entry.getKey() + "/" + entry.getValue());
            }*/

            if (map.size()>0) {
                if (map.containsKey("noti_title")){

                    if (map.get("noti_title").equals("new_message")){
                            if (ChatActivity.active){

                            }else {
                                Intent i = new Intent(context, MyConversationsActivity.class);
                                Notify(i, remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
                            }
                    } else if (map.get("noti_title").equals("offer_accepted")) {
                        if (NormalRequestActivity.active==false) {
                            Intent i = new Intent(context, NormalRequestActivity.class);
                            i.putExtra(ConfigurationFile.IntentConstants.OFFER_ID, Integer.parseInt(map.get("offer_id")));
                            Notify(i, remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
                        }else {
                            Intent intent = new Intent("CLOSE_ALL");
                            this.sendBroadcast(intent);
                        }
                    }else if (map.get("noti_title").equals("offer_accepted_urgent")) {
                        if (UrgentRequestActivity.active==false) {
                            Intent i = new Intent(context, UrgentRequestActivity.class);
                            i.putExtra(ConfigurationFile.IntentConstants.URGENT_OFFER_ID, Integer.parseInt(map.get("offer_id")));
                            Notify(i, remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
                        }else {
                            Intent intent = new Intent("CLOSE_ALL");
                            this.sendBroadcast(intent);
                        }
                    }

                }else if (map.containsKey("urgent_request_id")) {
                            if((map.get("urgent_request_id") != null)) {
                        Intent i = new Intent(context, UrgentRequestActivity.class);
                        Notify(i, remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
                    }
                }else if (map.containsKey("title")){
                    System.out.println("Notification Log Notification Log Notification Log");
                    if (map.get("title").equals("subscription-approved")) {
                        System.out.println("Notification Log 2");
                        Intent intent = new Intent("CLOSE_ALL");
                        this.sendBroadcast(intent);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error in firebaseMesage : " + e.getMessage());
        }
    }





    public void Notify(Intent intent, String messageTitle, String nb) {
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* request code */, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        long[] pattern = {500, 500, 500, 500, 500};

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_find_fix_logo)
                .setContentTitle(messageTitle)
                .setContentText(nb)
                .setAutoCancel(true)
                .setVibrate(pattern)
                .setLights(Color.BLUE, 1, 1)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationManager.IMPORTANCE_HIGH);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}


