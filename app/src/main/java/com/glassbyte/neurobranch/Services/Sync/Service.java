package com.glassbyte.neurobranch.Services.Sync;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.content.WakefulBroadcastReceiver;

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
import java.util.Calendar;

/**
 * Created by ed on 14/08/16
 */
public class Service {
    public static class SyncService extends IntentService {
        public SyncService() {
            super("SyncService");
        }

        @Override
        protected void onHandleIntent(Intent intent) {
            WebServer.PollAccount.pollTrialStates(getApplicationContext());
        }
    }

    public class AlarmReceiver extends WakefulBroadcastReceiver {
        public static final int INTERVAL = 1000 * 60 * 60; // 1 hour

        private AlarmManager alarmManager;
        private PendingIntent pendingIntent;

        @Override
        public void onReceive(Context context, Intent intent) {
            startWakefulService(context, new Intent(context, SyncService.class));
        }

        public void setAlarm(Context context) {
            alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            pendingIntent = PendingIntent.getBroadcast(context, 0,
                    new Intent(context, SyncService.class), 0);

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis() + INTERVAL);

            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_HOUR, pendingIntent);

            //reset alarm on reboot
            ComponentName receiver = new ComponentName(context, SyncService.class);
            PackageManager packageManager = context.getPackageManager();
            packageManager.setComponentEnabledSetting(receiver,
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                    PackageManager.DONT_KILL_APP);
        }

        //if user isn't part of any trials
        public void cancelAlarm(Context context) {
            if (alarmManager != null)
                alarmManager.cancel(pendingIntent);

            ComponentName receiver = new ComponentName(context, SyncService.class);
            PackageManager packageManager = context.getPackageManager();

            packageManager.setComponentEnabledSetting(receiver,
                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                    PackageManager.DONT_KILL_APP);
        }
    }

    public class BootReceiver extends BroadcastReceiver {
        AlarmReceiver alarmReceiver = new AlarmReceiver();

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED"))
                alarmReceiver.setAlarm(context);
        }
    }
}
