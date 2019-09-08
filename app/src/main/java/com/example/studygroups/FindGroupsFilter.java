package com.example.studygroups;

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
import android.widget.CheckBox;
import android.widget.Spinner;

public class FindGroupsFilter extends Fragment {

    CheckBox monday, tuesday,wednesday,thursday, friday,saturday, sunday;
    Spinner modulePicker;
    Button searchButton;
    View view;
    public static final int REQUEST_CODE = 1;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.filter_find_group, container, false);
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
                checkBox(data);

                getTargetFragment().onActivityResult(getTargetRequestCode(),REQUEST_CODE, data);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_Host, new FindGroups());
                fragmentTransaction.commit();
            }
        });

    }

    private void checkBox(Intent i) {
        //Montag
        if(monday.isChecked()){
            i.putExtra(getResources().getString(R.string.key_monday), getResources().getString(R.string.key_monday));
        }else if(!monday.isChecked()){
            i.putExtra(getResources().getString(R.string.key_monday), "");
        }
        //Dienstag
        if(tuesday.isChecked()){
            i.putExtra(getResources().getString(R.string.key_tuesday), getResources().getString(R.string.key_tuesday));
        }else if(!tuesday.isChecked()){
            i.putExtra(getResources().getString(R.string.key_tuesday), "");
        }
        //Mittwoch
        if(wednesday.isChecked()){
            i.putExtra(getResources().getString(R.string.key_wednesday), getResources().getString(R.string.key_wednesday));
        }else if(!wednesday.isChecked()){
            i.putExtra(getResources().getString(R.string.key_wednesday), "");
        }
        //Donnerstag
        if(thursday.isChecked()){
            i.putExtra(getResources().getString(R.string.key_thursday), getResources().getString(R.string.key_thursday));
        }else if(!thursday.isChecked()){
            i.putExtra(getResources().getString(R.string.key_thursday), "");
        }
        //Freitag
        if(friday.isChecked()){
            i.putExtra(getResources().getString(R.string.key_friday), getResources().getString(R.string.key_friday));
        }else if(!friday.isChecked()){
            i.putExtra(getResources().getString(R.string.key_friday), "");
        }
        //Samstag
        if(saturday.isChecked()){
            i.putExtra(getResources().getString(R.string.key_saturday), getResources().getString(R.string.key_saturday));
        }else if(!saturday.isChecked()){
            i.putExtra(getResources().getString(R.string.key_saturday), "");
        }
        //Sonntag
        if(sunday.isChecked()){
            i.putExtra(getResources().getString(R.string.key_sunday), getResources().getString(R.string.key_sunday));
        }else if(!sunday.isChecked()){
            i.putExtra(getResources().getString(R.string.key_sunday), "");
        }
    }

    private void findViews() {
        monday = view.findViewById(R.id.checkBox_Mo);
        tuesday = view.findViewById(R.id.checkBox_Tue);
        wednesday = view.findViewById(R.id.checkBox_Wed);
        thursday = view.findViewById(R.id.checkBox_Thur);
        friday = view.findViewById(R.id.checkBox_Fr);
        saturday = view.findViewById(R.id.checkBox_Sat);
        sunday = view.findViewById(R.id.checkBox_Sun);

        modulePicker = view.findViewById(R.id.spinner_Module);
        searchButton = view.findViewById(R.id.button_Search);
    }


}
