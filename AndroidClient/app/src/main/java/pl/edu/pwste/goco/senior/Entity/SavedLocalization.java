package pl.edu.pwste.goco.senior.Entity;

import java.io.Serializable;


public class SavedLocalization implements Serializable {
    public SavedLocalization() {
    }

    private int id;

    private String name;

    private double latitude;

    private double longitude;

    private Senior senior;

    private void setId(int value) {
        this.id = value;
    }


    public int getId() {
        return id;
    }


    public int getORMID() {
        return getId();
    }

    public void setName(String value) {
        this.name = value;
    }


    public String getName() {
        return name;
    }

    public void setLatitude(double value) {
        this.latitude = value;
    }


    public double getLatitude() {
        return latitude;
    }

    public void setLongitude(double value) {
        this.longitude = value;
    }


    public double getLongitude() {
        return longitude;
    }

    public void setSenior(Senior value) {
        this.senior = value;
    }


    public Senior getSenior() {
        return senior;
    }

    public String toString() {
        return name;
    }

}
