package com.example.niki.fieldoutlookandroid.helper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by Owner on 4/1/2016.
 */
public class NetworkHelper {
    public NetworkHelper() {

    }

    public Boolean isConnectionAvailable(Context context) {
        try {
            ConnectivityManager cm =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null &&
                    activeNetwork.isConnectedOrConnecting();
            return isConnected;
        } catch (Exception ex) {

        }
        return false;
    }


    public class NetworkChangedReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (isConnectionAvailable(context)) {
                Toast.makeText(context, "Network Available Do operations", Toast.LENGTH_LONG).show();


            }
        }
    }
}
