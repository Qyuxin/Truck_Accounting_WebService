package com.xin.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * Created by Y on 2018/2/28.
 */

public class DateUtils {
    private static int[] intsYMDHMS = new int[6];

    public static String getStringYMD(int[] ints) {
        return ints[0] + "-" + format2(ints[1]) + "-" + format2(ints[2]);
    }

    public static String getStringYMDHMS(int[] ints) {
        return getStringYMD(ints) + " " + format2(ints[3]) + ":" + format2(ints[4]) + ":" + format2(ints[5]);
    }

    public static String getStringYM(int[] ints) {
        return ints[0] + "-" + format2(ints[1]);
    }

    public static int[] getYMDHMS() {
        Calendar calendar = Calendar.getInstance();
        intsYMDHMS[0] = calendar.get(Calendar.YEAR);
        intsYMDHMS[1] = calendar.get(Calendar.MONTH) + 1;
        intsYMDHMS[2] = calendar.get(Calendar.DAY_OF_MONTH);
        intsYMDHMS[3] = calendar.get(Calendar.HOUR_OF_DAY);
        intsYMDHMS[4] = calendar.get(Calendar.MINUTE);
        intsYMDHMS[5] = calendar.get(Calendar.SECOND);
        return intsYMDHMS;
    }

    public static String format2(String str) {
        if (str.length() == 1) {
            return "0" + str;
        } else {
            return str;
        }
    }

    public static String format2(int date) {
        String str = date + "";
        return format2(str);
    }


    private static boolean isLeapYear(int year) {
        if (year % 100 == 0) {
            year /= 100;
        }
        return year % 4 == 0;
    }
}
