package pl.edu.pwste.goco.senior.Entity;

import java.io.Serializable;


public class TimeOfReceipt implements Serializable {
	public TimeOfReceipt() {
	}

	private int id;

	private java.sql.Time time;

	private String dosege;

	private Medicine medicine;

	private void setId(int value) {
		this.id = value;
	}

	public int getId() {
		return id;
	}

	public int getORMID() {
		return getId();
	}

	public void setTime(java.sql.Time value) {
		this.time = value;
	}

	public java.sql.Time getTime() {
		return time;
	}

	public void setDosege(String value) {
		this.dosege = value;
	}

	public String getDosege() {
		return dosege;
	}

	public void setMedicine(Medicine value) {
		this.medicine = value;
	}

	public Medicine getMedicine() {
		return medicine;
	}

	public String toString() {
		return String.valueOf(getId());
	}

}
