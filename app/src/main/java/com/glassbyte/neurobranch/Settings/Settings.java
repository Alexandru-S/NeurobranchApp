package com.glassbyte.neurobranch.Settings;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;

import com.glassbyte.neurobranch.R;
import com.glassbyte.neurobranch.Services.HTTP.HTTPRequest;
import com.glassbyte.neurobranch.Services.Interfaces.GetDetailsCallback;
import com.glassbyte.neurobranch.Services.Sync.WebServer;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ed on 10/06/16.
 */
public class Settings extends PreferenceActivity implements GetDetailsCallback {
    SharedPreferences sharedPreferences;
    PreferenceScreen preferenceScreen;

    Preference idPreference, emailPreference, verifiedPreference;

    @SuppressLint("ValidFragment")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new PreferenceFragment() {
            @Override
            public void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setTheme(R.style.AppTheme);

                new HTTPRequest.GetCandidateDetails(WebServer.getCandidateId(Settings.this),
                            Settings.this).execute();
            }

            @Override
            public void onActivityCreated(Bundle savedInstanceState) {
                super.onActivityCreated(savedInstanceState);
            }
        }).commit();
    }

    @Override
    public void onRetrieved(JSONObject jsonObject) {
        try {
            String id = jsonObject.getString("_id");
            String email = jsonObject.getString("email");
            String isVerified = jsonObject.getString("isverified");

            if(isVerified.equals("true")) isVerified = "Verified";
            else isVerified = "Unverified";

            createSettings(id, email, isVerified);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void createSettings(String id, String email, String isVerified) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Settings.this);
        preferenceScreen = getPreferenceManager().createPreferenceScreen(Settings.this);

        //headings for each category of user preference
        PreferenceCategory profileSection;

        profileSection = new PreferenceCategory(Settings.this);
        profileSection.setTitle("Profile Details");
        profileSection.setKey("profile_section");

        //preferences for each category
        idPreference = new Preference(Settings.this);
        idPreference.setTitle("ID");
        idPreference.setSummary(id);

        emailPreference = new Preference(Settings.this);
        emailPreference.setTitle("Email");
        emailPreference.setSummary(email);
        emailPreference.setKey("email_pref");

        verifiedPreference = new Preference(Settings.this);
        verifiedPreference.setTitle("Verification Status");
        verifiedPreference.setSummary(isVerified);

        preferenceScreen.addPreference(profileSection);

        profileSection.addPreference(idPreference);
        profileSection.addPreference(emailPreference);
        profileSection.addPreference(verifiedPreference);

        setPreferenceScreen(preferenceScreen);
    }
}
