package com.example.baseapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionUtils {

    public static final int NOTIFICATION_PERMISSION_CODE = 1001;

    public static boolean isNotificationPermissionGranted(Context context) {
        return ContextCompat.checkSelfPermission(context, "android.permission.POST_NOTIFICATIONS") == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestNotificationPermission(Activity activity) {
        if (!isNotificationPermissionGranted(activity)) {
            ActivityCompat.requestPermissions(activity, new String[]{"android.permission.POST_NOTIFICATIONS"}, NOTIFICATION_PERMISSION_CODE);
        }
    }

    public static void requestPermission(Activity activity, String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
        }
    }
}