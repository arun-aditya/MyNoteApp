package com.example.mynoteapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;

public class Notficationhelper extends ContextWrapper {

    public static final String CHANNEL1_ID="channel1id";
    public static final String CHANNEL1_NAME="First_Channel";

    public Notficationhelper(Context base) {
        super(base);
        createnotificationchannel();


    }
    private void createnotificationchannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel CHANEL_1 = new NotificationChannel(CHANNEL1_ID, CHANNEL1_NAME, NotificationManager.IMPORTANCE_HIGH);
            CHANEL_1.setDescription("This is for reminders");
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(CHANEL_1);
        }
    }
}
