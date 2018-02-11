package pl.edu.pwste.goco.senior.Entity;

import java.io.Serializable;

public class Contact implements Serializable {
    public Contact() {
    }

    private int id;

    private String name;

    private String phone;

    private Senior senior;

    private void setId(int value) {
        this.id = value;
    }


    public int getId() {
        return id;
    }


    public void setName(String value) {
        this.name = value;
    }

    public String getName() {
        return name;
    }

    public void setPhone(String value) {
        this.phone = value;
    }


    public String getPhone() {
        return phone;
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
