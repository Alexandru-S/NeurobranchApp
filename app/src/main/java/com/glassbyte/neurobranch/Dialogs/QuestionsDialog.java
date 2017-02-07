package com.glassbyte.neurobranch.Dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.glassbyte.neurobranch.Services.DataObjects.Attributes;
import com.glassbyte.neurobranch.Services.DataObjects.Question;
import com.glassbyte.neurobranch.Services.DataObjects.Trial;

/**
 * Created by Ed on 26/06/2016.
 */
@SuppressLint("ValidFragment")
public class QuestionsDialog extends android.support.v4.app.DialogFragment {

    Trial trial;
    int lastWindow;
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

    public QuestionsDialog setTrial(Trial trial) {
        this.trial = trial;
        return this;
    }

    public QuestionsDialog setLastWindow(int lastWindow) {
        this.lastWindow = lastWindow;
        this.isAnswerable = lastWindow < trial.getCurrentDay();
        System.out.println(lastWindow + " " + trial.getCurrentDay()git );
        return this;
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(trial.getTitle());
        if (trial.getTrialState().equals(Attributes.TrialState.active)) {
            if (isAnswerable) {
                builder.setMessage("New questions can be answered, click on respond in order to answer these now.")
                        .setPositiveButton("Respond", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                setTrialAnswerListener.onAnswerClick(QuestionsDialog.this);
                            }
                        })
                        .setNegativeButton("Cancel", null);
            } else {
                builder.setMessage("No new questions for this trial, check again later or wait for a notification to come in.")
                        .setNegativeButton("Cancel", null);
            }
        } else {
            builder.setMessage("You have not been verified yet as a candidate.")
                    .setNegativeButton("Cancel", null);
        }

        return builder.create();
    }

    public Trial getTrial() {
        return trial;
    }

    public int getLastWindow() {
        return lastWindow;
    }
}
