package com.example.studygroups;

import java.io.Serializable;
import java.util.ArrayList;

//Eigenschaften einzelner Lerngruppe
public class StudyGroup implements Serializable {


    private String subject, date, time, place, notes, weekday;
    private ArrayList<String> participants;

    public StudyGroup(){}

    public StudyGroup(String subject, String date, String weekday, String time, String place, String details){
        this.subject = subject;
        this.date = date;
        this.weekday = weekday;
        this.time = time;
        this.place = place;
        this.notes = details;
    }

    public String getNotes() { return notes; }

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

    public ArrayList<String> getParticipants() {
        return participants;
    }

    public String getWeekday() {
        return weekday;
    }
}
