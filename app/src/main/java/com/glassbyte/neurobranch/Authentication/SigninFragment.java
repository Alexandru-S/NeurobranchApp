package com.glassbyte.neurobranch.Authentication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.glassbyte.neurobranch.MainActivity;
import com.glassbyte.neurobranch.R;
import com.glassbyte.neurobranch.Services.Enums.Preferences;
import com.glassbyte.neurobranch.Services.HTTP.HTTPRequest;
import com.glassbyte.neurobranch.Services.Helpers.Manager;
import com.glassbyte.neurobranch.Services.Interfaces.LoginCallback;

import java.util.concurrent.ExecutionException;

/**
 * Created by ed on 10/06/16.
 */
public class SigninFragment extends Fragment {
    View view;
    Button btn_login;
    TextView tv_signup, tv_lost_password;

    EditText et_email, et_password;
    String email, password;

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

        et_email = (EditText) view.findViewById(R.id.et_email);
        et_password = (EditText) view.findViewById(R.id.et_password);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEmail(et_email.getText().toString());
                setPassword(et_password.getText().toString());

                if (getEmail().isEmpty() || getPassword().isEmpty()) {
                    Toast.makeText(getActivity(), "Please input all fields for login", Toast.LENGTH_LONG).show();
                } else {
                    LoginCallback loginCallback = new LoginCallback() {
                        @Override
                        public void onLoggedIn(String id) {
                            Manager.getInstance().setPreference(Preferences.id, id, getActivity());

                            startActivity(new Intent(getContext(), MainActivity.class));
                            getActivity().finish();
                        }

                        @Override
                        public void onLoginFailed() {
                            Toast.makeText(getContext(), "Incorrect login details provided!", Toast.LENGTH_SHORT).show();
                        }
                    };
                    System.out.println(getEmail() + " " + getPassword());
                    if (getEmail() != null || getPassword() != null)
                        new HTTPRequest.CandidateLogin(getEmail(), getPassword(), loginCallback).execute();
                }
            }
        });

        tv_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Manager.getInstance().setFragment(getFragmentManager(), new SignupFragment());
            }
        });

        tv_lost_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Manager.getInstance().setFragment(getFragmentManager(), new LostPasswordFragment());
            }
        });

        return view;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
