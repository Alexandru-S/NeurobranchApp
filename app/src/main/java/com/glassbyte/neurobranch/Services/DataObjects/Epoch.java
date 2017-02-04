package com.glassbyte.neurobranch.Services.DataObjects;

import java.util.ArrayList;

/**
 * Created by ed on 25/06/16.
 */
public class Epoch {
    private String researcherGroup;
    private int currentQuestion;
    private boolean isNotified;
    private long startTime, endTime;
    private ArrayList<Question> questions;

    public Epoch(ArrayList<Question> questions, long startTime, long endTime, String researcherGroup) {
        this.questions = questions;
        this.startTime = startTime;
        this.endTime = endTime;
        this.researcherGroup = researcherGroup;
        this.isNotified = false;
        this.currentQuestion = 0;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public String getResearcherGroup() {
        return researcherGroup;
    }

    public int getCurrentQuestion() {
        return currentQuestion;
    }

    public boolean isNotified() {
        return isNotified;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }
}
