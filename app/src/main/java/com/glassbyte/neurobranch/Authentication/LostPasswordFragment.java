package com.glassbyte.neurobranch.Authentication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.glassbyte.neurobranch.R;

/**
 * Created by ed on 10/06/16.
 */
public class LostPasswordFragment extends Fragment {
    View view;
    Button btn_reset_password;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.lost_password, container, false);
        btn_reset_password = (Button) view.findViewById(R.id.btn_reset_password);
        btn_reset_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthenticationActivity.setFragment(getFragmentManager(), new SigninFragment());
                Toast.makeText(getContext(), "You should receive an email soon for resetting your password",
                        Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }
}
