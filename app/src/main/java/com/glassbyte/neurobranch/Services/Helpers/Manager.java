package com.glassbyte.neurobranch.Services.Helpers;

import android.content.Context;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.glassbyte.neurobranch.R;
import com.glassbyte.neurobranch.Services.Enums.Preferences;

/**
 * Created by ed on 01/09/2016.
 */
public class Manager {
    private static Manager manager = null;
    public static Manager getInstance() {
        manager = manager == null ? new Manager() : manager;
        return manager;
    }

    public String getPreference(Preferences preference, Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(preference.name(), "");
    }

    public void setPreference(Preferences preference, String value, Context context) {
        PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putString(preference.name(), value).apply();
    }

    public void setFragment(FragmentManager fragmentManager, Fragment fragment) {
        fragmentManager.beginTransaction().replace(R.id.auth_frame, fragment).commit();
    }
}
