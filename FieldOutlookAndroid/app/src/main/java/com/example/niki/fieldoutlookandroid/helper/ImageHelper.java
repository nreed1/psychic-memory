package com.example.niki.fieldoutlookandroid.helper;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URI;

/**
 * Created by Niki on 6/17/2016.
 */
public class ImageHelper {
    public String getImageToString(Context context, String imageLocation){
        try{
            File imageFile=new File(URI.create(imageLocation));

            FileInputStream fileInputStream =new FileInputStream(imageFile);
            Bitmap bitmap= BitmapFactory.decodeStream(fileInputStream);
           // ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
            //bitmap.compress(Bitmap.CompressFormat.JPEG,100, byteArrayOutputStream);
            //byte[] byteArray=byteArrayOutputStream.toByteArray();
            return  getStringFromBitmap(bitmap);


        }catch (Exception ex){
            ExceptionHelper.LogException(context,ex);
        }
        return "";
    }
    public byte[] getImageToBytes(String imageLocation){
        try{
            File imageFile=new File(URI.create(imageLocation));

            FileInputStream fileInputStream =new FileInputStream(imageFile);
            Bitmap bitmap= BitmapFactory.decodeStream(fileInputStream);
            fileInputStream.close();
            BitmapFactory.Options o = new BitmapFactory.Options();
            int IMAGE_MAX_SIZE=50;

            int scale = 1;
            if (o.outHeight > IMAGE_MAX_SIZE || o.outWidth > IMAGE_MAX_SIZE) {
                scale = (int)Math.pow(2, (int) Math.ceil(Math.log(IMAGE_MAX_SIZE /
                        (double) Math.max(o.outHeight, o.outWidth)) / Math.log(0.5)));
            }

            //Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            fileInputStream = new FileInputStream(imageFile);
            bitmap = BitmapFactory.decodeStream(fileInputStream, null, o2);
            fileInputStream.close();
            return getBytesFromBitmap(bitmap);
        }catch (Exception ex){
            //ExceptionHelper.LogException();
        }
        return new byte[0];
    }
    public ByteArrayOutputStream getStreamFromLocation(String imageLocation) throws FileNotFoundException {
        try {

            File imageFile = new File(URI.create(imageLocation));

            FileInputStream fileInputStream = new FileInputStream(imageFile);
            Bitmap bitmap = BitmapFactory.decodeStream(fileInputStream);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
            return stream;
        }catch (Exception ex){
            Log.d("ExceptionImageBuilder",ex.getMessage());
        }
        return null;
    }

    public byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        return stream.toByteArray();
    }
    public String bytesToString(byte[] byteArray){
        return Base64.encodeToString(byteArray, Base64.NO_WRAP);
    }

    public String getStringFromBitmap(Bitmap bitmap){
        return bytesToString(getBytesFromBitmap(bitmap));
    }

}
