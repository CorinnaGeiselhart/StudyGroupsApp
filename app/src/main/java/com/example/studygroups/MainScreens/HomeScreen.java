package com.example.studygroups.MainScreens;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.studygroups.R;


public class HomeScreen extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main_frame, container, false);
        addFragments();
        return view;
    }

    private void addFragments() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MyStudyGroups myStudyGroups = new MyStudyGroups();
        AllStudyGroups allStudyGroups = new AllStudyGroups();
        fragmentTransaction.add(R.id.myGroups_Host, myStudyGroups);
        fragmentTransaction.add(R.id.newGroups_Host, allStudyGroups);
        fragmentTransaction.commit();
    }
}
