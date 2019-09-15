package com.example.studygroups;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;


public class MyStudyGroups extends ListViewFragment {


    private ArrayList<StudyGroup> myStudyGroups = new ArrayList<>();

    @Override
    protected void replaceFragment() {
        String tag = String.valueOf(view.getId()).trim();
        //in Meine Lerngruppen Details aufrufen
        if (tag.equals(String.valueOf(R.id.layout_ActivityMainFragments))) {
            fragmentTransaction.replace(R.id.nav_Host, details);
        }
        //in MainActivit/Home Details aufrufen
        else {
            fragmentTransaction.replace(R.id.layout_MainActivityFrame, details);
        }
    }

    @Override
    protected void specifyList(){
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentUser = user.getUid();

        for(StudyGroup studyGroup: allStudyGroups){
            ArrayList<String> participants = studyGroup.getParticipantsIds();
            if(!participants.equals(null)){
                for(String participant: participants){

                    if(currentUser.equals(participant)){
                        listStudyGroups.add(studyGroup);
                        break;
                    }
                }
            }
        }
    }

    @Override
    protected void setText() {
        textIfListIsEmpty.setText(R.string.text_empty_my_study_groups_list);
        header.setText(R.string.text_my_study_groups);
    }

}