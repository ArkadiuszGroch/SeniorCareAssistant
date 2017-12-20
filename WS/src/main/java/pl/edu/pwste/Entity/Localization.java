package pl.edu.pwste.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@org.hibernate.annotations.Proxy(lazy = false)
@Table(name = "Localization")
public class Localization implements Serializable {
	public Localization() {
	}

	@Column(name = "Id", nullable = false, length = 10)
	@Id
	@GeneratedValue(generator = "LOCALIZATION_ID_GENERATOR")
	@org.hibernate.annotations.GenericGenerator(name = "LOCALIZATION_ID_GENERATOR", strategy = "native")
	private int id;

	@Column(name = "Latitude", nullable = false, precision = 9, scale = 6)
	private java.math.BigDecimal latitude;

	@Column(name = "Longitude", nullable = false, precision = 9, scale = 6)
	private java.math.BigDecimal longitude;

	@Column(name = "Time", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private java.sql.Timestamp time;

	@JsonIgnore
	@ManyToOne(targetEntity = Senior.class, fetch = FetchType.LAZY)
	@org.hibernate.annotations.Cascade({ org.hibernate.annotations.CascadeType.LOCK })
	@JoinColumns({ @JoinColumn(name = "SeniorId", referencedColumnName = "Id", nullable = false) })
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
