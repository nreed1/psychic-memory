package com.example.niki.fieldoutlookandroid.helper.authentication_service;

import android.os.AsyncTask;

import com.example.niki.fieldoutlookandroid.helper.CryptoHelper;
import com.example.niki.fieldoutlookandroid.helper.ServiceHelper;
import com.example.niki.fieldoutlookandroid.helper.TokenHelper;


import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Owner on 6/6/2016.
 */
public class AuthenticateAsyncTask extends AsyncTask<String, Void, AuthenticationResponse> {
    public AuthenticationDelegate delegate=null;
    public AuthenticateAsyncTask(AuthenticationDelegate delegate){
        this.delegate=delegate;
    }
    public interface AuthenticationDelegate{
        void processFinish(AuthenticationResponse response);
    }
    @Override
    protected AuthenticationResponse doInBackground(String... params) {
        try {

           // AuthenticationResponse stock = intent.getParcelableExtra("authenticateResponse");
            //final ResultReceiver rec = (ResultReceiver) intent.getParcelableExtra("rec");
            CryptoHelper cryptoHelper=new CryptoHelper();
            HttpURLConnection con = (HttpURLConnection) ( new URL(ServiceHelper.GetServiceURL()+"AuthenticateUser/"+
                    params[0].trim()+"/"+cryptoHelper.encrypt(params[1]).trim()+"/"+ URLEncoder.encode(TokenHelper.GetToken(params[0],cryptoHelper.encrypt(params[1])
.toString()))).openConnection());
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
            authenticationResponse.SetUsername(params[0]);

            authenticationResponse.setEncryptedPassword(cryptoHelper.encrypt(params[1]));
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
                    else if(currentTag.equals("a:ApplicationCompanyID")){
                        int companyId=Integer.parseInt(parser.getText());
                        authenticationResponse.SetCompanyId(companyId);
                    }
                }

                event = parser.next();

            }
            return authenticationResponse;
        }catch (Exception ex){
            System.out.print(ex.getStackTrace());
           // ExceptionHelper.LogException(,ex);
        }
        return null;
    }
    @Override
    protected void onPostExecute(AuthenticationResponse result){
        // super.onPostExecute(result);
        delegate.processFinish(result);

    }
}
