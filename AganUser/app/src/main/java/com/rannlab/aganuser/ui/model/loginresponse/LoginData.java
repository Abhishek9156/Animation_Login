package com.rannlab.aganuser.ui.model.loginresponse;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginData implements Parcelable {

    @SerializedName("uid")
    @Expose
    private String uid;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("mobileNo")
    @Expose
    private String mobileNo;

    @SerializedName("created_at")
    @Expose
    private String created_at;

    @SerializedName("updated_at")
    @Expose
    private String updated_at;

    public String getToken() {
        return token;
    }

    @SerializedName("token")
    @Expose
    private String token;


    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    protected LoginData(Parcel in) {
        uid = in.readString();
        name = in.readString();
        mobileNo = in.readString();
        created_at = in.readString();
        updated_at = in.readString();
        token = in.readString();

    }

    public static final Creator<LoginData> CREATOR = new Creator<LoginData>() {
        @Override
        public LoginData createFromParcel(Parcel in) {
            return new LoginData(in);
        }

        @Override
        public LoginData[] newArray(int size) {
            return new LoginData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uid);
        dest.writeString(name);
        dest.writeString(mobileNo);
        dest.writeString(created_at);
        dest.writeString(updated_at);
        dest.writeString(token);
    }
}
