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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
;
import androidx.fragment.app.Fragment;




public class ColorSettingsFragment extends Fragment {

    Switch darkSwitch;
    Spinner colorSpinner;

    static String darkmode = "darkmodeNo";
    static String colorTheme = "STANDARD";


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
        if(darkmode.equals("darkmodeYes")) {
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
            darkmode="darkmodeYes";
            //saveModeForRestart();
        }
        else {
            darkmode="darkmodeNo";
            //saveModeForRestart();
        }
        saveModeForRestart();

    }


    private void changeColorScheme(){
        switch (colorSpinner.getSelectedItem().toString()){
            case "Faculty": {
                colorTheme = "STANDARD";
                saveColorForRestart();
                restartApp();
                break;
            }
            case "Ice": {
                colorTheme = "ICE";
                saveColorForRestart();
                restartApp();
                break;
            }
            case "Fire": {
                colorTheme = "FIRE";
                saveColorForRestart();
                restartApp();
                break;
            }
            case "Sun": {
                colorTheme = "SUN";
                saveColorForRestart();
                restartApp();
                break;
            }
            case "Nature": {
                colorTheme = "NATURE";
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
        editor.putString(getString(R.string.pref_mode_key), darkmode);
        editor.commit();
    }

    private void saveColorForRestart(){
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getString(R.string.pref_color_key), colorTheme);
        editor.commit();
    }

    private void restartApp() {
        Intent i = new Intent (getActivity(), MainActivity.class);
        startActivity(i);
        getActivity().finish();
    }

}
