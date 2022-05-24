package be.camping.campingzwaenepoel.domain.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import be.camping.campingzwaenepoel.common.enums.BetalingEnum;

/**
 * Faktuurdetails entity. @author Glenn Wybo
 */
@Entity
@Table(name = "factuur_detail")
public class FactuurDetail implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private int id;
	@Column(name = "DATUM", nullable = false, length = 15)
	private Date datum;
	@Column(name = "AARD_BETALING", nullable = false, length = 12)
	@Enumerated(EnumType.STRING)
	private BetalingEnum aardBetaling;
	@Column(name = "BEDRAG")
	private double bedrag;
	@Column(name = "UITSTELKOSTEN")
	private double uitstelkosten;
	@Column(name = "RAPPEL")
	private double rappel;
	@Column(name = "ANDEREKOSTEN")
	private double andereKosten;
	@Column(name = "OPMERKING")
	private String opmerking;
	@Column(name = "DATUM_AANMAAK", nullable = false)
	private long datumAanmaak;
	@Transient
	private boolean show = true;

	// Constructors

	/** default constructor */
	public FactuurDetail() {
	}

	// Property accessors
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOpmerking() {
		return this.opmerking;
	}

	public void setOpmerking(String opmerking) {
		this.opmerking = opmerking;
	}

	public Date getDatum() {
		return this.datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public BetalingEnum getAardBetaling() {
		return aardBetaling;
	}

	public void setAardBetaling(BetalingEnum aardBetaling) {
		this.aardBetaling = aardBetaling;
	}

	public double getBedrag() {
		return bedrag;
	}

	public void setBedrag(double bedrag) {
		this.bedrag = bedrag;
	}

	public double getUitstelkosten() {
		return uitstelkosten;
	}

	public void setUitstelkosten(double uitstelkosten) {
		this.uitstelkosten = uitstelkosten;
	}

	public double getRappel() {
		return rappel;
	}

	public void setRappel(double rappel) {
		this.rappel = rappel;
	}

	public double getAndereKosten() {
		return andereKosten;
	}

	public void setAndereKosten(double andereKosten) {
		this.andereKosten = andereKosten;
	}

	public long getDatumAanmaak() {
		return datumAanmaak;
	}

	public void setDatumAanmaak(long datumAanmaak) {
		this.datumAanmaak = datumAanmaak;
	}

	public boolean isShow() {
		return show;
	}

	public void setShow(boolean show) {
		this.show = show;
	}

}