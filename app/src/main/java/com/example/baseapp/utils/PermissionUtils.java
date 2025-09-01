package com.example.baseapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionUtils {

    public static final int NOTIFICATION_PERMISSION_CODE = 1001;

    // Função para verificar se a permissão de notificações está concedida
    public static boolean isNotificationPermissionGranted(Context context) {
        return ContextCompat.checkSelfPermission(context, "android.permission.POST_NOTIFICATIONS") == PackageManager.PERMISSION_GRANTED;
    }

    // Função para solicitar a permissão de notificações (se não concedida)
    public static void requestNotificationPermission(Activity activity) {
        if (!isNotificationPermissionGranted(activity)) {
            ActivityCompat.requestPermissions(activity, new String[]{"android.permission.POST_NOTIFICATIONS"}, NOTIFICATION_PERMISSION_CODE);
        }
    }

    // Função genérica para solicitar qualquer permissão (ex: para usar em outras permissões futuras)
    public static void requestPermission(Activity activity, String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
        }
    }
}