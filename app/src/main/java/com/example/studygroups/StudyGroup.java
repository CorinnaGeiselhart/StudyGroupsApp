package com.example.studygroups;

import android.telecom.Call;

import java.util.ArrayList;

//Eigenschaften einzelner Lerngruppe
public class StudyGroup {


    private String subject, date, time, place, details;
    private ArrayList<String> participants;

    public StudyGroup(String subject, String date, String time, String place, String details){
        this.subject = subject;
        this.date = date;
        this.time = time;
        this.place = place;
        this.details = details;
    }

    public String getDetails() { return details; }

    public String getDate() {
        return date;
    }

    public String getPlace() {
        return place;
    }

    public String getTime(){
        return time;
    }

    public String getSubject(){
        return subject;
    }
}
