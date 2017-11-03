package pl.edu.pwste.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@org.hibernate.annotations.Proxy(lazy = false)
@Table(name = "Notification")
public class Notification implements Serializable {
	public Notification() {
	}

	@Column(name = "Id", nullable = false, length = 10)
	@Id
	@GeneratedValue(generator = "NOTIFICATION_ID_GENERATOR")
	@org.hibernate.annotations.GenericGenerator(name = "NOTIFICATION_ID_GENERATOR", strategy = "native")
	private int id;

	@Column(name = "Name", nullable = false, length = 50)
	private String name;

	@Column(name = "Content", nullable = false, length = 200)
	private String content;

	@Column(name = "Status", nullable = false, length = 20)
	private String status;

	@Column(name = "Time", nullable = false)
	private java.sql.Timestamp time;

	@JsonIgnore
	@ManyToOne(targetEntity = Care.class, fetch = FetchType.LAZY)
	@org.hibernate.annotations.Cascade({ org.hibernate.annotations.CascadeType.LOCK })
	@JoinColumns({ @JoinColumn(name = "CareId", referencedColumnName = "Id", nullable = false) })
	private Care care;

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

	public void setContent(String value) {
		this.content = value;
	}

	 
	public String getContent() {
		return content;
	}

	public void setStatus(String value) {
		this.status = value;
	}

	 
	public String getStatus() {
		return status;
	}

	public void setTime(java.sql.Timestamp value) {
		this.time = value;
	}

	 
	public java.sql.Timestamp getTime() {
		return time;
	}

	public void setCare(Care value) {
		this.care = value;
	}

	 
	public Care getCare() {
		return care;
	}

	public String toString() {
		return String.valueOf(getId());
	}

}
