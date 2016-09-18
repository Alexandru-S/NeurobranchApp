package com.glassbyte.neurobranch.Dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.glassbyte.neurobranch.Services.DataObjects.Trial;

/**
 * Created by Ed on 26/06/2016.
 */
@SuppressLint("ValidFragment")
public class QuestionsDialog extends android.support.v4.app.DialogFragment {

    Trial trial;
    int mostRecentResponse;
    SetTrialAnswerListener setTrialAnswerListener;
    boolean isAnswerable;
    String message;

    //listener that the corresponding button implements
    public interface SetTrialAnswerListener {
        void onAnswerClick(QuestionsDialog dialogFragment);
    }

    public void setTrialAnswerDialogListener(SetTrialAnswerListener setTrialAnswerListener) {
        this.setTrialAnswerListener = setTrialAnswerListener;
    }

    @SuppressLint("ValidFragment")
    public QuestionsDialog(Trial trial, int mostRecentResponse) {
        this.trial = trial;
        this.mostRecentResponse = mostRecentResponse;
        isAnswerable = mostRecentResponse == trial.getCurrentDay();
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if(isAnswerable) message = "New questions available to be answered";
        else message = "No new questions";

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(trial.getTitle())
                .setMessage(message)
                .setPositiveButton("Respond", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (isAnswerable) setTrialAnswerListener.onAnswerClick(QuestionsDialog.this);
                        else Toast.makeText(getContext(), "Trial not answerable", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        return builder.create();
    }

    public Trial getTrial() {
        return trial;
    }

    public int getMostRecentResponse() {
        return mostRecentResponse;
    }
}
