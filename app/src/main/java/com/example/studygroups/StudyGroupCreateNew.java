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
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class StudyGroupCreateNew extends Fragment {

    private View view;

    private Spinner modulePicker;
    private EditText datePicker, timePicker;
    private EditText locationView;
    private EditText commentView;
    private FloatingActionButton createGroup;
    private FirebaseFirestore db;

    private int min, hour, day, month, year;
    private String weekday;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.create_group, container, false);
        initViews();
        
        return view;
    }

    private void initViews() {
        findViews();
        initCalender();
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
                String comment = commentView.getText().toString().trim();

                if(location.isEmpty() || subject.equals(modulePicker.getItemAtPosition(0)) || date.isEmpty()
                || time.isEmpty()){
                    //Nutzer auffordern alle Felder auszufüllen (nur Notizen darf frei bleiben)
                    Toast.makeText(getActivity(), R.string.text_warning_empty_field, Toast.LENGTH_LONG).show();

                }else{
                    //Lerngruppeneintrag hinzufügen
                    StudyGroup studyGroup = new StudyGroup(subject, date, weekday, time, location, comment);
                    addToDatabase(studyGroup);

                    startDetailsActivity(studyGroup);
                    //zur Datenbank hinzufügen
                    //würde eher die detail-ansicht der  neu erstellten gruppe aufrufen

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
        fm.popBackStack(StudyGroupCreateNew.class.getName(),FragmentManager.POP_BACK_STACK_INCLUSIVE);
        ft.replace(R.id.nav_Host,detailsActivity);
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
        commentView.setText("");
    }

    private void findViews(){
        modulePicker = view.findViewById (R.id.spinner_Modul);
        datePicker = view.findViewById (R.id.editText_Date);
        timePicker = view.findViewById (R.id.editText_Time);
        locationView = view.findViewById (R.id.editText_Location);
        commentView = view.findViewById (R.id.editText_Notes);
        createGroup = view.findViewById(R.id.button_CreateGroup);
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
        DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                GregorianCalendar date = new GregorianCalendar(year, month, dayOfMonth);
                //get Date
                DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, Locale.GERMANY);
                String dateAsString = dateFormat.format(date.getTime());
                //get name of day
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE", Locale.GERMANY);
                weekday = simpleDateFormat.format(date.getTime());

                datePicker.setText(weekday + ", " + dateAsString);
            }
        }, year, month, day);


        return datePickerDialog;
    }

    private void initCalender() {
        GregorianCalendar calender = new GregorianCalendar();
        day = calender.get(Calendar.DAY_OF_MONTH);
        month = calender.get(Calendar.MONTH);
        year = calender.get(Calendar.YEAR);
        min = calender.get(Calendar.MINUTE);
        hour = calender.get(Calendar.HOUR_OF_DAY);
    }
}
