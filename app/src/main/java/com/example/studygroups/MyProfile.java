package com.example.studygroups;


import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MyProfile extends Fragment {

    private ImageView profilePicture;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        initViews();
        setUserInformation();

        return inflater.inflate(R.layout.my_profile, container, false);
    }

    private void initViews(){
        profilePicture = (ImageView) getActivity().findViewById(R.id.imageView_Profile_settings_picture);
    }

    private void setUserInformation(){
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user.getPhotoUrl()!= null) {
            String picturePath = user.getPhotoUrl().toString();
            profilePicture.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
    }

}
