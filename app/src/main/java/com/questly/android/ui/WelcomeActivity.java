package com.questly.android.ui;

import com.parse.ParseUser;
import com.questly.android.R;
import com.questly.android.commons.BaseActivity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Probably the single point of entry in this application.
 * This will direct user to {@link MainActivity} if {@link ParseUser} is not null.
 */
public class WelcomeActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        getFragmentManager().beginTransaction()
                .add(getContainerId(), WelcomeFragment.newInstance())
                .commit();
    }

    public static class WelcomeFragment extends Fragment {

        private Button mEmailLoginButton;

        private Button mSignupButton;

        public static WelcomeFragment newInstance() {
            return new WelcomeFragment();
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_welcome, container, false);

            mEmailLoginButton = (Button) view.findViewById(R.id.email_login_button);
            mSignupButton = (Button) view.findViewById(R.id.signup_button);

            mEmailLoginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
                }
            });

            mSignupButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().startActivity(new Intent(getActivity(), SignUpActivity.class));
                }
            });

            if (ParseUser.getCurrentUser() != null) {
                getActivity().startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
            }
            return view;
        }
    }
}
