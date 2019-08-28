package com.example.studygroups;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

public class FilterFindGroups extends AppCompatActivity {

    CheckBox monday, tuesday,wednesday,thursday, friday,saturday, sunday;
    Spinner modulePicker;
    Button searchButton, cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_find_group);

        findViews();
        initButton();
    }

    private void initButton() {
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Intent data = new Intent();
                //check which checkbox was selected
                checkBox(data);

                setResult(RESULT_OK, data);
                finish();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
        monday = findViewById(R.id.checkBox_Mo);
        tuesday = findViewById(R.id.checkBox_Tue);
        wednesday = findViewById(R.id.checkBox_Wed);
        thursday = findViewById(R.id.checkBox_Thur);
        friday = findViewById(R.id.checkBox_Fr);
        saturday = findViewById(R.id.checkBox_Sat);
        sunday = findViewById(R.id.checkBox_Sun);

        modulePicker = findViewById(R.id.spinner_Module);
        searchButton = findViewById(R.id.button_Search);
        cancelButton = findViewById(R.id.button_Cancel);
    }


}
