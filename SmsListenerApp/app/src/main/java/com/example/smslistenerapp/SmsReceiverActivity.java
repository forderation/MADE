package com.example.smslistenerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SmsReceiverActivity extends AppCompatActivity implements View.OnClickListener{
    TextView tvSmsFrom;
    TextView tvSmsMessage;
    Button btnClose;
    public static final String EXTRA_SMS_NO = "extra_sms_no";
    public static final String EXTRA_SMS_MESSAGE = "extra_sms_message";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_receiver);
        setTitle("Incoming Message");
        tvSmsFrom = findViewById(R.id.tv_no);
        tvSmsMessage = findViewById(R.id.tv_message);
        btnClose = findViewById(R.id.btn_close);
        tvSmsFrom.setText(String.format("from : %s",getIntent().getStringExtra(EXTRA_SMS_NO)));
        tvSmsMessage.setText(getIntent().getStringExtra(EXTRA_SMS_MESSAGE));
        btnClose.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.btn_close){
            finish();
        }
    }
}
