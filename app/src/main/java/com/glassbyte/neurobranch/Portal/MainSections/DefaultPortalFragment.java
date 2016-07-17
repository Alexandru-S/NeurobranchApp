package com.glassbyte.neurobranch.Portal.MainSections;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.glassbyte.neurobranch.R;
import com.glassbyte.neurobranch.Services.Globals;
import com.glassbyte.neurobranch.Services.HTTP.HTTPRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ed on 10/06/16.
 */
public class DefaultPortalFragment extends android.support.v4.app.Fragment {
    View view;
    Button forcePushTrialBtn, forcePushResponseBtn;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.portal_default, container, false);

        forcePushTrialBtn = (Button) view.findViewById(R.id.post_trial_btn);
        forcePushTrialBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new HTTPRequest.ForcePushTrial(null).execute();
            }
        });

        forcePushResponseBtn = (Button) view.findViewById(R.id.post_response_btn);
        forcePushResponseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(Globals.MOCK_RESPONSE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                System.out.println(jsonObject);
                new HTTPRequest.PostTrialResponse(jsonObject).execute();
                Toast.makeText(getActivity(), "Response posted", Toast.LENGTH_LONG).show();

            }
        });

        return view;
    }
}
