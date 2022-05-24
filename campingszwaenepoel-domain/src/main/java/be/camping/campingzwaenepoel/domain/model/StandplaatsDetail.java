package be.camping.campingzwaenepoel.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * GrondDetail entity. @author MyEclipse Persistence Tools
 */

@Entity
@Table(name = "standplaats_detail")
public class StandplaatsDetail implements java.io.Serializable {

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
	@Column(name = "NAAM", nullable = true, length = 100)
	private String naam;
	@Column(name = "DATUMVELD", nullable = false, length = 1)
	private Boolean datumveld;
	@Column(name = "VERPLICHT", nullable = false)
	private Boolean verplicht;
	@Column(name = "PRINTALLES", nullable = false)
	private Boolean printAlles;
	@Column(name = "NAMENTONEN", nullable = false)
	private Boolean namenTonen;

	// Constructors

	/** default constructor */
	public StandplaatsDetail() {
	}

	/** full constructor */
	public StandplaatsDetail(Short nummer, String naam, boolean datumveld) {
		this.nummer = nummer;
		this.naam = naam;
		this.datumveld = datumveld;
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

	public String getNaam() {
		return this.naam;
	}

	public void setNaam(String naam) {
		this.naam = naam;
	}

	public boolean isDatumveld() {
		return this.datumveld;
	}

	public void setDatumveld(boolean datumveld) {
		this.datumveld = datumveld;
	}

	public Boolean isVerplicht() {
		return verplicht;
	}

	public void setVerplicht(Boolean verplicht) {
		this.verplicht = verplicht;
	}

	public Boolean isPrintAlles() {
		return printAlles;
	}

	public void setPrintAlles(Boolean printAlles) {
		this.printAlles = printAlles;
	}

	public Boolean isNamenTonen() {
		return namenTonen;
	}

	public void setNamenTonen(Boolean namenTonen) {
		this.namenTonen = namenTonen;
	}

}