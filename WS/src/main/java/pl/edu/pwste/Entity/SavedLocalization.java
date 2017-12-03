package pl.edu.pwste.Entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@org.hibernate.annotations.Proxy(lazy = false)
@Table(name = "SavedLocalization")
public class SavedLocalization implements Serializable {
	public SavedLocalization() {
	}

	@Column(name = "Id", nullable = false, length = 10)
	@Id
	@GeneratedValue(generator = "SAVEDLOCALIZATION_ID_GENERATOR")
	@org.hibernate.annotations.GenericGenerator(name = "SAVEDLOCALIZATION_ID_GENERATOR", strategy = "native")
	private int id;

	@Column(name = "Name", nullable = false, length = 50)
	private String name;

	@Column(name = "Latitude", nullable = false, precision = 9, scale = 6)
	private double latitude;

	@Column(name = "Longitude", nullable = false, precision = 9, scale = 6)
	private double longitude;

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

	public void setName(String value) {
		this.name = value;
	}

	 
	public String getName() {
		return name;
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
