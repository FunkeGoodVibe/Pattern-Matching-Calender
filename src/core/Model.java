/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Model extends Observable {
    
    private ArrayList<Reminder> reminders = new ArrayList<>();
    private ArrayList<CalendarEvent> events = new ArrayList<>();

    public Model() {
        File textToRead = new File("events.txt");
        if (textToRead.exists()) {
            FileReader fr = null;
            try {
                fr = new FileReader(textToRead);
                BufferedReader br = new BufferedReader(fr);
                String line;
                while ((line = br.readLine()) != null) {
                    addCalEvent(CalendarEvent.readEvent(line));
                }
            } catch (FileNotFoundException ex) {
                System.out.println("File doesn't exist.");
            } catch (IOException ex) {
                System.out.println("Cannot read from file.");
            }

        } else {
            try {
                textToRead.createNewFile();
            } catch (IOException ex) {
                System.out.println("Cannot create file.");
            }
        }
        //
        textToRead = new File("reminders.txt");
        if (textToRead.exists()) {
            FileReader fr = null;
            try {
                fr = new FileReader(textToRead);
                BufferedReader br = new BufferedReader(fr);
                String line;
                while ((line = br.readLine()) != null) {
                    addReminder(new Reminder(line));
                }
            } catch (FileNotFoundException ex) {
                System.out.println("File doesn't exist.");
            } catch (IOException ex) {
                System.out.println("Cannot read from file.");
            }

        } else {
            try {
                textToRead.createNewFile();
            } catch (IOException ex) {
                System.out.println("Cannot create file.");
            }
        }
       // addObserver(new AppPermStorage());
    }

    public Model(Observer o) throws IOException {
        this();
        addObserver(o);
        sendInitialState();
    }
    
    public Object[] getRemindersArray() {
        return reminders.toArray();
    }
    
    public Object[] getEventsArray() {
        return events.toArray();
    }

    public void addCalEvent(String entry) {
        String eventName = "";
        Date dateTime;
        String location = "";
        PatternMatch retrieveData = new PatternMatch();
        dateTime = retrieveData.getDate(entry);
        Scanner split = new Scanner(entry);
        while (split.hasNext()) {
            String next = split.next();
            if (next.toLowerCase().equals("on")
                    || next.toLowerCase().equals("at")
                    || next.toLowerCase().equals("in")) {
                break;
            }
            eventName += next + " ";
        }
        split = new Scanner(entry);
        while (split.hasNext()) {
            String next = split.next();
            if (next.toLowerCase().equals("at")) {
                String propObject = split.next();
                String firstChar = propObject.substring(0, 1);
                try {
                    Integer.parseInt(firstChar);
                } catch (NumberFormatException ex) {
                    location += propObject;
                }
            }
        }
        CalendarEvent newE = new CalendarEvent();
        if (!eventName.equals("")) {
            newE.setEvent(eventName);
        }
        newE.setDateTime(dateTime);
        if (!location.equals("")) {
            newE.setLocation(location);
        }
        events.add(newE);
        setChanged();
        notifyObservers(newE);
    }

    public void addCalEvent(CalendarEvent event) {
        events.add(event);
    }
    
    public void addReminder(Reminder reminder) {
        reminders.add(reminder);
    }

    public void delCalEvent(CalendarEvent tbr) {
        events.remove(tbr);
        setChanged();
        notifyObservers(tbr);
    }

    public void addReminder(String entry) {
        if (entry.toLowerCase().startsWith("remind me to")) {
            String chopped = entry.substring(13);
            Reminder newR = new Reminder(chopped);
            reminders.add(newR);
            setChanged();
            notifyObservers(newR);
        }
    }

    public void delReminder(Reminder tbr) {
        reminders.remove(tbr);
        setChanged();
        notifyObservers(tbr);
    }
    
    public void sendInitialState() {
        setChanged();
        notifyObservers(this);
    }

}
