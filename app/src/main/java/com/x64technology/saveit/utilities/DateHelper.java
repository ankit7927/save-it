package com.x64technology.saveit.utilities;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateHelper {
    static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy HH/mm/ss", Locale.getDefault());
    public static String getDateTimeString() {
        return simpleDateFormat.format(Calendar.getInstance().getTime());
    }

    public static String calcTimeAgo(String date) {
        try {
            Date parse = simpleDateFormat.parse(date);
            PrettyTime prettyTime = new PrettyTime();
            return prettyTime.format(parse);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

}
