package com.example.studygroups;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;


public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent startActivity = new Intent(MainActivity.this, ProfileFirebase.class);
        startActivity(startActivity);
        setContentView(R.layout.activity_main);

    }
}
