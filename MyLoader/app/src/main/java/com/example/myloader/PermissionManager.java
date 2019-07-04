package com.example.myloader;

import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

public class PermissionManager {
    public static void check(Activity activity, String permisson, int requestCode){
        if(ActivityCompat.checkSelfPermission(activity,permisson)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(activity,new String[]{permisson},requestCode);
        }
    }

    public static boolean isGranted(Activity activity, String permission){
        return (ActivityCompat.checkSelfPermission(activity, permission)) == PackageManager.PERMISSION_GRANTED;
    }
}
