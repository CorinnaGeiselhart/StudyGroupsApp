package com.example.studygroups;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

abstract class ListViewFragment extends Fragment {

    private FirebaseFirestore db;
    protected TextView header, textIfListIsEmpty;
    private ListView listView;
    protected View view;

    protected StudyGroupsListAdapter adapter;
    public ArrayList<StudyGroup> listStudyGroups = new ArrayList<>();

    public ArrayList<StudyGroup> allStudyGroups = new ArrayList<>();
    protected FragmentTransaction fragmentTransaction;
    protected Fragment details;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_main_fragments, container, false);

        initViews();
        setText();

        loadDatabaseStudyGroups(new OnDBComplete() {
            @Override
            public void onComplete() {
                setList();
                setView();
                adapter.notifyDataSetChanged();
            }
        });
        return view;
    }

    private void initViews() {
        header = view.findViewById(R.id.textView_Fragment);
        textIfListIsEmpty = view.findViewById(R.id.textView_ListEmpty);
        listView = view.findViewById(R.id.listView_Fragment);
    }

    private void setList() {
        //adapter
        listStudyGroups = new ArrayList<>();
        specifyList();
        adapter = new StudyGroupsListAdapter(view.getContext(), listStudyGroups);

        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        //draufklicken auf einzelne Items -> Details zur Gruppe öffnen
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                String tag = String.valueOf(view.getId()).trim();

                fragmentTransaction = getFragmentManager().beginTransaction();
                details = new StudyGroupDetails();

                //StudyGroup übergeben
                Bundle bundle = new Bundle();
                bundle.putSerializable(getResources().getString(R.string.key_fragment_transaction), listStudyGroups.get(position));
                details.setArguments(bundle);

                replaceFragment();

                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    protected void setView() {
        if (listStudyGroups.isEmpty()) {
            textIfListIsEmpty.setVisibility(View.VISIBLE);
            listView.setVisibility(View.INVISIBLE);
        } else {
            textIfListIsEmpty.setVisibility(View.INVISIBLE);
            listView.setVisibility(View.VISIBLE);
        }
    }


    private void loadDatabaseStudyGroups(final OnDBComplete onDBComplete) {
        allStudyGroups = new ArrayList<>();
        Log.d("Database", "build");
        db = FirebaseFirestore.getInstance();
        List<Task<QuerySnapshot>> tasks = new ArrayList<>();
        String[] subjects = getResources().getStringArray(R.array.modul_list);
        for (String subject : subjects) {
            tasks.add(db.collection(subject).get());
        }
        Task combindeTask = Tasks.whenAllComplete(tasks).addOnSuccessListener(new OnSuccessListener<List<Task<?>>>() {
            @Override
            public void onSuccess(List<Task<?>> tasks) {
                for (Task t : tasks) {
                    QuerySnapshot qs = (QuerySnapshot) t.getResult();
                    for (DocumentSnapshot doc : qs.getDocuments()) {
                        allStudyGroups.add(doc.toObject(StudyGroup.class));
                    }
                }

                onDBComplete.onComplete();
            }
        });


    }

    protected abstract void replaceFragment();

    protected abstract void setText();

    protected abstract void specifyList();

}
