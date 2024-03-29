package com.example.smslistenerapp;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;


public class DownloadService extends IntentService {

    public static final String TAG = DownloadService.class.getSimpleName();

    public DownloadService() {
        super("DownloadService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "Download service dijalankan");
        if(intent!=null){
            try {
                Thread.sleep(5000);
            }catch (Exception e){
                e.printStackTrace();
            }
            Intent notifyFinishIntent = new Intent(MainActivity.ACTION_DOWNLOAD_STATUS);
            sendBroadcast(notifyFinishIntent);
        }
    }
}
