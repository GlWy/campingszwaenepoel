package be.camping.campingzwaenepoel.domain.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import be.camping.campingzwaenepoel.common.enums.GebruikerEnum;

/**
 * Geschiedenis entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "geschiedenis")
public class Geschiedenis implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = -6367909982314863747L;
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private Integer id;
	@Column(name = "VERWITTIGING", nullable = false)
	private Boolean verwittiging;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATUM", nullable = false, length = 19)
	private Date datum;
	@Column(name = "NAAM", nullable = false, length = 45)
	@Enumerated(EnumType.STRING)
	private GebruikerEnum naam;
	@Column(name = "THEMA", nullable = false, length = 45)
	private String thema;
	@Column(name = "COMMUNICATIE", nullable = false, length = 45)
//	@Enumerated(EnumType.STRING)
	private String communicatie;
	@Column(name = "GESCHIEDENIS", nullable = false)
	private String geschiedenis;

	// Constructors

	/** default constructor */
	public Geschiedenis() {
	}

	/** full constructor */
	public Geschiedenis(Boolean verwittiging, Timestamp datum, GebruikerEnum naam,
			String thema, String communicatie, String geschiedenis) {
		this.verwittiging = verwittiging;
		this.datum = datum;
		this.naam = naam;
		this.thema = thema;
		this.communicatie = communicatie;
		this.geschiedenis = geschiedenis;
	}

	// Property accessors
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getVerwittiging() {
		return this.verwittiging;
	}

	public void setVerwittiging(Boolean verwittiging) {
		this.verwittiging = verwittiging;
	}

	public Date getDatum() {
		return this.datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public GebruikerEnum getNaam() {
		return this.naam;
	}

	public void setNaam(GebruikerEnum naam) {
		this.naam = naam;
	}

	public String getThema() {
		return this.thema;
	}

	public void setThema(String thema) {
		this.thema = thema;
	}

	public String getCommunicatie() {
		return this.communicatie;
	}

	public void setCommunicatie(String communicatie) {
		this.communicatie = communicatie;
	}

	public String getGeschiedenis() {
		return this.geschiedenis;
	}

	public void setGeschiedenis(String geschiedenis) {
		this.geschiedenis = geschiedenis;
	}

}