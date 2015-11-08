package com.questly.android.util;

import android.content.Context;

public class WindowsUtil {

    /**
     * For {@link com.questly.android.ui.WelcomeActivity}, {@link com.questly.android.ui.LoginActivity}
     * and {@link com.questly.android.ui.SignUpActivity}, translucent status bar is enabled so to have
     * {@link android.support.v7.widget.Toolbar} aligned underneath status bar, I need to calculate height
     * to set the toolbar padding.
     * Hackery.
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
