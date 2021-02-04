package com.hache.appentrepatas.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.hache.appentrepatas.MainActivity;

public  class  General {

    final private static int REQUEST_CODE_ASK_PERMISSIONS = 123;

    public static void permisoCall( Activity activity){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                // Se tiene permiso
            }else{
                ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CODE_ASK_PERMISSIONS);
                return;
            }
        }else{
            // No se necesita requerir permiso OS menos a 6.0.
        }
    }
}
