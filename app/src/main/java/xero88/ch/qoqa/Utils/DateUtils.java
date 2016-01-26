package xero88.ch.qoqa.Utils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Anthony on 23/01/2016.
 */
public class DateUtils {

    public static int SECONDS_IN_A_DAY = 24 * 60 * 60;

    public static String getCountdown(Date date) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        Calendar today = Calendar.getInstance();
        long diff =  cal.getTimeInMillis() - today.getTimeInMillis(); // TODO if the date is expired ?
        long diffSec = diff / 1000;

        long days = diffSec / SECONDS_IN_A_DAY;
        long secondsDay = diffSec % SECONDS_IN_A_DAY;
        long seconds = secondsDay % 60;
        long minutes = (secondsDay / 60) % 60;
        long hours = (secondsDay / 3600); // % 24 not needed

        if(days == 0)
            return hours + " hours, " + minutes + " minutes, " + seconds + " seconds"; // TODO Locale
        else if(days == 0 && hours == 0)
            return minutes + " minutes, " + seconds + " seconds"; // TODO Locale
        else if(days == 0 && hours == 0 && minutes == 0)
            return seconds + " seconds"; // TODO Locale
        else
            return days + " days, " + hours + " hours, " + minutes + " minutes"; // TODO Locale


    }

    public static long getMiliSecDiff(Date date) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        Calendar today = Calendar.getInstance();
        return cal.getTimeInMillis() - today.getTimeInMillis();

    }
}
