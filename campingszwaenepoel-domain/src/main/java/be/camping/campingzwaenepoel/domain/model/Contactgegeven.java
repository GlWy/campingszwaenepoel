package be.camping.campingzwaenepoel.domain.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import be.camping.campingzwaenepoel.common.enums.ContactgegevenTypeEnum;

@Entity
@Table(name = "contactgegeven")
public class Contactgegeven {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private int id;
	@Column(name = "CONTACTGEGEVEN_TYPE", nullable = false, length = 32)
	@Enumerated(EnumType.STRING)
	private ContactgegevenTypeEnum contactgegevenType;
	@Column(name = "WAARDE", length = 256)
	private String waarde;

	public Contactgegeven() {
	}

	public Contactgegeven(ContactgegevenTypeEnum contactgegevenType, String waarde) {
		if (contactgegevenType == null) {
			throw new IllegalArgumentException("contactgegeventype is verplicht voor een nieuw contactgegeven");
		}
		this.contactgegevenType = contactgegevenType;
		this.waarde = waarde;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ContactgegevenTypeEnum getContactgegevenType() {
		return contactgegevenType;
	}

	public void setContactgegevenType(ContactgegevenTypeEnum contactgegevenType) {
		this.contactgegevenType = contactgegevenType;
	}

	public String getWaarde() {
		return waarde;
	}

	public void setWaarde(String waarde) {
		this.waarde = waarde;
	}
}
