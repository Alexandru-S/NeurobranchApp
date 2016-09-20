package com.glassbyte.neurobranch.Portal.MainSections;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.glassbyte.neurobranch.Dialogs.QuestionsDialog;
import com.glassbyte.neurobranch.Dialogs.TrialInfo;
import com.glassbyte.neurobranch.R;
import com.glassbyte.neurobranch.Services.DataObjects.Attributes;
import com.glassbyte.neurobranch.Services.DataObjects.Trial;
import com.glassbyte.neurobranch.Services.Enums.PreferenceValues;
import com.glassbyte.neurobranch.Services.Enums.Preferences;
import com.glassbyte.neurobranch.Services.HTTP.HTTPRequest;
import com.glassbyte.neurobranch.Services.Helpers.Formatting;
import com.glassbyte.neurobranch.Services.Helpers.Manager;
import com.glassbyte.neurobranch.Services.Interfaces.GetDetailsCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ed on 25/06/16.
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.DataObjectHolder> implements GetDetailsCallback {
    ArrayList<Trial> trials;
    android.support.v4.app.FragmentManager fragmentManager;
    private Context context;
    private Trial trial;
    private Fragment fragment;

    public CardAdapter(ArrayList<Trial> trials, Fragment fragment, android.support.v4.app.FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        this.trials = trials;
        this.fragment = fragment;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trial_card, parent, false);
        return new DataObjectHolder(view, parent.getContext());
    }

    public static String capitalise(String line) {
        return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {
        this.trial = trials.get(position);
        final Trial trial = trials.get(position);

        holder.title.setText(trial.getTitle());
        holder.description.setText(Formatting.truncateText(trial.getBriefDescription(), Formatting.MAX_DESC_SIZE));
        holder.institute.setText(trial.getInstitute());
        holder.condition.setText(trial.getCondition() != null ? capitalise(trial.getCondition()) : null);

        this.context = holder.title.getContext();

        if (trial.isClickable()) {
            holder.description.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDescription(trial, context);
                }
            });
            holder.institute.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDescription(trial, context);
                }
            });
            holder.title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDescription(trial, context);
                }
            });
            holder.condition.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDescription(trial, context);
                }
            });
        }
    }

    private void alertDescription(final Trial trial, final Context context) {
        //if the current trial accessed is already in the user's subscribed list of trials
        if (fragment.getClass().equals(MyTrialsFragment.class)) {
            //TODO retrieve the last answered day, if any, from db and compare via callback
            String lastAnsweredDay = "-1";
            if ((Integer.parseInt(lastAnsweredDay) < trial.getCurrentDay()) || lastAnsweredDay.equals("")) {
                final QuestionsDialog questionsDialog = new QuestionsDialog(trial, 0);
                questionsDialog.show(fragmentManager, "answerQs");
                questionsDialog.setTrialAnswerDialogListener(new QuestionsDialog.SetTrialAnswerListener() {
                    @Override
                    public void onAnswerClick(QuestionsDialog dialogFragment) {
                        Toast.makeText(getContext(), "User allowed to answer questions", Toast.LENGTH_LONG).show();
                    }
                });
            } else {
                Toast.makeText(getContext(), "Answer not allowed", Toast.LENGTH_LONG).show();
            }

        } else {
            TrialInfo trialInfo = new TrialInfo(trial.getTitle(), trial.getDetailedDescription(),
                    trial.getResearcherId(), trial.getInstitute(), trial.getDateCreated());
            trialInfo.show(fragmentManager, "info");
            trialInfo.setTrialInfoDialogListener(new TrialInfo.SetTrialInfoListener() {
                @Override
                public void onJoinClick(TrialInfo dialogFragment) {
                    new AlertDialog.Builder(context)
                            .setTitle("Trial Waiver Form")
                            .setMessage(trial.getWaiver())
                            .setPositiveButton("I agree", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    new AlertDialog.Builder(context)
                                            .setTitle(trial.getTitle())
                                            .setMessage("To join a trial, choose an option below")
                                            .setPositiveButton("Join", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    new HTTPRequest.JoinTrial(Manager.getInstance().getPreference(
                                                            Preferences.id, getContext()), trial.getTrialId()).execute();
                                                }
                                            })
                                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                }
                                            })
                                            .show();
                                }
                            })
                            .setNegativeButton("I disagree", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return trials.size();
    }

    @Override
    public void onRetrieved(JSONObject jsonObject) {
        try {
            Manager.getInstance().notifyUserWeb(getContext(), getTrial().getTrialId());
            if(jsonObject.getString("isverified").equals(PreferenceValues.verified.name())) {
                Manager.getInstance().notifyUserWeb(getContext(), getTrial().getTrialId());
            } else {
                Toast.makeText(getContext(), "Please verify your account in order to join trials", Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Context getContext() {
        return context;
    }

    public Trial getTrial() { return trial; }

    public class DataObjectHolder extends RecyclerView.ViewHolder {
        TextView title, description, institute, condition;
        Context context;

        public DataObjectHolder(final View itemView, Context context) {
            super(itemView);
            this.context = context;
            title = (TextView) itemView.findViewById(R.id.trial_title);
            description = (TextView) itemView.findViewById(R.id.trial_desc);
            institute = (TextView) itemView.findViewById(R.id.trial_institute);
            condition = (TextView) itemView.findViewById(R.id.trial_condition);
        }

        public Context getContext() {
            return context;
        }
    }
}
