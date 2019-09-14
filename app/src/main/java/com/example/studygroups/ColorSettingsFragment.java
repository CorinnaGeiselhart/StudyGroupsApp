package com.example.studygroups;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
;
import androidx.fragment.app.Fragment;

import static com.example.studygroups.Themes.FIRE;
import static com.example.studygroups.Themes.ICE;
import static com.example.studygroups.Themes.NATURE;
import static com.example.studygroups.Themes.STANDARD;
import static com.example.studygroups.Themes.SUN;

public class ColorSettingsFragment extends Fragment {

    Switch darkSwitch;
    Spinner colorSpinner;

    private boolean isDarkmodeOn;
    private Themes theme;


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
        if(NavigationDrawer.isDarkmodeOn) {
            darkSwitch.setChecked(true);
        }

        colorSpinner = getActivity().findViewById(R.id.spinner_SettingsColor);
    }

    private void setListeners(){
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
        if(checked){
            isDarkmodeOn=true;
            //saveModeForRestart();
        }
        else {
            isDarkmodeOn=false;
            //saveModeForRestart();
        }
        saveModeForRestart();

    }


    private void changeColorScheme(){
        switch (colorSpinner.getSelectedItem().toString()){
            case "Faculty": {
                theme=STANDARD;
                saveColorForRestart();
                restartApp();
                break;
            }
            case "Ice": {
                theme=ICE;
                saveColorForRestart();
                restartApp();
                break;
            }
            case "Fire": {
                theme=FIRE;
                saveColorForRestart();
                restartApp();
                break;
            }
            case "Sun": {
                theme=SUN;
                saveColorForRestart();
                restartApp();
                break;
            }
            case "Nature": {
                theme=NATURE;
                saveColorForRestart();
                restartApp();
                break;
            }
            default: {}
        }
    }

    private void saveModeForRestart(){
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(getString(R.string.pref_mode_key), isDarkmodeOn);
        editor.commit();
    }

    private void saveColorForRestart(){
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getString(R.string.pref_color_key), theme.toString());
        editor.commit();
    }

    private void restartApp() {
        Intent i = new Intent (getActivity(), NavigationDrawer.class);
        startActivity(i);
        getActivity().finish();
    }

}
