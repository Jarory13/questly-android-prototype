package com.questly.android.ui;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.parse.ParseUser;
import com.questly.android.Config;
import com.questly.android.R;
import com.questly.android.util.AppHelper;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CreateTaskDialogFragment extends DialogFragment {

    private Button mCreateButton;

    private EditText mQuestName;

    private EditText mQuestType;

    private EditText mQuestTimer;

    private EditText mQuestDescription;

    private EditText mQuestReward;

    private ProgressDialog mProgressDialog;

    private AlertDialog mMessageDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_task, container, false);
        mCreateButton = (Button) view.findViewById(R.id.quest_create_button);
        mQuestName = (EditText) view.findViewById(R.id.quest_name_edittext);
        mQuestType = (EditText) view.findViewById(R.id.quest_type_edittext);
        mQuestReward = (EditText) view.findViewById(R.id.quest_reward_edittext);
        mQuestTimer = (EditText) view.findViewById(R.id.quest_timer_edittext);
        mQuestDescription = (EditText) view.findViewById(R.id.quest_description);

        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateForm()) {
                    executeCreateQuest();
                }
            }
        });

        clearInputs();
        return view;
    }

    //TODO: Post Quest
    private void executeCreateQuest() {
        Map<String, String> map = new HashMap<>();
        map.put("Date", Calendar.getInstance().getTime().toString());
        map.put("description", mQuestDescription.getText().toString());
        map.put("questName", mQuestName.getText().toString());
        map.put("questType", mQuestType.getText().toString());
        map.put("reward", mQuestReward.getText().toString());
        map.put("sender", "qwerty123");
        map.put("timer", mQuestDescription.getText().toString());
        map.put("userLat", String.valueOf(AppHelper.getInstance().getLat()));
        map.put("userLon", String.valueOf(AppHelper.getInstance().getLong()));

        showProgressDialog();
        Firebase app = new Firebase(Config.QUESTLY_FIREBASE_URL + "/quests");
        app.push().setValue(map, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                hideProgressDialog();
                if (firebaseError != null) {
                    showFailedSignUpDialog("An error occur! Please try again later.");
                } else {
                    dismiss();
                }
            }
        });
    }

    private void showFailedSignUpDialog(String errorMessage) {
        if (mMessageDialog == null) {
            mMessageDialog = new AlertDialog.Builder(getActivity())
                    .setMessage(R.string.signingup_failed_message + "\n" + errorMessage)
                    .setNegativeButton(android.R.string.cancel,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).create();
        }
        mMessageDialog.show();
    }


    //TODO: Consolidate progress dialog logic. Could be a util class
    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setMessage("Posting");
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.setCancelable(false);
        }
        mProgressDialog.show();
    }


    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setTitle(R.string.create_quest);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                clearInputs();
            }
        });
        return dialog;
    }

    private void clearInputs() {
        mQuestName.setText("");
        mQuestType.setText("");
        mQuestTimer.setText("");
        mQuestDescription.setText("");
        mQuestReward.setText("");
    }

    private boolean validateInput(EditText editText) {
        return !TextUtils.isEmpty(editText.getText().toString());
    }

    private boolean validateForm() {
        if (!validateInput(mQuestName)) {
            mQuestName.setError(getString(R.string.invalid_error_create_quest_empty));
            return false;
        }

        if (!validateInput(mQuestType)) {
            mQuestType.setError(getString(R.string.invalid_error_create_quest_empty));
            return false;
        }
        if (!validateInput(mQuestReward)) {
            mQuestReward.setError(getString(R.string.invalid_error_create_quest_empty));
            return false;
        }
        if (!validateInput(mQuestTimer)) {
            mQuestTimer.setError(getString(R.string.invalid_error_create_quest_empty));
            return false;
        }
        if (!validateInput(mQuestDescription)) {
            mQuestDescription.setError(getString(R.string.invalid_error_create_quest_empty));
            return false;
        }

        return true;
    }
}
