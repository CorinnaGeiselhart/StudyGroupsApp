package com.example.studygroups;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class CreateGroupFragment extends Fragment {

    LinearLayout linearLayout;
    Calendar calendar;

    View view;

    Spinner modulePicker;
    EditText datePicker, timePicker;
    EditText locationView;
    EditText notesView;
    FloatingActionButton createGroup;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.create_group_fragment, container, false);
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
    }

    /**private void setListener(){
        dateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("date view clicked");
                handleDateClick();
            }
        });

        timeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleTimeClick();
            }
        });
    }

    private void handleDateClick(){
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);
        datePickerDialog.show();
    }

    private void handleTimeClick(){
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),this,hour,minute,true);
        timePickerDialog.show();

    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        dateView.setText(currentDateString);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {

    }

    /**@Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.date_view:
                handleDateClick();
                break;
            case R.id.time_view:
                handleTimeClick();
                break;
        }
    }*/
}
