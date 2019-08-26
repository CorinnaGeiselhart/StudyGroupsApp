package com.example.studygroups;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class FragmentNewStudyGroups extends Fragment {

    private TextView header, textIfListIsEmpty;
    private View view;

    private ListView listView;
    private ArrayAdapter<StudyGroup> adapter;
    private ArrayList<StudyGroup> list = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_main_fragments,container,false);

        initViews();
        fillList();
        setupView();

        return view;
    }

    private void setupView() {
        if(list.isEmpty()){
            textIfListIsEmpty.setVisibility(View.VISIBLE);
            listView.setVisibility(View.INVISIBLE);
        }else {
            textIfListIsEmpty.setVisibility(View.INVISIBLE);
            listView.setVisibility(View.VISIBLE);
            setListView();
        }
    }

    private void setListView() {
        //adapter
        adapter = new StudyGroupsListAdapter(view.getContext(), list);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        //draufklicken auf einzelne Items -> Detail anzeige öffnen
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                Fragment details = new StudyGroupDetailsActivity();

                //StudyGroup übergeben
                Bundle bundle = new Bundle();
                bundle.putSerializable(getResources().getString(R.string.key_fragment_transaction), list.get(position));
                details.setArguments(bundle);

                //fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.layout_MainActivityFragments, details);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    private void fillList() {
        list = new ArrayList<>();
        //Beispiel, da Datenbank noch nicht erstellt
        list.add(new StudyGroup("EIMI", "Montag, 12.08.2019", "19:00", "Universität Regensburg",""));
    }

    private void initViews() {
        header = (TextView) view.findViewById(R.id.textView_Fragment);
        header.setText(R.string.text_new_study_groups);

        textIfListIsEmpty = (TextView) view.findViewById(R.id.textView_ListEmpty);
        textIfListIsEmpty.setText(R.string.text_empty_new_study_groups_list);
        textIfListIsEmpty.setVisibility(View.INVISIBLE);

        listView = (ListView) view.findViewById(R.id.listView_Fragment);
    }
}
