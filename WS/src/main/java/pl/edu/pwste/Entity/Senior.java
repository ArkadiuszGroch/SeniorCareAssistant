package pl.edu.pwste.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@org.hibernate.annotations.Proxy(lazy = false)
@Table(name = "Senior")
public class Senior {
    public Senior() {
        this.lastModification = new Timestamp(System.currentTimeMillis());
        this.lastSynchronization = new Timestamp(System.currentTimeMillis());
        this.locationUpdateFrequency = new Time(2000);
    }

    @Column(name = "Id", nullable = false, length = 10)
    @Id
    @GeneratedValue(generator = "SENIOR_ID_GENERATOR")
    @org.hibernate.annotations.GenericGenerator(name = "SENIOR_ID_GENERATOR", strategy = "native")
    private int id;

    @Column(name = "LastSynchronization", nullable = false)
    private Timestamp lastSynchronization;

    @Column(name = "LastModification", nullable = false)
    private Timestamp lastModification;

    @Column(name = "LocationUpdateFrequency", nullable = false, length = 7)
    private Time locationUpdateFrequency;

    @OneToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE,
            org.hibernate.annotations.CascadeType.LOCK})
    @JoinColumns({@JoinColumn(name = "UserId", referencedColumnName = "Id", nullable = false)})
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "senior", targetEntity = Localization.class, fetch = FetchType.EAGER)
    @org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE,
            org.hibernate.annotations.CascadeType.LOCK})
    private Set localization = new HashSet();

    @JsonIgnore
    @OneToMany(mappedBy = "senior", targetEntity = SavedLocalization.class, fetch = FetchType.EAGER)
    @org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE,
            org.hibernate.annotations.CascadeType.LOCK})
    private Set savedLocalization = new HashSet();

    @JsonIgnore
    @OneToMany(mappedBy = "senior", targetEntity = Contact.class, fetch = FetchType.EAGER)
    @org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE,
            org.hibernate.annotations.CascadeType.LOCK})
    private Set contacts = new HashSet();

    @JsonIgnore
    @OneToMany(mappedBy = "senior", targetEntity = Care.class, fetch = FetchType.EAGER)
    @org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE,
            org.hibernate.annotations.CascadeType.LOCK})
    private Set care = new HashSet();

    @JsonIgnore
    @OneToMany(mappedBy = "senior", targetEntity = Medicine.class, fetch = FetchType.EAGER)
    @org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE,
            org.hibernate.annotations.CascadeType.LOCK})
    private Set medicine = new HashSet();

    @Column(name = "SafeDistance", nullable = false)
    private int safeDistance;

    public int getSafeDistance() {
        return safeDistance;
    }

    public void setSafeDistance(int safeDistance) {
        this.safeDistance = safeDistance;
    }


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
        return user.getFirstName() + user.getLastName();
    }

}