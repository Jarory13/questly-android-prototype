package com.questly.android.api;

import com.firebase.client.Firebase;
import com.questly.android.Config;

import android.content.Context;

import java.util.Map;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class NetworkClient {

    private static NetworkClient mInstance = null;

    private static QuestlyFirebaseServices mFirebaseServices;

    private NetworkClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.QUESTLY_FIREBASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mFirebaseServices = retrofit.create(QuestlyFirebaseServices.class);
    }

    public static NetworkClient getInstance() {
        if (mInstance == null) {
            mInstance = new NetworkClient();
        }
        return mInstance;
    }

    public void postQuest(Context context, Map<String, String> questMapValues,
            Firebase.CompletionListener listener) {
        Firebase app = new Firebase(Config.QUESTLY_FIREBASE_URL + "/quests");
        app.setAndroidContext(context);
        app.setValue(questMapValues, listener);
    }


}
