package com.questly.android.util;

import java.util.Calendar;

public class DateUtil {

    /**
     * Should check if the date supplies is at least 18 years apart from {@link System#currentTimeMillis()}
     * TODO:Find an elegant solution to compare time.
     */
    public static boolean isDOBOver18(int month, int day, int year) {
        int userYear = Calendar.getInstance().get(Calendar.YEAR) - year;
        return userYear >= 18;
    }

}
