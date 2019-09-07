package com.example.studygroups;

import android.widget.Switch;

import java.io.Serializable;
import java.util.ArrayList;

public class Preference implements Serializable {


    private String name, explanation;
    private boolean prefGiven;

    public Preference(String name, String explanation, boolean prefGiven){
        this.name=name;
        this.explanation=explanation;
        this.prefGiven=prefGiven;
    }

    public String getName() {
        return name;
    }

    public String getExplanation() {
        return explanation;
    }

    public boolean isPrefGiven() {
        return prefGiven;
    }

    public void setPrefGiven(boolean prefGiven) {
        this.prefGiven = prefGiven;
    }
}
