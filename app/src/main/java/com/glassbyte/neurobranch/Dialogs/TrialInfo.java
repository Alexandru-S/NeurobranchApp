package com.glassbyte.neurobranch.Dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.glassbyte.neurobranch.Services.Globals;
import com.glassbyte.neurobranch.Services.Helpers.Formatting;

/**
 * Created by Ed on 26/06/2016.
 */
@SuppressLint("ValidFragment")
public class TrialInfo extends android.support.v4.app.DialogFragment {

    TextView tvDescription, tvResearcher, tvTime, tvOrganisation;

    SetTrialInfoListener setTrialInfoListener;

    String title, desc, researcher, organisation;
    long startTime, endTime;

    //listener that the corresponding button implements
    public interface SetTrialInfoListener {
        void onJoinClick(TrialInfo dialogFragment);
    }

    public void setTrialInfoDialogListener(SetTrialInfoListener setTrialInfoListener) {
        this.setTrialInfoListener = setTrialInfoListener;
    }

    @SuppressLint("ValidFragment")
    public TrialInfo(String title, String desc, String researcher, String organisation, long startTime, long endTime) {
        this.title = title;
        this.desc = desc;
        this.researcher = researcher;
        this.organisation = organisation;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title)
                .setPositiveButton("Join", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (setTrialInfoListener != null) {
                            setTrialInfoListener.onJoinClick(TrialInfo.this);
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        ScrollView scrollView = new ScrollView(this.getActivity());
        ScrollView.LayoutParams scrollEntryParams = new ScrollView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        scrollView.setLayoutParams(scrollEntryParams);

        RelativeLayout propertiesEntry = new RelativeLayout(this.getActivity());
        RelativeLayout.LayoutParams propertiesEntryParams =
                new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
        propertiesEntry.setLayoutParams(propertiesEntryParams);

        tvTime = new TextView(this.getActivity());
        RelativeLayout.LayoutParams timeParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        timeParams.addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE);
        timeParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        timeParams.setMargins(Globals.getDp(getActivity(), 24), Globals.getDp(getActivity(), 16),
                Globals.getDp(getActivity(), 16), Globals.getDp(getActivity(), 0));
        tvTime.setLayoutParams(timeParams);
        String timePeriod = "From " + Formatting.formatTime(getStartTime()) + " until " + Formatting.formatTime(getEndTime());
        tvTime.setText(timePeriod);
        tvTime.setId(View.generateViewId());

        tvOrganisation = new TextView(this.getActivity());
        RelativeLayout.LayoutParams organisationParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        organisationParams.setMargins(Globals.getDp(getActivity(), 24), Globals.getDp(getActivity(), 0),
                Globals.getDp(getActivity(), 16), Globals.getDp(getActivity(), 0));
        organisationParams.addRule(RelativeLayout.BELOW, tvTime.getId());
        tvOrganisation.setLayoutParams(organisationParams);
        tvOrganisation.setText(getOrganisation());
        tvOrganisation.setId(View.generateViewId());

        tvResearcher = new TextView(this.getActivity());
        RelativeLayout.LayoutParams researcherParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        researcherParams.addRule(RelativeLayout.BELOW, tvOrganisation.getId());
        researcherParams.setMargins(Globals.getDp(getActivity(), 24), Globals.getDp(getActivity(), 0),
                Globals.getDp(getActivity(), 16), Globals.getDp(getActivity(), 0));
        tvResearcher.setLayoutParams(researcherParams);
        tvResearcher.setText(getResearcher());
        tvResearcher.setId(View.generateViewId());

        tvDescription = new TextView(this.getActivity());
        RelativeLayout.LayoutParams descParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        descParams.addRule(RelativeLayout.BELOW, tvResearcher.getId());
        descParams.setMargins(Globals.getDp(getActivity(), 24), Globals.getDp(getActivity(), 16),
                Globals.getDp(getActivity(), 16), Globals.getDp(getActivity(), 16));
        tvDescription.setLayoutParams(descParams);
        tvDescription.setText(getDesc());
        tvDescription.setId(View.generateViewId());

        propertiesEntry.addView(tvTime);
        propertiesEntry.addView(tvOrganisation);
        propertiesEntry.addView(tvResearcher);
        propertiesEntry.addView(tvDescription);

        scrollView.addView(propertiesEntry);

        builder.setView(scrollView);

        return builder.create();
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getResearcher() {
        return researcher;
    }

    public String getOrganisation() {
        return organisation;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }
}
