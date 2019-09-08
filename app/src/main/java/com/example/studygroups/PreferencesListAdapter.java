package com.example.studygroups;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

public class PreferencesListAdapter extends ArrayAdapter<Preference> {

    private Context context;
    private ArrayList<Preference> preferenceList;
    private Preference preference;

    private Switch preferenceSwitch;
    private TextView preferenceExplanation;




    public PreferencesListAdapter(Context context, ArrayList<Preference> preferenceList){
        super(context, R.layout.preferences_item_layout, preferenceList);

        this.context = context;
        this.preferenceList = preferenceList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if(v == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = layoutInflater.inflate(R.layout.preferences_item_layout, null);
        }

        preference = preferenceList.get(position);

        if(preference != null){
            preferenceSwitch = v.findViewById(R.id.switch_Preference);
            preferenceExplanation = v.findViewById(R.id.textView_PreferenceExplanation);

            setViews();
        }
        return v;
    }


    private void setViews() {
        preferenceSwitch.setChecked(preference.isPrefGiven());
        preferenceSwitch.setText(preference.getName());
        preferenceExplanation.setText(preference.getExplanation());
    }
}
