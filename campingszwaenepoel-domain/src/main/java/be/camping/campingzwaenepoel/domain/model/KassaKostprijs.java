package be.camping.campingzwaenepoel.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * KassaKostprijs entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "kassa_kostprijs")
public class KassaKostprijs implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -3773834996306697987L;
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private Integer id;
	@Column(name = "NUMMER", nullable = false)
	private Integer nummer;
	@Column(name = "NAAM", length = 60)
	private String naam;
	@Column(name = "WAARDE", precision = 22, scale = 0)
	private Double waarde;

	// Constructors

	/** default constructor */
	public KassaKostprijs() {
	}

	/** minimal constructor */
	public KassaKostprijs(Integer nummer) {
		this.nummer = nummer;
	}

	/** full constructor */
	public KassaKostprijs(Integer nummer, String naam, Double waarde) {
		this.nummer = nummer;
		this.naam = naam;
		this.waarde = waarde;
	}

	// Property accessors
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNummer() {
		return this.nummer;
	}

	public void setNummer(Integer nummer) {
		this.nummer = nummer;
	}

	public String getNaam() {
		return this.naam;
	}

	public void setNaam(String naam) {
		this.naam = naam;
	}

	public Double getWaarde() {
		return this.waarde;
	}

	public void setWaarde(Double waarde) {
		this.waarde = waarde;
	}

}