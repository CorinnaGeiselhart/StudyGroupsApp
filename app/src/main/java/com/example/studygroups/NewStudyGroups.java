package com.example.studygroups;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class NewStudyGroups extends Fragment {

    private TextView header, textIfListIsEmpty;
    private View view;


    private ListView listView;
    private ArrayAdapter<StudyGroup> adapter;
    private ArrayList<StudyGroup> list = new ArrayList<>();



    public NewStudyGroups(){
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragments_main_activity,container,false);
        initViews();

        initViews();
        initList();
        initListView();

        return view;
    }

    private void initList() {
        list = new ArrayList<>();
        fillListMyStudyGroups();
    }

    private void fillListMyStudyGroups() {
        //Beispiel, da Datenbank noch nicht erstellt
        //listNewStudyGroups.add(new StudyGroup("EIMI", "Montag, 12.08.2019", "19:00", "Universität Regensburg"));
    }

    private void initListView() {

        if(list.isEmpty()){
            textIfListIsEmpty.setVisibility(View.VISIBLE);
            listView.setVisibility(View.INVISIBLE);
        }else{
            textIfListIsEmpty.setVisibility(View.INVISIBLE);
            listView.setVisibility(View.VISIBLE);
            //adapter
            adapter = new StudyGroupsListAdapter(view.getContext(), list);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            //draufklicken auf einzelne Items -> Detail anzeige öffnen
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    //Intent einfügen: Activity Anzeige Details LernGruppe (Ort, Teilnehmer, Beitretten Knopf...)
                    return false;
                }
            });
        }
    }

    private void initViews() {
        header = (TextView) view.findViewById(R.id.textView_Fragment);
        header.setTextColor(getResources().getColor(R.color.colorGreenDark));
        header.setText(R.string.text_new_study_groups);

        textIfListIsEmpty = (TextView) view.findViewById(R.id.textView_ListEmpty);
        textIfListIsEmpty.setText(R.string.text_empty_new_study_groups_list);
        textIfListIsEmpty.setVisibility(View.INVISIBLE);

        listView = (ListView) view.findViewById(R.id.listView_Fragment);
    }
}
