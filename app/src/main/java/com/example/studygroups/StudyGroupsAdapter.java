package com.example.studygroups;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.TextView;

import com.example.studygroups.StudyGroup.StudyGroup;

import java.util.ArrayList;

public class StudyGroupsAdapter extends ArrayAdapter<StudyGroup> {

    private Context context;
    private ArrayList<StudyGroup> studyGroupsList;
    private StudyGroup studyGroup;

    private TextView studyGroupSubject, studyGroupDate, studyGroupTime;



    public StudyGroupsAdapter(Context context, ArrayList<StudyGroup> studyGroupsList){
        super(context, R.layout.listitem_study_groups, studyGroupsList);

        this.context = context;
        this.studyGroupsList = studyGroupsList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if(v == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = layoutInflater.inflate(R.layout.listitem_study_groups, null);
        }

        studyGroup = studyGroupsList.get(position);

        if(studyGroup != null){
            studyGroupSubject = v.findViewById(R.id.textView_Subject);
            studyGroupDate = v.findViewById(R.id.textView_Date);
            studyGroupTime = v.findViewById(R.id.textView_Time);

            setTextViews();
        }
        return v;
    }

    //"Datum", "Uhrzeit" fett gedruckt
    //die Textview subject ist immer fett gedruckt (im Layout festgelegt)
    private void setTextViews() {
        studyGroupSubject.setText(studyGroup.getSubject());

        String date = "<b>" + context.getString(R.string.date) + "</b>" + " " + studyGroup.getWeekday() + ", " + studyGroup.getDate();
        studyGroupDate.setText(Html.fromHtml(date));

        String time = "<b>" + context.getString(R.string.time) + "</b>" + " " + studyGroup.getTime();
        studyGroupTime.setText(Html.fromHtml(time));
    }
}

