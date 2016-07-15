package com.glassbyte.neurobranch.Services.DataObjects;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;

/**
 * Created by ed on 14/06/16.
 */
public class Trial {
    String trialName;
    String trialId;
    String trialDescription;
    Attributes.Type trialType;
    String researchGroupId;
    String researcherId;
    ArrayList<String> researcherName;
    String organisation;

    String specialisation;
    Drawable trialImage;
    long startTime, endTime, notificationFreq, timePeriodFreq;
    int checkupThreshold, interactionFrequency;
    ArrayList<String> prerequisites;
    boolean isOffline;

    public Trial(String trialName, String trialDescription, String organisation, boolean isOffline) {
        this.trialName = trialName;
        this.trialDescription = trialDescription;
        this.organisation = organisation;
        this.isOffline = isOffline;
    }

    public Trial(Attributes.Type trialType, String researcherId, ArrayList<String> researcherName,
                 String researchGroupId, String trialName, String trialDescription, String trialId,
                 String organisation, String specialisation, Drawable trialImage, long startTime,
                 long endTime, int checkupThreshold, int interactionFrequency, ArrayList<String> prerequisites) {
        this.trialType = trialType;
        this.researcherId = researcherId;
        this.researcherName = researcherName;
        this.researchGroupId = researchGroupId;
        this.trialName = trialName;
        this.trialDescription = trialDescription;
        this.trialId = trialId;
        this.organisation = organisation;
        this.specialisation = specialisation;
        this.trialImage = trialImage;
        this.startTime = startTime;
        this.endTime = endTime;
        this.checkupThreshold = checkupThreshold;
        this.interactionFrequency = interactionFrequency;
        this.prerequisites = prerequisites;
    }

    public Attributes.Type getTrialType() {
        return trialType;
    }

    public String getResearcherId() {
        return researcherId;
    }

    public ArrayList<String> getResearcherName() {
        return researcherName;
    }

    public String getResearchGroupId() {
        return researchGroupId;
    }

    public String getTrialName() {
        return trialName;
    }

    public String getTrialDescription() {
        return trialDescription;
    }

    public String getTrialId() {
        return trialId;
    }

    public String getOrganisation() {
        return organisation;
    }

    public Drawable getTrialImage() {
        return trialImage;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public int getCheckupThreshold() {
        return checkupThreshold;
    }

    public void setCheckupThreshold(int checkupThreshold) {
        this.checkupThreshold = checkupThreshold;
    }

    public int getInteractionFrequency() {
        return interactionFrequency;
    }

    public String getSpecialisation() {
        return specialisation;
    }

    public long getNotificationFreq() {
        return notificationFreq;
    }

    public long getTimePeriodFreq() {
        return timePeriodFreq;
    }

    public ArrayList<String> getPrerequisites() {
        return prerequisites;
    }

    public boolean isOffline() {
        return isOffline;
    }
}
