package com.example.fingerprintsample;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.security.keystore.KeyProperties;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,FingerprintHelper.SimpleAuthenticationCallback {

    private Button encrypt, decrypt;
    private TextView tv;
    private FingerprintHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        encrypt = (Button) findViewById(R.id.encrypt);
        decrypt = (Button) findViewById(R.id.decrypt);
        tv = (TextView) findViewById(R.id.tv);
        encrypt.setOnClickListener(this);
        decrypt.setOnClickListener(this);
        helper = new FingerprintHelper(this);
        helper.setCallback(this);
        helper.generateKey();
        tv.setText("已生成Key");
        mKeyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.encrypt:
                helper.setPurpose(KeyProperties.PURPOSE_ENCRYPT);
                tv.setText("开始验证指纹......");
                helper.authenticate();
                break;
            case R.id.decrypt:
                helper.setPurpose(KeyProperties.PURPOSE_DECRYPT);
                tv.setText("开始验证指纹......");
                helper.authenticate();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_CONFIRM_DEVICE_CREDENTIALS) {
            // Challenge completed, proceed with using cipher
            if (resultCode == RESULT_OK) {
                helper.authenticate();
            } else {
                // The user canceled or didn’t complete the lock screen
                // operation. Go to error/cancellation flow.
            }
        }
    }

    public static final int REQUEST_CODE_CONFIRM_DEVICE_CREDENTIALS = 1;
    private KeyguardManager mKeyguardManager;
    @Override
    public void showAuthenticationScreen() {
        // Create the Confirm Credentials screen. You can customize the title and description. Or
        // we will provide a generic one for you if you leave it null
        Intent intent = mKeyguardManager.createConfirmDeviceCredentialIntent(null, null);
        if (intent != null) {
            startActivityForResult(intent, REQUEST_CODE_CONFIRM_DEVICE_CREDENTIALS);
        }
    }


    @Override
    public void onAuthenticationSucceeded(String value) {
        tv.setText(value);
    }

    @Override
    public void onAuthenticationFail() {
        tv.setText("验证失败");
    }
}
