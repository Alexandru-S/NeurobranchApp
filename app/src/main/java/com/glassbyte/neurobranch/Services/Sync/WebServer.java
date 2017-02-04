package com.glassbyte.neurobranch.Services.Sync;

import android.content.Context;
import android.preference.PreferenceManager;

import com.glassbyte.neurobranch.Services.DataObjects.Trial;
import com.glassbyte.neurobranch.Services.Enums.Preferences;
import com.glassbyte.neurobranch.Services.Globals;
import com.glassbyte.neurobranch.Services.HTTP.HTTPRequest;
import com.glassbyte.neurobranch.Services.Helpers.Manager;
import com.glassbyte.neurobranch.Services.Interfaces.JSONCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by ed on 14/08/16.
 */
public class WebServer {
    public static String getCandidateId(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString("id", "");
    }

    public static void pollTrialStates(final Context context) {
        JSONCallback jsonCallback = new JSONCallback() {
            @Override
            public void onLoadCompleted(JSONArray object) {
                ArrayList<Trial> trials = new ArrayList<>();
                if (object != null) {
                    System.out.println("partitive trials " + object);
                    System.out.println(object.length() + " trial(s) currently partitive + active");
                    for (int i = 0; i < object.length(); i++) {
                        try {
                            trials.add(new Trial(object.getJSONObject(i)));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    for (Trial trial : trials) Manager.getInstance().notifyUserWeb(context, trial);
                } else {
                    System.out.println("No trials currently partitive + active for user");
                }
            }
        };
        try {
            new HTTPRequest.ReceiveJSON(context, new URL(Globals.getActivePartitiveMyTrials(
                    Manager.getInstance().getPreference(Preferences.id, context), "active")),
                    jsonCallback).get();
        } catch (MalformedURLException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
