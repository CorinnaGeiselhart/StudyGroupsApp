package com.example.studygroups;

import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatDelegate;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import static android.provider.MediaStore.Images.Media.getBitmap;


public class MainActivity extends AppCompatActivity{

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    private NavigationView navigationView;
    private FragmentManager fragmentManager;

    private static final int ADD_TO_BACKSTACK = 1;
    private static final int DONT_ADD_TO_BACKSTACK = 0;
    public static Themes theme = Themes.STANDARD;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //App in Darkmode or Lightmode?
        if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES){
            setColorTheme(true);
        }
        else {
            setColorTheme(false);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        createNavDrawer();
        addMainFragment();
    }

    private void setColorTheme(boolean darkmodeOn){
        if(darkmodeOn){
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
        drawerLayout = (DrawerLayout) findViewById(R.id.main_Id);
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
        navigationView = (NavigationView) findViewById(R.id.nav_View);
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
                addFragment(new Settings(), ADD_TO_BACKSTACK);
                drawerLayout.closeDrawers();
            }
        });
    }

    private void createProfileListener() {
        NavigationView navigationView = findViewById(R.id.nav_View);
        View headerView = navigationView.getHeaderView(0);
        ImageView profile = headerView.findViewById(R.id.imageView_NavBarPPicture);
        TextView username = headerView.findViewById(R.id.textView_NavBarUsername);
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String picturePath = user.getPhotoUrl().toString();
        profile.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        username.setText(user.getDisplayName());
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
        addFragment(new MainActivityFrame(), DONT_ADD_TO_BACKSTACK);
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