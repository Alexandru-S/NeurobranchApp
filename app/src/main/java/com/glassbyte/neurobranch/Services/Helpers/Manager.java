package com.glassbyte.neurobranch.Services.Helpers;

import android.app.NotificationManager;
import android.app.PendingIntent;
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

    private final static AtomicInteger integer = new AtomicInteger(0);

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

    private int getNotificationId() {
        return integer.incrementAndGet();
    }

    public void notifyUserWeb(Context context, String trialId, boolean isEligibility) {
        Bundle bundle = new Bundle();
        bundle.putString("TRIAL_ID", trialId);
        bundle.putBoolean("TRIAL_ELIGIBILITY", isEligibility);
        notifyUser(context, bundle);
    }

    public void launchQuestionHolder(Context context, Trial trial, boolean isEligibilityLaunch) {
        Intent intent = new Intent(context, EpochHolder.class);
        intent.putExtra("TRIAL_ID", trial.getTrialId());
        intent.putExtra("TRIAL_ELIGIBILITY", isEligibilityLaunch);
        context.startActivity(intent);
    }

    private void notifyUser(Context context, Bundle dataBundle) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.web_hi_res_512))
                .setSmallIcon(R.drawable.cloud)
                .setContentTitle("New questions can be answered")
                .setContentText(dataBundle.getString("TRIAL_ID"));

        Intent resultIntent = new Intent(context, EpochHolder.class);
        resultIntent.putExtras(dataBundle);
        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(context);
        taskStackBuilder.addParentStack(EpochHolder.class).addNextIntent(resultIntent);

        PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(0,
                PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(getNotificationId(), builder.build());
    }
}
