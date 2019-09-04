package com.example.studygroups;


public class NewStudyGroups extends MainActivityFragment{

    @Override
    protected void replaceFragment() {
        fragmentTransaction.replace(R.id.layout_MainActivityFrame, details);
    }

    @Override
    protected void fillList() {
        //Beispiel, da Datenbank noch nicht erstellt
        list.add(new StudyGroup("EIMI", "Montag, 12.08.2019", "19:00", "Universität Regensburg",""));
    }

    @Override
    protected void setText() {
        header.setText(R.string.text_new_study_groups);
        textIfListIsEmpty.setText(R.string.text_empty_new_study_groups_list);
    }
}