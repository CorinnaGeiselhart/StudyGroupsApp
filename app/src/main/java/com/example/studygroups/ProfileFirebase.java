package com.example.studygroups;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.List;


public class ProfileFirebase extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private EditText name;
    private String email;

    private EditText password;
    private String newPassword;

    private Button login;
    Dialog myDialog;
    TextView txtclose;
    private static final String TAG = "ProfileFirebaseLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        //checkLogin();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        findViews();
        login.setOnClickListener(this);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        myDialog = new Dialog(this);

    }

    private void checkLogin(){
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }
    }

    private void findViews() {
        name = findViewById(R.id.editText_Name);
        password = findViewById(R.id.editText_Password);
        login = findViewById(R.id.button_Login);

    }
    private boolean validateForm() {
        boolean valid = true;

        String email = name.getText().toString();
        if (TextUtils.isEmpty(email)) {
            name.setError("Required.");
            valid = false;
        } else {
            name.setError(null);
        }

        String password = name.getText().toString();
        if (TextUtils.isEmpty(password)) {
            name.setError("Required.");
            valid = false;
        } else {
            name.setError(null);
        }

        return valid;
    }

    private void createNewUser() {
        mAuth.createUserWithEmailAndPassword(email, newPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            mDatabase.push();
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(ProfileFirebase.this, "Signed in",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(ProfileFirebase.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }
    private void accesUserInformation(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();
        }

    }

    private void signIn(){
        mAuth.signInWithEmailAndPassword(email, newPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            checkLogin();
                            Toast.makeText(ProfileFirebase.this, "login",
                                    Toast.LENGTH_SHORT).show();
                            Intent goToStart = new Intent(ProfileFirebase.this, MainActivity.class);
                            startActivity(goToStart);
                        } else {
                            createPopup();
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());


                        }

                        // ...
                    }
                });

    }
    private void createPopup(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setCancelable(true)
                .setTitle("E-mail existiert nicht")
                .setMessage("Diese E-mail kennen wir nicht. Überprüfen Sie Ihre Eingabe oder legen Sie einen neuen Account an")
                .setPositiveButton("Registrieren", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        createNewUser();
                    }
                })
                .setNegativeButton("Zurück",null);

        builder.create().show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_Login:
                email = name.getText().toString().trim();
                newPassword = password.getText().toString().trim();
                if(email.isEmpty() || newPassword.isEmpty()){
                    Toast.makeText(ProfileFirebase.this, "Please enter E-mail and Password",
                            Toast.LENGTH_SHORT).show();
                } else{
                    signIn();
                }
                break;
        }
    }
}

