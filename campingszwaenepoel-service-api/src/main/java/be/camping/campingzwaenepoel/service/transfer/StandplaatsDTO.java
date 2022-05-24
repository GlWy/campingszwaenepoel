package be.camping.campingzwaenepoel.service.transfer;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import be.camping.campingzwaenepoel.common.enums.GebruikerEnum;

public class StandplaatsDTO {

	// fields
	private int id;
	private String plaatsgroep;
	private int plaatsnummer;
	private String geschiedenis;
	private GebruikerEnum geschiedenisEditor;
	private Date geschiedenisDatum;
	private Set<GrondInformatieDTO> grondInformaties = new HashSet<GrondInformatieDTO>();
	private String algemeneOpmerking;
	private String fotoOpmerking;
	private Set<FactuurDetailDTO> factuurDetails = new HashSet<FactuurDetailDTO>();
	private Set<InschrijvingDTO> inschrijvingen = new HashSet<InschrijvingDTO>();
	private BadgeDTO badge;
	private Set<BetalerDTO> betalers = new HashSet<BetalerDTO>();
    private double kostprijs;
    private double totaal;
    private Boolean geschiedenisFlag;
    private Set<GeschiedenisDTO> geschiedenisPunten = new HashSet<GeschiedenisDTO>();
    private double teBetalenSom;
    private double betaaldeSom;
	
	// getters and setters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPlaatsgroep() {
		return this.plaatsgroep;
	}

	public void setPlaatsgroep(String plaatsgroep) {
		this.plaatsgroep = plaatsgroep;
	}

	public int getPlaatsnummer() {
		return this.plaatsnummer;
	}

	public void setPlaatsnummer(int plaatsnummer) {
		this.plaatsnummer = plaatsnummer;
	}
	public String getGeschiedenis() {
		return geschiedenis;
	}
	public void setGeschiedenis(String geschiedenis) {
		this.geschiedenis = geschiedenis;
	}
	public Set<GrondInformatieDTO> getGrondInformaties() {
		return grondInformaties;
	}
	public void setGrondInformaties(Set<GrondInformatieDTO> grondInformaties) {
		this.grondInformaties = grondInformaties;
	}

	public String getAlgemeneOpmerking() {
		return this.algemeneOpmerking;
	}

	public void setAlgemeneOpmerking(String algemeneOpmerking) {
		this.algemeneOpmerking = algemeneOpmerking;
	}

	public String getFotoOpmerking() {
		return fotoOpmerking;
	}

	public void setFotoOpmerking(String fotoOpmerking) {
		this.fotoOpmerking = fotoOpmerking;
	}

	public Set<FactuurDetailDTO> getFactuurDetails() {
		return factuurDetails;
	}

	public void setFactuurDetails(Set<FactuurDetailDTO> factuurDetails) {
		this.factuurDetails = factuurDetails;
	}
	
	public Set<InschrijvingDTO> getInschrijvingen() {
		return inschrijvingen;
	}

	public void setInschrijvingen(Set<InschrijvingDTO> inschrijvingen) {
		this.inschrijvingen = inschrijvingen;
	}
	public GebruikerEnum getGeschiedenisEditor() {
		return geschiedenisEditor;
	}

	public void setGeschiedenisEditor(GebruikerEnum geschiedenisEditor) {
		this.geschiedenisEditor = geschiedenisEditor;
	}

	public Date getGeschiedenisDatum() {
		return geschiedenisDatum;
	}

	public void setGeschiedenisDatum(Date geschiedenisDatum) {
		this.geschiedenisDatum = geschiedenisDatum;
	}

	public BadgeDTO getBadge() {
		return badge;
	}
	
	public void setBadge(BadgeDTO badge) {
		this.badge = badge;
	}
	
	public Set<BetalerDTO> getBetalers() {
		return betalers;
	}
	
	public void setBetalers(Set<BetalerDTO> betalers) {
		this.betalers = betalers;
	}

	public double getKostprijs() {
		return kostprijs;
	}

	public void setKostprijs(double kostprijs) {
		this.kostprijs = kostprijs;
	}

	public double getTotaal() {
		return totaal;
	}

	public void setTotaal(double totaal) {
		this.totaal = totaal;
	}

	public Boolean getGeschiedenisFlag() {
		return geschiedenisFlag;
	}

	public void setGeschiedenisFlag(Boolean geschiedenisFlag) {
		this.geschiedenisFlag = geschiedenisFlag;
	}
	
	public Set<GeschiedenisDTO> getGeschiedenisPunten() {
		return geschiedenisPunten;
	}
	
	public void setGeschiedenisPunten(Set<GeschiedenisDTO> geschiedenisPunten) {
		this.geschiedenisPunten = geschiedenisPunten;
	}

	public double getTeBetalenSom() {
		return teBetalenSom;
	}

	public void setTeBetalenSom(double teBetalenSom) {
		this.teBetalenSom = teBetalenSom;
	}

	public double getBetaaldeSom() {
		return betaaldeSom;
	}

	public void setBetaaldeSom(double betaaldeSom) {
		this.betaaldeSom = betaaldeSom;
	}

}
