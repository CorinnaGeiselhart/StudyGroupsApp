package com.example.studygroups;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

import static com.example.studygroups.NotificationSettingsFragment.userAge;

public class MyProfile extends Fragment {

    ImageView imgView;
    EditText name;
    EditText age;
    EditText mail;
    Button updateButton;

    FirebaseFirestore db;
    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String ageString;
    public static final int GET_FROM_GALLERY = 1;
    String picturePath;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.my_profile, container, false);

        initViews();
        setListeners();

        return v;
    }

    private void initViews() {
        imgView = getView().findViewById(R.id.imageView_myProfile_img);
        name = getView().findViewById(R.id.editText_myProfile_name);
        age = getView().findViewById(R.id.editText_myProfile_age);
        mail = getView().findViewById(R.id.editText_myProfile_age);
        updateButton = getView().findViewById(R.id.button_updateProfile);

        if(user.getPhotoUrl()!= null) {
            String picturePath = user.getPhotoUrl().toString();
            imgView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
        name.setText(user.getDisplayName());
        age.setText(getUserAge());
        mail.setText(user.getEmail());
    }

    private void setListeners(){
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, GET_FROM_GALLERY);
                } else{
                    choosePicture();
                }
            }
        });
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUser();
            }
        });
    }

    private void updateUser(){
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name.getText().toString().trim())
                .setPhotoUri(Uri.parse(picturePath))
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "Deine Änderungen wurden gespeichert.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        //Hier werden weitere Daten des Nutzers in einer collection gesammelt
        userInformation.put("age", age.getText().toString().trim());

        db.collection("studygroups-Accounts").document(user.getUid()).set(userInformation);
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
                    Toast.makeText(getContext(), "Ohne die Erlaubnis kann leider kein Bild hinzugefügt werden", Toast.LENGTH_LONG).show();                }
                break;
        }
    }

    //der Intent um auf die Gallerie zuzugreifen und startet die Methode um Bild rauszusuchen
    private void choosePicture(){
        Intent getPicture = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(getPicture, GET_FROM_GALLERY);
    }

    private String getUserAge(){
        db = FirebaseFirestore.getInstance();
        //Hier hol ich alle Documente aus einer Collection raus
        db.collection("studygroupAccunts").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        // Um aus dem DocumentSnapshot die documentdaten rauszufiltern und in ein neues Objekt "StudyGroup" gepackt und der Liste hinzugefügt
                        db.collection("studygroupsAccounts").document(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        ageString = document.getString("age");
                                    }}}
                        });
                    }
                }
            }
        });
        return ageString;
    }





}
