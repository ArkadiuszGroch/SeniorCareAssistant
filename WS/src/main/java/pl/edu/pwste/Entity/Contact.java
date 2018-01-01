package pl.edu.pwste.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.io.Serializable;
//TODO set unique to seniorId and phone
@Entity
@Proxy(lazy = false)
@Table(name = "Contact", uniqueConstraints = { @UniqueConstraint(columnNames = { "SeniorId", "Phone" }) })
public class Contact implements Serializable {
	public Contact() {
	}
	@JsonIgnore
	@Column(name = "Id", nullable = false, length = 10)
	@Id
	@GeneratedValue(generator = "CONTACT_ID_GENERATOR")
	@org.hibernate.annotations.GenericGenerator(name = "CONTACT_ID_GENERATOR", strategy = "native")
	private int id;

	@Column(name = "Name", nullable = false, length = 100)
	private String name;

	@Column(name = "Phone", nullable = false, length = 12)
	private String phone;

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

	 
	public void setName(String value) {
		this.name = value;
	}

	public String getName() {
		return name;
	}

	public void setPhone(String value) {
		this.phone = value;
	}

	 
	public String getPhone() {
		return phone;
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
