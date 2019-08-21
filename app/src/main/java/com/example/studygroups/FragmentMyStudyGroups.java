package com.example.studygroups;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
        fillListMyStudyGroups();
        setView();

        return view;
    }

    private void setView(){
        if(listMyStudyGroups.isEmpty()){
            textIfListIsEmpty.setVisibility(View.VISIBLE);
            listView.setVisibility(View.INVISIBLE);
        }else{
            textIfListIsEmpty.setVisibility(View.INVISIBLE);
            listView.setVisibility(View.VISIBLE);
            setList();
        }
    }

    private void setList() {
            //adapter
            adapter = new StudyGroupsListAdapter(view.getContext(), listMyStudyGroups);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            //draufklicken auf einzelne Items -> Details zur Gruppe öffnen
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                    String tag = String.valueOf(view.getId()).trim();

                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    Fragment details = new StudyGroupDetailsActivity();

                    //StudyGroup übergeben
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(getResources().getString(R.string.key_fragment_transaction), listMyStudyGroups.get(position));
                    details.setArguments(bundle);


                    //in Meine Lerngruppen Details aufrufen
                    if(tag.equals(String.valueOf(R.id.mainFragment))){
                        //fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.replace(R.id.nav_host, details);
                    }
                    //in MainActivit/Home Details aufrufen
                    else{
                        //fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.replace(R.id.mainActivityFragments, details);
                    }
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            });
    }


    private void fillListMyStudyGroups() {
        listMyStudyGroups = new ArrayList<>();
        //Beispiel, da Datenbank noch nicht erstellt
        listMyStudyGroups.add(new StudyGroup("EIMI", "Montag, 12.08.2019", "19:00", "Universität Regensburg", "PAARRTTYYY Ich freu mich auf euch!!! :D Das wird so schön!!" +
                "  @Tina: bleib zuhause! Du bist nicht willkommen ;) @Juliana: vergiss die Gewürze nicht WUHUUUUU "));
    }

    private void initViews() {
        header = (TextView) view.findViewById(R.id.textView_Fragment);
        header.setText(R.string.text_my_study_groups);

        textIfListIsEmpty = (TextView) view.findViewById(R.id.textView_ListEmpty);
        textIfListIsEmpty.setText(R.string.text_empty_my_study_groups_list);
        textIfListIsEmpty.setVisibility(View.INVISIBLE);

        listView = (ListView) view.findViewById(R.id.listView_Fragment);
    }
}
