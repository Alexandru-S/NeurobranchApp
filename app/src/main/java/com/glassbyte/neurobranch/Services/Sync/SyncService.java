package com.glassbyte.neurobranch.Services.Sync;

import android.app.IntentService;
import android.content.Intent;

import com.glassbyte.neurobranch.Services.DataObjects.Trial;
import com.glassbyte.neurobranch.Services.Enums.Preferences;
import com.glassbyte.neurobranch.Services.Globals;
import com.glassbyte.neurobranch.Services.HTTP.HTTPRequest;
import com.glassbyte.neurobranch.Services.Helpers.Manager;
import com.glassbyte.neurobranch.Services.Interfaces.JSONCallback;

import org.json.JSONArray;
import org.json.JSONException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by ed on 14/08/16
 */
public class SyncService extends IntentService {
    ArrayList<Trial> trials = new ArrayList<>();

    public SyncService() {
        super("SyncService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        System.out.println("Invoking web service");

        JSONCallback jsonCallback = new JSONCallback() {
            @Override
            public void onLoadCompleted(JSONArray object) {
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
                    for (Trial trial : trials)
                        Manager.getInstance().notifyUserWeb(getApplicationContext(), trial.getTrialId());
                } else {
                    System.out.println("No trials currently partitive + active for user");
                }
            }
        };
        try {
            URL url = new URL(Globals.getActivePartitiveMyTrials(Manager.getInstance()
                    .getPreference(Preferences.id, getApplicationContext()), "active"));
            new HTTPRequest.ReceiveJSON(getApplicationContext(), url, jsonCallback).get();
        } catch (InterruptedException | ExecutionException | MalformedURLException e) {
            e.printStackTrace();
        }
    }
}

