package com.glassbyte.neurobranch.Dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.glassbyte.neurobranch.Services.Globals;
import com.glassbyte.neurobranch.Services.Helpers.Formatting;

/**
 * Created by Ed on 26/06/2016.
 */
@SuppressLint("ValidFragment")
public class TrialInfo extends android.support.v4.app.DialogFragment {

    TextView tvDescription, tvResearcher, tvStartTime, tvEndTime;
    ImageView ivImage;

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

        RelativeLayout propertiesEntry = new RelativeLayout(this.getActivity());
        propertiesEntry.setGravity(Gravity.CENTER);
        RelativeLayout.LayoutParams propertiesEntryParams =
                new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
        propertiesEntry.setLayoutParams(propertiesEntryParams);

        tvResearcher = new TextView(this.getActivity());
        RelativeLayout.LayoutParams researcherParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        researcherParams.addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE);
        researcherParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        researcherParams.setMargins(Globals.getDp(getActivity(), 24), Globals.getDp(getActivity(), 16),
                Globals.getDp(getActivity(), 16), Globals.getDp(getActivity(), 0));
        tvResearcher.setLayoutParams(researcherParams);
        tvResearcher.setText(getResearcher());
        tvResearcher.setId(View.generateViewId());

        tvStartTime = new TextView(this.getActivity());
        RelativeLayout.LayoutParams startTimeParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        startTimeParams.addRule(RelativeLayout.BELOW, tvResearcher.getId());
        startTimeParams.setMargins(Globals.getDp(getActivity(), 24), Globals.getDp(getActivity(), 8),
                Globals.getDp(getActivity(), 16), Globals.getDp(getActivity(), 0));
        tvStartTime.setLayoutParams(startTimeParams);
        tvStartTime.setText("Start time: " + Formatting.formatTime(getStartTime()));
        tvStartTime.setId(View.generateViewId());

        tvEndTime = new TextView(this.getActivity());
        RelativeLayout.LayoutParams endTimeParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        endTimeParams.addRule(RelativeLayout.BELOW, tvStartTime.getId());
        endTimeParams.setMargins(Globals.getDp(getActivity(), 24), Globals.getDp(getActivity(), 0),
                Globals.getDp(getActivity(), 16), Globals.getDp(getActivity(), 0));
        tvEndTime.setLayoutParams(endTimeParams);
        tvEndTime.setText("End time: " + Formatting.formatTime(getEndTime()));
        tvEndTime.setId(View.generateViewId());

        tvDescription = new TextView(this.getActivity());
        RelativeLayout.LayoutParams descParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        descParams.addRule(RelativeLayout.BELOW, tvEndTime.getId());
        descParams.setMargins(Globals.getDp(getActivity(), 24), Globals.getDp(getActivity(), 16),
                Globals.getDp(getActivity(), 16), Globals.getDp(getActivity(), 16));
        tvDescription.setLayoutParams(descParams);
        tvDescription.setText(getDesc());
        tvDescription.setId(View.generateViewId());

        propertiesEntry.addView(tvResearcher);
        propertiesEntry.addView(tvStartTime);
        propertiesEntry.addView(tvEndTime);
        propertiesEntry.addView(tvDescription);

        builder.setView(propertiesEntry);

        return builder.create();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getResearcher() {
        return researcher;
    }

    public void setResearcher(String researcher) {
        this.researcher = researcher;
    }

    public String getOrganisation() {
        return organisation;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }
}
