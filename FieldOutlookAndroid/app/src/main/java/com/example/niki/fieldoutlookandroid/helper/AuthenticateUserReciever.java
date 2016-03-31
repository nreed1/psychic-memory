package com.example.niki.fieldoutlookandroid.helper;


import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

/**
 * Created by Niki on 3/11/2015.
 */
public class AuthenticateUserReciever extends ResultReceiver {
    private Listener listener;

    public AuthenticateUserReciever(Handler handler) {
        super(handler);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        if (listener != null)
            listener.onReceiveResult(resultCode, resultData);
    }

    public static interface Listener {
        void onReceiveResult(int resultCode, Bundle resultData);
    }
}
