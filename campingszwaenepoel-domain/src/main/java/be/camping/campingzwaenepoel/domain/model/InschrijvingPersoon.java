package be.camping.campingzwaenepoel.domain.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import be.camping.campingzwaenepoel.common.enums.GezinsPositieEnum;
import be.camping.campingzwaenepoel.common.enums.HuurderPositieEnum;

/**
 * InschrijvingPersoon entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "inschrijving_persoon")
public class InschrijvingPersoon implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7538646299141512826L;
	// Fields

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private int id;
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="FK_PERSOON_ID")
	private Persoon persoon;
	@Column(name = "INSCHRIJVING_DATUM", nullable = false)
	private Long inschrijvingDatum;
	@Column(name = "GEZINSPOSITIE", length = 10, nullable = false)
	@Enumerated(EnumType.STRING)
	private GezinsPositieEnum gezinsPositie;
	@Column(name = "HUURDERSPOSITIE", nullable = false, length = 13)
	@Enumerated(EnumType.STRING)
	private HuurderPositieEnum huurdersPositie;

	// Constructors

	/** default constructor */
	public InschrijvingPersoon() {
	}

	/** full constructor */
	public InschrijvingPersoon(int id, Long inschrijvingDatum) {
		this.id = id;
		this.inschrijvingDatum = inschrijvingDatum;
	}

	// Property accessors
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Persoon getPersoon() {
		return persoon;
	}

	public void setPersoon(Persoon persoon) {
		this.persoon = persoon;
	}

	public Long getInschrijvingDatum() {
		return this.inschrijvingDatum;
	}

	public void setInschrijvingDatum(Long inschrijvingDatum) {
		this.inschrijvingDatum = inschrijvingDatum;
	}
	public GezinsPositieEnum getGezinsPositie() {
		return gezinsPositie;
	}

	public void setGezinsPositie(GezinsPositieEnum gezinsPositie) {
		this.gezinsPositie = gezinsPositie;
	}

	public HuurderPositieEnum getHuurdersPositie() {
		return huurdersPositie;
	}

	public void setHuurdersPositie(HuurderPositieEnum huurdersPositie) {
		this.huurdersPositie = huurdersPositie;
	}

}