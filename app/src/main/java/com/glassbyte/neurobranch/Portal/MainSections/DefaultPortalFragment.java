package com.glassbyte.neurobranch.Portal.MainSections;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.glassbyte.neurobranch.R;
import com.glassbyte.neurobranch.Services.DataObjects.Response;
import com.glassbyte.neurobranch.Services.Globals;
import com.glassbyte.neurobranch.Services.HTTP.HTTPRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ed on 10/06/16.
 */
public class DefaultPortalFragment extends android.support.v4.app.Fragment {
    View view;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.portal_default, container, false);

        return view;
    }
}
