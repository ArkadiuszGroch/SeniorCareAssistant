package pl.edu.pwste.goco.senior.Entity;

import java.io.Serializable;
public class Medicine implements Serializable {
	public Medicine() {
	}
	
	private int id;
	
	private String name;
	
	private String description;
	
	private java.util.Date startDate;
	
	private java.util.Date endDate;
	
	private Senior senior;
	
	private java.util.Set timeOfReceipt = new java.util.HashSet();
	
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
	
	public void setDescription(String value) {
		this.description = value;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setStartDate(java.util.Date value) {
		this.startDate = value;
	}
	
	public java.util.Date getStartDate() {
		return startDate;
	}
	
	public void setEndDate(java.util.Date value) {
		this.endDate = value;
	}
	
	public java.util.Date getEndDate() {
		return endDate;
	}
	
	public void setSenior(Senior value) {
		this.senior = value;
	}
	
	public Senior getSenior() {
		return senior;
	}
	
	public void setTimeOfReceipt(java.util.Set value) {
		this.timeOfReceipt = value;
	}
	
	public java.util.Set getTimeOfReceipt() {
		return timeOfReceipt;
	}
	
	
	public String toString() {
		return String.valueOf(getId());
	}
	
}
