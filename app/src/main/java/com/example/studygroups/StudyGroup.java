package com.example.studygroups;

import java.util.ArrayList;

//Eigenschaften einzelner Lerngruppe
public class StudyGroup {


    private String subject, date, time, place;
    private ArrayList<String> participants;

    public StudyGroup(String subject, String date, String time, String place){
        this.subject = subject;
        this.date = date;
        this.time = time;
        this.place = place;
    }

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
