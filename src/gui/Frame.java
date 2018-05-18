/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import core.CalendarEvent;
import core.Listeners;
import core.Listeners.ALEnter;
import core.Model;
import core.Reminder;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

public class Frame extends JFrame implements Observer {

    private JTabbedPane selection;
    private JTextField entry;
    private JList calendar;
    private JList reminders;

    public Frame() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(600, 350));
        setMinimumSize(new Dimension(400,200));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        initWidgets();
        setVisible(true);
    }

    public void initWidgets() {
        selection = new JTabbedPane(JTabbedPane.TOP);
        calendar = new JList(new DefaultListModel<CalendarEvent>());
        reminders = new JList(new DefaultListModel<Reminder>());
        selection.addTab("Calendar", calendar);
        selection.addTab("Reminders", reminders);
        add(selection, BorderLayout.CENTER);
        entry = new JTextField();
        add(entry, BorderLayout.SOUTH);
        Listeners listeners = new Listeners();
        try {
            listeners.setModel(new Model(this));
        } catch (IOException ex) {
            System.out.println("Could not initialise Model.");
        }
        entry.addActionListener(listeners.new ALEnter(entry));
        calendar.addMouseListener(listeners.new ALDelete(calendar));
        reminders.addMouseListener(listeners.new ALDelete(reminders));
    }

    public static void main(String[] args) {
        Frame frame = new Frame();

    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof Reminder) {
            if (((DefaultListModel) (reminders.getModel())).contains(arg)) {
                ((DefaultListModel) (reminders.getModel())).removeElement(arg);
            } else {
                ((DefaultListModel) (reminders.getModel())).addElement(arg);
            }
        } else if (arg instanceof CalendarEvent) {
            if (((DefaultListModel) (calendar.getModel())).contains(arg)) {
            ((DefaultListModel) (calendar.getModel())).removeElement(arg);
            } else {
                ((DefaultListModel) (calendar.getModel())).addElement(arg);
            }
        } else if (arg instanceof Model) {
            DefaultListModel<CalendarEvent> calendarListModel = new DefaultListModel<> ();
            Object[] eventsArray = ((Model) arg).getEventsArray();
            for (int i=0; i<eventsArray.length; i++) {
                calendarListModel.addElement((CalendarEvent) eventsArray[i]);
            }
            calendar.setModel(calendarListModel);
            
            DefaultListModel<Reminder> reminderListModel = new DefaultListModel<> ();
            Object[] remindersArray = ((Model)arg).getRemindersArray();
            for (int i=0; i<remindersArray.length; i++) {
                reminderListModel.addElement((Reminder) remindersArray[i]);
            }
            reminders.setModel(reminderListModel);
        }
    }
}
