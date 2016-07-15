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

/**
 * Created by ed on 10/06/16.
 */
public class Settings extends PreferenceActivity {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    PreferenceScreen preferenceScreen;

    @SuppressLint("ValidFragment")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new PreferenceFragment() {
            @Override
            public void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setTheme(R.style.AppTheme);

                sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
                preferenceScreen = getPreferenceManager().createPreferenceScreen(this.getActivity());

                //headings for each category of user preference
                PreferenceCategory profileSection, notificationSection, updateSection, otherSection;

                profileSection = new PreferenceCategory(this.getActivity());
                profileSection.setTitle("Profile Settings");
                profileSection.setKey("profile_section");

                notificationSection = new PreferenceCategory(this.getActivity());
                notificationSection.setTitle("Notification Settings");
                notificationSection.setKey("profile_section");

                updateSection = new PreferenceCategory(this.getActivity());
                updateSection.setTitle("Sync/Update Settings");
                updateSection.setKey("profile_section");

                otherSection = new PreferenceCategory(this.getActivity());
                otherSection.setTitle("Miscellaneous Settings");
                otherSection.setKey("profile_section");

                //preferences for each category
                final Preference name = new Preference(this.getActivity());
                name.setTitle("Name");
                name.setSummary("Name here");
                name.setKey("name_pref");

                Preference changeUsername = new Preference(this.getActivity());
                changeUsername.setTitle("Username");
                changeUsername.setSummary("Username here");

                Preference changeDOB = new Preference(this.getActivity());
                changeDOB.setTitle("Date of Birth");
                changeDOB.setSummary("DOB here");

                Preference changePhoneNumber = new Preference(this.getActivity());
                changePhoneNumber.setTitle("Phone Number");
                changePhoneNumber.setSummary("Phone number here");

                /*
                    allowing the user to decide how frequent that notifications should notify the
                    user to complete a given trial, allowing the user to choose how bombarded they
                    want to be depending on their preferences
                 */
                Preference notificationFrequency = new Preference(this.getActivity());
                notificationFrequency.setTitle("Notification Frequency");
                notificationFrequency.setSummary("Summary");

                Preference updateFrequency = new Preference(this.getActivity());
                updateFrequency.setTitle("Content Update Frequency");
                updateFrequency.setSummary("Summary");

                preferenceScreen.addPreference(profileSection);
                preferenceScreen.addPreference(notificationSection);
                preferenceScreen.addPreference(updateSection);
                preferenceScreen.addPreference(otherSection);

                profileSection.addPreference(name);
                profileSection.addPreference(changeUsername);
                profileSection.addPreference(changeDOB);
                profileSection.addPreference(changePhoneNumber);

                setPreferenceScreen(preferenceScreen);
            }

            @Override
            public void onActivityCreated(Bundle savedInstanceState) {
                super.onActivityCreated(savedInstanceState);
            }
        }).commit();
    }
}
