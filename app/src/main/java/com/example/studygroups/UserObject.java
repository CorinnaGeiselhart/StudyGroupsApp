package com.example.studygroups;

import java.io.Serializable;

public class UserObject implements Serializable {

    private String id, Username;

    public UserObject(){}
    public UserObject(String id, String Username){
        this.id = id;
        this.Username =Username;
    }

    public String getId(){
        return id;
    }

    public String getUsername(){
        return Username;
    }

}

