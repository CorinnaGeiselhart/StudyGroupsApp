package com.example.studygroups;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
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
import java.util.Map;

public class NotificationSettingsFragment extends Fragment {

    //TEST
    Button test;
    FirebaseFirestore db;
    public static Map<String, String> map;
    public static String userAge;
    //END TEST

    ListView listView;
    PreferencesListAdapter adapter;
    ArrayList<NotificationPermission> permissionList = new ArrayList<>();

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
        fillList();
    }

    private void initViews(){
        listView = getView().findViewById(R.id.listView_preferences);

        //TEST
        test = getView().findViewById(R.id.test_button);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notificate();
                db = FirebaseFirestore.getInstance();
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                //Hier hol ich alle Documente aus einer Collection raus
                db.collection("studygroups-Accounts").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Um aus dem DocumentSnapshot die documentdaten rauszufiltern und in ein neues Objekt "StudyGroup" gepackt und der Liste hinzugefügt
                                db.collection("studygroups-Accounts").document(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot document = task.getResult();
                                            if (document.exists()) {
                                                userAge = document.getString("age");
                                                Toast.makeText(getActivity(), userAge, Toast.LENGTH_LONG).show();
                                            }
                                        }}
                                });
                            }
                        }
                    }
                });
            }
        });
        //END TEST

        adapter = new PreferencesListAdapter(getView().getContext(),permissionList);
        listView.setAdapter(adapter);
    }

    private void fillList(){
        permissionList.add(new NotificationPermission(getString(R.string.notification_group_name), getString(R.string.notification_group_explanation), true));
        permissionList.add(new NotificationPermission(getString(R.string.notification_reminder_name), getString(R.string.notification_reminder_explanation), true));
        adapter.notifyDataSetChanged();
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

}
