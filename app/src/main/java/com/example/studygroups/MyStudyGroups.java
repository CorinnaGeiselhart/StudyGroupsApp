package com.example.studygroups;


public class MyStudyGroups extends MainActivityFragment {

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
    public void fillList(OnDBComplete onDBComplete) {
        //Beispiel, da Datenbank noch nicht erstellt
        //list.add(new StudyGroup("EIMI", "12.08.2019", "Monday", "19:00", "Universität Regensburg", "Ich freu mich auf euch!"));
    }

    @Override
    protected void setText() {
        textIfListIsEmpty.setText(R.string.text_empty_my_study_groups_list);
        header.setText(R.string.text_my_study_groups);
    }
}