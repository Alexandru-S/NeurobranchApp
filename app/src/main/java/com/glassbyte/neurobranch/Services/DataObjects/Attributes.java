package com.glassbyte.neurobranch.Services.DataObjects;

/**
 * Created by ed on 25/06/16.
 */
public class Attributes {
    public enum Type {
        food, pharma, biodevice, undef
    }

    public enum TrialState {
        created, active, cancelled, completed, undef
    }

    public enum QuestionType {
        checkbox, drawing, multimedia, scale, choice, section, undef
    }

    public enum ResponseType {
        trial_response, post_trial
    }

    public static QuestionType getQuestionType(String checkType) {
        for (QuestionType questionType : QuestionType.values()) {
            if (questionType.name().equals(checkType)) {
                return questionType;
            }
        }
        return QuestionType.undef;
    }

    public static TrialState getTrialState(String checkState) {
        for (TrialState trialState : TrialState.values()) {
            if (trialState.name().equals(checkState)) {
                return trialState;
            }
        }
        return TrialState.undef;
    }

    public static Type getType(String checkType) {
        for (Type type : Type.values()) {
            if (type.name().equals(checkType)) {
                return type;
            }
        }
        return Type.undef;
    }
}
