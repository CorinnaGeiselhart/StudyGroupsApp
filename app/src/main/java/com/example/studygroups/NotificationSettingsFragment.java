package com.example.studygroups;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import static android.content.Context.ALARM_SERVICE;

public class NotificationSettingsFragment extends Fragment {

    //TEST
    Button test;
    FirebaseFirestore db;
    public static Map<String, String> map;
    public static String userAge;
    //END TEST

    private Switch switchJoin;
    private Switch switchReminder;

    private final String CHANNEL_ID = "personal_notification";
    private final int NOTIFICATION_ID = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.notification_settings_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initViews();
        setListeners();
    }

    private void initViews(){
        switchJoin = getActivity().findViewById(R.id.switch_notificationpermissionJoin);
        switchJoin.setChecked(NavigationDrawer.isNotoficationPermissionJoinGiven);

        switchReminder = getActivity().findViewById(R.id.switch_notificationpermissionReminder);
        switchReminder.setChecked(NavigationDrawer.isNotoficationPermissionReminderGiven);

        //TEST
        test = getView().findViewById(R.id.test_button);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notificate();
                //remind();
            }
        });
        //END TEST
    }

    private void setListeners(){
        switchJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveJoinPermissionForRestart(switchJoin.isChecked());
            }
        });

        switchReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveReminderPermissionForRestart(switchReminder.isChecked());
            }
        });
    }


    private void saveJoinPermissionForRestart(boolean isPermissionGiven){
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(getString(R.string.pref_joinPermission_key), isPermissionGiven);
        editor.commit();
    }

    private void saveReminderPermissionForRestart(boolean isPermissionGiven){
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(getString(R.string.pref_reminderPermission_key), isPermissionGiven);
        editor.commit();
    }



    //notification wird erstmal hier erstellt, solange die Eintritts-Methode noch nicht existiert
    private void notificate(){
        createNotificationChannel(); //necessary for Android 8 and higher

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this.getContext(), CHANNEL_ID);
        builder.setSmallIcon(R.drawable.icon);

        //if(newUserJoined()) {
            builder.setContentTitle(getString(R.string.notification_title_join));
            builder.setContentText(getString(R.string.notification_text_join));
        //}
        /*else if(timeToGo()){
            builder.setContentTitle(getString(R.string.notification_title_reminder));
            builder.setContentText(getString(R.string.notification_text_reminder));
        }*/

        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this.getContext());
        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());
    }

    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "notification", NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("notification description");
            NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    private void remind(){
        Calendar calendar = Calendar.getInstance();

        Intent intent = new Intent(getActivity(), Reminder.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.YEAR, 2019);
        calendar.set(Calendar.MONTH, 9);
        calendar.set(Calendar.DAY_OF_MONTH, 15);
        calendar.set(Calendar.HOUR_OF_DAY, 4);
        calendar.set(Calendar.MINUTE, 9);
        calendar.set(Calendar.SECOND, 1);

        AlarmManager alarmManager = (AlarmManager)this.getContext().getSystemService(Context.ALARM_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        else alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }


}
