package com.rannlab.aganuser.ui.model.registerresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterResponse {
    @SerializedName("data")
    @Expose
    private String data;

    public String getData() {
        return data;
    }

    public boolean isStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    @SerializedName("status")
    @Expose
    private boolean status;

    @SerializedName("msg")
    @Expose
    private String msg;



}
