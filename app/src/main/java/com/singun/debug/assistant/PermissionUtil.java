package com.singun.debug.assistant;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.provider.Settings;

import java.util.List;

/**
 * Created by dell on 2015/11/24 0024.
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class PermissionUtil {
    private static boolean hasSetting(Context context, String settingAction) {
        PackageManager packageManager = context.getApplicationContext()
                .getPackageManager();
        Intent intent = new Intent(settingAction);
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    private static void startSettingActivity(Context context, String settingAction) {
        Intent intent = new Intent(settingAction);
        context.startActivity(intent);
    }

    private static boolean checkAndStartSettingActivity(Context context, String settingAction) {
        if (hasSetting(context, settingAction)) {
            startSettingActivity(context, settingAction);
            return true;
        }
        return false;
    }

    public static boolean checkAndStartUsagePermissionSettingActivity(Context context) {
        return checkAndStartSettingActivity(context, Settings.ACTION_USAGE_ACCESS_SETTINGS);
    }

    public static boolean checkAndStartFloatPermissionSettingActivity(Context context) {
        return checkAndStartSettingActivity(context, Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
    }

    public static boolean checkAndStartApplicationSettingActivity(Context context) {
        return checkAndStartSettingActivity(context, Settings.ACTION_APPLICATION_SETTINGS);
    }

    public static boolean checkAndStartBatteryOptimizationSettingActivity(Context context) {
        return checkAndStartSettingActivity(context, Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS);
    }

    public static boolean checkAndStartSyncSettingActivity(Context context) {
        return checkAndStartSettingActivity(context, Settings.ACTION_SYNC_SETTINGS);
    }
}
