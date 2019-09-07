package com.example.studygroups;


import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class NewStudyGroups extends MainActivityFragment{

    private FirebaseFirestore db;

    @Override
    protected void replaceFragment() {
        fragmentTransaction.replace(R.id.layout_MainActivityFrame, details);
    }

    @Override
    protected void fillList() {
        //Beispiel, da Datenbank noch nicht erstellt
        list.add(new StudyGroup("EIMI", "12.08.2019","Monday" ,"19:00", "Universität Regensburg",""));
        db = FirebaseFirestore.getInstance();


        //Beispiel, da Datenbank noch nicht erstellt
        //list.add(new StudyGroup("EIMI", "12.08.2019","Monday" ,"19:00", "Universität Regensburg",""));
        db.collection("Einführung in die objektorientierte Programmierung").document("APnqssra3daEnDyR7bc7").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("NewStudygroup", "DocumentSnapshot data: " + document.getData());
                        String subject = document.getString("subject");
                        Log.d("NewStudygroup", subject );
                        String date = (String)document.getString("date");
                        Log.d("NewStudygroup","dat: " +  date);
                        String weekday = (String)document.getString("weekday");
                        Log.d("NewStudygroup","sub: " +  weekday);
                        String time = (String)document.getString("time");
                        Log.d("NewStudygroup","sub: " +  time);
                        String place = (String)document.getString("place");
                        Log.d("NewStudygroup","sub: " +  place);
                        String details = (String)document.getString("notes");
                        Log.d("NewStudygroup","sub: " +  details);
                        list.add(new StudyGroup(subject,date,weekday,time,place,details));
                        Log.d("NewStudygroup", String.valueOf(new StudyGroup(subject,date,weekday,time,place,details)));
                    } }}
        });
    }

    @Override
    protected void setText() {
        header.setText(R.string.text_new_study_groups);
        textIfListIsEmpty.setText(R.string.text_empty_new_study_groups_list);
    }
}