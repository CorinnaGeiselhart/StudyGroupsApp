package com.example.studygroups;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;


public class Settings extends Fragment {

    TextView colorView;
    TextView colorViewX;
    TextView notificationView;
    TextView notificationViewX;
    Button logout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.settings_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // TODO: 20.08.2019 ListView für Einstellungen
        initViews();
        setListeners();
    }

    private void setListeners() {
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileFirebase firebase = new ProfileFirebase();
                firebase.signOut();
                Intent signout = new Intent(getActivity(),ProfileFirebase.class);
                startActivity(signout);
            }
        });

        colorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("color was clicked");
                openColorFragment();
            }
        });

        colorViewX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("color was clicked");
                openColorFragment();
            }
        });

        notificationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //openNotificationFragment();
            }
        });
        notificationViewX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //openNotificationFragment();
            }
        });

    }

    private void initViews() {
        logout = getView().findViewById(R.id.button_Logout);
        colorView = getView().findViewById(R.id.textView_ColorSettings);
        colorViewX = getView().findViewById(R.id.textView_colorsetting_explanation);
        notificationView = getView().findViewById(R.id.textView_notificationpreferences);
        notificationViewX = getView().findViewById(R.id.textView_notificationpreferences_explanation);


        /**colorSwitchView = getView().findViewById(R.id.color_switch_view);
        if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES) {
            colorSwitchView.setChecked(true);
        }
        colorSwitchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeColorMode(colorSwitchView.isChecked());
                restartApp();
            }
        });*/
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

    private void openColorFragment(){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ColorSettingsFragment colorSettingsFragment = new ColorSettingsFragment();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.nav_host, colorSettingsFragment);
        fragmentTransaction.commit();
    }

}
