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
 * Created by ed on 25/06/16.
 */
public class Response {
    Attributes.ResponseType responseType;
    JSONObject questionResponse;

    enum ResponseFields {
        id, trialid, epochid, candidateid, response
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

    public static JSONObject generateResponse(String trialId, String epochId, String candidateId, ArrayList<Fragment> fragments) {
        JSONObject response = new JSONObject();
        JSONArray responseArray = new JSONArray();
        try {
            response.put(ResponseFields.trialid.name(), trialId);
            response.put(ResponseFields.epochid.name(), epochId);
            response.put(ResponseFields.candidateid.name(), candidateId);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        for(int i=0; i<fragments.size(); i++) {
            Object object = fragments.get(i);
            ArrayList<Object> questionResponse = new ArrayList<>();

            if(object.getClass() == Checkbox.class) {
                questionResponse.add(Attributes.QuestionType.checkbox.name());
                questionResponse.add(((Checkbox) object).getQuestionNumber());
                questionResponse.add(((Checkbox) object).getAnswers());
            } else if(object.getClass() == Scale.class) {
                questionResponse.add(Attributes.QuestionType.scale.name());
                questionResponse.add(((Scale) object).getQuestionNumber());
                questionResponse.add(((Scale) object).getResponse());
            } else if(object.getClass() == TextChoice.class) {
                questionResponse.add(Attributes.QuestionType.choice.name());
                questionResponse.add(((TextChoice) object).getQuestionNumber());
                questionResponse.add(((TextChoice) object).getAnswers());
            } else if(object.getClass() == TextSection.class) {
                questionResponse.add(Attributes.QuestionType.section.name());
                questionResponse.add(((TextSection) object).getQuestionNumber());
                questionResponse.add(((TextSection) object).getResponse());
            }
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("q" + i, JSON.parseList(questionResponse));
                responseArray.put(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        try {
            response.put(ResponseFields.response.name(), responseArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return response;
    }
}
