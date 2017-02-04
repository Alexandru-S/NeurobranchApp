package com.glassbyte.neurobranch.Services.DataObjects;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ed on 25/06/16.
 */
public class JSON {
    static class DataFormatting {
        //trial base attributes from trial object
        static final String TRIAL_ID = "_id";
        static final String TRIAL_NAME = "title";
        static final String TRIAL_BRIEF_DESCRIPTION = "briefdescription";
        static final String TRIAL_DETAILED_DESCRIPTION = "detaileddescription";
        static final String TRIAL_TYPE = "trialtype";
        static final String TRIAL_INSTITUTE = "institute";
        static final String TRIAL_TAGS = "tags";
        static final String TRIAL_DURATION = "duration";
        static final String TRIAL_FREQUENCY = "frequency";
        static final String TRIAL_WAIVER = "waiverform";
        static final String TRIAL_ELIGIBILITY_FORM = "has_eligibility";
        static final String TRIAL_DATE_CREATED = "datecreated";
        static final String TRIAL_DATE_STARTED = "datestarted";
        static final String TRIAL_DATE_ENDED = "dateended";
        static final String TRIAL_CANDIDATE_QUOTA = "candidatequota";
        static final String TRIAL_STATE = "state";
        static final String TRIAL_RESEARCHER_ID = "researcherid";
        static final String TRIAL_PASS_MARK = "min_pass_mark";
        static final String TRIAL_CURRENT_DURATION = "currentduration";
    }

    public static ArrayList<Object> parseQuestions(JSONArray receivedQuestions) {
        System.out.println(receivedQuestions);
        ArrayList<Object> questionGroup = new ArrayList<>();
        try {
            for (int i = 0; i < receivedQuestions.length(); i++) {
                ArrayList<String> questionSet = new ArrayList<>();

                JSONObject questionElement = receivedQuestions.getJSONObject(i);
                String questionTitle = questionElement.getString("title");
                String type = Attributes.getQuestionType(questionElement.getString("question_type")).name();

                questionSet.add(questionTitle);
                questionSet.add(type);

                if (questionElement.has("answers")) {
                    JSONArray answerElements = questionElement.getJSONArray("answers");

                    for (int j = 0; j < answerElements.length(); j++) {
                        String answer = answerElements.getJSONObject(j).getString("answer");
                        questionSet.add(answer);

                        String score = null;

                        if (answerElements.getJSONObject(j).has("score")) {
                            score = answerElements.getJSONObject(j).getString("score");
                            questionSet.add(score);
                        }
                        System.out.println(answer + ", " + score);
                    }
                }
                questionGroup.add(questionSet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(questionGroup);
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
                ArrayList<String> tags = parseJSONArray((JSONArray) trial.get(DataFormatting.TRIAL_TAGS));

                int duration = trial.getInt(DataFormatting.TRIAL_DURATION);
                String frequency = trial.getString(DataFormatting.TRIAL_FREQUENCY);
                String waiver = trial.getString(DataFormatting.TRIAL_WAIVER);

                Long dateCreated = trial.getLong(DataFormatting.TRIAL_DATE_CREATED);
                Long dateStarted = trial.getLong(DataFormatting.TRIAL_DATE_STARTED);
                Long dateEnded = trial.getLong(DataFormatting.TRIAL_DATE_ENDED);
                int candidateQuota = trial.getInt(DataFormatting.TRIAL_CANDIDATE_QUOTA);
                Attributes.TrialState state = Attributes.getTrialState(trial.getString(DataFormatting.TRIAL_STATE));
                String researcherId = trial.getString(DataFormatting.TRIAL_RESEARCHER_ID);
                int passmark = trial.getInt(DataFormatting.TRIAL_PASS_MARK);
                int currentDuration = trial.getInt(DataFormatting.TRIAL_CURRENT_DURATION);
                boolean hasEligibility = trial.getBoolean(DataFormatting.TRIAL_ELIGIBILITY_FORM);

                trials.add(new Trial(id, title, briefDesc, detailedDesc, type,
                        institute, tags, duration, frequency, waiver,
                        dateCreated, dateStarted, dateEnded, candidateQuota, state,
                        researcherId, passmark, currentDuration, hasEligibility));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return trials;
    }

    static ArrayList<String> parseJSONArray(JSONArray array) {
        ArrayList<String> objects = new ArrayList<>();

        for (int i = 0; i < array.length(); i++) {
            try {
                JSONObject object = array.getJSONObject(i);
                objects.add(object.getString("tag"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return objects;
    }

    static JSONObject parseList(ArrayList<Object> list) {
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
