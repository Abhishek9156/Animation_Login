package com.rannlab.aganuser.ui.utill;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {
    public static final String ORDER_DATE_PATTERN = "dd-MMM-yyyy";
    public static final String SERVER_REQUEST_DATE_FORMAT = "yyyy-MM-dd";
    public static final String BUSINESS_REPORT_DATE_FORMAT = "EEE MMM dd";
    public static final String ORDER_DATE_FORMAT = "EEE dd MMMM";

    public static String getLocalToUTCDate(Date date, String outputFormat) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));

        Date time = calendar.getTime();
        SimpleDateFormat outputFmt = new SimpleDateFormat(outputFormat);
        outputFmt.setTimeZone(TimeZone.getTimeZone("UTC"));
        return outputFmt.format(time);
    }

    public static String getFormattedDate(long milliseconds, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        return formatter.format(calendar.getTime());
    }

    public static String formatDateTime(String actualTime, String inputFormat,
                                        String outputFormat) {
        String formattedDate = null;
        try {
            DateFormat fromFormat = new SimpleDateFormat(inputFormat);

            DateFormat toFormat = new SimpleDateFormat(outputFormat);

            Date date = fromFormat.parse(actualTime);
            formattedDate = toFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return formattedDate;
    }
}

