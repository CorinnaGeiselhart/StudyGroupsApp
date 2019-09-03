package com.example.studygroups;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class StartView extends AppCompatActivity {

    private Button weiter;
    private Button addPicture;
    private EditText username;
    private EditText age;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_account);
        initViews();
        weiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upDateUser();
            }
        });
    }

    private void initViews(){
        weiter = findViewById(R.id.button_Next);
        username = findViewById(R.id.editText_Name);
        age = findViewById(R.id.editText_age);
        addPicture = findViewById(R.id.button_addImage);

    }

    private void upDateUser(){
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(username.getText().toString().trim())
                .setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(StartView.this, user.getDisplayName(),
                                    Toast.LENGTH_SHORT).show();
                            Intent mainActivity = new Intent(StartView.this,MainActivity.class);
                            startActivity(mainActivity);
                        }
                    }
                });

    }
}
