package be.camping.campingzwaenepoel.domain.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

import be.camping.campingzwaenepoel.common.enums.GeslachtEnum;
import be.camping.campingzwaenepoel.common.enums.TaalEnum;

@Entity
@Table(name = "persoon")
@NamedQuery(name = Persoon.Queries.FindPersonen.NAME, query = Persoon.Queries.FindPersonen.QUERY)
public class Persoon implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private int id;
	@Column(name = "NAAM", nullable = false, length = 100)
	private String naam;
	@Column(name = "VOORNAAM", nullable = false, length = 100)
	private String voornaam;
	@Column(name = "GEBOORTEPLAATS", length = 255)
	private String geboorteplaats;
	@Temporal(TemporalType.DATE)
	@Column(name = "GEBOORTEDATUM", length = 10)
	private Date geboortedatum;
	@Column(name = "TAAL")
	@Enumerated(EnumType.STRING)
	private TaalEnum taal = TaalEnum.NL;
	@Column(name = "OPMERKING")
	private String opmerking;
	@Column(name = "GESLACHT", nullable = false, length = 5)
	@Enumerated(EnumType.STRING)
	private GeslachtEnum geslacht;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="FK_ADRES_ID")
	private Adres adres;
	@Column(name = "BADGE", length = 10)
	private String badge;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "FK_PERSOON_ID", nullable = false)
    private Set<Contactgegeven> contactgegevens = new HashSet<Contactgegeven>();
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_PERSOON_ID", nullable = false)
    private Set<Wagen> wagens = new HashSet<Wagen>();
	@Column(name = "IDENTITEITSKAARTNUMMER", length = 50)
	private String identiteitskaartnummer;
	@Column(name = "NATIONALITEIT", length = 20)
	private String nationaliteit;
	@Column(name = "RIJKSREGISTERNUMMER", length = 20)
	private String rijksregisternummer;
//    @ManyToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE}, mappedBy="standplaatsDatums", targetEntity = StandplaatsDatum.class)
//    private Set<StandplaatsDatum> standplaatsDatums = new HashSet<StandplaatsDatum>();

	/** default constructor */
	public Persoon() {
//		gronden = new ArrayList<Standplaats>();
	}

	/** minimal constructor */
	public Persoon(String naam, String voornaam) {
		this.naam = naam;
		this.voornaam = voornaam;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNaam() {
		return this.naam;
	}

	public void setNaam(String naam) {
		this.naam = naam;
	}

	public String getVoornaam() {
		return this.voornaam;
	}

	public void setVoornaam(String voornaam) {
		this.voornaam = voornaam;
	}

	public String getGeboorteplaats() {
		return geboorteplaats;
	}

	public void setGeboorteplaats(String geboorteplaats) {
		this.geboorteplaats = geboorteplaats;
	}

	public Date getGeboortedatum() {
		return geboortedatum;
	}

	public void setGeboortedatum(Date geboortedatum) {
		this.geboortedatum = geboortedatum;
	}

	public TaalEnum getTaal() {
		return this.taal;
	}

	public void setTaal(TaalEnum taal) {
		this.taal = taal;
	}

	public String getOpmerking() {
		return this.opmerking;
	}

	public void setOpmerking(String opmerking) {
		this.opmerking = opmerking;
	}

	public Persoon getPartner() {
		Persoon partner = new Persoon();
		partner.setId(getId());
		return partner;
	}
	
	public GeslachtEnum getGeslacht() {
		return this.geslacht;
	}

	public void setGeslacht(GeslachtEnum geslacht) {
		this.geslacht = geslacht;
	}

	public Adres getAdres() {
		return adres;
	}

	public void setAdres(Adres adres) {
		this.adres = adres;
	}

	public String getBadge() {
		return badge;
	}

	public void setBadge(String badge) {
		this.badge = badge;
	}

	public Set<Contactgegeven> getContactgegevens() {
		return contactgegevens;
	}

	public void setContactgegevens(Set<Contactgegeven> contactgegevens) {
		this.contactgegevens = contactgegevens;
	}

	public Set<Wagen> getWagens() {
		return wagens;
	}

	public void setWagens(Set<Wagen> wagens) {
		this.wagens = wagens;
	}

/*	public Set<StandplaatsDatum> getStandplaatsDatums() {
		return standplaatsDatums;
	}

	public void setStandplaatsDatums(Set<StandplaatsDatum> standplaatsDatums) {
		this.standplaatsDatums = standplaatsDatums;
	}*/

	public String getIdentiteitskaartnummer() {
		return identiteitskaartnummer;
	}

	public void setIdentiteitskaartnummer(String identiteitskaartnummer) {
		this.identiteitskaartnummer = identiteitskaartnummer;
	}

	public String getNationaliteit() {
		return nationaliteit;
	}

	public void setNationaliteit(String nationaliteit) {
		this.nationaliteit = nationaliteit;
	}

	public String getRijksregisternummer() {
		return rijksregisternummer;
	}

	public void setRijksregisternummer(String rijksregisternummer) {
		this.rijksregisternummer = rijksregisternummer;
	}

	public void addContactgegeven(Contactgegeven contactgegeven) {
		getContactgegevens().add(contactgegeven);
	}
	
	public void addWagen(Wagen wagen) {
		getWagens().add(wagen);
	}

	public interface Queries {

		class FindPersonen {
			public static final String NAME = "Persoon.FindPersonen";
			static final String QUERY = "select naam, voornaam, id from Persoon order by naam, voornaam";
		}
	}
}