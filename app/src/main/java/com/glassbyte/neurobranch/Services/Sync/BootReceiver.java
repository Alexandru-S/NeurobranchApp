package com.glassbyte.neurobranch.Services.Sync;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by ed on 25/09/2016
 */
public class BootReceiver extends BroadcastReceiver {
    AlarmReceiver alarmReceiver = new AlarmReceiver();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED"))
            alarmReceiver.setAlarm(context);
    }
}