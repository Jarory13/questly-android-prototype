package com.questly.android;

import com.firebase.client.Firebase;
import com.parse.Parse;
import com.parse.ParseUser;

import android.app.Application;

public class QuestlyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, Config.PARSE_APPLICATION_ID, Config.PARSE_CLIENT_ID);
        Firebase.setAndroidContext(this);
    }
}
