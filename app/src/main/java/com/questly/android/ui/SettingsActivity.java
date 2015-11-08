package com.questly.android.ui;

import com.questly.android.commons.BaseActivity;
import com.questly.android.R;

import android.app.Fragment;
import android.os.Bundle;

public class SettingsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        getFragmentManager().beginTransaction()
                .add(getContainerId(), SettingsFragment.newInstance())
                .commit();
    }

    public static class SettingsFragment extends Fragment {

        public static SettingsFragment newInstance() {
            return new SettingsFragment();
        }
    }
}
