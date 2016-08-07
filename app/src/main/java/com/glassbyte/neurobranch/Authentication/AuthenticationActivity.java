package com.glassbyte.neurobranch.Authentication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.glassbyte.neurobranch.MainActivity;
import com.glassbyte.neurobranch.R;

/**
 * Created by ed on 10/06/16.
 */
public class AuthenticationActivity extends AppCompatActivity {
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authentication_frame);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(AuthenticationActivity.this);
        if(!sharedPreferences.getString("id", "").equals("")) {
            startActivity(new Intent(AuthenticationActivity.this, MainActivity.class));
        }

        getSupportFragmentManager().addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {

            }
        });

        setFragment(getSupportFragmentManager(), new SigninFragment());
    }

    public static void setFragment(FragmentManager fragmentManager, Fragment fragment) {
        fragmentManager.beginTransaction().replace(R.id.auth_frame, fragment)
                .addToBackStack(null).commit();
    }
}
