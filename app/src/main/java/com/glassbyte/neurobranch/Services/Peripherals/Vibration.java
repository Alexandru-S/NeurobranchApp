package com.glassbyte.neurobranch.Services.Peripherals;

import android.content.Context;
import android.os.Vibrator;

/**
 * Created by ed on 11/06/16.
 */
public class Vibration {
    public static final int SHORT = 500;
    public static final int MEDIUM = 1000;
    public static final int LONG = 1500;

    public static void vibrate(Context context, int length) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(length);
    }
}
