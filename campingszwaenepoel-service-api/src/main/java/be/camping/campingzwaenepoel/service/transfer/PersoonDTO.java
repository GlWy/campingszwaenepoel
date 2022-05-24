package be.camping.campingzwaenepoel.service.transfer;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import be.camping.campingzwaenepoel.common.enums.GeslachtEnum;
import be.camping.campingzwaenepoel.common.enums.TaalEnum;


public class PersoonDTO {

	private int id;
	private String naam;
	private String voornaam;
	private String geboorteplaats;
	private Date geboortedatum;
	private TaalEnum taal;
	private String opmerking;
	private GeslachtEnum geslacht;
    private AdresDTO adresDTO;
    private String badge;
    private Set<ContactgegevenDTO> contactgegevens = new HashSet<ContactgegevenDTO>();
    private Set<WagenDTO> wagens = new HashSet<WagenDTO>();    
	private String identiteitskaartnummer;
	private String nationaliteit;
	private String rijksregisternummer;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNaam() {
		return naam;
	}
	public void setNaam(String naam) {
		this.naam = naam;
	}
	public String getVoornaam() {
		return voornaam;
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
		return taal;
	}
	public void setTaal(TaalEnum taal) {
		this.taal = taal;
	}
	public String getOpmerking() {
		return opmerking;
	}
	public void setOpmerking(String opmerking) {
		this.opmerking = opmerking;
	}
	public GeslachtEnum getGeslacht() {
		return this.geslacht;
	}
	public void setGeslacht(GeslachtEnum geslacht) {
		this.geslacht = geslacht;
	}
	public AdresDTO getAdresDTO() {
		return adresDTO;
	}
	public void setAdresDTO(AdresDTO adresDTO) {
		this.adresDTO = adresDTO;
	}
	public String getBadge() {
		return badge;
	}
	public void setBadge(String badge) {
		this.badge = badge;
	}
	public Set<ContactgegevenDTO> getContactgegevens() {
		return contactgegevens;
	}
	public void setContactgegevens(Set<ContactgegevenDTO> contactgegevens) {
		this.contactgegevens = contactgegevens;
	}
	public Set<WagenDTO> getWagens() {
		return wagens;
	}
	public void setWagens(Set<WagenDTO> wagens) {
		this.wagens = wagens;
	}
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

}
