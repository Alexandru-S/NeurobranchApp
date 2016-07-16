package com.glassbyte.neurobranch.Services;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by ed on 10/06/16.
 */
public class Globals {
    //splash
    public static final int SPLASH_DURATION = 2000;

    //networking
    public static final String BACKEND_URL = "http://localhost:3000/";
    public static final String EMULATOR_LOOPBACK = "http://10.0.2.2:3000/";

    public static final String POST_TRIALS_ADDRESS = EMULATOR_LOOPBACK;
    public static final String GET_TRIALS_ADDRESS = POST_TRIALS_ADDRESS + "api/trialdata";
    public static final String POST_TRIAL_RESPONSE = POST_TRIALS_ADDRESS + "insert";

    //deprecated static hosting
    public static final String GET_TRIALS_GLASSBYTE = "http://www.glassbyte.com/neurobranch_mock_data/trials.json";
    public static final String GET_QUESTIONS_ADDRESS = "http://www.glassbyte.com/neurobranch_mock_data/questions.json";

    //programmatic layout helpers
    public static int getDp(Context context, float pixels) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                pixels, context.getResources().getDisplayMetrics());
    }
}
