package be.camping.campingzwaenepoel.service.transfer;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import be.camping.campingzwaenepoel.common.enums.SoortHuurderEnum;

public class InschrijvingDTO {

	private Integer id;
	private Date dateVan;
	private Date dateTot;
	private SoortHuurderEnum soorthuurder;
	private String opmerking;
	private Set<InschrijvingPersoonDTO> inschrijvingPersonen = new HashSet<InschrijvingPersoonDTO>();
	private BadgeDTO badge;
	private Integer standplaatsId;
	private int fichenummer;
	private String computer;

	// Property accessors
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDateVan() {
		return this.dateVan;
	}

	public void setDateVan(Date dateVan) {
		this.dateVan = dateVan;
	}

	public Date getDateTot() {
		return this.dateTot;
	}

	public void setDateTot(Date dateTot) {
		this.dateTot = dateTot;
	}

	public SoortHuurderEnum getSoorthuurder() {
		return this.soorthuurder;
	}

	public void setSoorthuurder(SoortHuurderEnum soorthuurder) {
		this.soorthuurder = soorthuurder;
	}

	public String getOpmerking() {
		return this.opmerking;
	}

	public void setOpmerking(String opmerking) {
		this.opmerking = opmerking;
	}

	public Set<InschrijvingPersoonDTO> getInschrijvingPersonen() {
		return inschrijvingPersonen;
	}

	public void setInschrijvingPersonen(Set<InschrijvingPersoonDTO> inschrijvingPersonen) {
		this.inschrijvingPersonen = inschrijvingPersonen;
	}

	public BadgeDTO getBadge() {
		return badge;
	}
	public void setBadge(BadgeDTO badge) {
		this.badge = badge;
	}

	public Integer getStandplaatsId() {
		return standplaatsId;
	}

	public void setStandplaatsId(Integer standplaatsId) {
		this.standplaatsId = standplaatsId;
	}

	public int getFichenummer() {
		return fichenummer;
	}

	public void setFichenummer(int fichenummer) {
		this.fichenummer = fichenummer;
	}

	public String getComputer() {
		return computer;
	}

	public void setComputer(String computer) {
		this.computer = computer;
	}
}
