package com.example.studygroups;

import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;

import androidx.fragment.app.Fragment;
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

        fragmentManager = getSupportFragmentManager();
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
                        addFragment(new MyStudyGroups());
                        break;
                    case R.id.find_groups_toolbar_item:
                        addFragment(new FindGroupFragment());
                        break;
                    case R.id.create_groups_toolbar_item:
                        addFragment(new CreateGroupFragment());
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
                addFragment(new SettingsFragment());
                drawerLayout.closeDrawers();
            }
        });
    }

    private void createProfileListener() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFragment(new MyProfileFragment());
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
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MainActivityFrame mainFrame = new MainActivityFrame();
        fragmentTransaction.replace(R.id.nav_host, mainFrame);
        fragmentTransaction.commit();
    }

    private void addFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nav_host, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}