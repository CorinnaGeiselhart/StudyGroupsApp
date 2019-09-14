package com.example.studygroups;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

public class PreferencesListAdapter extends ArrayAdapter<NotificationPermission> {

    private Context context;
    private ArrayList<NotificationPermission> notificationPermissionList;
    private NotificationPermission notificationPermission;

    private Switch preferenceSwitch;
    private TextView preferenceExplanation;


    public PreferencesListAdapter(Context context, ArrayList<NotificationPermission> notificationPermissionList){
        super(context, R.layout.preferences_item_layout, notificationPermissionList);

        this.context = context;
        this.notificationPermissionList = notificationPermissionList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if(v == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = layoutInflater.inflate(R.layout.preferences_item_layout, null);
        }

        notificationPermission = notificationPermissionList.get(position);

        if(notificationPermission != null){
            preferenceSwitch = v.findViewById(R.id.switch_Preference);
            preferenceExplanation = v.findViewById(R.id.textView_PreferenceExplanation);

            setViews();
        }
        return v;
    }


    private void setViews() {
        preferenceSwitch.setChecked(notificationPermission.isPrefGiven());
        preferenceSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notificationPermission.setPrefGiven(preferenceSwitch.isChecked());
            }
        });
        preferenceSwitch.setText(notificationPermission.getName());
        preferenceExplanation.setText(notificationPermission.getExplanation());
    }
}
