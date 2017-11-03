package pl.edu.pwste.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@org.hibernate.annotations.Proxy(lazy=false)
@Table(name="Medicine")
public class Medicine implements Serializable {
	public Medicine() {
	}
	
	@Column(name="Id", nullable=false, length=10)	
	@Id	
	@GeneratedValue(generator="MEDICINE_ID_GENERATOR")	
	@org.hibernate.annotations.GenericGenerator(name="MEDICINE_ID_GENERATOR", strategy="native")	
	private int id;
	
	@Column(name="Name", nullable=false, length=100)	
	private String name;
	
	@Column(name="Description", nullable=false, length=250)	
	private String description;
	
	@Column(name="StartDate", nullable=false)	
	@Temporal(TemporalType.DATE)	
	private java.util.Date startDate;
	
	@Column(name="EndDate", nullable=true)	
	@Temporal(TemporalType.DATE)	
	private java.util.Date endDate;
	
	@JsonIgnore
	@ManyToOne(targetEntity=Senior.class, fetch=FetchType.LAZY)	
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.LOCK})	
	@JoinColumns({ @JoinColumn(name="SeniorId", referencedColumnName="Id", nullable=false) })	
	private Senior senior;
	

	@OneToMany(mappedBy="medicine", targetEntity=TimeOfReceipt.class)	
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.LOCK})	
	@org.hibernate.annotations.LazyCollection(org.hibernate.annotations.LazyCollectionOption.FALSE)	
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
