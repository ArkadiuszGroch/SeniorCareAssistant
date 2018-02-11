package pl.edu.pwste.goco.senior.Entity;

import java.io.Serializable;
import java.sql.Timestamp;


public class CareAssistant implements Serializable {
	public CareAssistant() {
		this.setLastLogin(new Timestamp(System.currentTimeMillis()));
	}
	private int id;

	private Timestamp lastLogin;
	private User user;

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

	public void setLastLogin(Timestamp value) {
		this.lastLogin = value;
	}

	public Timestamp getLastLogin() {
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