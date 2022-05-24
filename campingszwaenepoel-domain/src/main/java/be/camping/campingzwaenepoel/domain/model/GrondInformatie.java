package be.camping.campingzwaenepoel.domain.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * GrondInformatie entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "grond_informatie")
public class GrondInformatie implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private Integer id;
	@Column(name = "NUMMER", nullable = false)
	private Short nummer;
	@Column(name = "WAARDE", length = 100)
	private String waarde;

	// Constructors

	/** default constructor */
	public GrondInformatie() {
	}

	/** full constructor */
	public GrondInformatie(Short nummer, String waarde) {
		this.nummer = nummer;
		this.waarde = waarde;
	}

	// Property accessors
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Short getNummer() {
		return this.nummer;
	}

	public void setNummer(Short nummer) {
		this.nummer = nummer;
	}

	public String getWaarde() {
		return this.waarde;
	}

	public void setWaarde(String waarde) {
		this.waarde = waarde;
	}

}