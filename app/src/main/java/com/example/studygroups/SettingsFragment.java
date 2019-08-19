package com.example.studygroups;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;


public class SettingsFragment extends Fragment {

    Switch colorSwitchView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.settings_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initViews();
    }

    private void initViews() {
        colorSwitchView = getView().findViewById(R.id.color_switch_view);
        colorSwitchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(colorSwitchView.isChecked()) startDarkmode();
                else startLightMode();
            }
        });
    }

    private void startLightMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        restartApp();
    }

    private void startDarkmode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        restartApp();
    }

    private void restartApp() {
        Intent i = new Intent (getActivity(), MainActivity.class);
        startActivity(i);
        getActivity().finish();
    }

}
