package com.glassbyte.neurobranch.Services.DataObjects;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ed on 14/06/16.
 */
public class Trial {
    String title;
    String briefDescription;
    String detailedDescription;
    String trialType;
    String institute;
    String condition;
    long dateCreated;
    long dateStarted;
    long dateEnded;
    int candidateQuota;
    Attributes.TrialState trialState;
    String researcherId;
    String trialId;
    boolean isClickable;
    int duration;
    String frequency;
    String waiver, eligibilityForm;
    int currentDay;
    int passmark;

    public Trial(JSONObject trialDetails) throws JSONException {
        this(trialDetails.getString(JSON.DataFormatting.TRIAL_ID),
                trialDetails.getString(JSON.DataFormatting.TRIAL_NAME),
                trialDetails.getString(JSON.DataFormatting.TRIAL_BRIEF_DESCRIPTION),
                trialDetails.getString(JSON.DataFormatting.TRIAL_DETAILED_DESCRIPTION),
                trialDetails.getString(JSON.DataFormatting.TRIAL_TYPE),
                trialDetails.getString(JSON.DataFormatting.TRIAL_INSTITUTE),
                trialDetails.getString(JSON.DataFormatting.TRIAL_CONDITION),
                trialDetails.getInt(JSON.DataFormatting.TRIAL_DURATION),
                trialDetails.getString(JSON.DataFormatting.TRIAL_FREQUENCY),
                trialDetails.getString(JSON.DataFormatting.TRIAL_WAIVER),
                trialDetails.getString(JSON.DataFormatting.TRIAL_ELIGIBILITY_FORM),
                trialDetails.getLong(JSON.DataFormatting.TRIAL_DATE_CREATED),
                trialDetails.getLong(JSON.DataFormatting.TRIAL_DATE_STARTED),
                trialDetails.getLong(JSON.DataFormatting.TRIAL_DATE_ENDED),
                trialDetails.getInt(JSON.DataFormatting.TRIAL_CANDIDATE_QUOTA),
                Attributes.getTrialState(trialDetails.getString(JSON.DataFormatting.TRIAL_STATE)),
                trialDetails.getString(JSON.DataFormatting.TRIAL_RESEARCHER_ID),
                trialDetails.getInt(JSON.DataFormatting.TRIAL_PASSMARK),
                trialDetails.getInt(JSON.DataFormatting.TRIAL_CURRENT_DURATION));
    }

    public Trial(String title, String briefDescription, String institute, boolean isClickable) {
        this.title = title;
        this.briefDescription = briefDescription;
        this.institute = institute;
        this.isClickable = isClickable;
    }

    public Trial(String trialId, String title, String briefDescription, String detailedDescription,
                 String trialType, String institute, String condition, int duration,
                 String frequency, String waiver, String eligibilityForm, long dateCreated,
                 long dateStarted, long dateEnded, int candidateQuota,
                 Attributes.TrialState trialState, String researcherId, int passmark, int currentDay) {
        this.trialId = trialId;
        this.title = title;
        this.briefDescription = briefDescription;
        this.detailedDescription = detailedDescription;
        this.trialType = trialType;
        this.institute = institute;
        this.condition = condition;
        this.duration = duration;
        this.frequency = frequency;
        this.waiver = waiver;
        this.eligibilityForm = eligibilityForm;
        this.dateCreated = dateCreated;
        this.dateStarted = dateStarted;
        this.dateEnded = dateEnded;
        this.candidateQuota = candidateQuota;
        this.trialState = trialState;
        this.researcherId = researcherId;
        this.isClickable = true;
        this.passmark = passmark;
        this.currentDay = currentDay;
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

    public String getCondition() {
        return condition;
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

    public String getEligibilityForm() {
        return eligibilityForm;
    }

    public int getCurrentDay() {
        return currentDay;
    }
}
