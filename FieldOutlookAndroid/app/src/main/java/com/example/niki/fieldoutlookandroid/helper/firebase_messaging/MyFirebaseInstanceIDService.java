package com.example.niki.fieldoutlookandroid.helper.firebase_messaging;
import android.util.Log;

import com.example.niki.fieldoutlookandroid.businessobjects.MobileRegistration;
import com.example.niki.fieldoutlookandroid.helper.TokenHelper;
import com.example.niki.fieldoutlookandroid.helper.singleton.Global;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Owner on 5/26/2016.
 */
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // TODO: Implement this method to send any registration to your app's servers.
        sendRegistrationToServer(refreshedToken);
    }
    // [END refresh_token]

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        // Add custom implementation, as needed.

        //TODO: send token to shared prefs and to the cloud
        try {
            while(Global.IsLoggedIn==false){

            }
                HttpURLConnection con = (HttpURLConnection) (new URL(Global.GetInstance().getServiceUri() + "SaveMobileRegistrationByUser?UserId=" + Global.GetInstance().getUser().GetUserId() + "&RegistrationId=" + token + "&Token=" + URLEncoder.encode(TokenHelper.getToken()) + "")).openConnection();
                con.setRequestMethod("GET");
//            con.setDoInput(true);
//            con.setDoOutput(true);
//            JSONObject json = new JSONObject();
//            MobileRegistration mobileRegistration=new MobileRegistration();
//            mobileRegistration.setUserId(Global.GetInstance().getUser().GetUserId());
//            mobileRegistration.setRegistrationId(token);
//            DataOutputStream dStream = new DataOutputStream(con.getOutputStream());
//            json.put("mobileRegistration",mobileRegistration);
//            StringEntity se = new StringEntity( json.toString());
//            se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                //dStream.writeBytes(mobileRegistration.); //Writes out the string to the underlying output stream as a sequence of bytes
//            dStream.flush(); // Flushes the data output stream.
//            dStream.close(); // Closing the output stream.
                con.connect();
            InputStream is = con.getInputStream();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
