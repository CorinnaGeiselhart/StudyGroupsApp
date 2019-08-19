package com.example.studygroups;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.TimePicker;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class CreateGroupFragment extends Fragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    LinearLayout linearLayout;
    Calendar calendar;

    Spinner modulPicker;
    TextView dateView;
    TextView timeView;
    EditText locationView;
    EditText notesView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.create_group_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initCalendar();
        initViews();
        setListener();
    }

    private void initCalendar(){
        calendar = new GregorianCalendar(Locale.getDefault());
    }

    private void initViews(){
        modulPicker = getView().findViewById (R.id.modul_view);

        dateView = getView().findViewById (R.id.date_view);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        dateView.setText(currentDateString);

        timeView = getView().findViewById (R.id.time_view);
        locationView = getView().findViewById (R.id.location_view);
        notesView = getView().findViewById (R.id.notes_view);
    }

    private void setListener(){
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
