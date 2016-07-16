package com.glassbyte.neurobranch.Portal.MainSections;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.glassbyte.neurobranch.R;
import com.glassbyte.neurobranch.Services.DataObjects.Attributes;
import com.glassbyte.neurobranch.Services.DataObjects.Response;
import com.glassbyte.neurobranch.Services.HTTP.HTTPRequest;

/**
 * Created by ed on 10/06/16.
 */
public class DefaultPortalFragment extends android.support.v4.app.Fragment {
    View view;
    Button forcePushTrialBtn;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.portal_default, container, false);

        forcePushTrialBtn = (Button) view.findViewById(R.id.post_trial_btn);
        forcePushTrialBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new HTTPRequest.PostTrialResponse(Response.generateTrial(1)).execute();
            }
        });

        return view;
    }
}
