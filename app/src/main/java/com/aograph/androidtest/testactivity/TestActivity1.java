package com.aograph.androidtest.testactivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.aograph.androidtest.BaseActivity;
import com.aograph.androidtest.R;

public class TestActivity1 extends BaseActivity implements View.OnClickListener {

    private EditText edit_version;
    private EditText edit_type;
    private EditText edit_json;
    private String goods_id;
    private int version = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Button button = (Button) findViewById(R.id.button);
        Button btn_login = (Button) findViewById(R.id.btn_login);
        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);
        Button button4 = (Button) findViewById(R.id.button4);
        Button button5 = (Button) findViewById(R.id.button5);
//        Button button6 = (Button) findViewById(R.id.button6);
        edit_version = findViewById(R.id.edit_version);
        edit_type = findViewById(R.id.edit_type);
        edit_json = findViewById(R.id.edit_json);
        Button btnSubmit = (Button) findViewById(R.id.btnSubmit);
        button.setOnClickListener(this);

        btn_login.setOnClickListener(this);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
//        button6.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }

}
