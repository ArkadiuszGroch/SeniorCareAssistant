package pl.edu.pwste.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@org.hibernate.annotations.Proxy(lazy = false)
@Table(name = "CareAssistant")
public class CareAssistant implements Serializable {
	public CareAssistant() {
		this.setLastLogin(new Timestamp(System.currentTimeMillis()));
	}

	@Column(name = "Id", nullable = false, length = 10)
	@Id
	@GeneratedValue(generator = "CAREASSISTANT_ID_GENERATOR")
	@org.hibernate.annotations.GenericGenerator(name = "CAREASSISTANT_ID_GENERATOR", strategy = "native")
	private int id;

	@JsonIgnore
	@Column(name = "LastLogin", nullable = false)
	private java.sql.Timestamp lastLogin;

	@OneToOne(targetEntity = User.class, fetch = FetchType.LAZY)
	@org.hibernate.annotations.Cascade({ org.hibernate.annotations.CascadeType.SAVE_UPDATE,
			org.hibernate.annotations.CascadeType.LOCK })
	@JoinColumns({ @JoinColumn(name = "UserId", referencedColumnName = "Id", nullable = false) })
	private User user;

	@OneToMany(mappedBy = "careAssistant", targetEntity = Care.class)
	@org.hibernate.annotations.Cascade({ org.hibernate.annotations.CascadeType.SAVE_UPDATE,
			org.hibernate.annotations.CascadeType.LOCK })
	@org.hibernate.annotations.LazyCollection(org.hibernate.annotations.LazyCollectionOption.TRUE)
	private java.util.Set care = new java.util.HashSet();

	private void setId(int value) {
		this.id = value;
	}

	public int getId() {
		return id;
	}

	public int getORMID() {
		return getId();
	}

	public void setLastLogin(java.sql.Timestamp value) {
		this.lastLogin = value;
	}

	public java.sql.Timestamp getLastLogin() {
		return lastLogin;
	}

	public void setUser(User value) {
		this.user = value;
	}

	public User getUser() {
		return user;
	}

	public void setCare(java.util.Set value) {
		this.care = value;
	}

	public java.util.Set getCare() {
		return care;
	}

	public String toString() {
		return String.valueOf(getId());
	}

}