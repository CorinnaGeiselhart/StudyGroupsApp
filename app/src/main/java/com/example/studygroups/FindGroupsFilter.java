package com.example.studygroups;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

public class FindGroupsFilter extends Fragment {

    CheckBox monday, tuesday,wednesday,thursday, friday,saturday, sunday;
    CheckBox[] weekdays = new CheckBox[7];
    //String[] keys = new String[7];
    Spinner modulePicker;
    Button searchButton;
    View view;
    public static final int REQUEST_CODE = 1;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.filter_find_group, container, false);
        Log.d("Test", "FindView");
        findViews();
        initButton();
        return view;
    }

    private void initButton() {
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent data = new Intent();
                //check which checkbox was selected
                getData(data);

                getTargetFragment().onActivityResult(getTargetRequestCode(),REQUEST_CODE, data);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_Host, new FindGroupsFilter());
                fragmentTransaction.commit();
            }
        });

    }

    private void getData(Intent i) {
        getSubject(i);

        String[] keys = {getResources().getString(R.string.key_monday),
                getResources().getString(R.string.key_tuesday),
                getResources().getString(R.string.key_wednesday),
                getResources().getString(R.string.key_thursday),
                getResources().getString(R.string.key_friday),
                getResources().getString(R.string.key_saturday),
                getResources().getString(R.string.key_sunday)};
        for(int x = 0; x < weekdays.length; x++){
            checkStatus(weekdays[x], keys[x], i);
        }
    }

    private void getSubject(Intent i) {
        String subject = modulePicker.getSelectedItem().toString();
        i.putExtra(getResources().getString(R.string.key_subject), subject);
        Log.d("Fach", subject);
    }

    private void checkStatus(CheckBox checkBox,String keyValue, Intent i){
        if(checkBox.isChecked()){
            i.putExtra(keyValue, keyValue);
        }else if(!checkBox.isChecked()){
            i.putExtra(keyValue, "");
        }
    }

    private void findViews() {
        monday = view.findViewById(R.id.checkBox_Mo);
        weekdays[0] = monday;
        tuesday = view.findViewById(R.id.checkBox_Tue);
        weekdays[1] = tuesday;
        wednesday = view.findViewById(R.id.checkBox_Wed);
        weekdays[2] = wednesday;
        thursday = view.findViewById(R.id.checkBox_Thur);
        weekdays[3] = thursday;
        friday = view.findViewById(R.id.checkBox_Fr);
        weekdays[4] = friday;
        saturday = view.findViewById(R.id.checkBox_Sat);
        weekdays[5] = saturday;
        sunday = view.findViewById(R.id.checkBox_Sun);
        weekdays[6] = sunday;

        modulePicker = view.findViewById(R.id.spinner_Module);
        searchButton = view.findViewById(R.id.button_Search);
    }


}
