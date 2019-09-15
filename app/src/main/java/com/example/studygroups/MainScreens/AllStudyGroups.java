package com.example.studygroups.MainScreens;

import com.example.studygroups.R;

public class AllStudyGroups extends ListViewFragment {

    @Override
    protected void replaceFragment() {
        fragmentTransaction.replace(R.id.layout_MainActivityFrame, details);
    }

    @Override
    protected void specifyList() {
        listStudyGroups = allStudyGroups;
    }

    @Override
    protected void setText() {
        header.setText(R.string.text_new_study_groups);
        textIfListIsEmpty.setText(R.string.text_empty_new_study_groups_list);
    }
}