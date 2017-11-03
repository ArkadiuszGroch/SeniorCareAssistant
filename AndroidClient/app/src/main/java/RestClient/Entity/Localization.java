package RestClient.Entity;

import java.io.Serializable;

public class Localization implements Serializable {
	public Localization() {
	}

	private int id;

	private java.math.BigDecimal latitude;

	private java.math.BigDecimal longitude;

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

	public void setLatitude(java.math.BigDecimal value) {
		this.latitude = value;
	}

	 
	public java.math.BigDecimal getLatitude() {
		return latitude;
	}

	public void setLongitude(java.math.BigDecimal value) {
		this.longitude = value;
	}

	 
	public java.math.BigDecimal getLongitude() {
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
