package com.example.studygroups;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MyProfile extends Fragment {

    private ImageView profilePicture;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        initViews();

        return inflater.inflate(R.layout.my_profile, container, false);
    }

    private void initViews(){
        profilePicture = getActivity().findViewById(R.id.imageView_Profile_settings_picture);

    }


}
