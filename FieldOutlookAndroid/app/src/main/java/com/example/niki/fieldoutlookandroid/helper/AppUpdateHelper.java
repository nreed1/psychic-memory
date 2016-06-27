package com.example.niki.fieldoutlookandroid.helper;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

/**
 * Created by Nicole on 6/24/2016.
 */
public class AppUpdateHelper {

    public static String updateLocation="https://foandroidstorage.blob.core.windows.net/debugbuild/app-debug-unaligned.apk";

    public static int getVersionCode(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException ex) {}
        return 0;
    }

    public static Intent getUpdateIntent(){
        return new Intent(Intent.ACTION_VIEW, Uri.parse(updateLocation));
    }


}
