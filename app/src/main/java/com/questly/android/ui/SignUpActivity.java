package com.questly.android.ui;

import com.parse.ParseException;
import com.parse.SignUpCallback;
import com.questly.android.R;
import com.questly.android.api.QuestlyParse;
import com.questly.android.commons.BaseActivity;
import com.questly.android.util.DateUtil;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

import static com.questly.android.util.WindowsUtil.getStatusBarHeight;

public class SignUpActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        getFragmentManager().beginTransaction().add(getContainerId(), SignUpFragment.newInstance())
                .commit();
    }

    public static class SignUpFragment extends Fragment {

        private EditText mEmailEditText;

        private EditText mDateOfBirthEditText;

        private EditText mPasswordEditText;

        private EditText mUsernameEditText;

        private DatePickerDialog mDatePickerDialog;

        private AlertDialog mErrorDialog;

        private int mSelectedDay;

        private int mSelectedMonth;

        private int mSelectedYear;

        private TextView mSignupText;

        private ProgressDialog mProgressDialog;

        private AlertDialog mSuccessDialog;

        public static SignUpFragment newInstance() {
            return new SignUpFragment();
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            setHasOptionsMenu(true);
            View rootView = inflater.inflate(R.layout.fragment_signup, container, false);
            Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
            toolbar.setTitle(R.string.signup);
            toolbar.setPadding(0, getStatusBarHeight(getActivity()), 0, 0);
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(
                    true);
            mEmailEditText = (EditText) rootView.findViewById(R.id.input_email);
            mPasswordEditText = (EditText) rootView.findViewById(R.id.input_password);
            mDateOfBirthEditText = (EditText) rootView.findViewById(R.id.input_dob);
            mUsernameEditText = (EditText) rootView.findViewById(R.id.input_username);
            mSignupText = (TextView) rootView.findViewById(R.id.signup_text);

            mDateOfBirthEditText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDateOfBirthEditText.setError(null);
                    if (mDatePickerDialog == null) {
                        mDatePickerDialog = new DatePickerDialog(getActivity(),
                                new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year,
                                            int monthOfYear, int dayOfMonth) {
                                        mSelectedYear = year;
                                        //January starts with 0.
                                        mSelectedMonth = monthOfYear;
                                        mSelectedDay = dayOfMonth;
                                        mDateOfBirthEditText.setText(getString(R.string.dob_format,
                                                monthOfYear + 1, mSelectedDay, mSelectedYear));

                                    }
                                }, Calendar.getInstance().get(Calendar.YEAR) - 18, 0, 1);
                    }
                    mDatePickerDialog.show();
                }
            });

            mSignupText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (validateForm()) {
                        executeSignup();
                    }
                }
            });
            return rootView;
        }

        private void executeSignup() {
            String email = mEmailEditText.getText().toString().trim();
            String password = mPasswordEditText.getText().toString().trim();
            String username = mUsernameEditText.getText().toString().trim();
            String yearOfDOB = String.valueOf(mSelectedYear);

            showProgressDialog();
            QuestlyParse.getInstance().signUpUser(username, email, password, yearOfDOB,
                    new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            hideProgressDialog();
                            if (e != null) {
                                showFailedSignUpDialog(e.getMessage());
                            } else {
                                Intent i = new Intent(getActivity(), OnboardingActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                getActivity().startActivity(i);
                                getActivity().finish();
                            }

                        }
                    });
        }


        //TODO: Consolidate progress dialog logic. Could be a util class
        private void showProgressDialog() {
            if (mProgressDialog == null) {
                mProgressDialog = new ProgressDialog(getActivity());
                mProgressDialog.setMessage(getString(R.string.signingup_progress_text));
                mProgressDialog.setIndeterminate(true);
                mProgressDialog.setCanceledOnTouchOutside(false);
                mProgressDialog.setCancelable(false);
            }
            mProgressDialog.show();
        }

        private void showFailedSignUpDialog(String errorMessage) {
            if (mSuccessDialog == null) {
                mSuccessDialog = new AlertDialog.Builder(getActivity())
                        .setMessage(R.string.signingup_failed_message + "\n" + errorMessage)
                        .setNegativeButton(android.R.string.cancel,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).create();
            }
            mSuccessDialog.show();
        }

        private void hideProgressDialog() {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
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
            return !TextUtils.isEmpty(mEmailEditText.getText().toString())
                    && mEmailEditText.getText().toString().contains("@");
        }

        private boolean validatePassword() {
            return !TextUtils.isEmpty(mPasswordEditText.getText().toString());
        }

        private boolean validateUsername() {
            return !TextUtils.isEmpty(mUsernameEditText.getText().toString());
        }

        private boolean validateDateOfBirth() {
            return DateUtil.isDOBOver18(mSelectedMonth, mSelectedDay, mSelectedYear);
        }

        private boolean validateForm() {
            if (!validateUsername()) {
                mUsernameEditText.setError(getString(ErrorFormType.USERNAME.getStringResId()));
                return false;
            }

            if (!validateEmail()) {
                mEmailEditText.setError(getString(ErrorFormType.EMAIL.getStringResId()));
                return false;
            }

            if (!validatePassword()) {
                mPasswordEditText.setError(getString(ErrorFormType.PASSWORD.getStringResId()));
                return false;
            }

            if (!validateDateOfBirth()) {
                showFormErrorDialog(ErrorFormType.DOB);
                return false;
            }

            return true;
        }

        /**
         * Need?
         */
        private void showFormErrorDialog(ErrorFormType type) {
            if (mErrorDialog == null) {
                mErrorDialog = new AlertDialog.Builder(getActivity())
                        .setMessage(type.getStringResId()).setNegativeButton(android.R.string.ok,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).create();
            }
            mErrorDialog.show();
        }

        private enum ErrorFormType {
            USERNAME(R.string.invalid_error_msg_username),
            DOB(R.string.invalid_error_msg_dob),
            EMAIL(R.string.invalid_error_msg_email),
            PASSWORD(R.string.invalid_error_msg_password);

            private final int resId;

            ErrorFormType(int resId) {
                this.resId = resId;
            }

            public int getStringResId() {
                return resId;
            }
        }


    }
}
