package com.example.studygroups;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class Reminder extends BroadcastReceiver {

    private final String CHANNEL_ID = "personal_notification";
    private final int NOTIFICATION_ID = 1;
    private Context context;

    @Override
    public void onReceive(Context context, Intent intent){
        this.context = context;
        Log.d(TAG, "onReceive: wurde benachrichtigt");
        remindUser();
    }

    private void remindUser(){
        createNotificationChannel(); //necessary for Android 8 and higher

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID);
        builder.setSmallIcon(R.drawable.icon);

        //if(newUserJoined()) {
        builder.setContentTitle(Resources.getSystem().getString(R.string.notification_title_join));
        builder.setContentText(Resources.getSystem().getString(R.string.notification_text_join));
        //}
    /*else if(timeToGo()){
        builder.setContentTitle(getString(R.string.notification_title_reminder));
        builder.setContentText(getString(R.string.notification_text_reminder));
    }*/

        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());
    }

    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "notification", NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("notification description");
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }
}
