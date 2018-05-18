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
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AppPermStorage implements Observer {

    File reminders;
    File events;

    public AppPermStorage() {
        reminders = new File("reminders.txt");
        events = new File("events.txt");
        if (!reminders.exists()) {
            try {
                reminders.createNewFile();
            } catch (IOException ex) {
                System.out.println("Cannot create new file.");
            }
        }
        if (!events.exists()) {
            try {
                events.createNewFile();
            } catch (IOException ex) {
                System.out.println("Cannot create new file");
            }
        }
    }

    public boolean remindersContains(Reminder reminder) {
        try {
            FileReader fr = new FileReader(reminders);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                if (reminder.getEvent().equals(line)) {
                    return true;
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File does not exist.");
        } catch (IOException ex) {
            System.out.println("Cannot read file.");
        }
        return false;
    }

    public boolean eventsContains(CalendarEvent event) {
        try {
            FileReader fr = new FileReader(events);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                Scanner split = new Scanner(line);
                split.useDelimiter(";");
                if ((Integer.parseInt(split.next())) == event.getId()) {
                    return true;
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File does not exist.");
        } catch (IOException ex) {
            System.out.println("Cannot read file.");
        }
        return false;
    }

    public void addReminder(Reminder reminder) {
        try {
            FileWriter fw = new FileWriter(reminders);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(reminder.getEvent());
            pw.close();
            fw.close();
        } catch (IOException ex) {
            System.out.println("Cannot access file to write to.");
        }
    }

    public void removeReminder(Reminder reminder) {
        String id = reminder.getEvent();
        File reminders2 = new File("reminders2.txt");
        try {
            reminders2.createNewFile();
            FileWriter fw = new FileWriter(reminders2);
            PrintWriter pw = new PrintWriter(fw);
            FileReader fr = new FileReader(reminders);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.equals(id)) {
                    pw.println(line);
                }
            }
            pw.close();
            fw.close();
            reminders2.renameTo(reminders);
        } catch (FileNotFoundException ex) {
            System.out.println("Original file not found or cannot access new file to write to.");
        } catch (IOException ex) {
            System.out.println("Cannot read original file.");
        }
    }

    public void addEvent(CalendarEvent event) {
        try {
            FileWriter fw = new FileWriter(events);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(event.toStoreInFile());
            pw.close();
            fw.close();
        } catch (IOException ex) {
            System.out.println("Cannot access file to write to.");
        }
    }

    public void removeEvent(CalendarEvent event) {
        int id = event.getId();
        File events2 = new File("events2.txt");
        try {
            events2.createNewFile();
            FileWriter fw = new FileWriter(events2);
            PrintWriter pw = new PrintWriter(fw);
            FileReader fr = new FileReader(events);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                Scanner getId = new Scanner(line);
                getId.useDelimiter(";");
                int currentId = Integer.parseInt(getId.next());
                if (currentId != id) {
                    pw.println(line);
                }
            }
            pw.close();
            fw.close();
            events2.renameTo(events);
        } catch (FileNotFoundException ex) {
            System.out.println("Original file not found or cannot access new file to write to.");
        } catch (IOException ex) {
            System.out.println("Cannot read original file.");
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof Reminder) {
            if (remindersContains((Reminder) arg)) {
                removeReminder((Reminder) arg);
            } else {
                addReminder((Reminder) arg);
            }
        } else if (arg instanceof CalendarEvent) {
            if (eventsContains((CalendarEvent) arg)) {
                removeEvent((CalendarEvent) arg);
            } else {
                addEvent((CalendarEvent) arg);
            }
        }
    }

}
