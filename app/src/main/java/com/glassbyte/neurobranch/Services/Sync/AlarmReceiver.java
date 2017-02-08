package com.glassbyte.neurobranch.Services.Sync;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.glassbyte.neurobranch.Services.Globals;

import java.util.Calendar;

/**
 * Created by ed on 25/09/2016
 */
public class AlarmReceiver extends WakefulBroadcastReceiver {
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    // debug 1 min v release every hour
    private static final long INTERVAL = Globals.isDebug ? (1000 * 60) : AlarmManager.INTERVAL_HOUR;

    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("Invoking wakeful service");
        startWakefulService(context, new Intent(context, SyncService.class));
    }

    public void setAlarm(Context context) {
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        pendingIntent = PendingIntent.getBroadcast(context, 0,
                new Intent(context, AlarmReceiver.class), 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis() + INTERVAL);

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                INTERVAL, pendingIntent);

        //reset alarm on reboot
        ComponentName receiver = new ComponentName(context, BootReceiver.class);
        PackageManager packageManager = context.getPackageManager();
        packageManager.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }

    public void cancelAlarm(Context context) {
        if (alarmManager != null)
            alarmManager.cancel(pendingIntent);

        ComponentName receiver = new ComponentName(context, BootReceiver.class);
        PackageManager packageManager = context.getPackageManager();

        packageManager.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }
}
