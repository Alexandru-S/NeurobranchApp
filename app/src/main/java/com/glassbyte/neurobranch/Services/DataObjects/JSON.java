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
        //0: string -> group #
        //1: arrayList -> q, type, answer, answer, answer...answer
        //2: arrayList -> q, type
        //...
        //n
        System.out.println(receivedQuestions);

        ArrayList<Object> questionGroup = new ArrayList<>();
        try {
            for (int i = 0; i < receivedQuestions.length(); i++) {
                ArrayList<String> questionSet = new ArrayList<>();

                JSONObject questionElement = receivedQuestions.getJSONObject(i);
                String questionTitle = questionElement.getString("question");
                String type = questionElement.getString("questiontype");

                questionSet.add(questionTitle);
                questionSet.add(type);

                if (questionElement.has("options")) {
                    JSONArray answerElements = questionElement.getJSONArray("options");

                    for (int j = 0; j < answerElements.length(); j++) {
                        JSONObject answer = answerElements.getJSONObject(j);
                        questionSet.add(answer.getString("answer"));
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
        Drawable cardIcon = null; //ResourcesCompat.getDrawable(getResources(), R.mipmap.ic_launcher, null);

        try {
            System.out.println(retrievedTrials);

            for (int i = 0; i < retrievedTrials.length(); i++) {
                JSONObject trial = retrievedTrials.getJSONObject(i);

                String title = trial.getString(JSON.DataFormatting.TRIAL_NAME);
                String briefDesc = trial.getString(DataFormatting.TRIAL_BRIEF_DESCRIPTION);
                String detailedDesc = trial.getString(DataFormatting.TRIAL_DETAILED_DESCRIPTION);
                String type = trial.getString(JSON.DataFormatting.TRIAL_TYPE);
                String institute = trial.getString(DataFormatting.TRIAL_INSTITUTE);
                String condition = trial.getString(DataFormatting.TRIAL_CONDITION);
                String dateCreated = trial.getString(DataFormatting.TRIAL_DATE_CREATED);
                String dateStarted = trial.getString(DataFormatting.TRIAL_DATE_STARTED);
                String dateEnded = trial.getString(DataFormatting.TRIAL_DATE_ENDED);
                String candidateQuota = trial.getString(DataFormatting.TRIAL_CANDIDATE_QUOTA);
                String state = trial.getString(DataFormatting.TRIAL_STATE);
                String researcherId = trial.getString(DataFormatting.TRIAL_RESEARCHER_ID);
                String id = trial.getString(JSON.DataFormatting.TRIAL_ID);

                trials.add(new Trial(title, briefDesc, detailedDesc, Attributes.getType(type),
                        institute, condition, Long.parseLong(dateCreated),
                        0,//dateStarted == null ? 0 : Long.parseLong(dateStarted),
                        0,//dateEnded == null ? 0 : Long.parseLong(dateEnded),
                        Integer.parseInt(candidateQuota),
                        Attributes.getTrialState(state), researcherId, id));
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
