package com.example.studygroups;

import android.widget.Toast;

public enum Themes {
    STANDARD, ICE, SUN, NATURE, FIRE;

    public static Themes parseStringToTheme (String themeString) {
        try {
            return valueOf(themeString);
        } catch (Exception ex) {
            return STANDARD;
        }
    }
}
