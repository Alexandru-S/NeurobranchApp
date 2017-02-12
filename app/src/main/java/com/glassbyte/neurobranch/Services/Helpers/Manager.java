package com.glassbyte.neurobranch.Services.Helpers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.glassbyte.neurobranch.Portal.QuestionPrefabs.EpochHolder;
import com.glassbyte.neurobranch.R;
import com.glassbyte.neurobranch.Services.DataObjects.Epoch;
import com.glassbyte.neurobranch.Services.DataObjects.Trial;
import com.glassbyte.neurobranch.Services.Enums.Preferences;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by ed on 01/09/2016.
 */
public class Manager {

    private static Manager manager = new Manager();
    private ProgressDialog progressDialog;

    public static Manager getInstance() {
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

    public void notifyUserWeb(Context context, Trial trial) {
        Intent intent = new Intent(context, EpochHolder.class);
        intent.putExtra("TRIAL", trial);
        notifyUser(context, intent, trial);
    }

    public static void cancelNotification(Context context, Trial trial) {
        NotificationManager manager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        int notificationId = parseMongoId(trial);
        manager.cancel(notificationId);
    }

    public static int parseMongoId(Trial trial) {
        int id = 0;

        for (int i = trial.getTrialId().length() - 1; i > -1; i--)
            id += ((int) trial.getTrialId().charAt(i)) * ((i) * 16);

        return id;
    }

    public void notifyIndefinite(Context context) {
        if(progressDialog == null)
            progressDialog = new ProgressDialog(context);
        if(progressDialog.getContext() != context)
            progressDialog.cancel();
            progressDialog = new ProgressDialog(context);

        progressDialog.setMessage("Please wait ...");
        progressDialog.show();
    }

    public void cancelNotifyIndefinite() {
        progressDialog.cancel();
    }

    private void notifyUser(Context context, Intent intent, Trial trial) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.web_hi_res_512))
                .setSmallIcon(R.drawable.cloud)
                .setContentTitle("New questions can be answered")
                .setContentText(trial.getTitle());

        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(context);
        taskStackBuilder.addParentStack(EpochHolder.class).addNextIntent(intent);

        PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(
                0, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        // TODO 0 is placeholder, replace with trial id parsed to int
        notificationManager.notify(parseMongoId(trial), builder.build());
    }
}
