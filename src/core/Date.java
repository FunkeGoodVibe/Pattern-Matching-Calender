/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.util.Calendar;
import java.util.Scanner;

public class Date {

    private String day;
    private int date;
    private String month;
    private int year;
    private int hour; //24 h
    private int minute;

    private int offset; //only changed temporarily when working out "on"/"next" type entries

    public Date() {
        initDefaultValues();
    }
    
    public Date(int hour, int minute, String day, int date, String month, int year) {
        this.hour = hour;
        this.minute = minute;
        this.day = day;
        this.date = date;
        this.month = month;
        this.year = year;
    }

    public void initDefaultValues() {
        day = "none";
        date = 0;
        month = "none";
        year = -1;
        offset = 0;
        hour = -1;
        minute = 0;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setDate(int date) {
        if (date % 31 == 0) {
            this.date = 31;
        } else {
            this.date = (date % 31);
        }
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setMonth(int month) {
        if (month % 12 == 0) {
            setMonth("December");
        } else {
            setMonth(monthNum2String(month % 12));
        }
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDay() {
        return day;
    }

    public int getDate() {
        return date;
    }

    public String getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }
    
    public int getHour() {
        return hour;
    }
    
    public int getMinute() {
        return minute;
    }
    
    public void setHour(int hour) {
        if (hour<0 || hour > 24) {
            this.hour = -1;
        } else if (hour==24) {
            this.hour = 0;
        } else {
            this.hour = hour;
        }
    }
    
    public void setMinute(int minute) {
        this.minute = minute;
    }

    public void format() {
        formatDay();
        formatMonth();
    }

    public void formatDay() {
        if (day.toLowerCase().contains("sun") && !day.toLowerCase().contains("sunday")) {
            day = "sunday";
        } else if (day.toLowerCase().contains("mon") && !day.toLowerCase().contains("monday")) {
            day = "monday";
        } else if (day.toLowerCase().contains("tue") && !day.toLowerCase().contains("tuesday")) {
            day = "tuesday";
        } else if (day.toLowerCase().contains("wed") && !day.toLowerCase().contains("wednesday")) {
            day = "wednesday";
        } else if (day.toLowerCase().contains("thu") && !day.toLowerCase().contains("thursday")) {
            day = "thursday";
        } else if (day.toLowerCase().contains("fri") && !day.toLowerCase().contains("friday")) {
            day = "friday";
        } else if (day.toLowerCase().contains("sat") && !day.toLowerCase().contains("saturday")) {
            day = "saturday";
        }
        day = day.substring(0, 1).toUpperCase() + day.substring(1);
    }

    public void formatMonth() {
        if (month.toLowerCase().contains("jan") && !month.toLowerCase().contains("january")) {
            month = "january";
        } else if (month.toLowerCase().contains("feb") && !month.toLowerCase().contains("february")) {
            month = "february";
        } else if (month.toLowerCase().contains("mar") && !month.toLowerCase().contains("march")) {
            month = "march";
        } else if (month.toLowerCase().contains("apr") && !month.toLowerCase().contains("april")) {
            month = "april";
        } else if (month.toLowerCase().contains("jun") && !month.toLowerCase().contains("june")) {
            month = "june";
        } else if (month.toLowerCase().contains("jul") && !month.toLowerCase().contains("july")) {
            month = "july";
        } else if (month.toLowerCase().contains("aug") && !month.toLowerCase().contains("august")) {
            month = "august";
        } else if (month.toLowerCase().contains("sep") && !month.toLowerCase().contains("september")) {
            month = "september";
        } else if (month.toLowerCase().contains("oct") && !month.toLowerCase().contains("october")) {
            month = "october";
        } else if (month.toLowerCase().contains("nov") && !month.toLowerCase().contains("november")) {
            month = "november";
        } else if (month.toLowerCase().contains("dec") && !month.toLowerCase().contains("december")) {
            month = "december";
        }
        month = month.substring(0, 1).toUpperCase() + month.substring(1);
    }

    public String monthNum2String(int month) {
        switch (month) {
            case 1:
                return "January";
            case 2:
                return "February";
            case 3:
                return "March";
            case 4:
                return "April";
            case 5:
                return "May";
            case 6:
                return "June";
            case 7:
                return "July";
            case 8:
                return "August";
            case 9:
                return "September";
            case 10:
                return "October";
            case 11:
                return "November";
            case 12:
                return "December";
            default:
                return "none";
        }
    }

    @Override
    public String toString() {
        String displayNonDefault = "";
        if (day.toLowerCase().equals("none")) {
            displayNonDefault += "-- ";
        } else {
            displayNonDefault += day + " ";
        }
        if (date == 0) {
            displayNonDefault += "-- ";
        } else {
            displayNonDefault += (date + " ");
        }
        if (month.toLowerCase().equals("none")) {
            displayNonDefault += "-- ";
        } else {
            displayNonDefault += month + " ";
        }
        if (year == -1) {
            displayNonDefault += "--";
        } else {
            displayNonDefault += year;
        }
        displayNonDefault += ", ";
        if (hour == -1) {
            displayNonDefault += "--:--";
        } else {
            displayNonDefault += (hour + ":");
            if (minute <10) {
                displayNonDefault +=("0");
            }
            displayNonDefault += minute;
        }
        return displayNonDefault;
    }

    public int onDay(String day) {
        Calendar todaysDate = Calendar.getInstance();
        int offset = 0;
        int newDate = 0;
        if (day.toLowerCase().contains("sat")) {
            offset = Calendar.SATURDAY - todaysDate.get(Calendar.DAY_OF_WEEK);
        } else if (day.toLowerCase().contains("sun")) {
            offset = Calendar.SUNDAY - todaysDate.get(Calendar.DAY_OF_WEEK);
        } else if (day.toLowerCase().contains("mon")) {
            offset = Calendar.MONDAY - todaysDate.get(Calendar.DAY_OF_WEEK);
        } else if (day.toLowerCase().contains("tue")) {
            offset = Calendar.TUESDAY - todaysDate.get(Calendar.DAY_OF_WEEK);
        } else if (day.toLowerCase().contains("wed")) {
            offset = Calendar.WEDNESDAY - todaysDate.get(Calendar.DAY_OF_WEEK);
        } else if (day.toLowerCase().contains("thu")) {
            offset = Calendar.THURSDAY - todaysDate.get(Calendar.DAY_OF_WEEK);
        } else if (day.toLowerCase().contains("fri")) {
            offset = Calendar.FRIDAY - todaysDate.get(Calendar.DAY_OF_WEEK);
        }
        if (offset <= 0) {
            offset += 7;
        }
        if ((offset + todaysDate.get(Calendar.DAY_OF_MONTH)) > getMonthLength(monthToNum(month))) {
            newDate = offset + todaysDate.get(Calendar.DAY_OF_MONTH) - getMonthLength(monthToNum(month));
            if (todaysDate.get(Calendar.MONTH) + 2 > 12) {
                setMonth("January");
            } else {
                setMonth(monthToNum(month) + 1);
            }

        } else {
            newDate = offset + todaysDate.get(Calendar.DAY_OF_MONTH);
        }
        todaysDate.set(Calendar.DAY_OF_MONTH, newDate);
        return todaysDate.get(Calendar.DAY_OF_MONTH);
    }

    public int nextDay(String day) {
        if (onDay(day) + 7 > getMonthLength(monthToNum(month))) {
            int newDate = 7 + onDay(day) - getMonthLength(monthToNum(month));
            Calendar todaysDate = Calendar.getInstance();
            if (todaysDate.get(Calendar.MONTH) + 2 > 12) {
                setMonth("January");
            } else {
                setMonth(monthToNum(month) + 1);
            }
            return newDate;
        }
        return onDay(day) + 7;
    }

    public static int getMonthLength(int month) {
        switch (month) {
            case 1:
                return 31;
            case 2:
                int year = Calendar.getInstance().get(Calendar.YEAR);
                boolean leapyear = false;
                if (year % 4 == 0) {
                    if (year % 100 == 0) {
                        if (year % 400 == 0) {
                            leapyear = true;
                        }
                    } else {
                        leapyear = true;
                    }
                }
                if (leapyear) {
                    return 29;
                }
                return 28;
            case 3:
                return 31;
            case 4:
                return 30;
            case 5:
                return 31;
            case 6:
                return 30;
            case 7:
                return 31;
            case 8:
                return 31;
            case 9:
                return 30;
            case 10:
                return 31;
            case 11:
                return 30;
            case 12:
                return 31;
            default:
                return 31;
        }
    }

    public static int monthToNum(String month) {
        String lcMonth = month.toLowerCase();
        if (lcMonth.startsWith("jan")) {
            return 1;
        } else if (lcMonth.startsWith("feb")) {
            return 2;
        } else if (lcMonth.startsWith("mar")) {
            return 3;
        } else if (lcMonth.startsWith("apr")) {
            return 4;
        } else if (lcMonth.startsWith("may")) {
            return 5;
        } else if (lcMonth.startsWith("jun")) {
            return 6;
        } else if (lcMonth.startsWith("jul")) {
            return 7;
        } else if (lcMonth.startsWith("aug")) {
            return 8;
        } else if (lcMonth.startsWith("sep")) {
            return 9;
        } else if (lcMonth.startsWith("oct")) {
            return 10;
        } else if (lcMonth.startsWith("nov")) {
            return 11;
        } else if (lcMonth.startsWith("dec")) {
            return 12;
        }
        return 0;
    }
    
    public static Date pullDate(String dateText) {
        Scanner split = new Scanner(dateText);
        split.useDelimiter("/");
        int hour = Integer.parseInt(split.next());
        int minute = Integer.parseInt(split.next());
        String day = split.next();
        int date = Integer.parseInt(split.next());
        String month = split.next();
        int year = Integer.parseInt(split.next());
        return new Date(hour, minute, day, date, month, year);
    }
    
    public String dateToStore() {
        return (hour + "/" + minute + "/" + day + "/" + date + "/" + month + "/" + year);
    }

}
