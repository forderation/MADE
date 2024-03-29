package com.example.myservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;

public class BoundService extends Service {
    final String TAG = BoundService.class.getSimpleName();
    MyBinder mBinder = new MyBinder();
    final long startTime = System.currentTimeMillis();
    CountDownTimer mTimer = new CountDownTimer(100000,1000) {
        @Override
        public void onTick(long l) {
            long elapsedTime = System.currentTimeMillis() - startTime;
            Log.d(TAG,"onTick " + elapsedTime);
        }

        @Override
        public void onFinish() {

        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"On Create .. ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "On Destroy ..");
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.d(TAG,"On Rebind ....");
    }

    public BoundService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
       Log.d(TAG,"On Bind ... ");
       mTimer.start();
       return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "On Unbind ...");
        mTimer.cancel();
        return super.onUnbind(intent);
    }

    class MyBinder extends Binder{
        BoundService getService(){
            return BoundService.this;
        }
    }
}
