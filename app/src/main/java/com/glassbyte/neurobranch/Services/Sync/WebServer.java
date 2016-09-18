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

/**
 * Created by ed on 14/08/16.
 */
public class WebServer {
    public static void synchronise(Context context, String trialid) {
        Manager.getInstance().notifyUserWeb(context, trialid);
    }

    public static class PollAccount {
        public static String getCandidateId(Context context) {
            return PreferenceManager.getDefaultSharedPreferences(context).getString("id", "");
        }

        public static void pollTrialStates(final Context context) {
            final ArrayList<Trial> trials = new ArrayList<>();

            JSONCallback jsonCallback = new JSONCallback() {
                @Override
                public void onLoadCompleted(JSONArray object) {
                    System.out.println("partitive trials " + object);
                    for (int i = 0; i < object.length(); i++) {
                        try {
                            trials.add(new Trial(object.getJSONObject(i)));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    for (Trial trial : trials) WebServer.synchronise(context, trial.getTrialId());
                }
            };
            try {
                new HTTPRequest.ReceiveJSON(context, new URL(Globals.getPartitiveMyTrials(
                        Manager.getInstance().getPreference(Preferences.id, context))), jsonCallback).execute();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }
}
