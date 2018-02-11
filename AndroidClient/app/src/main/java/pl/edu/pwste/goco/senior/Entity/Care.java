package pl.edu.pwste.goco.senior.Entity;

import java.io.Serializable;

public class Care implements Serializable {

    public Care() {
    }

    private int id;

    private CareAssistant careAssistant;

    private Senior senior;

    private java.util.Set notification = new java.util.HashSet();

    private void setId(int value) {
        this.id = value;
    }

    public int getId() {
        return id;
    }

    public int getORMID() {
        return getId();
    }

    public void setCareAssistant(CareAssistant value) {
        this.careAssistant = value;
    }

    public CareAssistant getCareAssistant() {
        return careAssistant;
    }

    public void setSenior(Senior value) {
        this.senior = value;
    }

    public Senior getSenior() {
        return senior;
    }

    public void setNotification(java.util.Set value) {
        this.notification = value;
    }

    public java.util.Set getNotification() {
        return notification;
    }

    public String toString() {
        return String.valueOf(getId());
    }

}
