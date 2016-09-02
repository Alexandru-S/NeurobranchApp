package com.glassbyte.neurobranch.Services.DataObjects;

import android.support.v4.app.Fragment;

import com.glassbyte.neurobranch.Portal.QuestionPrefabs.Checkbox;
import com.glassbyte.neurobranch.Portal.QuestionPrefabs.Scale;
import com.glassbyte.neurobranch.Portal.QuestionPrefabs.TextChoice;
import com.glassbyte.neurobranch.Portal.QuestionPrefabs.TextSection;

import org.json.JSONArray;
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

    public static JSONObject generateResponse(String trialid, String questionId, String candidateId, Fragment fragment) {
        JSONObject response = new JSONObject();
        try {
            response.put(ResponseFields.trialid.name(), trialid);
            response.put(ResponseFields.questionid.name(), questionId);
            response.put(ResponseFields.candidateid.name(), candidateId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayList<Object> questionResponse = new ArrayList<>();

        if (fragment.getClass() == Checkbox.class) {
            questionResponse.add(Attributes.QuestionType.checkbox.name());
            questionResponse.add(((Checkbox) fragment).getQuestionNumber());
            questionResponse.add(((Checkbox) fragment).getAnswers());
        } else if (fragment.getClass() == Scale.class) {
            questionResponse.add(Attributes.QuestionType.scale.name());
            questionResponse.add(((Scale) fragment).getQuestionNumber());
            questionResponse.add(((Scale) fragment).getResponse());
        } else if (fragment.getClass() == TextChoice.class) {
            questionResponse.add(Attributes.QuestionType.choice.name());
            questionResponse.add(((TextChoice) fragment).getQuestionNumber());
            questionResponse.add(((TextChoice) fragment).getAnswers());
        } else if (fragment.getClass() == TextSection.class) {
            questionResponse.add(Attributes.QuestionType.section.name());
            questionResponse.add(((TextSection) fragment).getQuestionNumber());
            questionResponse.add(((TextSection) fragment).getResponse());
        }
        try {
            response.put(ResponseFields.response.name(), JSON.parseList(questionResponse));
            System.out.println("RESPONSE GENERATED " + JSON.parseList(questionResponse));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return response;
    }
}
