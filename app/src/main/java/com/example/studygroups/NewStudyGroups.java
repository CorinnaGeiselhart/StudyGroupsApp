package com.example.studygroups;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class NewStudyGroups extends MainActivityFragment {

    private FirebaseFirestore db;

    @Override
    protected void replaceFragment() {
        fragmentTransaction.replace(R.id.layout_MainActivityFrame, details);
    }

    @Override
    protected void fillList(final OnDBComplete onDBComplete) {
        db = FirebaseFirestore.getInstance();
        List<Task<QuerySnapshot>> tasks = new ArrayList<>();
        String[] subjects = getResources().getStringArray(R.array.modul_list);
        for(String subject : subjects){
            tasks.add(db.collection(subject).get());
        }
       Task combindeTask = Tasks.whenAllComplete(tasks).addOnSuccessListener(new OnSuccessListener<List<Task<?>>>() {
           @Override
           public void onSuccess(List<Task<?>> tasks) {
               for (Task t : tasks){
                   QuerySnapshot qs = (QuerySnapshot) t.getResult();
                   for (DocumentSnapshot doc : qs.getDocuments()){
                       list.add(doc.toObject(StudyGroup.class));
                   }
               }
               onDBComplete.onComplete();
           }
       });
    }

    @Override
    protected void setText() {
        header.setText(R.string.text_new_study_groups);
        textIfListIsEmpty.setText(R.string.text_empty_new_study_groups_list);
    }
}