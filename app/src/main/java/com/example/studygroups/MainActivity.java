package com.example.studygroups;

import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatDelegate;

import android.view.MenuItem;
import android.view.View;


//import static com.example.studygroups.R.id.toolbar_fragment;


public class MainActivity extends AppCompatActivity{

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    private NavigationView navigationView;
    private FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //App in Darkmode or Lightmode?
        if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.DarkModeTheme);
        }
        else{
            setTheme(R.style.AppTheme);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createNavDrawer();
        addMainFragment();
    }

   private void createNavDrawer(){
        drawerLayout = (DrawerLayout) findViewById(R.id.main_id);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        createDrawerListener();
    }

    private void createDrawerListener() {
        createMenuItemListener();
        createProfileListener();
        createSettingsListener();
    }

    private void createMenuItemListener() {
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home_toolbar_item:
                        addMainFragment();
                        break;
                    case R.id.my_groups_toolbar_item:
                        addMyGroupsFragment();
                        break;
                    case R.id.find_groups_toolbar_item:
                        addFindGroupFragment();
                        break;
                    case R.id.create_groups_toolbar_item:
                        addCreateGroupFragment();
                        break;
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    private void createSettingsListener() {
        View view = findViewById(R.id.settings_toolbar_button);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addSettingsFragment();
                drawerLayout.closeDrawers();
            }
        });
    }

    private void createProfileListener() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerview = navigationView.getHeaderView(0);
        headerview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMyProfileFragment();
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
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MainActivityFrame mainFrame = new MainActivityFrame();
        fragmentTransaction.replace(R.id.nav_host, mainFrame);
        fragmentTransaction.commit();
    }

    private void addMyProfileFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MyProfileFragment myProfileFragment = new MyProfileFragment();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.nav_host, myProfileFragment);
        fragmentTransaction.commit();
    }

    public void addMyGroupsFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FragmentMyStudyGroups fragmentMyStudyGroups = new FragmentMyStudyGroups();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.nav_host, fragmentMyStudyGroups);
        fragmentTransaction.commit();
    }

    private void addFindGroupFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FindGroupFragment findGroupFragment = new FindGroupFragment();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.nav_host, findGroupFragment);
        fragmentTransaction.commit();
    }

    private void addCreateGroupFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        CreateGroupFragment createGroupFragment = new CreateGroupFragment();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.nav_host, createGroupFragment);
        fragmentTransaction.commit();
    }

    private void addSettingsFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        SettingsFragment settingsFragment = new SettingsFragment();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.nav_host, settingsFragment);
        fragmentTransaction.commit();
    }

}
