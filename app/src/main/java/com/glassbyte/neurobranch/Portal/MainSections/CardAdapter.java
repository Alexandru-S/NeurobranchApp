package com.glassbyte.neurobranch.Portal.MainSections;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import java.text.Normalizer;
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
        return new DataObjectHolder(view, parent.getContext());
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {
        final Trial trial = trials.get(position);

        holder.title.setText(Formatting.truncateText(trial.getTrialName(), Formatting.MAX_TITLE_SIZE));
        holder.description.setText(Formatting.truncateText(trial.getTrialDescription(), Formatting.MAX_DESC_SIZE));
        holder.organisation.setText(trial.getOrganisation());
        holder.image.setImageDrawable(trial.getTrialImage());

        if(!trial.isOffline()) {
            holder.description.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDescription(trial, holder.getContext());
                }
            });
            holder.organisation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDescription(trial, holder.getContext());
                }
            });
            holder.title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDescription(trial, holder.getContext());
                }
            });
            holder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDescription(trial, holder.getContext());
                }
            });
        }
    }

    private void alertDescription(final Trial trial, final Context context) {
        TrialInfo trialInfo = new TrialInfo(trial.getTrialName() + " (" + (trial.getOrganisation()) + ")",
                trial.getTrialDescription(), Formatting.formatResearchers(trial.getResearcherName()),
                trial.getOrganisation(), trial.getStartTime(), trial.getEndTime());
        trialInfo.show(fragmentManager, "info");
        trialInfo.setTrialInfoDialogListener(new TrialInfo.SetTrialInfoListener() {
            @Override
            public void onJoinClick(TrialInfo dialogFragment) {
                Bundle bundle = new Bundle();
                bundle.putString("TRIAL_ID", trial.getTrialId());
                bundle.putString("USER_ID", "");
                bundle.putString("EPOCH_ID", "");

                Toast.makeText(context, "Trial accessed: " + trial.getTrialId(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, EpochHolder.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return trials.size();
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder {

        Context context;
        TextView title, description, organisation;
        ImageView image;

        public DataObjectHolder(final View itemView, Context context) {
            super(itemView);
            this.context = context;
            title = (TextView) itemView.findViewById(R.id.trial_title);
            description = (TextView) itemView.findViewById(R.id.trial_desc);
            organisation = (TextView) itemView.findViewById(R.id.trial_organisation);
            image = (ImageView) itemView.findViewById(R.id.trial_image);
        }

        public Context getContext(){
            return context;
        }
    }
}
