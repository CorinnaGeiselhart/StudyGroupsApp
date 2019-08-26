package com.example.studygroups;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        addFragments();

        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    private void addFragments(){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FragmentMyStudyGroups fragmentMyStudyGroups = new FragmentMyStudyGroups();
        NewStudyGroups newStudyGroups = new NewStudyGroups();
        fragmentTransaction.add(R.id.myGroups_host, fragmentMyStudyGroups);
        fragmentTransaction.add(R.id.newGroups_host, newStudyGroups);
        fragmentTransaction.commit();
    }

}
