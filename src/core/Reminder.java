/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package core;

import java.util.Objects;


public class Reminder {
    
    private String event;
    
    public Reminder(String event) {
        this.event = event;
    }
    
    public String getEvent() {
        return event;
    }
    
    public String toString() {
        return event;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.event);
        return hash;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o instanceof Reminder) {
            if (event.equals(((Reminder) o).getEvent())) {
                return true;
            }
        }
        return false;
    }
    
}
