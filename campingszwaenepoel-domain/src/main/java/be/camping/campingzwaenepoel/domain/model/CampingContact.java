package be.camping.campingzwaenepoel.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * CampingContact entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "camping_contact")
public class CampingContact implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private Integer id;
	@Column(name = "NAAM")
	private String naam;
	@Column(name = "TELEFOON", length = 20)
	private String telefoon;

	// Constructors

	/** default constructor */
	public CampingContact() {
	}

	/** minimal constructor */
	public CampingContact(String telefoon) {
		this.telefoon = telefoon;
	}

	/** full constructor */
	public CampingContact(String naam, String telefoon) {
		this.naam = naam;
		this.telefoon = telefoon;
	}

	// Property accessors
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNaam() {
		return this.naam;
	}

	public void setNaam(String naam) {
		this.naam = naam;
	}

	public String getTelefoon() {
		return this.telefoon;
	}

	public void setTelefoon(String telefoon) {
		this.telefoon = telefoon;
	}

}