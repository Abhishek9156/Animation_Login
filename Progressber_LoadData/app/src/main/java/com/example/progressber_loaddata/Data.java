package com.example.progressber_loaddata;

import java.util.Date;

public class Data {
    String Status;
    String Time;
    String date;

    public String getDate() {
        return date;
    }

    public Data(String status, String time,String da) {
        Status = status;
        Time = time;
        date=da;

    }

    public String getStatus() {
        return Status;
    }

    public String getTime() {
        return Time;
    }
}
