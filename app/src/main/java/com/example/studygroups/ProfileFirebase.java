package com.example.studygroups;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ProfileFirebase extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private EditText name;
    private String email;

    private EditText password;
    private String newPassword;

    private Button login;
    Dialog myDialog;
    private static final String TAG = "ProfileFirebaseLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        checkLogin();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        findViews();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = name.getText().toString().trim();
                newPassword = password.getText().toString().trim();
                if(email.isEmpty() || newPassword.isEmpty()) {
                    Toast.makeText(ProfileFirebase.this,
                            "Please enter Email and Password", Toast.LENGTH_SHORT).show();
                } else {
                    signIn();
                }
            }
        });
        mDatabase = FirebaseDatabase.getInstance().getReference();
        myDialog = new Dialog (this);

    }

    private void checkLogin() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            startScreen();
        }
    }

    private void startScreen() {
        Intent startScreen = new Intent(ProfileFirebase.this, MainActivity.class);
        //überspringt Login-activity beim Back_button drücken am Handy
        startScreen.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(startScreen);
    }

    private void findViews() {
        name = findViewById(R.id.editText_Name);
        password = findViewById(R.id.editText_Password);
        login = findViewById(R.id.button_Login);
    }

    private void createNewUser() {
        mAuth.createUserWithEmailAndPassword(email, newPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            mDatabase.push();
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent createAcc = new Intent(ProfileFirebase.this, StartView.class);
                            startActivity(createAcc);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(ProfileFirebase.this, "Überprüfe deine E-mail und dein Password(mindestens 6 Zeichen)",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void signIn(){
        mAuth.signInWithEmailAndPassword(email, newPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            checkLogin();
                            startScreen();
                        } else {
                            createPopup();
                        }
                    }
                });

    }

    public void signOut(){
        FirebaseAuth.getInstance().signOut();
    }


    //User wird gefragt ob er einen neuen Account erstellen will oder sich beim Passwort/Email verschrieben hat
    private void createPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setCancelable(true)
                .setTitle("Email existiert nicht")
                .setMessage("Diese E-mail kennen wir nicht. Überprüfen Sie ihre Eingabe oder legen Sie ein neues Profil an")
                .setPositiveButton("Registrieren", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        createNewUser();
                    }
                })
                .setNegativeButton("Zurück", null);
        builder.create().show();
    }
}