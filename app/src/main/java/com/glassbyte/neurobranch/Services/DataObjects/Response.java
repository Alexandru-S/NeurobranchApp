package com.glassbyte.neurobranch.Services.DataObjects;

/**
 * Created by ed on 25/06/16.
 */
public class Response {
    String questionResponse;
    Attributes.ResponseType responseType;

    public Response(String questionResponse, Attributes.ResponseType responseType) {
        this.questionResponse = questionResponse;
        this.responseType = responseType;
    }

    public String getQuestionResponse() {
        return questionResponse;
    }

    public void setQuestionResponse(String questionResponse) {
        this.questionResponse = questionResponse;
    }

    public Attributes.ResponseType getResponseType() {
        return responseType;
    }

    public void setResponseType(Attributes.ResponseType responseType) {
        this.responseType = responseType;
    }
}
