package com.glassbyte.neurobranch.Portal.MainSections;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.glassbyte.neurobranch.Dialogs.QuestionsDialog;
import com.glassbyte.neurobranch.Dialogs.TrialInfo;
import com.glassbyte.neurobranch.Portal.QuestionPrefabs.EpochHolder;
import com.glassbyte.neurobranch.R;
import com.glassbyte.neurobranch.Services.DataObjects.Epoch;
import com.glassbyte.neurobranch.Services.DataObjects.Trial;
import com.glassbyte.neurobranch.Services.Enums.PreferenceValues;
import com.glassbyte.neurobranch.Services.Enums.Preferences;
import com.glassbyte.neurobranch.Services.Globals;
import com.glassbyte.neurobranch.Services.HTTP.HTTPRequest;
import com.glassbyte.neurobranch.Services.Helpers.Formatting;
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
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.DataObjectHolder> implements GetDetailsCallback, JSONCallback {
    private ArrayList<Trial> trials;
    private android.support.v4.app.FragmentManager fragmentManager;
    private Context context;
    private Trial trial, currTrial;
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

        String tags = "";

        if (trial.getTags() != null) {
            for (int i = 0; i < trial.getTags().size(); i++) {
                String tag = trial.getTags().get(i);
                if (i == 0 || i == trial.getTags().size()) tags += tag;
                else tags += ", " + tag;
            }
        }
        holder.tags.setText(tags);

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
            holder.tags.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDescription(trial, context);
                }
            });
        }
    }

    private void alertDescription(final Trial trial, final Context context) {
        //if the current trial accessed is already in the user's subscribed list of trials -- was mytrials
        if (fragment.getClass().equals(TrialsAvailableFragment.class)) {
            //TODO retrieve the last answered day, if any, from db and compare via callback
            //TODO internet service here to check candidate's last window

            try {
                this.currTrial = trial;
                new HTTPRequest.ReceiveJSON(getContext(), new URL(Globals.getLatestWindow(trial.getTrialId(), Manager.getInstance().getPreference(Preferences.id, getContext()))), CardAdapter.this).execute();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } else {
            TrialInfo trialInfo = new TrialInfo(trial.getTitle(), trial.getDetailedDescription(), trial.getInstitute(), trial.getDateCreated());
            trialInfo.show(fragmentManager, "info");
            trialInfo.setCancelable(false);
            trialInfo.setTrialInfoDialogListener(new TrialInfo.SetTrialInfoListener() {
                @Override
                public void onJoinClick(TrialInfo dialogFragment) {
                    Manager.getInstance().launchQuestionHolder(getContext(), trial, true);
                    /*
                    new AlertDialog.Builder(context)
                            .setTitle("Trial Waiver Form")
                            .setMessage(trial.getWaiver())
                            .setCancelable(false)
                            .setPositiveButton("I agree", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (trial.isHasEligibility()) {
                                        new AlertDialog.Builder(context)
                                                .setTitle("Trial Eligibility Form")
                                                .setMessage("This trial requires candidates complete an eligibility form before partaking in it. " +
                                                        "If you agree to answer these questions, another form will be shown to you by clicking \"I agree\" below.")
                                                .setCancelable(false)
                                                .setPositiveButton("I agree", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        Manager.getInstance().notifyUserWeb(getContext(), trial.getTrialId(), trial.isHasEligibility());
                                                    }
                                                })
                                                .setNegativeButton("I disagree", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                    }
                                                })
                                                .show();
                                    } else {
                                        //disabled to prevent accidental joining
                                        new HTTPRequest.JoinTrial(Manager.getInstance().getPreference(
                                                Preferences.id, getContext()), trial.getTrialId()).execute();
                                        Toast.makeText(getContext(), "Trial request successful", Toast.LENGTH_LONG).show();
                                    }
                                }
                            })
                            .setNegativeButton("I disagree", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();*/
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
            Manager.getInstance().notifyUserWeb(getContext(), getTrial());
            if (jsonObject.getString("isverified").equals(PreferenceValues.verified.name())) {
                Manager.getInstance().notifyUserWeb(getContext(), getTrial());
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

    public Trial getTrial() {
        return trial;
    }

    private Trial getCurrTrial() {
        return currTrial;
    }

    @Override
    public void onLoadCompleted(JSONArray object) {
        int currentWindow = -1;

        //TODO works for no response as defaulting to -1 from server, must check with existing windows
        try {
            currentWindow = object.getJSONObject(0).getInt("window");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final QuestionsDialog questionsDialog = new QuestionsDialog(getCurrTrial(), currentWindow);
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
        TextView title, description, institute, tags;
        Context context;

        DataObjectHolder(final View itemView, Context context) {
            super(itemView);
            this.context = context;
            title = (TextView) itemView.findViewById(R.id.trial_title);
            description = (TextView) itemView.findViewById(R.id.trial_desc);
            institute = (TextView) itemView.findViewById(R.id.trial_institute);
            tags = (TextView) itemView.findViewById(R.id.trial_tags);
        }

        public Context getContext() {
            return context;
        }
    }
}
