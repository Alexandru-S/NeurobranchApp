package com.glassbyte.neurobranch.Portal.MainSections;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.glassbyte.neurobranch.R;
import com.glassbyte.neurobranch.Services.DataObjects.JSON;
import com.glassbyte.neurobranch.Services.DataObjects.Trial;
import com.glassbyte.neurobranch.Services.Globals;
import com.glassbyte.neurobranch.Services.HTTP.HTTPRequest;
import com.glassbyte.neurobranch.Services.Helpers.Connectivity;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by ed on 10/06/16.
 */
public class TrialsAvailableFragment extends android.support.v4.app.Fragment {
    View view;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.trials_available_layout, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.card_view_recycler_view);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        loadTrials();

        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_portal_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadTrials();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadTrials();
    }

    private void loadTrials() {
        /*if(Connectivity.isNetworkConnected(getActivity())) {
            try {
                HTTPRequest.ReceiveJSON httpRequest = new HTTPRequest.ReceiveJSON(getActivity(), new URL(Globals.RETRIEVE_TRIALS_ADDRESS));
                adapter = new CardAdapter(JSON.parseTrialJSON(httpRequest.execute().get()), getActivity().getSupportFragmentManager());
            } catch (InterruptedException | MalformedURLException | ExecutionException e1) {
                e1.printStackTrace();
            }
        } else {
            ArrayList<Trial> trials = new ArrayList<>();
            trials.add(new Trial("Internet unavailable", "Please enable an internet connection in order to use Neurobranch services.", "You", true));
            adapter = new CardAdapter(trials, getActivity().getSupportFragmentManager());
        }

        recyclerView.setAdapter(adapter);*/
    }
}
