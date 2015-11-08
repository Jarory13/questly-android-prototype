package com.questly.android.api;

import java.util.Map;

import retrofit.Callback;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

public interface QuestlyFirebaseServices {

    @FormUrlEncoded
    @POST("/post")
    void createQuest(@FieldMap Map<String, String> map, Callback<Void> callback);
}
