package com.huypham.instagramdemo.utils.display;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Build;
import android.view.View;

import com.huypham.instagramdemo.R;

import androidx.core.content.ContextCompat;

public class ScreenUtils implements ScreenResourceProvider {
    @Override
    public int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    @Override
    public int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static void setLightDisplayStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = activity.getWindow().getDecorView().getSystemUiVisibility();
            flags = flags | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            activity.getWindow().getDecorView().setSystemUiVisibility(flags);
            activity.getWindow().setStatusBarColor(ContextCompat.getColor(activity, R.color.colorStatus));
        }
    }
}
