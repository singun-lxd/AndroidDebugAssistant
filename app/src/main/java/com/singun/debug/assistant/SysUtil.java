package com.singun.debug.assistant;

import android.content.ClipData;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;

/**
 * Created by dell on 2016/10/31 0031.
 */

public class SysUtil {
    public static String getAndroidId(Context pContext) {
        String androidID = "";
        try {
            androidID = Settings.Secure.getString(pContext.getContentResolver(), Settings.Secure.ANDROID_ID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return androidID;
    }

    @SuppressWarnings("deprecation")
    public static String getCpuAbi() {
        String abi = "";
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            abi = Build.CPU_ABI;
        } else {
            for (String cpu : Build.SUPPORTED_ABIS) {
                abi += cpu;
                abi += " ";
            }
        }
        return abi;
    }

    @SuppressWarnings("deprecation")
    public static boolean copyToClipboard(Context context, String content) {
        boolean result = false;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                android.content.ClipboardManager c = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                c.setPrimaryClip(ClipData.newPlainText(null, content));
            } else {
                android.text.ClipboardManager c = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                c.setText(content);
            }
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
