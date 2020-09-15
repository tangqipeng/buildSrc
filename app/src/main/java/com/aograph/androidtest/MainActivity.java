package com.aograph.androidtest;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.aograph.androidtest.testactivity.TestActivity1;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = MainActivity.class.getName();

    TextView textView1;

    EditText edit_phone;

    String txt = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.button);
        textView1 = (TextView) findViewById(R.id.text);
        Button button_device = (Button) findViewById(R.id.button_device);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, TestActivity1.class);
        startActivity(intent);
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("DDDD", "onDestroy");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
