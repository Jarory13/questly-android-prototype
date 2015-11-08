package com.questly.android.ui;

import com.questly.android.R;
import com.questly.android.commons.BaseActivity;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import static com.questly.android.util.WindowsUtil.getStatusBarHeight;

public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        getFragmentManager().beginTransaction().add(getContainerId(), LoginFragment.newInstance())
                .commit();
    }

    public static class LoginFragment extends Fragment {

        private EditText mEmailEditText;
        private EditText mPasswordEditText;
        private TextView mLoginText;
        private AlertDialog mInvalidDialog;

        public static LoginFragment newInstance() {
            return new LoginFragment();
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            setHasOptionsMenu(true);
            View rootView = inflater.inflate(R.layout.fragment_login, container, false);
            Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
            toolbar.setTitle(R.string.login);
            toolbar.setPadding(0, getStatusBarHeight(getActivity()), 0, 0);
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            ((AppCompatActivity) getActivity()).getSupportActionBar()
                    .setDisplayHomeAsUpEnabled(true);
            mEmailEditText = (EditText) rootView.findViewById(R.id.input_email);
            mPasswordEditText = (EditText) rootView.findViewById(R.id.input_password);
            mLoginText = (TextView) rootView.findViewById(R.id.login_text);
            mLoginText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (validateEmail() && validatePassword()) {
                        //TODO: Actual Login
                        getActivity().startActivity(new Intent(getActivity(), MainActivity.class));
                    } else {
                        if (mInvalidDialog == null) {
                            mInvalidDialog = new AlertDialog.Builder(getActivity())
                                    .setMessage(getString(R.string.login_invalid_email_password))
                                    .setNegativeButton(android.R.string.ok,
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog,
                                                        int which) {
                                                    dialog.dismiss();
                                                }
                                            })
                                    .create();
                        }
                        mInvalidDialog.show();
                    }
                }
            });
            return rootView;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case android.R.id.home:
                    getActivity().finish();
                    break;
            }
            return true;
        }

        private boolean validateEmail() {
            return !TextUtils.isEmpty(mEmailEditText.getText().toString());
        }

        private boolean validatePassword() {
            return !TextUtils.isEmpty(mPasswordEditText.getText().toString());
        }

    }


}
