package com.example.studygroups.MainScreens;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.studygroups.OnDBComplete;
import com.example.studygroups.R;
import com.example.studygroups.StudyGroup.StudyGroup;
import com.example.studygroups.StudyGroup.StudyGroupDetails;
import com.example.studygroups.StudyGroupsAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class FindGroups extends Fragment {

    private CheckBox monday, tuesday,wednesday,thursday, friday,saturday, sunday;
    private CheckBox[] weekdays = new CheckBox[7];
    private Spinner modulePicker;
    private Button searchButton;
    private ListView resultFilterListView;
    private View view;

    private boolean[] isWeekdaySelected = new boolean[7];
    private String subject;
    private FirebaseFirestore db;
    private ArrayList<StudyGroup> wantedLerngroups = new ArrayList<>();
    private StudyGroupsAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.find_group, container, false);
        db = FirebaseFirestore.getInstance();
        findViews();
        initButton();
        return view;
    }

    private void findViews() {
        monday = view.findViewById(R.id.checkBox_Mo);
        weekdays[0] = monday;
        tuesday = view.findViewById(R.id.checkBox_Tue);
        weekdays[1] = tuesday;
        wednesday = view.findViewById(R.id.checkBox_Wed);
        weekdays[2] = wednesday;
        thursday = view.findViewById(R.id.checkBox_Thur);
        weekdays[3] = thursday;
        friday = view.findViewById(R.id.checkBox_Fr);
        weekdays[4] = friday;
        saturday = view.findViewById(R.id.checkBox_Sat);
        weekdays[5] = saturday;
        sunday = view.findViewById(R.id.checkBox_Sun);
        weekdays[6] = sunday;

        modulePicker = view.findViewById(R.id.spinner_Module);
        searchButton = view.findViewById(R.id.button_Search);
        resultFilterListView = view.findViewById(R.id.listView_ResultFilter);
    }

    private void setListView() {
        adapter = new StudyGroupsAdapter(view.getContext(),wantedLerngroups);
        resultFilterListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        resultFilterListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                Fragment details = new StudyGroupDetails();

                //StudyGroup übergeben
                Bundle bundle = new Bundle();
                Log.d("HIlFE", "bundle");
                bundle.putSerializable(getResources().getString(R.string.key_fragment_transaction), wantedLerngroups.get(position));
                Log.d("HIlFE", "bundle1");
                details.setArguments(bundle);
                Log.d("HIlFE", "arguments");
                fragmentTransaction.replace(R.id.nav_Host, details);
                Log.d("HIlFE", "" + wantedLerngroups.get(position));
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                Log.d("HILFE", "commit");
            }
        });
    }

    private void initButton() {

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get, which checkbox was selected
                getSelectedWeekdays();
                showDatabaseLerngroups(new OnDBComplete() {
                    @Override
                    public void onComplete() {
                        setListView();
                        adapter.notifyDataSetChanged();
                    }
                });

            }
        });

    }


    private void getSelectedWeekdays() {
        String[] keys = {getResources().getString(R.string.key_monday),
                getResources().getString(R.string.key_tuesday),
                getResources().getString(R.string.key_wednesday),
                getResources().getString(R.string.key_thursday),
                getResources().getString(R.string.key_friday),
                getResources().getString(R.string.key_saturday),
                getResources().getString(R.string.key_sunday)};
        for(int x = 0; x < weekdays.length; x++){
            checkStatus(weekdays[x], isWeekdaySelected[x]);
        }
    }

    private void checkStatus(CheckBox checkBox,boolean isSelected){
        if(checkBox.isChecked()){
            isSelected = true;
        }else if(!checkBox.isChecked()){
            isSelected = false;
        }
    }

    private void showDatabaseLerngroups(final OnDBComplete onDBComplete) {
        wantedLerngroups = new ArrayList<>();
        subject = modulePicker.getSelectedItem().toString();
        db.collection(subject).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document: task.getResult()){
                        wantedLerngroups.add(document.toObject(StudyGroup.class));
                        Log.d("Studygroup", "" + document.toObject(StudyGroup.class));
                    onDBComplete.onComplete();
                    }
                }
            }
        });
    }

}
