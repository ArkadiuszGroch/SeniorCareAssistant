package pl.edu.pwste.goco.senior.Entity;

import java.io.Serializable;

public class Localization implements Serializable {
	public Localization() {
	}

	private int id;

	private double latitude;

	private double longitude;

	private java.sql.Timestamp time;

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


	public java.sql.Timestamp getTime() {
		return time;
	}


	public void setTime(java.sql.Timestamp time) {
		this.time = time;
	}


	public void setSenior(Senior value) {
		this.senior = value;
	}

	 
	public Senior getSenior() {
		return senior;
	}

	public String toString() {
		return String.valueOf(getId());
	}

}
