package com.glassbyte.neurobranch.Portal.QuestionPrefabs.Unimplemented;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.glassbyte.neurobranch.R;

/**
 * Created by ed on 10/06/16.
 */
public class Drawing extends android.support.v4.app.Fragment {
    View view;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.portal_default, container, false);

        return view;
    }
}
