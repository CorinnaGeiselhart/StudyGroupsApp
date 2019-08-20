package com.example.studygroups;

import android.content.Context;
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

import java.util.ArrayList;


public class StudyGroupDetailsActivity extends Fragment {

    private View view;
    private TextView date, time,place, subject, notes;
    private ListView participants;
    private Button sign;

    private ArrayList<String> list = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    private StudyGroup studyGroup;

    public StudyGroupDetailsActivity(){}



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.study_group_details, container, false);
        studyGroup = (StudyGroup) getArguments().getSerializable(getResources().getString(R.string.key_fragment_transaction));
        initViews();
        setViews();
        setupButton();

        return view;
    }

    private void setupButton() {
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Teilnehmen bzw austretten
                //-> zur Teilnehmerliste hinzufügen/entfernen
            }
        });
    }

    private void setViews() {
        subject.setText(studyGroup.getSubject());

        Context context = view.getContext();
        String d = "<b>" + context.getString(R.string.date) + "</b>" + ": " + studyGroup.getDate();
        date.setText(Html.fromHtml(d));

        String t = "<b>" + context.getString(R.string.time) + "</b>" + ": " + studyGroup.getTime();
        time.setText(Html.fromHtml(t));

        String p = "<b>" + context.getString(R.string.place) + "</b>" + ": " + studyGroup.getPlace();
        place.setText(Html.fromHtml(p));

        String n = "<b>" + context.getString(R.string.notes) + "</b>" + ": <br>" + studyGroup.getNotes() + "</br>";
        notes.setText(Html.fromHtml(n));

        getParticipants();
        initListView();
    }

    private void initListView() {
        adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1, list);
        participants.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void getParticipants() {
        //aus Datenbank Teilnehmer bekommen
        list.add("Teilnehmer");
    }


    private void initViews() {
        date = view.findViewById(R.id.textView_Date);
        time = view.findViewById(R.id.textView_Time);
        place = view.findViewById(R.id.textView_Place);
        subject = view.findViewById(R.id.textView_Subject);
        notes = view.findViewById(R.id.textView_Notes);

        sign = view.findViewById(R.id.button_sign);
        participants = view.findViewById(R.id.listView_Participants);
    }
}
