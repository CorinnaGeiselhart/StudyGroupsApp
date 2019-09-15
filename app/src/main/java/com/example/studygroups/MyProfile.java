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

import static android.app.Activity.RESULT_OK;


public class MyProfile extends Fragment {

    private ImageView imgView;
    private EditText name;
    private EditText age;
    private TextView mail;
    private Button updateButton;

    private FirebaseFirestore db;
    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private String ageString;
    public static final int GET_FROM_GALLERY = 1;
    private String picturePath;

    View v;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.my_profile, container, false);

        initViews();
        setListeners();

        return v;
    }

    private void initViews() {
        imgView = v.findViewById(R.id.imageView_img);
        name = v.findViewById(R.id.editText_myProfile_name);
        age = v.findViewById(R.id.editText_myProfile_age);
        mail = v.findViewById(R.id.textView_myProfile_mail);
        updateButton = v.findViewById(R.id.button_updateProfile);

        if(user.getPhotoUrl()!= null) {
            String picturePath = user.getPhotoUrl().toString();
            imgView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
        name.setText(user.getDisplayName());
        mail.setText(user.getEmail());
        getUserAge(new OnDBComplete() {
            @Override
            public void onComplete() {
                age.setText(ageString);
            }
        });
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
                            Toast.makeText(getContext(), getString(R.string.update_profile_successfull), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        db.collection("studygroups-Accounts").document(user.getUid()).update("age", age.getText().toString().trim());
        updateNavigationDrawerHeader();
    }

    private void updateNavigationDrawerHeader(){
        TextView username = getActivity().findViewById(R.id.textView_NavBarUsername);
        username.setText(name.getText().toString().trim());

        ImageView profilePicture = getActivity().findViewById(R.id.imageView_NavBarPPicture);
        profilePicture.setImageBitmap(BitmapFactory.decodeFile(picturePath));
    }

    //greift auf die Gallerie zu um ein Bild zu bekommen
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GET_FROM_GALLERY && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath= cursor.getString(columnIndex);
            cursor.close();

            imgView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
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

    private void getUserAge(final OnDBComplete onDBComplete){
        db = FirebaseFirestore.getInstance();
        //Hier hol ich alle Documente aus einer Collection raus
        db.collection("studygroups-Accounts").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        // Um aus dem DocumentSnapshot die documentdaten rauszufiltern und in ein neues Objekt "StudyGroup" gepackt und der Liste hinzugefügt
                        db.collection("studygroups-Accounts").document(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        ageString = document.getString("age");
                                        onDBComplete.onComplete();
                                    }}}
                        });
                    }
                }
            }
        });
    }


}
