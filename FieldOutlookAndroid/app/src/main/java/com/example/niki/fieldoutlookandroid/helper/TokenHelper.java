package com.example.niki.fieldoutlookandroid.helper;

import android.util.Base64;

import com.example.niki.fieldoutlookandroid.helper.singleton.Global;

import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

/**
 * Created by Owner on 4/1/2016.
 */
public class TokenHelper {

    private static String Key="046f9128-0975-428a-a468-bd222f211135";
    public static String getToken() {
        return GetToken(Global.GetInstance().getUser().GetUsername(),Global.GetInstance().getUser().getEncryptedPassword());
    }

    public static String GetToken(String UserName, String EncryptedPassword)
    {
        try {CryptoHelper cryptoHelper = new CryptoHelper();
            Date now = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.MILLISECOND, 0); // Clear the millis part. Silly API.
            calendar.set(2010, 8, 14, 0, 0, 0); // Note that months are 0-based
            Date date = calendar.getTime();
            long millis = date.getTime(); // Millis since Unix epoch
            String UnencryptedToken = String.valueOf(millis) + "||" + UserName + "||" + EncryptedPassword + "||" + Key;



            return cryptoHelper.encrypt(UnencryptedToken).toString().replace('/','~').replace('+','`').replace('\n','|');
            //return Cryptography.Utilities.EncryptString(UnencryptedToken);
        }catch (Exception ex){

        }
        return "";
    }
}
