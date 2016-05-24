package com.example.niki.fieldoutlookandroid.helper.authentication_service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.os.ResultReceiver;

import com.example.niki.fieldoutlookandroid.helper.CryptoHelper;

import java.net.*;
import java.io.InputStream;
import org.xmlpull.v1.*;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * helper methods.
 */
public class IntentServiceHelper extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "com.example.niki.fieldoutlookandroid.helper.action.FOO";
    private static final String ACTION_BAZ = "com.example.niki.fieldoutlookandroid.helper.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.example.niki.fieldoutlookandroid.helper.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.example.niki.fieldoutlookandroid.helper.extra.PARAM2";
    private static final String DEBUG_SERVICE_URI="http://localhost:32251/WebDataService.svc/xml/";
    private static final String SERVICE_URI="http://fieldoutlookcloudservice.cloudapp.net/WebDataService.svc/xml/";

    public static String USERNAME;
    public static String PASSWORD;
    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, IntentServiceHelper.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionAuthenticate(Context context, String param1, String param2) {
        Intent intent = new Intent(context, IntentServiceHelper.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    public IntentServiceHelper() {
        super("IntentServiceHelper");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            AuthenticationResponse stock = intent.getParcelableExtra("authenticateResponse");
            final ResultReceiver rec = (ResultReceiver) intent.getParcelableExtra("rec");
            HttpURLConnection con = (HttpURLConnection) ( new URL(SERVICE_URI+"AuthenticateUser/"+stock.GetUsername()+"/"+stock.GetPassword())).openConnection();
            con.setRequestMethod("GET");
            con.connect();
            InputStream is = con.getInputStream();

            // Start parsing XML
            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(is, null);
            int event = parser.getEventType();
            String tagName = null;
            String currentTag = null;
            AuthenticationResponse authenticationResponse= new AuthenticationResponse();
            authenticationResponse.SetUsername(stock.GetUsername());
           CryptoHelper cryptoHelper=new CryptoHelper();
            authenticationResponse.setEncryptedPassword(cryptoHelper.encrypt(stock.GetPassword()));
            while (event != XmlPullParser.END_DOCUMENT) {
                tagName = parser.getName();

                if (event == XmlPullParser.START_TAG) {
                    currentTag = tagName;
                }
                else if (event == XmlPullParser.TEXT) {
                    if(currentTag.equals("a:IsActive")) {
                        authenticationResponse.SetIsAuthenticated(parser.getText());
                    }else if(currentTag.equals("a:FirstName")){
                        authenticationResponse.SetFirstName(parser.getText());
                    }else if(currentTag.equals("a:LastName")){
                        authenticationResponse.SetLastName(parser.getText());
                    }else if(currentTag.equals("a:FullName")){
                        authenticationResponse.SetFullName(parser.getText());
                    }else if(currentTag.equals("a:UserID")){
                        int userId = Integer.parseInt(parser.getText());
                        authenticationResponse.SetUserId(userId);
                    }
                }

                event = parser.next();

            }
            Bundle b = new Bundle();
            b.putParcelable("authencticateResponse", authenticationResponse);
            rec.send(0, b);

//            DefaultHttpClient httpClient = new DefaultHttpClient();
//            HttpGet request = new HttpGet(SERVICE_URI + "GetPersonListByCompany/1/asdf");
//
//            request.setHeader("Accept", "application/xml");
//            request.setHeader("Content-type", "application/xml");
//
//            HttpResponse response = httpClient.execute(request);
//
//            HttpEntity responseEntity = response.getEntity();
//            String output = null;

//            output = EntityUtils.toString(responseEntity);

            //return output;
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo

    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
