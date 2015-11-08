package com.questly.android.api;

import com.questly.android.Config;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

public class NetworkClient {

    private static NetworkClient mInstance = null;

    private QuestlyFirebaseServices mFirebaseServices;

    private NetworkClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.QUESTLY_FIREBASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        mFirebaseServices = retrofit.create(QuestlyFirebaseServices.class);
    }

    public static NetworkClient getInstance() {
        if (mInstance == null) {
            mInstance = new NetworkClient();
        }
        return mInstance;
    }




}
