package com.glassbyte.neurobranch.Services.DataObjects;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ed on 14/06/16.
 */
public class Trial {
    private String title;
    private String briefDescription;
    private String detailedDescription;
    private String trialType;
    private String institute;
    private ArrayList<String> tags;
    private long dateCreated;
    private long dateStarted;
    private long dateEnded;
    private int candidateQuota;
    private Attributes.TrialState trialState;
    private String researcherId;
    private String trialId;
    private boolean isClickable;
    private int duration;
    private String frequency;
    private String waiver;
    private int currentDay;
    private int passmark;
    private boolean hasEligibility;

    public Trial(JSONObject trialDetails) throws JSONException {
        this(trialDetails.getString(JSON.DataFormatting.TRIAL_ID),
                trialDetails.getString(JSON.DataFormatting.TRIAL_NAME),
                trialDetails.getString(JSON.DataFormatting.TRIAL_BRIEF_DESCRIPTION),
                trialDetails.getString(JSON.DataFormatting.TRIAL_DETAILED_DESCRIPTION),
                trialDetails.getString(JSON.DataFormatting.TRIAL_TYPE),
                trialDetails.getString(JSON.DataFormatting.TRIAL_INSTITUTE),
                JSON.parseJSONArray((JSONArray) trialDetails.get(JSON.DataFormatting.TRIAL_TAGS)),
                trialDetails.getInt(JSON.DataFormatting.TRIAL_DURATION),
                trialDetails.getString(JSON.DataFormatting.TRIAL_FREQUENCY),
                trialDetails.getString(JSON.DataFormatting.TRIAL_WAIVER),
                trialDetails.getLong(JSON.DataFormatting.TRIAL_DATE_CREATED),
                trialDetails.getLong(JSON.DataFormatting.TRIAL_DATE_STARTED),
                trialDetails.getLong(JSON.DataFormatting.TRIAL_DATE_ENDED),
                trialDetails.getInt(JSON.DataFormatting.TRIAL_CANDIDATE_QUOTA),
                Attributes.getTrialState(trialDetails.getString(JSON.DataFormatting.TRIAL_STATE)),
                trialDetails.getString(JSON.DataFormatting.TRIAL_RESEARCHER_ID),
                trialDetails.getInt(JSON.DataFormatting.TRIAL_PASS_MARK),
                trialDetails.getInt(JSON.DataFormatting.TRIAL_CURRENT_DURATION),
                trialDetails.getBoolean(JSON.DataFormatting.TRIAL_ELIGIBILITY_FORM));
    }

    public Trial(String title, String briefDescription, String institute, boolean isClickable) {
        this.title = title;
        this.briefDescription = briefDescription;
        this.institute = institute;
        this.isClickable = isClickable;
    }

    public Trial(String trialId, String title, String briefDescription, String detailedDescription,
                 String trialType, String institute, ArrayList<String> tags, int duration,
                 String frequency, String waiver, long dateCreated,
                 long dateStarted, long dateEnded, int candidateQuota,
                 Attributes.TrialState trialState, String researcherId, int passmark, int currentDay, boolean hasEligibility) {
        this.trialId = trialId;
        this.title = title;
        this.briefDescription = briefDescription;
        this.detailedDescription = detailedDescription;
        this.trialType = trialType;
        this.institute = institute;
        this.tags = tags;
        this.duration = duration;
        this.frequency = frequency;
        this.waiver = waiver;
        this.dateCreated = dateCreated;
        this.dateStarted = dateStarted;
        this.dateEnded = dateEnded;
        this.candidateQuota = candidateQuota;
        this.trialState = trialState;
        this.researcherId = researcherId;
        this.isClickable = true;
        this.passmark = passmark;
        this.currentDay = currentDay;
        this.hasEligibility = hasEligibility;
    }

    public boolean isClickable() {
        return isClickable;
    }

    public String getTitle() {
        return title;
    }

    public String getBriefDescription() {
        return briefDescription;
    }

    public String getDetailedDescription() {
        return detailedDescription;
    }

    public String getTrialType() {
        return trialType;
    }

    public String getInstitute() {
        return institute;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public long getDateCreated() {
        return dateCreated;
    }

    public long getDateStarted() {
        return dateStarted;
    }

    public long getDateEnded() {
        return dateEnded;
    }

    public int getCandidateQuota() {
        return candidateQuota;
    }

    public Attributes.TrialState getTrialState() {
        return trialState;
    }

    public String getResearcherId() {
        return researcherId;
    }

    public String getTrialId() {
        return trialId;
    }

    public int getDuration() {
        return duration;
    }

    public String getFrequency() {
        return frequency;
    }

    public String getWaiver() {
        return waiver;
    }

    public int getPassmark() {
        return passmark;
    }

    public boolean isHasEligibility() {
        return hasEligibility;
    }

    public int getCurrentDay() {
        return currentDay;
    }
}
