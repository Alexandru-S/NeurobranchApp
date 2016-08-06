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
    public static final String BACKEND_URL = "http://http://ec2-54-229-150-246.eu-west-1.compute.amazonaws.com";
    public static final String EMULATOR_LOOPBACK = "http://10.0.2.2:3000";

    public static final String HOST_ADDRESS = EMULATOR_LOOPBACK;

    public static final String POST_TRIAL_RESPONSE = HOST_ADDRESS + "insert";
    public static final String POST_QUESTION_RESPONSE = HOST_ADDRESS + "api/responsedata";

    //candidate actions
    public static final String CANDIDATE_SIGNUP_ADDRESS = HOST_ADDRESS + "/api/create-candidate";
    public static String CANDIDATE_LOGIN_ADDRESS(String email, String password) {
        return HOST_ADDRESS + "/api/candidate-login/" + email + "/" + password;
    }
    public static final String RETRIEVE_TRIALS_ADDRESS = HOST_ADDRESS + "/api/get-trials";
    public static String POST_RESPONSE_ADDRESS = HOST_ADDRESS + "/api/create-response/";

    //deprecated static hosting
    public static final String GET_QUESTIONS_ADDRESS = "http://www.glassbyte.com/neurobranch_mock_data/questions.json";

    //debug statics
    public static final String MOCK_RESPONSE = "{\"trialid\":\"trialidbody111\",\"epochid\":\"epochidbody111\",\"candidateid\":\"candidateidbody111\"}"; //,\"response\":[{\"q0\":[{\"qtype\":\"checkbox\"},{\"qindex\":0},{\"response0\":\"\"},{\"response1\":\"\"},{\"response2\":\"\"},{\"response3\":\"\"}]},{\"q1\":[{\"qtype\":\"scale\"},{\"qindex\":1}]},{\"q2\":[{\"qtype\":\"choice\"},{\"qindex\":2},{\"response0\":\"This is a radio button!\"},{\"response1\":\"Radio buttons ahoy\"},{\"response2\":\"Is this a radio button\"},{\"response3\":\"Radio button\"}]},{\"q3\":[{\"qtype\":\"section\"},{\"qindex\":3},{\"response0\":\"\"}]}]}";

    //programmatic layout helpers
    public static int getDp(Context context, float pixels) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                pixels, context.getResources().getDisplayMetrics());
    }
}
