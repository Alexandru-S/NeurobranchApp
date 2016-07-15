package com.glassbyte.neurobranch.Services.DataObjects;

import java.util.ArrayList;

/**
 * Created by ed on 30/06/2016.
 */
public class Question {
    String title;
    ArrayList<String> answers;
    Attributes.QuestionType type;
    int id;
    boolean isAnswered;

    public Question(String title, ArrayList<String> answers, Attributes.QuestionType type, int id, boolean isAnswered) {
        this.title = title;
        this.answers = answers;
        this.type = type;
        this.id = id;
        this.isAnswered = isAnswered;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public Attributes.QuestionType getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public boolean isAnswered() {
        return isAnswered;
    }
}
