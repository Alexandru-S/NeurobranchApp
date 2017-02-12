package com.glassbyte.neurobranch.Services;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by ed on 10/06/16.
 */
public class Globals {
    public static final boolean isDebug = false;

    //networking
    private static final String BACKEND_URL = "http://www.neurobranchbeta.com";
    private static final String EMULATOR_LOOPBACK = "http://10.0.2.2:80";
    public static final String HOST_ADDRESS = BACKEND_URL;

    public static final String POST_TRIAL_RESPONSE = HOST_ADDRESS + "insert";
    public static final String POST_QUESTION_RESPONSE = HOST_ADDRESS + "api/responsedata";

    //candidate actions
    public static String getCandidateInfo(String candidateId) {
        return HOST_ADDRESS + "/api/get-candidates/" + candidateId;
    }

    public static String retrieveTrialQuestions(String trialid) {
        return HOST_ADDRESS + "/api/get-questions/trialid/" + trialid;
    }

    public static String retrieveEligibilityQuestions(String trialid) {
        return HOST_ADDRESS + "/api/get-eligibility/trialid/" + trialid;
    }

    public static String getRequestedCandidates(String candidateid) {
        return HOST_ADDRESS + "/api/get-requested-candidates/candidate/" + candidateid;
    }

    public static String getPartitiveMyTrials(String candidateid) {
        return HOST_ADDRESS + "/api/get-candidate-my-trials/" + candidateid;
    }

    public static String getActivePartitiveMyTrials(String candidateid) {
        return HOST_ADDRESS + "/api/get-candidate-trials/" + candidateid + "/state/active";
    }

    public static String getActivePartitiveUnansweredTrials(String candidateid) {
        return HOST_ADDRESS + "/api/get-candidate-unanswered-trials/" + candidateid;
    }

    public static String getExcludedTrials(String candidateid) {
        return HOST_ADDRESS + "/api/get-candidate-excluded-trials/" + candidateid;
    }

    public static String getTrialById(String trialid) {
        return HOST_ADDRESS + "/api/get-trial/trialid/" + trialid;
    }

    public static String getLatestWindow(String trialid, String candidateid) {
        return HOST_ADDRESS + "/api/get-latest-window/trialid/" + trialid + "/candidateid/" + candidateid;
    }

    public static String getLastResponse(String trialid, String candidateid) {
        return HOST_ADDRESS + "/api/get-last-candidate-response/" + trialid + "/" + candidateid;
    }

    public static String createTrialRelationship(String trialid, String candidateid) {
        return HOST_ADDRESS + "/api/create-trial-relationship/candidateid/" + candidateid + "/trialid/" + trialid;
    }

    public static final String CANDIDATE_SIGNUP_ADDRESS = HOST_ADDRESS + "/api/create-candidate";
    public static final String CANDIDATE_LOGIN_ADDRESS = HOST_ADDRESS + "/api/candidate-login";

    public static final String RETRIEVE_TRIALS_ADDRESS = HOST_ADDRESS + "/api/get-trials";
    public static final String POST_RESPONSE_ADDRESS = HOST_ADDRESS + "/api/create-response/";

    public static final String ADD_TO_REQUESTED_LIST = HOST_ADDRESS + "/api/create-requested-candidate";

    //programmatic layout helpers
    public static int getDp(Context context, float pixels) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                pixels, context.getResources().getDisplayMetrics());
    }
}
