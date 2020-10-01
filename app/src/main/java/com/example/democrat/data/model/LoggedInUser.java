package com.example.democrat.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import kotlin.jvm.internal.Intrinsics;

import org.json.JSONObject;

public class LoggedInUser implements Parcelable{
    int id = 0;
    String firstName = "";
    String lastName = "";
    String photo = "";
    Boolean deactivated = false;


    public LoggedInUser(Parcel parcel) {
        this.id = parcel.readInt();
        this.firstName = parcel.readString();
        this.lastName = parcel.readString();
        this.photo = parcel.readString();
        if(parcel.readByte() == 1){
            deactivated = true;
        }
        else{
            deactivated = false;
        }
    }

    public LoggedInUser(int id, String firstName, String lastName, String photo, Boolean deactivated) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.photo = photo;
        this.deactivated = deactivated;
    }

    public LoggedInUser(JSONObject jsonLoggedInUser) {
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(firstName);
        parcel.writeString(lastName);
        parcel.writeString(photo);
        if(deactivated == true){
            parcel.writeByte((byte) 1);
        }else {
            parcel.writeByte((byte) 0);
        }
    }

    public static final Parcelable.Creator<LoggedInUser> CREATOR = new Parcelable.Creator<LoggedInUser>() {
        @Override
        public LoggedInUser createFromParcel(Parcel in) {
            return new LoggedInUser(in);
        }

        @Override
        public LoggedInUser[] newArray(int size) {
            return new LoggedInUser[size];
        }
        public LoggedInUser parse(JSONObject json){
            Intrinsics.checkParameterIsNotNull(json, "json");
            int id = json.optInt("id", 0);
            Intrinsics.checkExpressionValueIsNotNull("first_name", "json.optString(\"first_name\", \"\")");
            String firstName = json.optString("first_name", "");
            Intrinsics.checkExpressionValueIsNotNull("last_name", "json.optString(\"last_name\", \"\")");
            String lastName = json.optString("last_name", "");
            Intrinsics.checkExpressionValueIsNotNull("photo", "json.optString(\"photo_200\", \"\")");
            String photo = json.optString("photo_200", "");
            return new LoggedInUser(id, firstName, lastName, photo, json.optBoolean("deactivated", false));
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    public String getDisplayName() {
        return "Типа имя";
    }
}