package be.camping.campingzwaenepoel.service.transfer;

import java.util.Date;

import be.camping.campingzwaenepoel.common.enums.CashSourceEnum;

public class KasbonDTO {

	private Integer id;
	private Integer kasbonnummer;
	private Integer aantalNachten;
	private Integer auto100;
	private Integer auto150;
	private Integer waarborg;
	private Integer jeton;
	private Integer zakGeel;
	private Integer zakBlauw;
	private Integer car;
	private Integer tent;
	private Integer volwassenen;
	private Integer kinderen;
	private Double telefoon;
	private Date kasbonDatum;
	private String naam;
	private String standplaats;
	private String nummerplaat;
	private String badge;
	private Date datumVan;
	private Date datumTot;
	private Double betaling;
	private Double terug;
	private String computer;
    private CashSourceEnum cashSource = CashSourceEnum.CASH;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getKasbonnummer() {
		return kasbonnummer;
	}

	public void setKasbonnummer(Integer kasbonnummer) {
		this.kasbonnummer = kasbonnummer;
	}

	public Integer getAantalNachten() {
		return this.aantalNachten;
	}

	public void setAantalNachten(Integer aantalNachten) {
		this.aantalNachten = aantalNachten;
	}

	public Integer getAuto100() {
		return this.auto100;
	}

	public void setAuto100(Integer auto100) {
		this.auto100 = auto100;
	}

	public Integer getAuto150() {
		return this.auto150;
	}

	public void setAuto150(Integer auto150) {
		this.auto150 = auto150;
	}

	public Integer getWaarborg() {
		return this.waarborg;
	}

	public void setWaarborg(Integer waarborg) {
		this.waarborg = waarborg;
	}

	public Integer getJeton() {
		return this.jeton;
	}

	public void setJeton(Integer jeton) {
		this.jeton = jeton;
	}

	public Integer getZakGeel() {
		return this.zakGeel;
	}

	public void setZakGeel(Integer zakGeel) {
		this.zakGeel = zakGeel;
	}

	public Integer getZakBlauw() {
		return this.zakBlauw;
	}

	public void setZakBlauw(Integer zakBlauw) {
		this.zakBlauw = zakBlauw;
	}

	public Integer getCar() {
		return this.car;
	}

	public void setCar(Integer car) {
		this.car = car;
	}

	public Integer getTent() {
		return this.tent;
	}

	public void setTent(Integer tent) {
		this.tent = tent;
	}

	public Integer getVolwassenen() {
		return this.volwassenen;
	}

	public void setVolwassenen(Integer volwassenen) {
		this.volwassenen = volwassenen;
	}

	public Integer getKinderen() {
		return this.kinderen;
	}

	public void setKinderen(Integer kinderen) {
		this.kinderen = kinderen;
	}

	public Double getTelefoon() {
		return this.telefoon;
	}

	public void setTelefoon(Double telefoon) {
		this.telefoon = telefoon;
	}

	public Date getKasbonDatum() {
		return this.kasbonDatum;
	}

	public void setKasbonDatum(Date kasbonDatum) {
		this.kasbonDatum = kasbonDatum;
	}

	public String getNaam() {
		return this.naam;
	}

	public void setNaam(String naam) {
		this.naam = naam;
	}

	public String getStandplaats() {
		return this.standplaats;
	}

	public void setStandplaats(String standplaats) {
		this.standplaats = standplaats;
	}

	public String getNummerplaat() {
		return this.nummerplaat;
	}

	public void setNummerplaat(String nummerplaat) {
		this.nummerplaat = nummerplaat;
	}

	public String getBadge() {
		return this.badge;
	}

	public void setBadge(String badge) {
		this.badge = badge;
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

	public Double getBetaling() {
		return betaling;
	}

	public void setBetaling(Double betaling) {
		this.betaling = betaling;
	}

	public Double getTerug() {
		return terug;
	}

	public void setTerug(Double terug) {
		this.terug = terug;
	}

	public String getComputer() {
		return computer;
	}

	public void setComputer(String computer) {
		this.computer = computer;
	}

    public CashSourceEnum getCashSource() {
    	return cashSource;
    }
    
    public void setCashSource(CashSourceEnum cashSource) {
    	this.cashSource = cashSource;
    }
}
