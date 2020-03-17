package com.example.mynoteapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class alertreciever extends BroadcastReceiver {


    public static final String CHANNEL1_ID="channel1id";
    public static final String CHANNEL1_NAME="First_Channel";
    NotificationManagerCompat notificationManagerCompat;

    @Override
    public void onReceive(Context context, Intent intent) {


        Notficationhelper notficationhelper=new Notficationhelper(context);

        notificationManagerCompat=NotificationManagerCompat.from(context);

        Notification notification= new NotificationCompat.Builder(context,CHANNEL1_ID)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentTitle("MyNoteApp")
                .setContentText("Remainder for your app")
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setSmallIcon(R.drawable.note1)
                .build();
        notificationManagerCompat.notify(1,notification);
    }
}
