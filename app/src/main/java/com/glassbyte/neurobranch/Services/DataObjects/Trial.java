package com.glassbyte.neurobranch.Services.DataObjects;

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
                 Attributes.TrialState trialState, String researcherId) {
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
