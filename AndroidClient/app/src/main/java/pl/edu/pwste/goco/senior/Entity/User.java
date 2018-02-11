package pl.edu.pwste.goco.senior.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class User {
    public User() {
    }

    @JsonIgnore
    private int id;

    private String login;

    private String password;
    private String firstName;

    private String lastName;
    private String phone;
    private String email;
    @JsonIgnore
    private String securityString;
    @JsonIgnore
    private Senior senior;
    @JsonIgnore
    private CareAssistant careAssistant;

    private void setId(int value) {
        this.id = value;
    }

    public int getId() {
        return id;
    }

    public String getSecurityString() {
        return securityString;
    }

    public void setSecurityString(String securityString) {
        this.securityString = securityString;
    }

    public void setLogin(String value) {
        this.login = value;
    }

    public String getLogin() {
        return login;
    }

    public void setPassword(String value) {
        this.password = value;
    }

    public String getPassword() {
        return password;
    }

    public void setFirstName(String value) {
        this.firstName = value;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String value) {
        this.lastName = value;
    }

    public String getLastName() {
        return lastName;
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

    public void setCareAssistant(CareAssistant value) {
        this.careAssistant = value;
    }

    public CareAssistant getCareAssistant() {
        return careAssistant;
    }

    public String toString() {
        return String.valueOf(getId());
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}