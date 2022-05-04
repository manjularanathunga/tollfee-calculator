package com.smr.service.year.impl;

import com.smr.service.year.DateOnYear;

import java.util.Calendar;

public class Y2013 implements DateOnYear {
    @Override
    public Boolean isTollFreeDate(int year, int month, int day, int dayOfWeek) {
        return month == Calendar.JANUARY && day == 1 ||
                month == Calendar.MARCH && (day == 28 || day == 29) ||
                month == Calendar.APRIL && (day == 1 || day == 30) ||
                month == Calendar.MAY && (day == 1 || day == 8 || day == 9) ||
                month == Calendar.JUNE && (day == 5 || day == 6 || day == 21) ||
                month == Calendar.JULY ||
                month == Calendar.NOVEMBER && day == 1 ||
                month == Calendar.DECEMBER && (day == 24 || day == 25 || day == 26 || day == 31);
    }
}
