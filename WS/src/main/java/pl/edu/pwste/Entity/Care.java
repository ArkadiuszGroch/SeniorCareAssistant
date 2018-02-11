package pl.edu.pwste.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@org.hibernate.annotations.Proxy(lazy = false)
@Table(name = "Care", uniqueConstraints = { @UniqueConstraint(columnNames = { "CareAssistantId", "SeniorId" }) })
public class Care implements Serializable {

	public Care() {
	}

	@Column(name = "Id", nullable = false, length = 10)
	@Id
	@GeneratedValue(generator = "CARE_ID_GENERATOR")
	@org.hibernate.annotations.GenericGenerator(name = "CARE_ID_GENERATOR", strategy = "native")
	private int id;

	@ManyToOne(targetEntity = CareAssistant.class)
	@org.hibernate.annotations.Cascade({ org.hibernate.annotations.CascadeType.LOCK })
	@JoinColumns({ @JoinColumn(name = "CareAssistantId", referencedColumnName = "Id", nullable = false) })
	private CareAssistant careAssistant;

	@ManyToOne(targetEntity = Senior.class)
	@org.hibernate.annotations.Cascade({ org.hibernate.annotations.CascadeType.LOCK })
	@JoinColumns({ @JoinColumn(name = "SeniorId", referencedColumnName = "Id", nullable = false) })
	private Senior senior;

	@JsonIgnore
	@OneToMany(mappedBy = "care", targetEntity = Notification.class)
	@org.hibernate.annotations.Cascade({ org.hibernate.annotations.CascadeType.SAVE_UPDATE,
			org.hibernate.annotations.CascadeType.LOCK })
	@org.hibernate.annotations.LazyCollection(org.hibernate.annotations.LazyCollectionOption.TRUE)
	private java.util.Set notification = new java.util.HashSet();

	private void setId(int value) {
		this.id = value;
	}

	public int getId() {
		return id;
	}

	public int getORMID() {
		return getId();
	}

	public void setCareAssistant(CareAssistant value) {
		this.careAssistant = value;
	}

	public CareAssistant getCareAssistant() {
		return careAssistant;
	}

	public void setSenior(Senior value) {
		this.senior = value;
	}

	public Senior getSenior() {
		return senior;
	}

	public void setNotification(java.util.Set value) {
		this.notification = value;
	}

	public java.util.Set getNotification() {
		return notification;
	}

	public String toString() {
		return String.valueOf(getId());
	}

}
