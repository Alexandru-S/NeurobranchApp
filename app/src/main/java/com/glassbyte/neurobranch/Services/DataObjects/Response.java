package com.glassbyte.neurobranch.Services.DataObjects;

import com.glassbyte.neurobranch.Portal.QuestionPrefabs.Checkbox;
import com.glassbyte.neurobranch.Portal.QuestionPrefabs.Radio;
import com.glassbyte.neurobranch.Portal.QuestionPrefabs.QuestionFragment;
import com.glassbyte.neurobranch.Portal.QuestionPrefabs.Scale;
import com.glassbyte.neurobranch.Portal.QuestionPrefabs.Text;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ed on 25/06/2016
 */
public class Response {
    private Attributes.ResponseType responseType;
    private JSONObject questionResponse;

    private enum ResponseFields {
        id, trialid, questionid, candidateid, response, window
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

    public static JSONObject generateResponse(String trialid, String questionId, String candidateId, QuestionFragment fragment, int window) {
        JSONObject response = new JSONObject();
        try {
            response.put(ResponseFields.trialid.name(), trialid);
            response.put(ResponseFields.questionid.name(), questionId);
            response.put(ResponseFields.candidateid.name(), candidateId);
            response.put(ResponseFields.window.name(), window);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayList<Object> questionResponse = new ArrayList<>();

        if (fragment.getClass().equals(Checkbox.class)) {
            questionResponse.add(Attributes.QuestionType.checkbox.name());
        } else if (fragment.getClass().equals(Scale.class)) {
            questionResponse.add(Attributes.QuestionType.scale.name());
        } else if (fragment.getClass().equals(Radio.class)) {
            questionResponse.add(Attributes.QuestionType.radio.name());
        } else if (fragment.getClass().equals(Text.class)) {
            questionResponse.add(Attributes.QuestionType.text.name());
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
