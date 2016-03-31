package com.example.niki.fieldoutlookandroid.helper;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Niki on 3/12/2015.
 */
public class AuthenticationResponse implements Parcelable {
    private String isAuthenticated;
    private String username;
    private String password;
    public AuthenticationResponse(){}

    public void SetIsAuthenticated(String value){
        this.isAuthenticated=value;
    }
    public void SetUsername(String value){this.username=value;}
    public void SetPassword(String value){this.password=value;}
    public String GetUsername(){
        return this.username;
    }
    public String GetPassword(){
        return this.password;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(password);
        dest.writeString(isAuthenticated);
    }
    public static  final Creator<AuthenticationResponse> CREATOR= new Creator<AuthenticationResponse>() {
        @Override
        public AuthenticationResponse createFromParcel(Parcel source) {
            AuthenticationResponse authenticationResponse= new AuthenticationResponse();
            authenticationResponse.username=source.readString();
            authenticationResponse.password=source.readString();
            authenticationResponse.isAuthenticated=source.readString();
            return authenticationResponse;
        }

        @Override
        public AuthenticationResponse[] newArray(int size) {
            return new AuthenticationResponse[0];
        }
    };
}
