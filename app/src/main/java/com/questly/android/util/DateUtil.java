package com.questly.android.util;

import java.util.Calendar;

public class DateUtil {

    /**
     * TODO:Find an elegant solution to compare time.
     */
    public static boolean isDOBOver18(int month, int day, int year) {
        int userYear = Calendar.getInstance().get(Calendar.YEAR) - year;
        return userYear >= 18;
    }

}
