package be.camping.campingzwaenepoel.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

import be.camping.campingzwaenepoel.common.enums.LandEnum;

/**
 * Adres entity. @author Glenn Wybo
 */
@Entity
@Table(name = "adres")
public class Adres implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private int id;
	@Column(name = "STRAAT")
	private String straat;
	@Column(name = "HUISNUMMER", length = 20)
	private String huisnummer;
	@Column(name = "BUS", length = 10)
	private String bus;
	@Column(name = "PLAATS")
	private String plaats;
	@Column(name = "POSTCODE", length = 6)
	private String postcode;
	@Column(name = "LAND", length = 100)
	@Enumerated(EnumType.STRING)
	private LandEnum land;

	// Constructors

	/** default constructor */
	public Adres() {
	}

	/** full constructor */
	public Adres(String straat, String huisnummer, String bus, String plaats,
			String postcode, LandEnum land) {
		this.straat = straat;
		this.huisnummer = huisnummer;
		this.bus = bus;
		this.plaats = plaats;
		this.postcode = postcode;
		this.land = land;
	}

	// Property accessors
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStraat() {
		return this.straat;
	}

	public void setStraat(String straat) {
		this.straat = straat;
	}

	public String getHuisnummer() {
		return this.huisnummer;
	}

	public void setHuisnummer(String huisnummer) {
		this.huisnummer = huisnummer;
	}

	public String getBus() {
		return this.bus;
	}

	public void setBus(String bus) {
		this.bus = bus;
	}

	public String getPlaats() {
		return this.plaats;
	}

	public void setPlaats(String plaats) {
		this.plaats = plaats;
	}

	public String getPostcode() {
		return this.postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public LandEnum getLand() {
		return this.land;
	}

	public void setLand(LandEnum land) {
		this.land = land;
	}

}