package com.example.niki.fieldoutlookandroid.helper.time_entry_type_service;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;


import com.example.niki.fieldoutlookandroid.helper.authentication_service.AuthenticateUserReciever;

/**
 * Created by Owner on 4/1/2016.
 */
public class TimeEntryTypeReciever extends ResultReceiver {
    private Listener listener;

    /**
     * Create a new ResultReceive to receive results.  Your
     * {@link #onReceiveResult} method will be called from the thread running
     * <var>handler</var> if given, or from an arbitrary thread if null.
     *
     * @param handler
     */
    public TimeEntryTypeReciever(Handler handler) {
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
