package com.example.studygroups;

import android.graphics.Color;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class FragmentMyStudyGroups extends Fragment {

    private TextView header, textIfListIsEmpty;
    private ListView listView;
    private View view;

    private StudyGroupsListAdapter adapter;
    private ArrayList<StudyGroup> listMyStudyGroups = new ArrayList<>();

    public FragmentMyStudyGroups(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragments_main_activity,container,false);

        initViews();
        initList();
        initListView();

        return view;
    }

    private void initList() {
        listMyStudyGroups = new ArrayList<>();
        fillListMyStudyGroups();
    }

    private void initListView() {

        if(listMyStudyGroups.isEmpty()){
            textIfListIsEmpty.setVisibility(View.VISIBLE);
            listView.setVisibility(View.INVISIBLE);
        }else{
            textIfListIsEmpty.setVisibility(View.INVISIBLE);
            listView.setVisibility(View.VISIBLE);
            //adapter
            adapter = new StudyGroupsListAdapter(view.getContext(), listMyStudyGroups);
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

    private void fillListMyStudyGroups() {
        //Beispiel, da Datenbank noch nicht erstellt
        listMyStudyGroups.add(new StudyGroup("EIMI", "Montag, 12.08.2019", "19:00", "Universität Regensburg", null));
    }




    private void initViews() {
        header = (TextView) view.findViewById(R.id.textView_Fragment);
        header.setText(R.string.text_my_study_groups);
        header.setTextColor(getResources().getColor(R.color.colorGreenLight));

        textIfListIsEmpty = (TextView) view.findViewById(R.id.textView_ListEmpty);
        textIfListIsEmpty.setText(R.string.text_empty_my_study_groups_list);
        textIfListIsEmpty.setVisibility(View.INVISIBLE);

        listView = (ListView) view.findViewById(R.id.listView_Fragment);
    }
}
