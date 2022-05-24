package be.camping.campingzwaenepoel.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Wagen entity. @author Glenn Wybo
 */
@Entity
@Table(name = "wagen")
public class Wagen implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private int id;
	@Column(name = "NUMMERPLAAT", nullable = false, length = 8)
	private String nummerplaat;
	@Column(name = "MERK")
	private String merk;
	@Column(name = "OPMERKING")
	private String opmerking;
	@Column(name = "STICKER")
	private String sticker;

	// Constructors

	/** default constructor */
	public Wagen() {
	}

	/** minimal constructor */
	public Wagen(String nummerplaat, String sticker) {
		this.nummerplaat = nummerplaat;
		this.sticker = sticker;
	}

	/** full constructor */
	public Wagen(String nummerplaat, String merk, String opmerking, String sticker) {
		this.nummerplaat = nummerplaat;
		this.merk = merk;
		this.opmerking = opmerking;
		this.sticker = sticker;
	}

	// Property accessors
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNummerplaat() {
		return this.nummerplaat;
	}

	public void setNummerplaat(String nummerplaat) {
		this.nummerplaat = nummerplaat;
	}

	public String getMerk() {
		return this.merk;
	}

	public void setMerk(String merk) {
		this.merk = merk;
	}

	public String getOpmerking() {
		return this.opmerking;
	}

	public void setOpmerking(String opmerking) {
		this.opmerking = opmerking;
	}

	public String getSticker() {
		return this.sticker;
	}

	public void setSticker(String sticker) {
		this.sticker = sticker;
	}

}