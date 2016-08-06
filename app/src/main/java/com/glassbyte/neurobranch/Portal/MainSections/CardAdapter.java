package com.glassbyte.neurobranch.Portal.MainSections;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.glassbyte.neurobranch.Dialogs.TrialInfo;
import com.glassbyte.neurobranch.Portal.QuestionPrefabs.EpochHolder;
import com.glassbyte.neurobranch.R;
import com.glassbyte.neurobranch.Services.DataObjects.Trial;
import com.glassbyte.neurobranch.Services.HTTP.HTTPRequest;
import com.glassbyte.neurobranch.Services.Helpers.Formatting;

import java.util.ArrayList;

/**
 * Created by ed on 25/06/16.
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.DataObjectHolder> {
    ArrayList<Trial> trials;
    android.support.v4.app.FragmentManager fragmentManager;

    public CardAdapter(ArrayList<Trial> trials, android.support.v4.app.FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        this.trials = trials;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trial_card, parent, false);
        return new DataObjectHolder(view);
    }

    public static String capitalise(String line) {
        return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {
        final Trial trial = trials.get(position);

        holder.title.setText(trial.getTitle());
        holder.description.setText(trial.getBriefDescription());
        holder.institute.setText(trial.getInstitute());
        holder.condition.setText(capitalise(trial.getCondition()));

        final Context context = holder.title.getContext();

        if(!trial.isOffline()) {
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

    private void alertDescription(Trial trial, final Context context) {
        TrialInfo trialInfo = new TrialInfo(trial.getTitle(), trial.getDetailedDescription(),
                trial.getResearcherId(), trial.getInstitute(), trial.getDateCreated());
        trialInfo.show(fragmentManager, "info");
        trialInfo.setTrialInfoDialogListener(new TrialInfo.SetTrialInfoListener() {
            @Override
            public void onJoinClick(TrialInfo dialogFragment) {
                context.startActivity(new Intent(context, EpochHolder.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return trials.size();
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder {

        TextView title, description, institute, condition;

        public DataObjectHolder(final View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.trial_title);
            description = (TextView) itemView.findViewById(R.id.trial_desc);
            institute = (TextView) itemView.findViewById(R.id.trial_institute);
            condition = (TextView) itemView.findViewById(R.id.trial_condition);
        }
    }
}
