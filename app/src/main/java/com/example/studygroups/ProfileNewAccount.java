package com.example.studygroups;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ProfileNewAccount extends AppCompatActivity {

    private Button next;
    private Button addPicture;
    private EditText username;
    private EditText age;
    private ImageView profilePicture;
    String picturePath;

    public static final int GET_FROM_GALLERY = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_account);
        initViews();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upDateUser();
            }
        });
        //Permissionabfrage + Bild hinzufügen
        addPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ActivityCompat.checkSelfPermission(ProfileNewAccount.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(ProfileNewAccount.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, GET_FROM_GALLERY);
                } else{
                    choosePicture();
                }
            }
        });
    }
    //der Intent um auf die Gallerie zuzugreifen und startet die Methode um Bild rauszusuchen
    private void choosePicture(){
        Intent getPicture = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(getPicture, GET_FROM_GALLERY);
    }

    private void initViews(){
        next = findViewById(R.id.button_Next);
        username = findViewById(R.id.editText_Name);
        age = findViewById(R.id.editText_Age);
        addPicture = findViewById(R.id.button_AddImage);
        profilePicture = findViewById(R.id.imageView_ProfilePicture);

    }

    private void upDateUser(){
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(username.getText().toString().trim())
                .setPhotoUri(Uri.parse(picturePath))
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Intent mainActivity = new Intent(ProfileNewAccount.this,MainActivity.class);
                            startActivity(mainActivity);
                        }
                    }
                });
        //Hier werden weitere Daten des Nutzers in einer collection gesammelt
        Map<String, String> userInformation = new HashMap<>();
        userInformation.put("age", age.getText().toString().trim());

    }
    //greift auf die Gallerie zu um ein Bild zu bekommen
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GET_FROM_GALLERY && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath= cursor.getString(columnIndex);
            cursor.close();

            profilePicture.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
    }
    //Entscheidet was bei den zwei Optionen der Permissionabfrage passiert
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults)
    {
        switch (requestCode) {
            case GET_FROM_GALLERY:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    choosePicture();
                } else {
                    Toast.makeText(this, "Ohne die Erlaubnis kann leider kein Bild hinzugefügt werden", Toast.LENGTH_LONG).show();                }
                break;
        }
    }
}
