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

    TextView tvDescription, tvResearcher, tvStartTime, tvInstitute;
    SetTrialInfoListener setTrialInfoListener;
    String title, desc, researcher, institute;
    long startTime;

    //listener that the corresponding button implements
    public interface SetTrialInfoListener {
        void onJoinClick(TrialInfo dialogFragment);
    }

    public void setTrialInfoDialogListener(SetTrialInfoListener setTrialInfoListener) {
        this.setTrialInfoListener = setTrialInfoListener;
    }

    @SuppressLint("ValidFragment")
    public TrialInfo(String title, String desc, String researcher, String institute, long startTime) {
        this.title = title;
        this.desc = desc;
        this.researcher = researcher;
        this.institute = institute;
        this.startTime = startTime;
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

        tvInstitute = new TextView(this.getActivity());
        RelativeLayout.LayoutParams instituteParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        instituteParams.addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE);
        instituteParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        instituteParams.setMargins(Globals.getDp(getActivity(), 24), Globals.getDp(getActivity(), 8),
                Globals.getDp(getActivity(), 0), Globals.getDp(getActivity(), 0));
        tvInstitute.setLayoutParams(instituteParams);
        tvInstitute.setText(getInstitute());
        tvInstitute.setId(View.generateViewId());

        tvStartTime = new TextView(this.getActivity());
        RelativeLayout.LayoutParams startParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        startParams.addRule(RelativeLayout.BELOW, tvInstitute.getId());
        startParams.setMargins(Globals.getDp(getActivity(), 24), Globals.getDp(getActivity(), 0),
                Globals.getDp(getActivity(), 16), Globals.getDp(getActivity(), 0));
        tvStartTime.setLayoutParams(startParams);
        String createdOn = "Created on: " + Formatting.formatTime(getStartTime());
        tvStartTime.setText(createdOn);
        tvStartTime.setId(View.generateViewId());

        tvResearcher = new TextView(this.getActivity());
        RelativeLayout.LayoutParams researcherParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        researcherParams.addRule(RelativeLayout.BELOW, tvStartTime.getId());
        researcherParams.setMargins(Globals.getDp(getActivity(), 24), Globals.getDp(getActivity(), 0),
                Globals.getDp(getActivity(), 16), Globals.getDp(getActivity(), 0));
        tvResearcher.setLayoutParams(researcherParams);
        String hostedBy = "Hosted by: " + getResearcher();
        tvResearcher.setText(hostedBy);
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

        propertiesEntry.addView(tvInstitute);
        propertiesEntry.addView(tvResearcher);
        propertiesEntry.addView(tvStartTime);
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

    public String getInstitute() {
        return institute;
    }

    public long getStartTime() {
        return startTime;
    }
}
