package com.example.studygroups.Settings;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.studygroups.Profile.ProfileFirebase;
import com.example.studygroups.R;


public class MainSettings extends Fragment {

    TextView colorView;
    TextView colorViewX;
    TextView notificationView;
    TextView notificationViewX;
    Button logout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.settings, container, false);
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
                openNotificationFragment();
            }
        });
        notificationViewX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNotificationFragment();
            }
        });

    }

    private void initViews() {
        logout = getView().findViewById(R.id.button_Logout);
        colorView = getView().findViewById(R.id.textView_ColorSettings);
        colorViewX = getView().findViewById(R.id.textView_colorsetting_explanation);
        notificationView = getView().findViewById(R.id.textView_notificationpreferences);
        notificationViewX = getView().findViewById(R.id.textView_NotificationsExplanation);
    }

    private void openColorFragment(){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ColorsSettings colorsSettings = new ColorsSettings();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.nav_Host, colorsSettings);

        fragmentTransaction.commit();
    }

    private void openNotificationFragment(){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        NotificationSettings notificationSettings = new NotificationSettings();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.nav_Host, notificationSettings);

        fragmentTransaction.commit();
    }

}
