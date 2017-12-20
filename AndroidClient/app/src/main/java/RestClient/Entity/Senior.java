package RestClient.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;


public class Senior {
    public Senior() {
        this.lastModification = new Timestamp(System.currentTimeMillis());
        this.lastSynchronization = new Timestamp(System.currentTimeMillis());
    }

    @JsonIgnore
    private int id;
    @JsonIgnore
    private Timestamp lastSynchronization;
    @JsonIgnore
    private Timestamp lastModification;
    @JsonIgnore
    private Time locationUpdateFrequency;

    private User user;
    @JsonIgnore
    private Set localization = new HashSet();
    @JsonIgnore
    private Set savedLocalization = new HashSet();
    @JsonIgnore
    private Set contacts = new HashSet();
    @JsonIgnore
    private Set care = new HashSet();
    @JsonIgnore
    private Set medicine = new HashSet();

    private void setId(int value) {
        this.id = value;
    }


    public int getId() {
        return id;
    }


    public void setLastSynchronization(Timestamp value) {
        this.lastSynchronization = value;
    }


    public Timestamp getLastSynchronization() {
        return lastSynchronization;
    }

    public void setLastModification(Timestamp value) {
        this.lastModification = value;
    }


    public Timestamp getLastModification() {
        return lastModification;
    }

    public void setLocationUpdateFrequency(Time value) {
        this.locationUpdateFrequency = value;
    }


    public Time getLocationUpdateFrequency() {
        return locationUpdateFrequency;
    }

    public void setUser(User value) {
        this.user = value;
    }


    public User getUser() {
        return user;
    }

    public void setLocalization(Set value) {
        this.localization = value;
    }


    public Set getLocalization() {
        return localization;
    }

    public void setSavedLocalization(Set value) {
        this.savedLocalization = value;
    }


    public Set getSavedLocalization() {
        return savedLocalization;
    }

    public void setContacts(Set value) {
        this.contacts = value;
    }


    public Set getContacts() {
        return contacts;
    }

    public void setCare(Set value) {
        this.care = value;
    }


    public Set getCare() {
        return care;
    }

    public void setMedicine(Set value) {
        this.medicine = value;
    }


    public Set getMedicine() {
        return medicine;
    }

    public String toString() {
        return getUser().getLogin() + "\n" + getUser().getPassword();
    }

}