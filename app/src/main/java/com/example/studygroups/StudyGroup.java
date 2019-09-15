package com.example.studygroups;

import java.io.Serializable;
import java.util.ArrayList;

//Eigenschaften einzelner Lerngruppe
public class StudyGroup implements Serializable {


    private String subject, date, time, place, notes, weekday, id;
    private ArrayList<String> participantsIds = new ArrayList<>();
    private ArrayList<String> participantsNames = new ArrayList<>();

    public StudyGroup(){}

    public StudyGroup(String subject, String date, String weekday, String time, String place, String details, String id,String participantId, String participantName){
        this.subject = subject;
        this.date = date;
        this.weekday = weekday;
        this.time = time;
        this.place = place;
        this.notes = details;
        this.id = id;
        participantsIds.add(participantId);
        participantsNames.add(participantName);
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

    public String getId() {return id;}

    public ArrayList<String> getParticipantsIds() {
        return participantsIds;
    }

    public void addNewUserId(String userId){
        participantsIds.add(userId);
    }

    public void removeUserId(String userId){
        participantsIds.remove(userId);
    }

    public ArrayList<String> getParticipantsNames() {
        return participantsNames;
    }

    public void addNewUserName(String userName){
        participantsNames.add(userName);
    }

    public void removeUserName(String userName){
        participantsNames.remove(userName);
    }

    public String getWeekday() {
        return weekday;
    }
}
