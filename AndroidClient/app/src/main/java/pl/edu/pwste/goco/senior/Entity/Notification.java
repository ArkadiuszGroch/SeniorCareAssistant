package pl.edu.pwste.goco.senior.Entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class Notification implements Serializable {
	public Notification() {
		setTime(new Timestamp(System.currentTimeMillis()));
	}

	private int id;
	private String name;

	private String content;

	private String status;

	private Timestamp time;

	private Care care;

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

	public void setContent(String value) {
		this.content = value;
	}

	 
	public String getContent() {
		return content;
	}

	public void setStatus(String value) {
		this.status = value;
	}

	 
	public String getStatus() {
		return status;
	}

	public void setTime(Timestamp value) {
		this.time = value;
	}

	 
	public Timestamp getTime() {
		return time;
	}

	public void setCare(Care value) {
		this.care = value;
	}

	 
	public Care getCare() {
		return care;
	}

	public String toString() {
		return String.valueOf(getId());
	}

}
