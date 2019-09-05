package com.example.studygroups;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatDelegate;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;


public class Settings extends Fragment {

    Switch colorSwitchView;
    Button Logout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.settings_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // TODO: 20.08.2019 ListView für Einstellungen
        initViews();
    }

    private void initViews() {
        Logout = getView().findViewById(R.id.button_Logout);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileFirebase firebase = new ProfileFirebase();
                firebase.signOut();
                Intent signout = new Intent(getActivity(),ProfileFirebase.class);
                startActivity(signout);
            }
        });

        colorSwitchView = getView().findViewById(R.id.switch_Color);
        if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES) {
            colorSwitchView.setChecked(true);
        }
        colorSwitchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeColorMode(colorSwitchView.isChecked());
                restartApp();
            }
        });
    }

    private void changeColorMode(boolean checked) {
        if(checked)AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }

    private void restartApp() {
        Intent i = new Intent (getActivity(), MainActivity.class);
        startActivity(i);
        getActivity().finish();
    }

}
