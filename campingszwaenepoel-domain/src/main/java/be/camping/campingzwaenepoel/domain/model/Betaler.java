package be.camping.campingzwaenepoel.domain.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.CascadeType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * StandplaatsBetaler entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "betaler")
public class Betaler implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 5933577292122418976L;
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private Integer id;
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="FK_HOOFDBETALER_ID")
	private Persoon hoofdbetaler;
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="FK_BETALER_ID")
	private Persoon betaler;
	@Temporal(TemporalType.DATE)
	@Column(name = "DATUM_VAN", nullable = false, length = 10)
	private Date datumVan;
	@Temporal(TemporalType.DATE)
	@Column(name = "DATUM_TOT", length = 10)
	private Date datumTot;
	@Column(name = "OPMERKING", length = 16777215)
	private String opmerking;
	@Column(name = "REGLEMENT_UITLEG_NAAM", length = 1)
	private int reglementUitlegNaam;
	@Temporal(TemporalType.DATE)
	@Column(name = "REGLEMENT_UITLEG_DATUM", length = 10)
	private Date reglementUitlegDatum;
	@Column(name = "REGLEMENT_GETEKEND_NAAM", length = 1)
	private int reglementGetekendNaam;
	@Temporal(TemporalType.DATE)
	@Column(name = "REGLEMENT_GETEKEND_DATUM", length = 10)
	private Date reglementGetekendDatum;
	@Column(name = "BAREEL_UITLEG_NAAM", length = 1)
	private int bareelUitlegNaam;
	@Temporal(TemporalType.DATE)
	@Column(name = "BAREEL_UITLEG_DATUM", length = 10)
	private Date bareelUitlegDatum;
	@Column(name = "BAREEL_GETEKEND_NAAM", length = 1)
	private int bareelGetekendNaam;
	@Temporal(TemporalType.DATE)
	@Column(name = "BAREEL_GETEKEND_DATUM", length = 10)
	private Date bareelGetekendDatum;
	@Column(name="FK_STANDPLAATS_ID", nullable=false, insertable=false, updatable=false)
	private Integer standplaatsId;

	// Constructors

	/** default constructor */
	public Betaler() {
	}

	/** minimal constructor */
	public Betaler(Date datumVan) {
		this.datumVan = datumVan;
	}

	// Property accessors
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Persoon getHoofdbetaler() {
		return hoofdbetaler;
	}

	public void setHoofdbetaler(Persoon hoofdbetaler) {
		this.hoofdbetaler = hoofdbetaler;
	}

	public Persoon getBetaler() {
		return betaler;
	}

	public void setBetaler(Persoon betaler) {
		this.betaler = betaler;
	}

	public Date getDatumVan() {
		return this.datumVan;
	}

	public void setDatumVan(Date datumVan) {
		this.datumVan = datumVan;
	}

	public Date getDatumTot() {
		return this.datumTot;
	}

	public void setDatumTot(Date datumTot) {
		this.datumTot = datumTot;
	}

	public String getOpmerking() {
		return this.opmerking;
	}

	public void setOpmerking(String opmerking) {
		this.opmerking = opmerking;
	}

	public int getReglementUitlegNaam() {
		return reglementUitlegNaam;
	}

	public void setReglementUitlegNaam(int reglementUitlegNaam) {
		this.reglementUitlegNaam = reglementUitlegNaam;
	}

	public Date getReglementUitlegDatum() {
		return reglementUitlegDatum;
	}

	public void setReglementUitlegDatum(Date reglementUitlegDatum) {
		this.reglementUitlegDatum = reglementUitlegDatum;
	}

	public int getReglementGetekendNaam() {
		return reglementGetekendNaam;
	}

	public void setReglementGetekendNaam(int reglementGetekendNaam) {
		this.reglementGetekendNaam = reglementGetekendNaam;
	}

	public Date getReglementGetekendDatum() {
		return reglementGetekendDatum;
	}

	public void setReglementGetekendDatum(Date reglementGetekendDatum) {
		this.reglementGetekendDatum = reglementGetekendDatum;
	}

	public int getBareelUitlegNaam() {
		return bareelUitlegNaam;
	}

	public void setBareelUitlegNaam(int bareelUitlegNaam) {
		this.bareelUitlegNaam = bareelUitlegNaam;
	}

	public Date getBareelUitlegDatum() {
		return bareelUitlegDatum;
	}

	public void setBareelUitlegDatum(Date bareelUitlegDatum) {
		this.bareelUitlegDatum = bareelUitlegDatum;
	}

	public int getBareelGetekendNaam() {
		return bareelGetekendNaam;
	}

	public void setBareelGetekendNaam(int bareelGetekendNaam) {
		this.bareelGetekendNaam = bareelGetekendNaam;
	}

	public Date getBareelGetekendDatum() {
		return bareelGetekendDatum;
	}

	public void setBareelGetekendDatum(Date bareelGetekendDatum) {
		this.bareelGetekendDatum = bareelGetekendDatum;
	}

	public Integer getStandplaatsId() {
		return standplaatsId;
	}

	public void setStandplaatsId(Integer standplaatsId) {
		this.standplaatsId = standplaatsId;
	}

}