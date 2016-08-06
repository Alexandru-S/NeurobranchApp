package com.glassbyte.neurobranch.Services.DataObjects;

/**
 * Created by ed on 14/06/16.
 */
public class Trial {
    String title;
    String briefDescription;
    String detailedDescription;
    Attributes.Type trialType;
    String institute;
    String condition;
    long dateCreated;
    long dateStarted;
    long dateEnded;
    int candidateQuota;
    Attributes.TrialState trialState;
    String researcherId;
    String trialId;
    boolean isOffline;

    public Trial(String title, String briefDescription, String institute, boolean isOffline) {
        this.title = title;
        this.briefDescription = briefDescription;
        this.institute = institute;
        this.isOffline = isOffline;
    }

    public Trial(String title, String briefDescription, String detailedDescription, Attributes.Type trialType,
                 String institute, String condition, long dateCreated, long dateStarted, long dateEnded,
                 int candidateQuota, Attributes.TrialState trialState, String researcherId, String trialId) {
        this.title = title;
        this.briefDescription = briefDescription;
        this.detailedDescription = detailedDescription;
        this.trialType = trialType;
        this.institute = institute;
        this.condition = condition;
        this.dateCreated = dateCreated;
        this.dateStarted = dateStarted;
        this.dateEnded = dateEnded;
        this.candidateQuota = candidateQuota;
        this.trialState = trialState;
        this.researcherId = researcherId;
        this.trialId = trialId;
    }

    public boolean isOffline() {
        return isOffline;
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

    public Attributes.Type getTrialType() {
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
}
