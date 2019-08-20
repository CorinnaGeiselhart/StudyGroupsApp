package com.example.studygroups;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
                //get, which checkbox is selected
                //Intent Daten mitgeben

                Intent intent = new Intent();
                finish();
            }
        });

        //cancelBitton on Click Listener
        //ist es geschickter implement onClickListener?
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
