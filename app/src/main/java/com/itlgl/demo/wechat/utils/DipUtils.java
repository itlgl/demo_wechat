package com.itlgl.demo.wechat.utils;

import android.content.res.Resources;
import android.util.DisplayMetrics;

public class DipUtils {
    public static float dpToPixel(float dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return dp * metrics.density;
    }
}
