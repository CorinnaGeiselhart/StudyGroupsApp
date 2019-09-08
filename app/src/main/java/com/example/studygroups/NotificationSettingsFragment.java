package com.example.studygroups;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class NotificationSettingsFragment extends Fragment {

    ListView listView;
    PreferencesListAdapter adapter;
    ArrayList<Preference> prefList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.notification_settings_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initViews();
        fillList();
    }

    private void initViews(){
        listView = getView().findViewById(R.id.listView_preferences);
        adapter = new PreferencesListAdapter(getView().getContext(),prefList);
        listView.setAdapter(adapter);
    }

    private void fillList(){
        prefList.add(new Preference(getString(R.string.notification_group_name), getString(R.string.notification_group_explanation), true));
        prefList.add(new Preference(getString(R.string.notification_reminder_name), getString(R.string.notification_reminder_explanation), true));
        adapter.notifyDataSetChanged();
    }


}
