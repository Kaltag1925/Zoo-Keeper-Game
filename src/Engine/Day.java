package Engine;

import CustomMisc.IDayListener;

public class Day extends Engine {

    public static int day = 1;
    public static int month = 1;
    public static int year = 1970;
    public static final String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    public static final int[] monthDays = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    public static void setDay(int dayNew){
        day = dayNew;
    }

    public static void setMonth(int monthNew){
        month = monthNew;
    }

    public static void setYear(int yearNew){
        year = yearNew;
    }

    public static String getDate(){
        if(month < 10){
            if(day < 10){
                return "0" + month + "/0" + day + "/" + year;
            } else {
                return "0" + month + "/" + day + "/" + year;
            }
        } else {
            if(day < 10){
                return month + "/0" + day + "/" + year;
            } else {
                return month + "/" + day + "/" + year;
            }
        }
    }

    public static String getDateLong(){
        return monthNames[month - 1] + " " + day + ", " + year;
    }

    public static void advanceDay(int numDays){
        day += numDays;
        while (day > monthDays[month - 1]) {
            day -= monthDays[month - 1];
            month++;
            if(month > 12){
                month -= 12;
                year++;
            }
        }
        IDayListener.Event.newDayEvent(numDays);
    }

    public static String hypotheticalAdvanceDay(int numDays){
        int newDay = day;
        int newMonth = month;
        int newYear = year;
        newDay += numDays;
        while (newDay > monthDays[newMonth - 1]) {
            newDay -= monthDays[newMonth - 1];
            newMonth++;
            if(newMonth > 12){
                newMonth -= 12;
                newYear++;
            }
        }
        return monthNames[newMonth - 1] + " " + newDay + ", " + newYear;
    }
}
