package com.example.studygroups;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

abstract class MainActivityFragment extends Fragment {

    protected TextView header, textIfListIsEmpty;
    private ListView listView;
    protected View view;

    private StudyGroupsListAdapter adapter;
    protected ArrayList<StudyGroup> list = new ArrayList<>();
    protected FragmentTransaction fragmentTransaction;
    protected Fragment details;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_main_fragments,container,false);

        initViews();
        setText();
        fillList();
        setView();

        return view;
    }

    private void setView(){
        if(list.isEmpty()){
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
        adapter = new StudyGroupsListAdapter(view.getContext(), list);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        //draufklicken auf einzelne Items -> Details zur Gruppe öffnen
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                String tag = String.valueOf(view.getId()).trim();

                fragmentTransaction = getFragmentManager().beginTransaction();
                details = new StudyGroupDetailsActivity();

                //StudyGroup übergeben
                Bundle bundle = new Bundle();
                bundle.putSerializable(getResources().getString(R.string.key_fragment_transaction), list.get(position));
                details.setArguments(bundle);

                replaceFragment();

                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }


    private void initViews() {
        header = (TextView) view.findViewById(R.id.textView_Fragment);
        textIfListIsEmpty = (TextView) view.findViewById(R.id.textView_ListEmpty);
        listView = (ListView) view.findViewById(R.id.listView_Fragment);
    }

    protected abstract void replaceFragment();
    protected abstract void fillList();
    protected abstract void setText();

}