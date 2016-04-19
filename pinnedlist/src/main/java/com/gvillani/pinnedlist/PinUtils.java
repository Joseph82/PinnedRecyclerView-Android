package com.gvillani.pinnedlist;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by Giuseppe on 08/04/2016.
 */
public class PinUtils {
    public static int dpToPx(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }
}
