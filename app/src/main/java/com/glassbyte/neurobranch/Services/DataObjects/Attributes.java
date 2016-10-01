package com.glassbyte.neurobranch.Services.DataObjects;

/**
 * Created by ed on 25/06/16.
 */
public class Attributes {
    public enum Type {
        food, pharma, biodevice, behavioural, undef
    }

    public enum TrialState {
        created, active, cancelled, completed, undef
    }

    public enum QuestionType {
        checkbox, drawing, multimedia, scale, radio, text, undef
    }

    public enum ResponseType {
        trial_response
    }

    public static QuestionType getQuestionType(String checkType) {
        for (QuestionType questionType : QuestionType.values()) {
            if (questionType.name().equals(checkType.toLowerCase())) {
                return questionType;
            }
        }
        return QuestionType.undef;
    }

    static TrialState getTrialState(String checkState) {
        for (TrialState trialState : TrialState.values()) {
            if (trialState.name().equals(checkState.toLowerCase())) {
                return trialState;
            }
        }
        return TrialState.undef;
    }
}
