package be.camping.campingzwaenepoel.domain.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Wagenverkeer entity. @author Glenn Wybo
 */
@Entity
@Table(name = "wagenverkeer")
public class Wagenverkeer implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private int id;
	@Column(name = "FK_WAGEN_ID", nullable = false)
	private int fkWagenId;
	@Temporal(TemporalType.DATE)
	@Column(name = "DATUM", nullable = false, length = 10)
	private Date datum;
	@Column(name = "VERKEER", nullable = false, length = 3)
	private String verkeer;

	// Constructors

	/** default constructor */
	public Wagenverkeer() {
	}

	/** full constructor */
	public Wagenverkeer(int fkWagenId, Date datum, String verkeer) {
		this.fkWagenId = fkWagenId;
		this.datum = datum;
		this.verkeer = verkeer;
	}

	// Property accessors
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFkWagenId() {
		return this.fkWagenId;
	}

	public void setFkWagenId(int fkWagenId) {
		this.fkWagenId = fkWagenId;
	}

	public Date getDatum() {
		return this.datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public String getVerkeer() {
		return this.verkeer;
	}

	public void setVerkeer(String verkeer) {
		this.verkeer = verkeer;
	}

}