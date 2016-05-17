package com.example.niki.fieldoutlookandroid.helper;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.spec.KeySpec;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

import org.apache.commons.codec.binary.Base64;
/**
 * Created by Owner on 4/1/2016.
 */
public class CryptoHelper {
   // private static final String UNICODE_FORMAT = "UTF8";
    private String HashKey="1QAzxSW23EDcvFR45TGbnHY67UJmKI89OLP0PLmKO09IJnbHU87YGvcFT65RDxzSE43WAq21";
    String encryptionScheme="DESede";
    byte[] arrayBytes;
    KeySpec ks;
    private SecretKeyFactory skf;

    private static final String UNICODE_FORMAT = "UTF8";
    public static final String DESEDE_ENCRYPTION_SCHEME = "DESede";
    private KeySpec myKeySpec;
    private SecretKeyFactory mySecretKeyFactory;
    private Cipher cipher;
    byte[] keyAsBytes;
   // private String myEncryptionKey;
    private String myEncryptionScheme;
    SecretKey key;
    private byte[] _EncryptKey = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24};
    private byte[] _iv = {8,7,6,5,4,3,2,1};
    public CryptoHelper() throws Exception {
        //myEncryptionKey = "ThisIsSecretEncryptionKey";
//        myEncryptionScheme = DESEDE_ENCRYPTION_SCHEME;
//        keyAsBytes = _EncryptKey;//myEncryptionKey.getBytes(UNICODE_FORMAT);
//        myKeySpec = new DESedeKeySpec(_iv);//new DESedeKeySpec(keyAsBytes);
//        mySecretKeyFactory = SecretKeyFactory.getInstance(myEncryptionScheme);
//       // cipher = Cipher.getInstance(myEncryptionScheme);
//        key = mySecretKeyFactory.generateSecret(myKeySpec);
    }


    public String encrypt(String unencryptedString) {
try {
    if(unencryptedString==null) return "";
    byte[] plaintext = unencryptedString.getBytes();
    byte[] tdesKeyData = _EncryptKey;
    byte[] myIV = _iv;

    Cipher c3des = Cipher.getInstance("DESede/CBC/PKCS5Padding");
    SecretKeySpec myKey = new SecretKeySpec(tdesKeyData, "DESede");
    IvParameterSpec ivspec = new IvParameterSpec(myIV);
    c3des.init(Cipher.ENCRYPT_MODE, myKey, ivspec);
    byte[] cipherText = c3des.doFinal(plaintext);
    //sun.misc.BASE64Encoder obj64=new sun.misc.BASE64Encoder();
    return  android.util.Base64.encodeToString(cipherText,0);//new String(android.util.Base64.encode(cipherText,0));//obj64.encode(cipherText);
}catch (Exception ex) {
    System.out.print("oops");
}
        return null;

//        String encryptedString = null;
//        try {
//            cipher.init(Cipher.ENCRYPT_MODE, key);
//            byte[] plainText = unencryptedString.getBytes(UNICODE_FORMAT);
//            byte[] encryptedText = cipher.doFinal(plainText);
//            //BASE64Encoder base64encoder = new BASE64Encoder();
//            encryptedString = new String(android.util.Base64.encode(encryptedText,0));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return encryptedString;
    }
    /**
     * Method To Decrypt An Ecrypted String
     */
    public String decrypt(String encryptedString) {
        String decryptedText=null;
        try {
            cipher.init(Cipher.DECRYPT_MODE, key);
            //BASE64Decoder base64decoder = new BASE64Decoder();
            //byte[] encryptedText = base64decoder.decodeBuffer(encryptedString);
            //byte[] plainText = cipher.doFinal(encryptedText);
            //decryptedText= bytes2String(plainText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return decryptedText;
    }
    /**
     * Returns String From An Array Of Bytes
     */
    private static String bytes2String(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            stringBuffer.append((char) bytes[i]);
        }
        return stringBuffer.toString();
    }



}
