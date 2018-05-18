/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.util.Scanner;

public class CalendarEvent {

    private int id;
    private static int maxID = 1;
    
    private String event;
    private Date dateTime;
    private String location;
    
    public void giveId() {
        id = maxID;
        maxID ++;
    }
    
    public int getId() {
        return id;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    
    
    public CalendarEvent() {
        this("--", new Date(), "--");
    }

    public CalendarEvent(String event, Date dateTime, String location) {
        this.event = event;
        this.dateTime = dateTime;
        this.location = location;
        giveId();
    }
    
    public CalendarEvent(int id, String event, Date dateTime, String location) {
        this.id = id;
        this.event = event;
        this.dateTime = dateTime;
        this.location = location;
    }
   

    public String toString() {
        String displayEvent = ("Name: " + event + "| Date/Time: " + dateTime + " | Location: " + location);
        return displayEvent;
    }
    
    public static CalendarEvent readEvent(String line) {
        Scanner split = new Scanner(line);
        split.useDelimiter(";");
        int id = Integer.parseInt(split.next());
        if (id >= maxID) {
            maxID = id + 1;
        }
        String event = split.next();
        Date dateTime = Date.pullDate(split.next());
        String location = split.next();
        return new CalendarEvent(id, event, dateTime, location);
    }
    
    public String toStoreInFile() {
        return id + ";" + event + ";" + dateTime.dateToStore() + ";" + location;
    }


}
