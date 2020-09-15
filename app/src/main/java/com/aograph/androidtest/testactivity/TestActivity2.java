package com.aograph.androidtest.testactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.aograph.androidtest.BaseActivity;
import com.aograph.androidtest.MainActivity;
import com.aograph.androidtest.R;

public class TestActivity2 extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
        TextView textView = findViewById(R.id.textView);
        textView.setText("test2");

        Button button = findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(TestActivity2.this, MainActivity.class);
//                startActivity(intent);
            }
        });

        Button button_aa = (Button) findViewById(R.id.button_aa);
        Button button_bb = (Button) findViewById(R.id.button_bb);

        button_aa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(TestActivity2.this, WebActvity.class);
                intent1.putExtra("url", "http://192.168.1.195:8089/sdk_uglify/androidtest.html");
                startActivity(intent1);
            }
        });
        button_bb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(TestActivity2.this, WebActvity.class);
                intent1.putExtra("url", "http://121.69.87.238:45467/sdk_uglify/androidtest.html");
                startActivity(intent1);
            }
        });
    }
}
