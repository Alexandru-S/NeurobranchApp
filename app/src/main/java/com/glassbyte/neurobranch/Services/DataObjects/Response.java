package com.glassbyte.neurobranch.Services.DataObjects;

import com.glassbyte.neurobranch.Portal.QuestionPrefabs.Checkbox;
import com.glassbyte.neurobranch.Portal.QuestionPrefabs.Radio;
import com.glassbyte.neurobranch.Portal.QuestionPrefabs.QuestionFragment;
import com.glassbyte.neurobranch.Portal.QuestionPrefabs.Scale;
import com.glassbyte.neurobranch.Portal.QuestionPrefabs.Text;

import org.json.JSONArray;
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
        id, trialid, questionid, candidateid, answers, answer, window, question_type, index
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
        JSONArray answers = new JSONArray();

        try {
            response.put(ResponseFields.trialid.name(), trialid);
            response.put(ResponseFields.questionid.name(), questionId);
            response.put(ResponseFields.candidateid.name(), candidateId);
            response.put(ResponseFields.window.name(), window);
            response.put(ResponseFields.index.name(), fragment.getQuestionIndex());

            if (fragment.getClass().equals(Checkbox.class)) {
                response.put(ResponseFields.question_type.name(), Attributes.QuestionType.checkbox.name());
            } else if (fragment.getClass().equals(Scale.class)) {
                response.put(ResponseFields.question_type.name(), Attributes.QuestionType.scale.name());
            } else if (fragment.getClass().equals(Radio.class)) {
                response.put(ResponseFields.question_type.name(), Attributes.QuestionType.radio.name());
            } else if (fragment.getClass().equals(Text.class)) {
                response.put(ResponseFields.question_type.name(), Attributes.QuestionType.text.name());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (Object answer : fragment.getAnswersChosen()) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(ResponseFields.answer.name(), answer);
                answers.put(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        try {
            response.put(ResponseFields.answers.name(), answers);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return response;
    }
}
