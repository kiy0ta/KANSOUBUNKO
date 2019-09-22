package com.kansoubunko.kiyota.kansoubunko.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class KansouTimeUtils {

    public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat DATE_FORMAT_DATE = new SimpleDateFormat("yyyy-MM-dd");

    private KansouTimeUtils() {
        throw new AssertionError();
    }

    /**
     * long time to string
     *
     * @param timeInMillis
     * @param dateFormat
     * @return
     */
    public static String getTime(long timeInMillis, SimpleDateFormat dateFormat) {
        return dateFormat.format(new Date(timeInMillis));
    }

    /**
     * long time to string, format is {@link #DEFAULT_DATE_FORMAT}
     *
     * @param timeInMillis
     * @return
     */
    public static String getTime(long timeInMillis) {
        return getTime(timeInMillis, DEFAULT_DATE_FORMAT);
    }

    /**
     * get current time in milliseconds
     *
     * @return
     */
    public static long getCurrentTimeInLong() {
        return System.currentTimeMillis();
    }

    /**
     * get timeString 2 timeLong
     *
     * @param TargetTime
     * @return
     */
    public static long getTimeString2TimeLong(String TargetTime) {
        try {
            return DEFAULT_DATE_FORMAT.parse(TargetTime).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * UTC 2 Local
     */
    public static String utc2Local(String utcTime) {
        SimpleDateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date gpsUTCDate = null;
        try {
            gpsUTCDate = utcFormat.parse(utcTime.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat localFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        localFormat.setTimeZone(TimeZone.getDefault());
        String localTime = localFormat.format(gpsUTCDate.getTime());
        return localTime;
    }

    /**
     * get current time in milliseconds, format is {@link #DEFAULT_DATE_FORMAT}
     *
     * @return
     */
    public static String getCurrentTimeInString() {
        return getTime(getCurrentTimeInLong());
    }

    /**
     * get current time in milliseconds
     *
     * @return
     */
    public static String getCurrentTimeInString(SimpleDateFormat dateFormat) {
        return getTime(getCurrentTimeInLong(), dateFormat);
    }

    /**
     * current time      message time
     */
//    public static String calculateTime(Context context, String mTime){
//        long currentTime = getCurrentTimeInLong();
//        try {
//            long mTimes = DEFAULT_DATE_FORMAT.parse(mTime).getTime();
//            long timeInterval = (currentTime - mTimes)/1000;
//            long temp = 0;
//            if(timeInterval<60){
//                return context.getResources().getString(R.string.time_just);
//            }else if((temp = timeInterval/60)<60){
//                return temp+context.getResources().getString(R.string.time_minute_ago);
//            }else if((temp = temp / 60)<24){
//                return temp+context.getResources().getString(R.string.time_hour_ago);
//            }else if((temp = temp / 24)<30){
//                return temp+context.getResources().getString(R.string.time_day_ago);
//            }else if((temp = temp / 30)<12){
//                return temp+context.getResources().getString(R.string.time_month_ago);
//            }else{
//                temp = temp/12;
//                return temp+context.getResources().getString(R.string.time_year_ago);
//            }
//        } catch (ParseException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return "";
//    }
    public static long getLongTime(String date) {
        try {
            return DEFAULT_DATE_FORMAT.parse(date).getTime();
        } catch (ParseException e) {
            return 0;
        }
    }
}
