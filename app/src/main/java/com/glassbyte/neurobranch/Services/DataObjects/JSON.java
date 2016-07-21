package com.glassbyte.neurobranch.Services.DataObjects;

import android.graphics.drawable.Drawable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ed on 25/06/16.
 */
public class JSON {

    public static class DataFormatting {
        //trial base attributes
        public static final String TRIAL_ID = "_id";
        public static final String TRIAL_NAME = "trialname";
        public static final String TRIAL_DESCRIPTION = "description";
        public static final String TRIAL_TYPE = "trialtype";
        public static final String TRIAL_ORGANISATION = "organisation";
        public static final String TRIAL_SPECIALISATION = "specialisation";
        public static final String TRIAL_START_TIME = "starttime";
        public static final String TRIAL_END_TIME = "endtime";
        public static final String TRIAL_TIME_PERIOD_FREQUENCY = "timeperiodfrequency";
        public static final String TRIAL_NOTIFICATION_FREQUENCY = "notificationfrequency";
        public static final String TRIAL_IMAGE_RESOURCE = "imageresource";

        //prereqs
        public static final String TRIAL_PREREQUISITES = "prerequisites";
        public static final String TRIAL_PREREQ_MIN_AGE = "minage";
        public static final String TRIAL_PREREQ_CONDITION = "condition";
        public static final String TRIAL_PREREQ_PREREQ_TYPE = "prereqtype";

        //researcher
        public static final String TRIAL_RESEARCHER = "researcher";
        public static final String TRIAL_RESEARCHER_NAME = "researchername";
        public static final String TRIAL_RESEARCH_GROUP_ID = "researchgroup";
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

                String trialId = trial.getString(JSON.DataFormatting.TRIAL_ID);
                String trialName = trial.getString(JSON.DataFormatting.TRIAL_NAME);
                String trialDesc = trial.getString(JSON.DataFormatting.TRIAL_DESCRIPTION);
                String trialType = trial.getString(JSON.DataFormatting.TRIAL_TYPE);
                String trialOrganisation = trial.getString(JSON.DataFormatting.TRIAL_ORGANISATION);
                String trialSpecialisation = trial.getString(JSON.DataFormatting.TRIAL_SPECIALISATION);
                String trialStartTime = trial.getString(JSON.DataFormatting.TRIAL_START_TIME);
                String trialEndTime = trial.getString(JSON.DataFormatting.TRIAL_END_TIME);
                String trialTimePeriodFreq = "0"; //trial.getString(JSON.DataFormatting.TRIAL_TIME_PERIOD_FREQUENCY);
                String trialNotificationFreq = trial.getString(JSON.DataFormatting.TRIAL_NOTIFICATION_FREQUENCY);
                String trialImageResource = trial.getString(JSON.DataFormatting.TRIAL_IMAGE_RESOURCE);

                //prereqs
                ArrayList<String> prerequisites = new ArrayList<>();
                JSONArray conditions = trial.getJSONArray(DataFormatting.TRIAL_PREREQUISITES);
                for (int j = 0; j < conditions.length(); j++) {
                    JSONObject prerequisite = conditions.getJSONObject(j);
                    prerequisites.add(prerequisite.getString(prerequisite.keys().next()));
                }
                System.out.println(prerequisites);

                //sub elements for researchers list
                JSONArray trialResearch = trial.getJSONArray(DataFormatting.TRIAL_RESEARCHER);
                JSONObject researchGroup = trialResearch.getJSONObject(0);
                System.out.println(researchGroup.toString());
                String trialResearchGroupId = researchGroup.getString(DataFormatting.TRIAL_RESEARCH_GROUP_ID);
                String researcherName = researchGroup.getString(DataFormatting.TRIAL_RESEARCHER_NAME);

                ArrayList<String> researcherNames = new ArrayList<>();
                researcherNames.add(researcherName);

                System.out.println(trialResearchGroupId + " " + researcherName);

                /*
                ArrayList<String> researcherNames = new ArrayList<>();
                for (int j = 1; j < trialResearch.length(); j++) {
                    JSONObject researcherName = trialResearch.getJSONObject(j);
                    researcherNames.add(researcherName.getString(DataFormatting.TRIAL_RESEARCHER_NAME));
                }
                System.out.println(researcherNames);
                */

                //Bitmap imageResource = new HTTPRequest().new GetImageResource().execute(Globals.GET_TRIAL_IMAGE);

                trials.add(new Trial(Attributes.getType(trialType), trialId, researcherNames, trialResearchGroupId, //researcher IDs and names
                        trialName, trialDesc, trialId, trialOrganisation, trialSpecialisation, null, //imageResource
                        Long.parseLong(trialStartTime), Long.parseLong(trialEndTime),
                        Integer.parseInt(trialTimePeriodFreq), Integer.parseInt(trialNotificationFreq), prerequisites));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return trials;
    }

    public static JSONArray parseList(ArrayList<Object> list) {
        ArrayList<JSONObject> response = new ArrayList<>();
        JSONArray jsonArray = new JSONArray();
        try {
            JSONObject type = new JSONObject();
            type.put("qtype", list.get(0));
            response.add(type);

            JSONObject index = new JSONObject();
            index.put("qindex", list.get(1));
            response.add(index);

            for (int i = 2; i < list.size(); i++) {
                ArrayList<String> answers = (ArrayList<String>) list.get(i);
                for (int j = 0; j < answers.size(); j++) {
                    JSONObject answerObject = new JSONObject();
                    answerObject.put("response" + j, answers.get(j));
                    response.add(answerObject);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            for (JSONObject jsonObject : response) {
                jsonArray.put(jsonObject);
            }
        }
        return jsonArray;
    }
}
