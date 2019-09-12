package com.example.studygroups;

import android.content.Intent;
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
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import static com.example.studygroups.ProfileNewAccount.getUserInformation;
import static com.example.studygroups.Themes.FIRE;
import static com.example.studygroups.Themes.ICE;
import static com.example.studygroups.Themes.NATURE;
import static com.example.studygroups.Themes.STANDARD;
import static com.example.studygroups.Themes.SUN;

public class ColorSettingsFragment extends Fragment {

    Switch darkSwitch;
    Spinner colorSpinner;
    FirebaseFirestore db;
    ProfileNewAccount profileNewAccount;
    static String darkmode = "darkmodeNo";
    static String colorTheme = "STANDARD";
    public static String userAge;
    public static Map<String, String> map;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.color_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        getUserTheme();
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
            saveColorToUser();
            //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else {
            darkmode="darkmodeNo";
            saveColorToUser();
            //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

    }

    private void changeColorScheme(){
        switch (colorSpinner.getSelectedItem().toString()){
            case "Faculty": {
                colorTheme = "STANDARD";
                saveColorToUser();
                restartApp();
                break;
            }
            case "Ice": {
                colorTheme = "ICE";
                saveColorToUser();
                restartApp();
                break;
            }
            case "Fire": {
                colorTheme = "FIRE";
                saveColorToUser();
                restartApp();
                break;
            }
            case "Sun": {
                colorTheme = "SUN";
                saveColorToUser();
                restartApp();
                break;
            }
            case "Nature": {
                colorTheme = "NATURE";
                saveColorToUser();
                restartApp();
                break;
            }
            default: {}
        }
    }

    private void getUserTheme(){
        db = FirebaseFirestore.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        //Hier hol ich alle Documente aus einer Collection raus
        db.collection("studygroups-Accounts").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        // Um aus dem DocumentSnapshot die documentdaten rauszufiltern und in ein neues Objekt "StudyGroup" gepackt und der Liste hinzugefügt
                        db.collection("studygroups-Accounts").document(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        darkmode = document.getString("mode");
                                        colorTheme = document.getString("theme");
                                    }
                                }}
                        });
                    }
                }
            }
        });
    }

    private void saveColorToUser() {
        db = FirebaseFirestore.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        /*getUserInformation().put("age", userAge);
        getUserInformation().put("theme", colorTheme);
        getUserInformation().put("mode", darkmode);


        db.collection("studygroups-Accounts").document(user.getUid()).set(getUserInformation());*/

        /*Map<String, String> map = new HashMap<>();
        map.put("age", userAge);
        map.put("theme", colorTheme);
        map.put("mode", darkmode);

        db.collection("studygroups-Accounts").document(user.getUid()).set(map);*/

        db.collection("studygroups-Accounts").document(user.getUid()).update("theme", colorTheme);
        db.collection("studygroups-Accounts").document(user.getUid()).update("mode", darkmode);
    }


    /*private void saveModeToUser(String darkmodeOn){
        db = FirebaseFirestore.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        profileNewAccount.getUserInformation().put("mode", darkmodeOn);

        db.collection("studygroups-Accounts").document(user.getUid()).set(profileNewAccount.getUserInformation());
    }*/

    private void restartApp() {
        Intent i = new Intent (getActivity(), MainActivity.class);
        startActivity(i);
        getActivity().finish();
    }

}
