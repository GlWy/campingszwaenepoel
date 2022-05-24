package be.camping.campingzwaenepoel.service.transfer;

import java.util.Date;

public class BetalerDTO {

	private Integer id;
	private PersoonDTO hoofdbetaler;
	private PersoonDTO betaler;
	private Date datumVan;
	private Date datumTot;
	private String opmerking;
	private int reglementUitlegNaam;
	private Date reglementUitlegDatum;
	private int reglementGetekendNaam;
	private Date reglementGetekendDatum;
	private int bareelUitlegNaam;
	private Date bareelUitlegDatum;
	private int bareelGetekendNaam;
	private Date bareelGetekendDatum;
	private Integer standplaatsId;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public PersoonDTO getHoofdbetaler() {
		return hoofdbetaler;
	}
	public void setHoofdbetaler(PersoonDTO hoofdbetaler) {
		this.hoofdbetaler = hoofdbetaler;
	}
	public PersoonDTO getBetaler() {
		return betaler;
	}
	public void setBetaler(PersoonDTO betaler) {
		this.betaler = betaler;
	}
	public Date getDatumVan() {
		return datumVan;
	}
	public void setDatumVan(Date datumVan) {
		this.datumVan = datumVan;
	}
	public Date getDatumTot() {
		return datumTot;
	}
	public void setDatumTot(Date datumTot) {
		this.datumTot = datumTot;
	}
	public String getOpmerking() {
		return opmerking;
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
