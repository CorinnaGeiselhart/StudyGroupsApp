package com.example.studygroups;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public abstract class Firebase {

    private static FirebaseAuth mAuth;
    private static FirebaseUser currentUser;

    public Firebase(){
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
    }

    public static FirebaseUser getCurrentUser (){
        return currentUser;
    }

    public static FirebaseAuth getFirebaseAuth(){
        return mAuth;
    }

}
