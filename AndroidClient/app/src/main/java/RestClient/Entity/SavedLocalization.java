package RestClient.Entity;

import java.io.Serializable;


public class SavedLocalization implements Serializable {
	public SavedLocalization() {
	}

	private int id;

	private String name;

	private java.math.BigDecimal latitude;

	private java.math.BigDecimal longitude;

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
