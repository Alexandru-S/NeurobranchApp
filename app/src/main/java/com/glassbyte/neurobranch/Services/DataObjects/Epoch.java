package com.glassbyte.neurobranch.Services.DataObjects;

import android.content.Context;

import com.glassbyte.neurobranch.Services.Globals;
import com.glassbyte.neurobranch.Services.HTTP.HTTPRequest;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by ed on 25/06/16.
 */
public class Epoch {
    String researcherGroup, epochId, trialId, candidateId;
    int currentQuestion;
    boolean isNotified;
    long startTime, endTime;
    ArrayList<Question> questions;
    ArrayList<android.support.v4.app.Fragment> fragments;

    public Epoch(ArrayList<Question> questions, long startTime, long endTime, String researcherGroup) {
        this.questions = questions;
        this.startTime = startTime;
        this.endTime = endTime;
        this.researcherGroup = researcherGroup;
        this.isNotified = false;
        this.currentQuestion = 0;
    }

    public int getCurrentQuestion() {
        return currentQuestion;
    }

    public void setCurrentQuestion(int currentQuestion) {
        this.currentQuestion = currentQuestion;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public boolean isNotified() {
        return isNotified;
    }

    public void setNotified(boolean notified) {
        isNotified = notified;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public String getResearcherGroup() {
        return researcherGroup;
    }

    public ArrayList<android.support.v4.app.Fragment> getFragments() {
        return fragments;
    }

    public static ArrayList<Object> populateEpoch(ArrayList<Object> properties, Context context) {
        try {
            properties = JSON.parseQuestions(new HTTPRequest.ReceiveJSON(context,
                    new URL(Globals.GET_QUESTIONS_ADDRESS)).execute().get());
        } catch (InterruptedException | ExecutionException | MalformedURLException e) {
            e.printStackTrace();
        }

        return properties;
        }
    }
