package com.example.myservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button btnStartService;
    Button btnStartIntentService;
    Button btnStartBoundService;
    Button btnStopBoundService;
    boolean mServiceBound = false;
    BoundService boundService;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            BoundService.MyBinder myBinder = (BoundService.MyBinder) iBinder;
            boundService = myBinder.getService();
            mServiceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mServiceBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnStartService = findViewById(R.id.btn_start_service);
        btnStartService.setOnClickListener(this);
        btnStartIntentService = findViewById(R.id.btn_start_intent_service);
        btnStartIntentService.setOnClickListener(this);
        btnStartBoundService = findViewById(R.id.btn_start_bound_service);
        btnStartBoundService.setOnClickListener(this);
        btnStopBoundService = findViewById(R.id.btn_stop_bound_service);
        btnStopBoundService.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mServiceBound){
            unbindService(mServiceConnection);
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_start_bound_service:
                Intent mBoundServiceIntent = new Intent(MainActivity.this,BoundService.class);
                bindService(mBoundServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
                break;
            case R.id.btn_start_intent_service:
                Intent mStartIntentService = new Intent(MainActivity.this, DicodingIntentService.class);
                mStartIntentService.putExtra(DicodingIntentService.TAG,5000);
                startService(mStartIntentService);
                break;
            case R.id.btn_stop_bound_service:
                unbindService(mServiceConnection);
                break;
            case R.id.btn_start_service:
                Intent mStartServiceIntent = new Intent(MainActivity.this,OriginService.class);
                startService(mStartServiceIntent);
                break;
        }
    }
}
