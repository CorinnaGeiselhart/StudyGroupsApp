package com.example.studygroups;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import static com.example.studygroups.Themes.FIRE;
import static com.example.studygroups.Themes.ICE;
import static com.example.studygroups.Themes.NATURE;
import static com.example.studygroups.Themes.STANDARD;
import static com.example.studygroups.Themes.SUN;

public class ColorSettingsFragment extends Fragment {

    Switch darkSwitch;
    Spinner colorSpinner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.color_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initViews();
        setListeners();
    }

    private void initViews(){
        darkSwitch = getActivity().findViewById(R.id.switch_SettingsDarkmode);
        if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES) {
            darkSwitch.setChecked(true);
        }

        colorSpinner = getActivity().findViewById(R.id.spinner_SettingsColor);
    }

    private void setListeners(){ ;
        darkSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeColorMode(darkSwitch.isChecked());
                restartApp();
            }
        });

        colorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                changeColorScheme();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void changeColorMode(boolean checked) {
        if(checked)AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }

    private void changeColorScheme(){
        switch (colorSpinner.getSelectedItem().toString()){
            case "Faculty": {
                MainActivity.theme=STANDARD;
                restartApp();
                break;
            }
            case "Ice": {
                MainActivity.theme=ICE;
                restartApp();
                break;
            }
            case "Fire": {
                MainActivity.theme=FIRE;
                restartApp();
                break;
            }
            case "Sun": {
                MainActivity.theme=SUN;
                restartApp();
                break;
            }
            case "Nature": {
                MainActivity.theme=NATURE;
                restartApp();
                break;
            }
        }
    }

    private void restartApp() {
        Intent i = new Intent (getActivity(), MainActivity.class);
        startActivity(i);
        getActivity().finish();
    }

}
