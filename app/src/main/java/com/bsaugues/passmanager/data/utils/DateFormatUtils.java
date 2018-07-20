package com.bsaugues.passmanager.data.utils;

import android.annotation.SuppressLint;

import com.bsaugues.passmanager.data.exception.DateParseException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateFormatUtils {
    private static final String GMT_TIMEZONE = "GMT";
    private static final String JSON_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    private static final String DAY_DATE_FORMAT = "E dd/MM/yyyy";
    private static final String HOUR_DATE_FORMAT = "HH:mm";

    private DateFormatUtils() {
    }

    /*
    The TimeZone of a DateFormat tells what TimeZone the STRING representation of the Date has.
    The Date object doesn't keep track of any data about TimeZone - It is just a long (a timestamp).

    The string that we can see in debugging just concatenates the phone's timezone to the converted timestamp. Date has NO DATA ABOUT ANY TIMEZONE.
    This means that you cannot match the time we see with the timezone we see. In String representation of Date, these two data ARE COMPLETELY UNCORRELATED.

    The time we see in debugging before the timezone is simply the converted value of the "milliseconds" attribute in Date object.

    In DateFormat object, we can set the TimeZone to tell the formatter to convert our Date object from our current TimeZone to the given TimeZone.

    Conclusion :
        dateFormat.parse(String) -> Converts the String considering it in dateFormat.getTimeZone()
        dateFormat.format(Date) -> Converts the Date considering its timestamp in getCurrentTimeZone() to dateFormat.getTimeZone()

    Example :

    We have a String object which represents a Date : 30/11/2017 14:49:24. We set the TimeZone of the DateFormat to GMT. This means, as stated in the first line of this comment,
    that the formatter will consider this String as a GMT time.
    Our current TimeZone is GMT+1, so when we call dateFormat.parse(String), we will get a Date with a timestamp of 1512053364, which means 30/11/2017 15:49:24.

    We have a Date object with 1512049764000, which is 30/11/2017 14:49:24. We set the TimeZone of the formatter to GMT. This means, as stated in the first line of this comment,
    that the formatter will convert the String result in GMT.
    Our current TimeZone is GMT+1, so when we call dateFormat.format(Date), we will get a this String : 30/11/2017 13:49:24.
     */

    @SuppressLint("SimpleDateFormat")
    //This method is used from Entity to Remote
    public static String getGMTStringFromCurrentTimeZoneDate(Date date) {
        DateFormat dateFormat = new SimpleDateFormat(JSON_DATE_FORMAT);
        /* Every Date which is converted in this utils class gets the current timezone offset in its timestamp,
         so the resulting String needs to be converted back to GMT
          */
        dateFormat.setTimeZone(TimeZone.getTimeZone(GMT_TIMEZONE));
        return dateFormat.format(date);
    }

    @SuppressLint("SimpleDateFormat")
    //This method is used from Remote to Entity
    public static Date getCurrentTimeZoneDateFromGMTString(String dateString) {
        Date date;
        DateFormat dateFormat = new SimpleDateFormat(JSON_DATE_FORMAT);
        /*Here we set the TimeZone because the resulting Date must have the corresponding timestamp with our current timezone offset in the database,
        because our operations are made with Dates get automatically with things like new Date, which will ALL have our current timezone offset*/
        dateFormat.setTimeZone(TimeZone.getTimeZone(GMT_TIMEZONE));
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            throw new DateParseException();
        }
        return date;
    }

    @SuppressLint("SimpleDateFormat")
    //This method is used from Entity to DBEntity
    public static String convertDateToString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat(JSON_DATE_FORMAT);
        /*The given date is just transformed to String object
          The time displayed in the string will match exactly the milliseconds in the Date object starting from 1st january 1970
          */
        return dateFormat.format(date);
    }

    @SuppressLint("SimpleDateFormat")
    public static String getDayFromDateString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat(DAY_DATE_FORMAT);
        return dateFormat.format(date);
    }

    @SuppressLint("SimpleDateFormat")
    public static String getHourFromDateString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat(HOUR_DATE_FORMAT);
        return dateFormat.format(date);
    }

    @SuppressLint("SimpleDateFormat")
    //This method is used from DBEntity to Entity
    public static Date convertStringToDate(String dateString) {
        Date date;
        DateFormat dateFormat = new SimpleDateFormat(JSON_DATE_FORMAT);
        /*
        The given String is just parsed into a Date object
        The milliseconds in the Date object will match exactly the number of milliseconds elapsed from 1st january 1970 to the time specified in the string
        */
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            throw new DateParseException();
        }
        return date;
    }

    // This method is used when we need to send current Date to Remote, because we send everything in GMT
    @SuppressLint("SimpleDateFormat")
    public static String getCurrentGMTDateString() {
        DateFormat dateFormat = new SimpleDateFormat(JSON_DATE_FORMAT);
        dateFormat.setTimeZone(TimeZone.getTimeZone(GMT_TIMEZONE));
        return dateFormat.format(new Date());
    }
}
