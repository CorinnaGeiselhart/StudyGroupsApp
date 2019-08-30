package com.example.studygroups;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class CreateGroupFragment extends Fragment {

    private View view;

    private Spinner modulePicker;
    private EditText datePicker, timePicker;
    private EditText locationView;
    private EditText notesView;
    private FloatingActionButton createGroup;
    private TextView warning;
    private FirebaseFirestore db;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.create_group, container, false);
        initViews();
        
        return view;
    }

    private void initViews() {
        findViews();
        initDateView();
        initTimeView();
        createNewGroup();
    }

    private void createNewGroup() {
        createGroup.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View v) {
                String subject = modulePicker.getSelectedItem().toString();
                String date = datePicker.getText().toString();
                String time = timePicker.getText().toString();
                String location = locationView.getText().toString().trim();
                String comment = notesView.getText().toString().trim();

                if(location.isEmpty() || subject.equals(modulePicker.getItemAtPosition(0)) || date.isEmpty()
                || time.isEmpty()){
                    //Nutzer auffordern alle Felder auszufüllen (nur Notizen darf frei bleiben)
                    warning.setVisibility(View.VISIBLE);

                }else{
                    //Lerngruppeneintrag hinzufügen
                    StudyGroup studyGroup = new StudyGroup(subject, date, time, location, comment);
                    addToDatabase(studyGroup);

                    startDetailsActivity(studyGroup);

                    warning.setVisibility(View.INVISIBLE);
                    resetView();
                }

            }
        });
    }

    private void startDetailsActivity(StudyGroup studyGroup) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        Bundle bundle = new Bundle();
        bundle.putSerializable(getResources().getString(R.string.key_fragment_transaction),studyGroup);
        Fragment detailsActivity = new StudyGroupDetailsActivity();
        detailsActivity.setArguments(bundle);
        ft.addToBackStack(MainActivity.class.getName());
        fm.popBackStack(CreateGroupFragment.class.getName(),FragmentManager.POP_BACK_STACK_INCLUSIVE);
        ft.replace(R.id.nav_host,detailsActivity);
        ft.commit();
    }

    public void addToDatabase(StudyGroup studyGroup){
        db = FirebaseFirestore.getInstance();
        //Admin wird durch Nutzername des Admins ausgetauscht
        Map<String,StudyGroup> lerngroup = new HashMap<>();
        lerngroup.put("Admin", studyGroup);

        db.collection(studyGroup.getSubject()).add(lerngroup).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Error adding document", e);
            }
        });

    }
    private void resetView() {
        modulePicker.setSelection(0);
        datePicker.setText("");
        timePicker.setText("");
        locationView.setText("");
        notesView.setText("");
    }

    private void findViews(){
        modulePicker = view.findViewById (R.id.modul_view);
        datePicker = view.findViewById (R.id.date_view);
        timePicker = view.findViewById (R.id.time_view);
        locationView = view.findViewById (R.id.location_view);
        notesView = view.findViewById (R.id.notes_view);
        createGroup = view.findViewById(R.id.button_create_group);
        warning = view.findViewById(R.id.textView_WarningCreateGroup);
        warning.setVisibility(View.INVISIBLE);
    }

    private void initTimeView() {
        timePicker.setFocusable(false);
        timePicker.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                createTimePickerDialog().show();
            }
        });
    }

    private TimePickerDialog createTimePickerDialog(){
        GregorianCalendar calender = new GregorianCalendar();
        int min = calender.get(Calendar.MINUTE);
        int hour = calender.get(Calendar.HOUR_OF_DAY);

        TimePickerDialog timePickerDialog = new TimePickerDialog(view.getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minutes) {
                String timeAsString = String.format("%02d:%02d", hourOfDay, minutes);
                timePicker.setText(timeAsString);
            }
        }, hour,min,true);
        return timePickerDialog;
    }

    private void initDateView() {
        datePicker.setFocusable(false);
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                createDatePickerDialog().show();
            }
        });
    }

    private DatePickerDialog createDatePickerDialog(){
        GregorianCalendar calender = new GregorianCalendar();
        int day = calender.get(Calendar.DAY_OF_MONTH);
        int month = calender.get(Calendar.MONTH);
        int year = calender.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                GregorianCalendar date = new GregorianCalendar(year, month, dayOfMonth);
                DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, Locale.GERMANY);
                String dateAsString = dateFormat.format(date.getTime());
                datePicker.setText(dateAsString);
            }
        }, year, month, day);

        return datePickerDialog;
    }
}
