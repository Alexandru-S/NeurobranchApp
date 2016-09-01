package com.glassbyte.neurobranch.Authentication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.glassbyte.neurobranch.MainActivity;
import com.glassbyte.neurobranch.R;
import com.glassbyte.neurobranch.Services.Enums.Preferences;
import com.glassbyte.neurobranch.Services.Helpers.Manager;

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

        if (!Manager.getInstance().getPreference(Preferences.id, getApplicationContext()).isEmpty())
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        else Toast.makeText(getApplicationContext(), "No id attributed to user", Toast.LENGTH_LONG).show();

        Manager.getInstance().setFragment(getSupportFragmentManager(), new SigninFragment());
    }
}
