package com.dp.dell.workshopmodule.view.ui.activity;

import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;
import com.pusher.client.connection.ConnectionEventListener;
import com.pusher.client.connection.ConnectionStateChange;

/**
 * Created by DELL on 23/04/2018.
 */

public class PusherDemo {

    public void exp(){
        PusherOptions options = new PusherOptions();
        options.setCluster("eu");
        Pusher pusher = new Pusher("53dafcfcae8778980d83", options);

        Channel channel = pusher.subscribe("find-and-fix");

        channel.bind("my-event", (channelName, eventName, data) -> System.out.println(data));

        pusher.connect();

        pusher.connect(new ConnectionEventListener() {
            @Override
            public void onConnectionStateChange(ConnectionStateChange connectionStateChange) {

            }

            @Override
            public void onError(String s, String s1, Exception e) {

            }
        });
    }
}
