package com.example.studygroups;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;


public class FindGroupFragment extends Fragment {

    View view;

    FloatingActionButton filter;
    ListView results;

    public static final int REQUEST_CODE = 1;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.find_group, container, false);

        initViews();

        return view;
    }



    private void initViews() {
        results = view.findViewById(R.id.listView_StudyGroups);
        filter = view.findViewById(R.id.floatingActionButton_Filter);
        filter.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), FilterFindGroups.class);

                startActivityForResult(intent, REQUEST_CODE);

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("onActivityResult");
        try{
            super.onActivityResult(requestCode, resultCode, data);

            if(requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK){
                getFilterData(data);
            }

        }catch (Exception ex){
            Toast.makeText(view.getContext(), ex.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void getFilterData(Intent data) {
        String monday = data.getExtras().getString(view.getResources().getString(R.string.key_monday));
        String tuesday = data.getExtras().getString(view.getResources().getString(R.string.key_tuesday));
        String wednesday = data.getExtras().getString(view.getResources().getString(R.string.key_wednesday));
        String thursday = data.getExtras().getString(view.getResources().getString(R.string.key_thursday));
        String friday = data.getExtras().getString(view.getResources().getString(R.string.key_friday));
        String saturday = data.getExtras().getString(view.getResources().getString(R.string.key_saturday));
        String sunday = data.getExtras().getString(view.getResources().getString(R.string.key_sunday));
    }
}
