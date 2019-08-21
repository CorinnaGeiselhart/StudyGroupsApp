package com.example.studygroups;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.main_fragment, container, false);

        addFragemnts();

        return v;
    }

    private void addFragemnts(){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FragmentMyStudyGroups fragmentMyStudyGroups = new FragmentMyStudyGroups();
        NewStudyGroups newStudyGroups = new NewStudyGroups();
        fragmentTransaction.add(R.id.myGroups_host, fragmentMyStudyGroups);
        fragmentTransaction.add(R.id.newGroups_host, newStudyGroups);
        fragmentTransaction.commit();
    }

}
