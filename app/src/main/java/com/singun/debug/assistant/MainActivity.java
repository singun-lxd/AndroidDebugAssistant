package com.singun.debug.assistant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.singun.debug.R;

public class MainActivity extends Activity implements View.OnClickListener {
    private String mAndroidId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        updateView();
    }

    private void updateView() {
        mAndroidId = SysUtil.getAndroidId(this);
        if (!TextUtils.isEmpty(mAndroidId)) {
            ((TextView) findViewById(R.id.text_aid)).setText(mAndroidId);
        } else {
            Toast.makeText(this, R.string.toast_aid_failed, Toast.LENGTH_SHORT).show();
        }

        findViewById(R.id.btn_copy).setOnClickListener(this);
        findViewById(R.id.btn_request_usage).setOnClickListener(this);
        findViewById(R.id.btn_request_float).setOnClickListener(this);
        findViewById(R.id.btn_app_setting).setOnClickListener(this);
        findViewById(R.id.btn_show_accounts).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_copy:
                onCopyAndroidIdClick();
                break;
            case R.id.btn_request_usage:
                onRequestUsagePermissionClick();
                break;
            case R.id.btn_request_float:
                onRequestFloatPermissionClick();
                break;
            case R.id.btn_app_setting:
                onAppSettingClick();
                break;
            case R.id.btn_show_accounts:
                onShowAccountsClick();
                break;
        }
    }

    private void onCopyAndroidIdClick() {
        if (TextUtils.isEmpty(mAndroidId)) {
            Toast.makeText(this, R.string.toast_aid_failed, Toast.LENGTH_SHORT).show();
        }
        boolean result = SysUtil.copyToClipboard(this, mAndroidId);
        if (result) {
            Toast.makeText(this, R.string.toast_copy, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, R.string.toast_copy_failed, Toast.LENGTH_SHORT).show();
        }
    }

    private void onRequestUsagePermissionClick() {
        boolean result = PermissionUtil.checkAndStartUsagePermissionSettingActivity(this);
        if (!result) {
            Toast.makeText(this, R.string.failed_start_activity, Toast.LENGTH_SHORT).show();
        }
    }

    private void onRequestFloatPermissionClick() {
        boolean result = PermissionUtil.checkAndStartFloatPermissionSettingActivity(this);
        if (!result) {
            Toast.makeText(this, R.string.failed_start_activity, Toast.LENGTH_SHORT).show();
        }
    }

    private void onAppSettingClick() {
        boolean result = PermissionUtil.checkAndStartApplicationSettingActivity(this);
        if (!result) {
            Toast.makeText(this, R.string.failed_start_activity, Toast.LENGTH_SHORT).show();
        }
    }

    private void onShowAccountsClick() {
        Intent intent = new Intent(this, AccountActivity.class);
        startActivity(intent);
    }
}