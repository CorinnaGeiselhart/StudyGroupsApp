package com.example.studygroups;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class CreateGroupFragment extends Fragment {

    Spinner modulPicker;
    EditText datePicker;
    EditText timePicker;
    EditText locationView;
    EditText notesView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initViews();
        return inflater.inflate(R.layout.create_group_fragment, container, false);
    }

    private void initViews(){
        modulPicker = getActivity().findViewById (R.id.modul_view);
        datePicker = getActivity().findViewById (R.id.date_view);
        timePicker = getActivity().findViewById (R.id.time_view);
        locationView = getActivity().findViewById (R.id.location_view);
        notesView = getActivity().findViewById (R.id.notes_view);
    }
}
