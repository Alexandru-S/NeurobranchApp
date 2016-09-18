package com.glassbyte.neurobranch.Services.DataObjects;

import android.graphics.drawable.Drawable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by ed on 25/06/16.
 */
public class JSON {

    public static class DataFormatting {
        //trial base attributes from trial object
        public static final String TRIAL_ID = "_id";
        public static final String TRIAL_NAME = "title";
        public static final String TRIAL_BRIEF_DESCRIPTION = "briefdescription";
        public static final String TRIAL_DETAILED_DESCRIPTION = "detaileddescription";
        public static final String TRIAL_TYPE = "trialtype";
        public static final String TRIAL_INSTITUTE = "institute";
        public static final String TRIAL_CONDITION = "condition";
        public static final String TRIAL_DURATION = "duration";
        public static final String TRIAL_FREQUENCY = "frequency";
        public static final String TRIAL_WAIVER = "waiver";
        public static final String TRIAL_ELIGIBILITY_FORM = "eligibility";
        public static final String TRIAL_DATE_CREATED = "datecreated";
        public static final String TRIAL_DATE_STARTED = "datestarted";
        public static final String TRIAL_DATE_ENDED = "dateended";
        public static final String TRIAL_CANDIDATE_QUOTA = "candidatequota";
        public static final String TRIAL_STATE = "state";
        public static final String TRIAL_RESEARCHER_ID = "researcherid";
    }

    public static JSONObject generatePost(ArrayList<String> metaKeys, ArrayList<String> metaValues,
                                          ArrayList<String> questions, ArrayList<String> answers) throws JSONException {
        JSONObject trialResponses = new JSONObject();
        for (int i = 0; i < questions.size(); i++) {
            trialResponses.put(questions.get(i), answers.get(i));
        }

        JSONArray data = new JSONArray();
        data.put(trialResponses);

        JSONObject metaData = new JSONObject();
        for (int i = 0; i < metaKeys.size(); i++) {
            metaData.put(metaKeys.get(i), metaValues.get(i));
        }
        metaData.put("Post", data);

        return metaData;
    }

    public static ArrayList<Object> parseQuestions(JSONArray receivedQuestions) {
        System.out.println(receivedQuestions);
        ArrayList<Object> questionGroup = new ArrayList<>();
        try {
            for (int i = 0; i < receivedQuestions.length(); i++) {
                ArrayList<String> questionSet = new ArrayList<>();

                JSONObject questionElement = receivedQuestions.getJSONObject(i);
                String questionTitle = questionElement.getString("title");
                String type = questionElement.getString("questiontype");

                questionSet.add(questionTitle);
                questionSet.add(type);

                if (questionElement.has("answers")) {
                    JSONObject answerElements = questionElement.getJSONObject("answers");

                    for (int j = 0; j < answerElements.length(); j++) {
                        String answer = answerElements.get("answer" + j).toString();
                        System.out.println(answer);
                        questionSet.add(answer);
                    }
                }
                questionGroup.add(questionSet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return questionGroup;
    }

    public static ArrayList<Trial> parseTrialJSON(JSONArray retrievedTrials) {
        ArrayList<Trial> trials = new ArrayList<>();
        try {
            System.out.println(retrievedTrials);

            for (int i = 0; i < retrievedTrials.length(); i++) {
                JSONObject trial = retrievedTrials.getJSONObject(i);

                String id = trial.getString(JSON.DataFormatting.TRIAL_ID);
                String title = trial.getString(JSON.DataFormatting.TRIAL_NAME);
                String briefDesc = trial.getString(DataFormatting.TRIAL_BRIEF_DESCRIPTION);
                String detailedDesc = trial.getString(DataFormatting.TRIAL_DETAILED_DESCRIPTION);
                String type = trial.getString(JSON.DataFormatting.TRIAL_TYPE);
                String institute = trial.getString(DataFormatting.TRIAL_INSTITUTE);
                String condition = trial.getString(DataFormatting.TRIAL_CONDITION);

                String duration = trial.getString(DataFormatting.TRIAL_DURATION);
                String frequency = trial.getString(DataFormatting.TRIAL_FREQUENCY);
                String eligibilityForm = "eligibilityForm"; //trial.getString(DataFormatting.TRIAL_ELIGIBILITY_FORM);
                String waiver = "Waiver ayyyyyyyyy lmao lol"; //trial.getString(DataFormatting.TRIAL_SCREENING_FORM);

                String dateCreated = trial.getString(DataFormatting.TRIAL_DATE_CREATED) == null ? "0" : trial.getString(DataFormatting.TRIAL_DATE_CREATED);
                String dateStarted = "0"; //trial.getString(DataFormatting.TRIAL_DATE_STARTED) == null ? "0" : trial.getString(DataFormatting.TRIAL_DATE_STARTED);
                String dateEnded = "0"; //trial.getString(DataFormatting.TRIAL_DATE_ENDED)== null ? "0" : trial.getString(DataFormatting.TRIAL_DATE_ENDED);
                String candidateQuota = trial.getString(DataFormatting.TRIAL_CANDIDATE_QUOTA);
                String state = trial.getString(DataFormatting.TRIAL_STATE);
                String researcherId = trial.getString(DataFormatting.TRIAL_RESEARCHER_ID);

                trials.add(new Trial(id, title, briefDesc, detailedDesc, type,
                        institute, condition, Integer.parseInt(duration), frequency,
                        waiver, eligibilityForm, Long.parseLong(dateCreated),
                        Long.parseLong(dateStarted), Long.parseLong(dateEnded),
                        Integer.parseInt(candidateQuota), Attributes.getTrialState(state), researcherId));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return trials;
    }

    public static JSONObject parseList(ArrayList<Object> list) {
        JSONObject response = new JSONObject();
        try {
            response.put("questiontype", list.get(0));
            response.put("questionindex", list.get(1));

            for (int i = 2; i < list.size(); i++) {
                ArrayList<String> answers = (ArrayList<String>) list.get(i);
                for (int j = 0; j < answers.size(); j++) {
                    response.put("answer" + j, answers.get(j));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return response;
    }
}
