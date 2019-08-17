package com.example.studygroups;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


public class FindGroupFragment extends Fragment {

    View view;

    FloatingActionButton filter;
    ListView results;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.find_group_fragment, container, false);

        initViews();

        return view;
    }



    private void initViews() {
        results = view.findViewById(R.id.listView_StudyGroups);
        filter = view.findViewById(R.id.floatingActionButton_Filter);
        System.out.println("hier1");
        filter.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), FilterFindGroups.class);

                startActivity(intent); //austauschen mit startActivityForResult
                //Extras auslesen
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Datenbank filtern
        //Listview updaten
    }
}
