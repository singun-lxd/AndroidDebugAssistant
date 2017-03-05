package com.singun.debug.assistant;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AccountActivity extends Activity implements View.OnClickListener {
    private static final int REQUEST_CODE = 10086;

    private View mErrorZone;
    private ListView mAccountList;
    private ArrayAdapter<Account> mAccountAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        setTitle(R.string.title_accounts);
        updateView();
    }

    private void updateView() {
        mErrorZone = findViewById(R.id.error_zone);
        mAccountList = (ListView) findViewById(R.id.account_list);
        mAccountAdapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1);
        mAccountList.setAdapter(mAccountAdapter);
        findViewById(R.id.btn_request_account_permission).setOnClickListener(this);
        findViewById(R.id.btn_sync_setting).setOnClickListener(this);

        updateData();
    }

    private void updateData() {
        AccountManager accountManager = AccountManager.get(this);
        if (checkPermission(Manifest.permission.GET_ACCOUNTS, Process.myPid(), Process.myUid()) != PackageManager.PERMISSION_GRANTED) {
            requestPermission();
            return;
        }
        Account[] accounts = accountManager.getAccounts();
        if (accounts == null || accounts.length == 0) {
            String noAccount = getString(R.string.text_no_account);
            mAccountAdapter.add(new Account(noAccount, noAccount) {
                @Override
                public String toString() {
                    return name;
                }
            });
        } else {
            mAccountAdapter.addAll(accounts);
        }
        mAccountAdapter.notifyDataSetChanged();
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void requestPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }
        requestPermissions(new String[] { Manifest.permission.GET_ACCOUNTS }, REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED
                && Manifest.permission.GET_ACCOUNTS.equals(permissions[0])) {
            mErrorZone.setVisibility(View.GONE);
            updateData();
        } else {
            mErrorZone.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_request_account_permission:
                requestPermission();
                break;
            case R.id.btn_sync_setting:
                PermissionUtil.checkAndStartSyncSettingActivity(this);
                break;
        }
    }
}
