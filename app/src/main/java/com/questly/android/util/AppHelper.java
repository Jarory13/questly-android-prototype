package com.questly.android.util;

/**
 * Created by eyau on 11/8/15.
 */
public class AppHelper {

    private static AppHelper mInstance;

    private double mLat;

    private double mLong;

    public static AppHelper getInstance() {
        if (mInstance == null) {
            mInstance = new AppHelper();
        }
        return mInstance;
    }

    public void setLat(double lat) {
        mLat = lat;
    }

    public void setLong(double aLong) {
        mLong = aLong;
    }

    public double getLat() {
        return mLat;
    }

    public double getLong() {
        return mLong;
    }
}
