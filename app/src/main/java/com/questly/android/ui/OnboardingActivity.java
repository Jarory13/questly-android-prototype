package com.questly.android.ui;


import com.questly.android.R;
import com.questly.android.commons.BaseActivity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Onboarding experience a user see after {@link SignUpActivity}
 */
public class OnboardingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        getFragmentManager().beginTransaction()
                .add(getContainerId(), OnboardingFragment.newInstance()).commit();

    }

    public static class OnboardingFragment extends Fragment {

        public static OnboardingFragment newInstance() {
            return new OnboardingFragment();
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            //TODO: Finish onboarding experience
            getActivity().startActivity(new Intent(getActivity(), MainActivity.class));
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }
}
