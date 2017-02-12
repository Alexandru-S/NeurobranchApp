package com.glassbyte.neurobranch;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.glassbyte.neurobranch.Authentication.AuthenticationActivity;
import com.glassbyte.neurobranch.Portal.MainSections.MyTrialsFragment;
import com.glassbyte.neurobranch.Portal.MainSections.TrialsAvailableFragment;
import com.glassbyte.neurobranch.Services.Enums.Preferences;
import com.glassbyte.neurobranch.Services.Globals;
import com.glassbyte.neurobranch.Services.Helpers.Fragments;
import com.glassbyte.neurobranch.Services.Helpers.Manager;
import com.glassbyte.neurobranch.Services.Sync.AlarmReceiver;
import com.glassbyte.neurobranch.Services.Sync.SyncService;
import com.glassbyte.neurobranch.Settings.Settings;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    DrawerLayout drawer;

    AlarmReceiver alarmReceiver = new AlarmReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // debug for force resynchronisation
        if(Globals.isDebug) alarmReceiver.cancelAlarm(this);

        boolean alarmUp = (PendingIntent.getBroadcast(this, 0, new Intent(this, SyncService.class),
                PendingIntent.FLAG_NO_CREATE) != null);

        if (alarmUp) {
            System.out.println("Synchronisation alarm is already active");
        } else {
            alarmReceiver.setAlarm(this);
            System.out.println("Synchronisation alarm invoked and set to enabled");
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        if (drawer != null) {
            drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
                @Override
                public void onDrawerSlide(View drawerView, float slideOffset) {

                }

                @Override
                public void onDrawerOpened(View drawerView) {

                }

                @Override
                public void onDrawerClosed(View drawerView) {

                }

                @Override
                public void onDrawerStateChanged(int newState) {

                }
            });
        }
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            navigationView.getMenu().getItem(0).setChecked(true);
            navigationView.setNavigationItemSelectedListener(this);
        }
        new Fragments.AsyncSetFrag(getSupportFragmentManager(), new TrialsAvailableFragment()).execute();
    }

    @Override
    public void onBackPressed() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, Settings.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.my_trials) {
            Fragments.setFragment(getSupportFragmentManager(), new MyTrialsFragment());
        } else if (id == R.id.trials_available) {
            Fragments.setFragment(getSupportFragmentManager(), new TrialsAvailableFragment());
        } else if (id == R.id.signout) {
            // disable notifications, reset ID to null, and return to sign in screen
            alarmReceiver.cancelAlarm(getApplicationContext());
            Manager.getInstance().setPreference(Preferences.id, null, MainActivity.this);
            startActivity(new Intent(MainActivity.this, AuthenticationActivity.class));
            MainActivity.this.finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
