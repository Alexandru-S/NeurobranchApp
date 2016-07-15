package com.glassbyte.neurobranch.Services.DataObjects;

/**
 * Created by ed on 25/06/16.
 */
public class Attributes {
    public enum Type {
        food, pharma, biodevice, undef
    }

    public enum QuestionType {
        checkbox, drawing, multimedia, scale, choice, section, undef
    }

    public enum ResponseType {
        post, confirmation, retrieval
    }

    public static QuestionType getQuestionType(String type) {
        switch (type) {
            case "checkbox":
                return QuestionType.checkbox;
            case "drawing":
                return QuestionType.drawing;
            case "multimedia":
                return QuestionType.multimedia;
            case "scale":
                return QuestionType.scale;
            case "choice":
                return QuestionType.choice;
            case "section":
                return QuestionType.section;
            default:
                return QuestionType.undef;
        }
    }

    public static Type getType(String type) {
        switch(type) {
            case "Food":
                return Type.food;
            case "Pharma":
                return Type.pharma;
            case "Biodevice":
                return Type.biodevice;
            default:
                return Type.undef;
        }
    }
}
