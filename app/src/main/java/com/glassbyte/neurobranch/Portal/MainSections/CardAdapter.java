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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.glassbyte.neurobranch.Dialogs.QuestionsDialog;
import com.glassbyte.neurobranch.Dialogs.TrialInfo;
import com.glassbyte.neurobranch.Portal.QuestionPrefabs.EpochHolder;
import com.glassbyte.neurobranch.R;
import com.glassbyte.neurobranch.Services.DataObjects.Trial;
import com.glassbyte.neurobranch.Services.Enums.Preferences;
import com.glassbyte.neurobranch.Services.Globals;
import com.glassbyte.neurobranch.Services.HTTP.HTTPRequest;
import com.glassbyte.neurobranch.Services.Helpers.Formatting;
import com.glassbyte.neurobranch.Services.Helpers.Fragments;
import com.glassbyte.neurobranch.Services.Helpers.Manager;
import com.glassbyte.neurobranch.Services.Interfaces.GetDetailsCallback;
import com.glassbyte.neurobranch.Services.Interfaces.JSONCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by ed on 25/06/16.
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.DataObjectHolder>
        implements GetDetailsCallback, JSONCallback {
    private ArrayList<Trial> trials;
    private android.support.v4.app.FragmentManager fragmentManager;
    private Trial trial, currTrial;
    private Fragment fragment;

    public CardAdapter(ArrayList<Trial> trials, Fragment fragment,
                       android.support.v4.app.FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        this.trials = trials;
        this.fragment = fragment;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trial_card, parent, false);
        return new DataObjectHolder(view, parent.getContext());
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {
        this.trial = trials.get(position);
        final Trial trial = trials.get(position);

        holder.title.setText(trial.getTitle());
        holder.description.setText(Formatting.truncateText(trial.getBriefDescription(), Formatting.MAX_DESC_SIZE));
        holder.institute.setText(trial.getInstitute());

        holder.trialDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (trial.isClickable())
                    alertDescription(trial, holder.itemView.getContext());
            }
        });
    }

    private void alertDescription(final Trial trial, final Context context) {
        //if the current trial accessed is already in the user's subscribed list of trials
        if (fragment.getClass().equals(MyTrialsFragment.class)) {
            try {
                this.currTrial = trial;
                new HTTPRequest.ReceiveJSON(getContext(),
                        new URL(Globals.getLatestWindow(trial.getTrialId(),
                                Manager.getInstance().getPreference(Preferences.id, getContext()))),
                        CardAdapter.this).execute();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } else {
            TrialInfo trialInfo = new TrialInfo(trial);
            trialInfo.show(fragmentManager, "info");
            trialInfo.setCancelable(false);
            trialInfo.setTrialInfoDialogListener(new TrialInfo.SetTrialInfoListener() {
                @Override
                public void onJoinClick(TrialInfo dialogFragment) {
                    new AlertDialog.Builder(context)
                            .setTitle("Trial Waiver Form")
                            .setMessage(trial.getWaiver())
                            .setCancelable(false)
                            .setPositiveButton("I agree", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    try {
                                        new HTTPRequest.JoinTrial(Manager.getInstance().getPreference(
                                                Preferences.id, getContext()), trial.getTrialId()).execute();
                                        new HTTPRequest.ReceiveJSON(getContext(),
                                                new URL(Globals.createTrialRelationship(trial.getTrialId(),
                                                        Manager.getInstance().getPreference(Preferences.id, getContext())))).execute();
                                        Fragments.setFragment(fragmentManager, new TrialsAvailableFragment());
                                    } catch (MalformedURLException e) {
                                        e.printStackTrace();
                                    }
                                    Toast.makeText(getContext(), "Trial request successful", Toast.LENGTH_LONG).show();
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
        Manager.getInstance().notifyUserWeb(getContext(), getTrial());
    }

    @Override
    public void onFail() {

    }

    public Context getContext() {
        return fragment.getContext();
    }

    public Trial getTrial() {
        return trial;
    }

    private Trial getCurrTrial() {
        return currTrial;
    }

    @Override
    public void onLoadCompleted(JSONArray object) {
        int lastMostRecentWindowFromResponses = -1;

        try {
            lastMostRecentWindowFromResponses = object.getJSONObject(0).getInt("window");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        QuestionsDialog questionsDialog = new QuestionsDialog()
                .setTrial(getCurrTrial())
                .setLastWindow(lastMostRecentWindowFromResponses);

        questionsDialog.show(fragmentManager, "answerQs");
        questionsDialog.setTrialAnswerDialogListener(new QuestionsDialog.SetTrialAnswerListener() {
            @Override
            public void onAnswerClick(QuestionsDialog dialogFragment) {
                Intent intent = new Intent(getContext(), EpochHolder.class);
                intent.putExtra("TRIAL", getCurrTrial());
                intent.putExtra("IS_ELIGIBILITY", false);
                getContext().startActivity(intent);
            }
        });
    }

    class DataObjectHolder extends RecyclerView.ViewHolder {
        TextView title, description, institute;
        Context context;
        RelativeLayout trialDetails;

        DataObjectHolder(final View itemView, Context context) {
            super(itemView);
            this.context = context;
            title = (TextView) itemView.findViewById(R.id.trial_title);
            description = (TextView) itemView.findViewById(R.id.trial_desc);
            institute = (TextView) itemView.findViewById(R.id.trial_institute);
            trialDetails = (RelativeLayout) itemView.findViewById(R.id.trial_details);
        }

        public Context getContext() {
            return context;
        }
    }
}
