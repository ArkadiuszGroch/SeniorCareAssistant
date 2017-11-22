package pl.edu.pwste.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Set;

@Entity
@org.hibernate.annotations.Proxy(lazy = false)
@Table(name = "User")
public class User {
    public User() {
    }

    @Column(name = "Id", nullable = false, length = 10)
    @Id
    @GeneratedValue(generator = "USER_ID_GENERATOR")
    @GenericGenerator(name = "USER_ID_GENERATOR", strategy = "native")
    private int id;

    @Column(name = "Login", unique = true, length = 50)
    @NotEmpty(message = "*Wprowadz login")
    private String login;

    @Column(name = "Password")
    @NotEmpty(message = "*Wprowadz haslo")
    @Length(min = 5, message = "*Twoje haslo musi miec wiecej niz 5 znakow")
    private String password;

    @NotEmpty(message = "*Wprowadz imie")
    @Column(name = "FirstName", length = 50)
    private String firstName;

    @NotEmpty(message = "*Wprowadz nazwisko")
    @Column(name = "LastName", length = 50)
    private String lastName;

    @NotEmpty(message = "*Wprowadz numer telefonu")
    @Length(min = 9, message = "*Numer jest zbyt krotki")
    @Column(name = "Phone", nullable = false, length = 12)
    private String phone;

    @Column(name = "email", unique = true)
    @Email(message = "*Wprowadz prawidlowy adres email")
    @NotEmpty(message = "*Wprowadz adres email")
    private String email;

    @Column(name = "SecurityString", nullable = false, length = 30, unique = true)
    private String securityString;

    @JsonIgnore
    @OneToOne(mappedBy = "user", targetEntity = Senior.class, fetch = FetchType.LAZY)
    @Cascade({CascadeType.SAVE_UPDATE, CascadeType.LOCK})
    private Senior senior;

    @JsonIgnore
    @OneToOne(mappedBy = "user", targetEntity = CareAssistant.class, fetch = FetchType.LAZY)
    @Cascade({CascadeType.SAVE_UPDATE, CascadeType.LOCK})
    private CareAssistant careAssistant;

    @ManyToMany(cascade = javax.persistence.CascadeType.ALL)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}