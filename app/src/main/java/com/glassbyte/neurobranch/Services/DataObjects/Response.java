package com.glassbyte.neurobranch.Services.DataObjects;

import android.app.Fragment;

import com.glassbyte.neurobranch.Portal.QuestionPrefabs.*;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ed on 25/06/2016
 */
public class Response {
    Attributes.ResponseType responseType;
    JSONObject questionResponse;

    enum ResponseFields {
        id, trialid, questionid, candidateid, response
    }

    public Response(JSONObject questionResponse, Attributes.ResponseType responseType) {
        this.questionResponse = questionResponse;
        this.responseType = responseType;
    }

    public Attributes.ResponseType getResponseType() {
        return responseType;
    }

    public JSONObject getQuestionResponse() {
        return questionResponse;
    }

    public static JSONObject generateResponse(String trialid, String questionId, String candidateId, QuestionFragment fragment) {
        JSONObject response = new JSONObject();
        try {
            response.put(ResponseFields.trialid.name(), trialid);
            response.put(ResponseFields.questionid.name(), questionId);
            response.put(ResponseFields.candidateid.name(), candidateId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayList<Object> questionResponse = new ArrayList<>();

        if (fragment.getClass().equals(Checkbox.class)) {
            questionResponse.add(Attributes.QuestionType.checkbox.name());
        } else if (fragment.getClass().equals(Scale.class)) {
            questionResponse.add(Attributes.QuestionType.scale.name());
        } else if (fragment.getClass().equals(Choice.class)) {
            questionResponse.add(Attributes.QuestionType.choice.name());
        } else if (fragment.getClass().equals(Section.class)) {
            questionResponse.add(Attributes.QuestionType.section.name());
        }

        questionResponse.add(fragment.getQuestionIndex());
        questionResponse.add(fragment.getAnswersChosen());

        try {
            response.put(ResponseFields.response.name(), JSON.parseList(questionResponse));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return response;
    }
}
