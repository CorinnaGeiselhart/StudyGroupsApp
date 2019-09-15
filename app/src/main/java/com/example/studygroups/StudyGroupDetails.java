package com.example.studygroups;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class StudyGroupDetails extends Fragment {

    private View view;
    private TextView date, time, place, subject, notes;
    private ListView participants;
    private Button signIn;
    private Button signOut;


    private ArrayList<String> participantsList = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    private StudyGroup studyGroup;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private int reminderMinute;
    private int reminderHour;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.study_group_details, container, false);
        studyGroup = (StudyGroup) getArguments().getSerializable(getResources().getString(R.string.key_fragment_transaction));
        initViews();
        setViews();
        participateButton();
        leaveButton();
        return view;
    }

    private void initViews() {
        date = view.findViewById(R.id.textView_Date);
        time = view.findViewById(R.id.textView_Time);
        place = view.findViewById(R.id.textView_Place);
        subject = view.findViewById(R.id.textView_Subject);
        notes = view.findViewById(R.id.textView_Notes);

        signIn = view.findViewById(R.id.button_SignIn);
        signOut = view.findViewById(R.id.button_SignOut);
        participants = view.findViewById(R.id.listView_Participants);
    }

    private void setViews() {
        subject.setText(studyGroup.getSubject());

        Context context = view.getContext();
        String d = "<b>" + context.getString(R.string.date) + " " + "</b>" + studyGroup.getWeekday() +  ", " + studyGroup.getDate();
        date.setText(Html.fromHtml(d));

        String t = "<b>" + context.getString(R.string.time) + " " + "</b>" + studyGroup.getTime();
        time.setText(Html.fromHtml(t));

        String p = "<b>" + context.getString(R.string.place) + " " + "</b>" + studyGroup.getPlace();
        place.setText(Html.fromHtml(p));

        String n = "<b>" + context.getString(R.string.notes) + " " + "</b>" + "<br>" + studyGroup.getNotes() + "</br>";
        notes.setText(Html.fromHtml(n));

        getParticipantNames();
        initListView();
    }

    private void initListView() {
        adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1, participantsList);
        participants.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void getParticipantNames() {
        //Teilnehmernamen aus der Objektliste mit Id's und Namen raus lesen
        for (String user : studyGroup.getParticipantsNames()) {
            participantsList.add(user);
        }
    }

    private void participateButton() {
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(studyGroup.getParticipantsIds().contains(user.getUid()))) {
                    studyGroup.addNewUserId(user.getUid());
                    studyGroup.addNewUserName(user.getDisplayName());
                    db.collection(studyGroup.getSubject()).document(studyGroup.getId()).update("participantsIds", studyGroup.getParticipantsIds(), "participantsNames", studyGroup.getParticipantsNames());
                    participantsList.add(user.getDisplayName());
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getActivity(), R.string.user_already_participant, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void leaveButton() {
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studyGroup.removeUserId(user.getUid());
                studyGroup.removeUserName(user.getDisplayName());
                db.collection(studyGroup.getSubject()).document(studyGroup.getId()).update("participantsIds", studyGroup.getParticipantsIds(), "participantsNames", studyGroup.getParticipantsNames());
                participantsList.remove(user.getDisplayName());
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void setReminder() {
        setReminderTime();
        Date reminderDate = stringToDate(studyGroup.getDate());

        AlarmManager manager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

        Intent alarmIntent = new Intent(getActivity(), Reminder.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, alarmIntent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 3);
        calendar.set(Calendar.MINUTE, 23);
        calendar.set(Calendar.SECOND, 1);

        manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    /*private boolean parseStringToDate(){
        boolean isExpired=false;
        Date expiredDate = stringToDate(date.getText().toString());

        if (new Date().after(expiredDate)) {
            isExpired=true;
        }

        return isExpired;
    }*/

    private Date stringToDate(String dateString) {
        ParsePosition pos = new ParsePosition(0);
        SimpleDateFormat simpledateformat = new SimpleDateFormat("EEE MMM d HH:mm:ss zz yyyy");
        Date date = simpledateformat.parse(dateString, pos);
        return date;
    }


    private void setReminderTime() {
        String timeString = time.getText().toString();
        int actualMinute = getMinute(timeString);
        int actualHour = getHour(timeString);

        if (actualMinute > 29) {
            reminderMinute = actualMinute - 30;
            reminderHour = actualHour;
        } else {
            reminderMinute = (60 + (actualMinute - 30));
            reminderHour = actualMinute - 1;
        }
    }

    private int getHour(String timeString) {
        String hourString = timeString.substring(0, 2);
        return Integer.parseInt(hourString);
    }

    private int getMinute(String timeString) {
        String minuteString = timeString.substring(3);
        return Integer.parseInt(minuteString);
    }
}
