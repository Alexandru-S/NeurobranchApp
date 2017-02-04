package com.glassbyte.neurobranch.Authentication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.glassbyte.neurobranch.R;
import com.glassbyte.neurobranch.Services.HTTP.HTTPRequest;
import com.glassbyte.neurobranch.Services.Helpers.Manager;

/**
 * Created by ed on 10/06/16.
 */
public class SignupFragment extends Fragment {
    View view;
    Button btn_signup;
    EditText et_email, et_verify_email, et_password, et_verify_password;
    private String email, verifyEmail, password, verifyPassword;
    TextView tv_redirectSignin;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.signup_layout, container, false);

        btn_signup = (Button) view.findViewById(R.id.btn_signup_signup);
        et_email = (EditText) view.findViewById(R.id.et_email_signup);
        et_verify_email = (EditText) view.findViewById(R.id.et_email_signup_verify);
        et_password = (EditText) view.findViewById(R.id.et_password_signup);
        et_verify_password = (EditText) view.findViewById(R.id.et_password_signup_verify);
        tv_redirectSignin = (TextView) view.findViewById(R.id.tv_sign_up_redirect);
        tv_redirectSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Manager.getInstance().setFragment(getFragmentManager(), new SigninFragment());
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEmail(et_email.getText().toString());
                setVerifyEmail(et_verify_email.getText().toString());
                setPassword(et_password.getText().toString());
                setVerifyPassword(et_verify_password.getText().toString());

                if(!isEmailMatch()) {
                    Toast.makeText(getContext(), "Email addresses do not match!", Toast.LENGTH_SHORT).show();
                }
                if(!isPasswordMatch()) {
                    Toast.makeText(getContext(), "Passwords do not match!", Toast.LENGTH_SHORT).show();
                }
                if(isEmailMatch() && isPasswordMatch()) {
                    new HTTPRequest.CreateCandidateAccount(getEmail(), getPassword()).execute();
                    Manager.getInstance().setFragment(getFragmentManager(), new SigninFragment());
                    //Toast.makeText(getContext(), "Please verify your account for enabling all functionality.", Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }

    public boolean isEmailMatch() {
        return getEmail().equals(getVerifyEmail());
    }

    public boolean isPasswordMatch() {
        return getPassword().equals(getVerifyPassword());
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVerifyEmail() {
        return verifyEmail;
    }

    public void setVerifyEmail(String verifyEmail) {
        this.verifyEmail = verifyEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerifyPassword() {
        return verifyPassword;
    }

    public void setVerifyPassword(String verifyPassword) {
        this.verifyPassword = verifyPassword;
    }
}
