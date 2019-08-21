package com.example.studygroups;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class CreateGroupFragment extends Fragment {

    Spinner modulPicker;
    EditText datePicker;
    EditText timePicker;
    EditText locationView;
    EditText notesView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initViews();
<<<<<<< Updated upstream
        return inflater.inflate(R.layout.create_group_fragment, container, false);
=======
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

                if(location.isEmpty() || subject.equals(modulePicker.getItemAtPosition(1)) || date.isEmpty()
                || time.isEmpty()){
                    //keine Lerngruppe erstellen lassen. Nutzer auffordern Ort/Tag/Zeit/Modul anzugeben

                }else{
                    StudyGroup studyGroup = new StudyGroup(subject, date, time, location, comment);

                    //zur Datenbank hinzufügen
                    //Activity mit allen Lerngruppen aufrufen??

                    resetView();
                }

            }
        });
    }

    private void resetView() {
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
>>>>>>> Stashed changes
    }

    private void initViews(){
        modulPicker = getActivity().findViewById (R.id.modul_view);
        datePicker = getActivity().findViewById (R.id.date_view);
        timePicker = getActivity().findViewById (R.id.time_view);
        locationView = getActivity().findViewById (R.id.location_view);
        notesView = getActivity().findViewById (R.id.notes_view);
    }
}
