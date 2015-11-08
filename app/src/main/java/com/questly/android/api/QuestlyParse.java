package com.questly.android.api;

import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class QuestlyParse {

    private static QuestlyParse mInstance;

    public static QuestlyParse getInstance() {
        if (mInstance == null) {
            mInstance = new QuestlyParse();
        }
        return mInstance;
    }

    public void signUpUser(String username, String email, String password, String dateofbirth,
            SignUpCallback callback) {
        ParseUser user = new ParseUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.put("yob", dateofbirth);
        user.signUpInBackground(callback);
    }
}
