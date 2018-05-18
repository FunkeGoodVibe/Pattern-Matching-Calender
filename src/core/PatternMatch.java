package core;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternMatch {

    private String[] datePattern;
    private String[] timePattern;

    private String timePattern1;
    private String timePattern2;
    private String timePattern3;
    private String timePattern4;
    private String timePatternAllowed;

    private Pattern currentSearchPattern;

    private String regexOR;

    public PatternMatch() {
        assignRegex();

    }

    public void assignRegex() {

        regexOR = ("|");

        datePattern = new String[4];

        //group(1) = day of week, group(4) = date, group(9) = month, group(10) = year
        datePattern[0] = "((mon|tue|tues|wed|wednes|thu|thur|thurs|fri|sat|satur|sun)(day)?)\\s(([0]?[1-9])|([12]\\d)|([3][01]))(st|nd|rd|th)?\\s(jan|january|feb|february|mar|march|apr|april|may|june|june|july|aug|august|sep|september|oct|october|nov|november|dec|december)\\s?([12]\\d\\d\\d)?";
        datePattern[1] = "((([012]?(\\d))|(3[01]))(\\.|\\/)(0?[1-9]|([1][012]))(\\.|\\/)((([12]\\d)?\\d\\d)))";  //or (\d{1,2}/){2}\d{1,4});
        datePattern[2] = "((on)\\s((mon|tue|tues|wed|wednes|thu|thur|thurs|fri|sat|satur|sun)(day)?))";
        datePattern[3] = "((next)\\s((mon|tue|tues|wed|wednes|thu|thur|thurs|fri|sat|satur|sun)(day)?))";

        timePattern = new String[5];

        timePattern[0] = "((1[012])|(0?\\d))((\\.|\\:)([0-5]\\d))?.*(morning)";
        timePattern[1] = "((0?\\d)|(1[012]))((\\.|\\:)([0-5]\\d))?.*(evening)";
        timePattern[2] = "((([01]\\d)|([2][0123])|\\d))((\\.|\\:)([0-5]\\d))?(\\s)?([ap]m)?";
        timePattern[3] = "evening";
        timePattern[4] = "morning";
    }

    public Date getDate(String entry) {
        String dateEntered = "";
        boolean found;
        Date eventDate = new Date();
        for (int i = 0; i < datePattern.length; i++) {
            currentSearchPattern = Pattern.compile(datePattern[i]);
            Matcher matchPattern = currentSearchPattern.matcher(entry.toLowerCase());
            found = matchPattern.find();
            if (found) {
                parseDate(eventDate, i, matchPattern);
                break;
            }
        }
        for (int i = 0; i < timePattern.length; i++) {
            currentSearchPattern = Pattern.compile(timePattern[i]);
            Matcher matchPattern = currentSearchPattern.matcher(entry.toLowerCase());
            found = matchPattern.find();
            if (found) {
                parseTime(eventDate, i, matchPattern);
                break; //to be removed 
            }

        }
        eventDate.format();
        return eventDate;
    }

    public void parseDate(Date eventDate, int patternIndex, Matcher matchPattern) throws NumberFormatException {
        if (patternIndex == 0) {
            eventDate.setDay(matchPattern.group(1));
            eventDate.setMonth(matchPattern.group(9));
            try {
                eventDate.setDate(Integer.parseInt(matchPattern.group(4)));
            } catch (NumberFormatException ex) {
                //specifically want nothing to happen for no present int value
            }
            try {
                eventDate.setYear(Integer.parseInt(matchPattern.group(10)));
            } catch (NumberFormatException ex) {
                //separate try/catch block so that one exception doesn't prevent reading the other value
            }
        } else if (patternIndex == 1) {
            //eventDate.setDay(Integer.parseInt(matchPattern.group(2)));
            eventDate.setMonth(Integer.parseInt(matchPattern.group(7)));
            try {
                eventDate.setDate(Integer.parseInt(matchPattern.group(2)));
            } catch (NumberFormatException ex) {
                //nothing
            }
            try {
                eventDate.setYear(Integer.parseInt(matchPattern.group(10)));
            } catch (NumberFormatException ex) {
                //nothing
            }
        } else if (patternIndex == 2) {
            eventDate.setDay(matchPattern.group(3));
            eventDate.setDate(eventDate.onDay(matchPattern.group(3)));
            eventDate.setMonth(Calendar.getInstance().get(Calendar.MONTH) + 1);
            eventDate.setYear(Calendar.getInstance().get(Calendar.YEAR));

        } else if (patternIndex == 3) {
            eventDate.setYear(Calendar.getInstance().get(Calendar.YEAR));
            eventDate.setMonth(Calendar.getInstance().get(Calendar.MONTH) + 1);
            eventDate.setDate(7 + eventDate.onDay(matchPattern.group(3)));
            eventDate.setDay(matchPattern.group(3));
        }
    }

    public void parseTime(Date eventDate, int patternIndex, Matcher matchPattern) {
        if (patternIndex == 0) {
            int hour = Integer.parseInt(matchPattern.group(1)); //input will be an int else no match found
            if (hour >= 12) {
                hour -= 12;
            }
            eventDate.setHour(hour);
            if (matchPattern.group(4) != null) {
                eventDate.setMinute(Integer.parseInt(matchPattern.group(6)));
            }
        } else if (patternIndex == 1) {
            int hour = Integer.parseInt(matchPattern.group(1)); //input will be an int else no match found
            if (hour < 12) {
                hour += 12;
            }
            eventDate.setHour(hour);
            if (matchPattern.group(4) != null) {
                eventDate.setMinute(Integer.parseInt(matchPattern.group(6)));
            }
        } else if (patternIndex == 2) {
            int hour = Integer.parseInt(matchPattern.group(1)); //input will definitely  be an int? else no match found
            String ampm = matchPattern.group(9);
            if (ampm != null) {
                if (ampm.equals("pm") && hour < 12 && hour >= 0) {
                    hour = hour + 12;
                } else if (ampm.equals("am") && hour >= 12 && hour <24) { //user error, am/pm specification to override
                    hour = hour - 12;
                }
            }
            eventDate.setHour(hour);
            if (matchPattern.group(5) != null) {
                eventDate.setMinute(Integer.parseInt(matchPattern.group(7)));
            }
        } else if (patternIndex == 3) {
            eventDate.setHour(22);
        } else if (patternIndex == 4) {
            eventDate.setHour(9);
        }
    }

    public static void main(String args[]) {

        PatternMatch r = new PatternMatch();
        System.out.println(r.getDate("eat food next wednesday at 9:00 in the evening"));

    }
}
