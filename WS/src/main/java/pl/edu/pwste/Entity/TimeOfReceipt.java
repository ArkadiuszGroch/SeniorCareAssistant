package pl.edu.pwste.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@org.hibernate.annotations.Proxy(lazy = false)
@Table(name = "TimeOfReceipt")
public class TimeOfReceipt implements Serializable {
	public TimeOfReceipt() {
	}

	@Column(name = "Id", nullable = false, length = 10)
	@Id
	@GeneratedValue(generator = "TIMEOFRECEIPT_ID_GENERATOR")
	@org.hibernate.annotations.GenericGenerator(name = "TIMEOFRECEIPT_ID_GENERATOR", strategy = "native")
	private int id;

	@Column(name = "Time", nullable = false, length = 7)
	private java.sql.Time time;

	@Column(name = "Dosege", nullable = false, length = 100)
	private String dosege;

	@JsonIgnore
	@ManyToOne(targetEntity = Medicine.class, fetch = FetchType.LAZY)
	@org.hibernate.annotations.Cascade({ org.hibernate.annotations.CascadeType.LOCK })
	@JoinColumns({ @JoinColumn(name = "MedicineId", referencedColumnName = "Id", nullable = false) })
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
