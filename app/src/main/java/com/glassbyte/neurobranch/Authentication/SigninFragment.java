package com.glassbyte.neurobranch.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.glassbyte.neurobranch.MainActivity;
import com.glassbyte.neurobranch.R;

/**
 * Created by ed on 10/06/16.
 */
public class SigninFragment extends Fragment {
    View view;
    Button btn_login;
    TextView tv_signup, tv_lost_password;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.signin_layout, container, false);

        btn_login = (Button) view.findViewById(R.id.btn_sign_in);
        tv_signup = (TextView) view.findViewById(R.id.tv_sign_up);
        tv_lost_password = (TextView) view.findViewById(R.id.tv_lost_password);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), MainActivity.class));
                getActivity().finish();
            }
        });

        tv_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthenticationActivity.setFragment(getFragmentManager(), new SignupFragment());
            }
        });

        tv_lost_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthenticationActivity.setFragment(getFragmentManager(), new LostPasswordFragment());
            }
        });

        return view;
    }
}
