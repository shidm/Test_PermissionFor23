package com.example.sdm.test_permissionfor23;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private String TAG = "权限授权测试" ;
    private String permission[] = {Manifest.permission.GET_ACCOUNTS};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.d(TAG, ">=M");
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.GET_ACCOUNTS)
                    != PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "No Permission");
                    ActivityCompat.requestPermissions(this,permission,164);
            } else {
                AccountManager accountManager =  AccountManager.get(this);
                Account[] accounts = accountManager.getAccounts();
                for(Account account:accounts){
                    Log.w(TAG, "hongyan:account.name="+account.name);
                    Log.w(TAG, "hongyan:account.type="+account.type);}
                Log.w(TAG, "hongyan:accounts.length="+accounts.length);
                if (hasFlymeAccount(this)) {
                    Log.d(TAG, "flyme账号已登录");
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 164) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "onRequestPermissionsResult:没权限 ");
                boolean b = shouldShowRequestPermissionRationale(Manifest.permission.GET_ACCOUNTS);
                if (b) {
                    Log.d(TAG, "onRequestPermissionsResult: 没有点击不再提醒");
                    ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.GET_ACCOUNTS}
                            ,164);
                }else
                    Log.d(TAG,"点击了不在提醒");
            }else {
                Toast.makeText(this, "授权成功", Toast.LENGTH_SHORT).show();

                AccountManager accountManager =  AccountManager.get(this);
                Account[] accounts = accountManager.getAccounts();
                for(Account account:accounts){
                    Log.w(TAG, "hongyan:account.name="+account.name);
                    Log.w(TAG, "hongyan:account.type="+account.type);}
                Log.w(TAG, "hongyan:accounts.length="+accounts.length);
                if (hasFlymeAccount(this)) {
                    Log.d(TAG, "Flyme账号已登录");
                }
            }
        }
    }

    public static boolean hasFlymeAccount(Context context) {
        AccountManager am = AccountManager.get(context);
        Account[] accounts = am.getAccountsByType("com.meizu.account");
        Log.d("是", String.valueOf(accounts.length));
        if (accounts == null || accounts.length == 0) {
            return false;
        }
        if (accounts.length != 1) {
            Log.d("hasFlymeAccount: ", "more than one account ");
        }

        return true;
    }
}
