package com.example.studygroups.MainScreens;

import androidx.annotation.NonNull;

import com.example.studygroups.MainScreens.FindGroups;
import com.example.studygroups.MainScreens.HomeScreen;
import com.example.studygroups.MainScreens.MyStudyGroups;
import com.example.studygroups.Profile.MyProfile;
import com.example.studygroups.R;
import com.example.studygroups.Settings.MainSettings;
import com.example.studygroups.Settings.Themes;
import com.example.studygroups.StudyGroup.StudyGroupCreateNew;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class NavigationDrawer extends AppCompatActivity{

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    private NavigationView navigationView;
    private FragmentManager fragmentManager;

    private static final int ADD_TO_BACKSTACK = 1;
    private static final int DONT_ADD_TO_BACKSTACK = 0;
    private Themes theme;
    public static boolean isDarkmodeOn;
    public static boolean isNotoficationPermissionJoinGiven;
    public static boolean isNotoficationPermissionReminderGiven;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        readSharedPreference();
        setColorTheme();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        createNavDrawer();
        addMainFragment();
    }

    private void readSharedPreference(){
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);

        String defaultValueColor = "STANDARD";
        theme = Themes.parseStringToTheme(sharedPref.getString(getString(R.string.pref_color_key), defaultValueColor));
        isDarkmodeOn = sharedPref.getBoolean(getString(R.string.pref_mode_key), false);

        isNotoficationPermissionJoinGiven = sharedPref.getBoolean(getString(R.string.pref_joinPermission_key), true);
        isNotoficationPermissionReminderGiven = sharedPref.getBoolean(getString(R.string.pref_reminderPermission_key), true);
    }

    private void setColorTheme(){
        if(isDarkmodeOn){
            switch(theme) {
                case STANDARD: {
                    setTheme(R.style.DarkModeTheme);
                    break;
                }
                case ICE: {
                    setTheme(R.style.IceDarkTheme);
                    break;
                }
                case SUN: {
                    setTheme(R.style.SunDarkTheme);
                    break;
                }
                case NATURE: {
                    setTheme(R.style.NatureDarkTheme);
                    break;
                }
                case FIRE: {
                    setTheme(R.style.FireDarkTheme);
                    break;
                }
            }
        }
        else {
            switch (theme) {
                case STANDARD: {
                    setTheme(R.style.AppTheme);
                    break;
                }
                case ICE: {
                    setTheme(R.style.IceTheme);
                    break;
                }
                case SUN: {
                    setTheme(R.style.SunTheme);
                    break;
                }
                case NATURE: {
                    setTheme(R.style.NatureTheme);
                    break;
                }
                case FIRE: {
                    setTheme(R.style.FireTheme);
                    break;
                }
            }
        }
    }

    private void createNavDrawer(){
        drawerLayout = findViewById(R.id.main_Id);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        createDrawerListener();
    }

    private void createDrawerListener() {
        createMenuItemListener();
        createNavigationBarHeader();
        createSettingsListener();
    }

    private void createMenuItemListener() {
        navigationView = findViewById(R.id.nav_View);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home_toolbar_item:
                        addMainFragment();
                        break;
                    case R.id.my_groups_toolbar_item:
                        addFragment(new MyStudyGroups(), ADD_TO_BACKSTACK);
                        break;
                    case R.id.find_groups_toolbar_item:
                        addFragment(new FindGroups(), ADD_TO_BACKSTACK);
                        break;
                    case R.id.create_groups_toolbar_item:
                        addFragment(new StudyGroupCreateNew(), ADD_TO_BACKSTACK);
                        break;
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    private void createSettingsListener() {
        View view = findViewById(R.id.button_SettingsToolbar);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFragment(new MainSettings(), ADD_TO_BACKSTACK);
                drawerLayout.closeDrawers();
            }
        });
    }

    private void createNavigationBarHeader() {
        NavigationView navigationView = findViewById(R.id.nav_View);
        View headerView = navigationView.getHeaderView(0);
        ImageView profile = headerView.findViewById(R.id.imageView_NavBarPPicture);
        TextView username = headerView.findViewById(R.id.textView_NavBarUsername);
        TextView email = headerView.findViewById(R.id.textView_NavBarEmail);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user.getPhotoUrl() != null) {
            String picturePath = user.getPhotoUrl().toString();
            profile.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
        username.setText(user.getDisplayName());
        email.setText(user.getEmail());

        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFragment(new MyProfile(), ADD_TO_BACKSTACK);
                drawerLayout.closeDrawers();
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addMainFragment(){
        addFragment(new HomeScreen(), DONT_ADD_TO_BACKSTACK);
    }

    private void addFragment(Fragment fragment, int backStack){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nav_Host, fragment);
        if(backStack == ADD_TO_BACKSTACK){
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
    }
}