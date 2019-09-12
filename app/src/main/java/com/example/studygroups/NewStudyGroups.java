package com.example.studygroups;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewStudyGroups extends MainActivityFragment {

    private FirebaseFirestore db;

    @Override
    protected void replaceFragment() {
        fragmentTransaction.replace(R.id.layout_MainActivityFrame, details);
    }

    @Override
    protected void fillList(final OnDBComplete onDBComplete) {
        db = FirebaseFirestore.getInstance();
        //Hier hol ich alle Documente aus einer Collection raus
       /* db.collection("Einführung in die objektorientierte Programmierung").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        // Um aus dem DocumentSnapshot die documentdaten rauszufiltern und in ein neues Objekt "StudyGroup" gepackt und der Liste hinzugefügt
                        db.collection("Einführung in die objektorientierte Programmierung").document(document.getId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                            String subject = document.getString("subject");
                                            String date = document.getString("date");
                                            String weekday = document.getString("weekday");
                                            String time = document.getString("time");
                                            String place = document.getString("place");
                                            String details = document.getString("details");

                                            list.add(new StudyGroup(subject, date, weekday, time, place, details));
                                            onDBComplete.onComplete();

                                    }}}
                        });
                    }
                }
            }
        });
       db.collection("Vorlesungsfächer").document("Einführung in die Medieninformatik").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
           @Override
           public void onSuccess(DocumentSnapshot documentSnapshot) {
                StudyGroup newEntry = documentSnapshot.toObject(StudyGroup.class);
               Log.d("Eintrag", "Cached document data: " + newEntry);
               list.add(newEntry);
               onDBComplete.onComplete();
           }
       });*/
        db.collection("Einführung in die Medieninformatik").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot d : queryDocumentSnapshots.getDocuments()) {
                    StudyGroup newEntry = d.toObject(StudyGroup.class);
                    Log.d("SCHEIßOBJEKT", d.getId() + " => " + d.getData());
                    list.add(newEntry);
                }
                onDBComplete.onComplete();
            }
        });
        /*db.collection("Vorlesungsfächer").document("Einführung in die Medieninformatik").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                documentSnapshot.
                StudyGroup newEntry = documentSnapshot.toObject(StudyGroup.class);
                Log.d("Eintrag", "Cached document data: " + newEntry);
                list.add(newEntry);
                onDBComplete.onComplete();
            }
        });
        db.collection("Vorlesungsfächer").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot subject : task.getResult()) {
                       HashMap<String, Object> groups = new HashMap<>(subject.getData());
                       for(Map.Entry<String, Object> entry : groups.entrySet()){

                           list.add();
                       }
                    }
                } else {

                }
            }
        });*/
    }

    @Override
    protected void setText() {
        header.setText(R.string.text_new_study_groups);
        textIfListIsEmpty.setText(R.string.text_empty_new_study_groups_list);
    }
}