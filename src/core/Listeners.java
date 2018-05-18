/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ListModel;

public class Listeners {
    
    private JTextField entry;
    private Model model;
    
    public void setEntry(JTextField entry) {
        this.entry = entry;
    }
    
    public void setModel(Model model) {
        this.model = model;
    }
    
    public Listeners() {
        
    }
    
    public class ALEnter implements ActionListener {
        
        public ALEnter(JTextField entry) {
            setEntry(entry);
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            if (entry.getText().toLowerCase().startsWith("remind me to")) {
                model.addReminder(entry.getText());
            } else {
                model.addCalEvent(entry.getText());
            }
            entry.setText("");
        }
    }
    
    public class ALDelete implements MouseListener {
        
        private JList listToDelFrom;
        public static final int CALENDAR_LIST = 0;
        public static final int REMINDER_LIST = 1;
        
        public ALDelete(JList listToDelFrom) {
            this.listToDelFrom = listToDelFrom;
        }
        
        @Override
        public void mouseClicked(MouseEvent e) {
            if ((e.getButton() == MouseEvent.BUTTON1) && (e.getClickCount() == 2) && !listToDelFrom.isSelectionEmpty()) {
                try {
                    if (identifyListModel() == CALENDAR_LIST) {
                        model.delCalEvent((CalendarEvent) listToDelFrom.getSelectedValue());
                    } else if (identifyListModel() == REMINDER_LIST) {
                        model.delReminder((Reminder) listToDelFrom.getSelectedValue());
                    }
                    
                } catch (ClassCastException ex) {
                    System.out.println("Source is either not a JList or it does not have a DefaultListModel.");
                }
            }
        }
        
        private int identifyListModel() {
            if (((DefaultListModel) (listToDelFrom.getModel())).getElementAt(listToDelFrom.getSelectedIndex()) instanceof CalendarEvent) {
                return CALENDAR_LIST;
            } else if (((DefaultListModel) (listToDelFrom.getModel())).getElementAt(listToDelFrom.getSelectedIndex()) instanceof Reminder) {
                return REMINDER_LIST;
            }
            return -1;
        }
        
        @Override
        public void mousePressed(MouseEvent e) {
            //
        }
        
        @Override
        public void mouseReleased(MouseEvent e) {
            //
        }
        
        @Override
        public void mouseEntered(MouseEvent e) {
            //
        }
        
        @Override
        public void mouseExited(MouseEvent e) {
            //
        }
        
    }
    
}
