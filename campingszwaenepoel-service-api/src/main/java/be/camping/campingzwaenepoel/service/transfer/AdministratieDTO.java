package be.camping.campingzwaenepoel.service.transfer;

import java.util.Date;

public class AdministratieDTO {

	private Integer id;
	private String opmerking;
	private int reglementUitlegNaam;
	private Date reglementUitlegDatum;
	private int reglementGetekendNaam;
	private Date reglementGetekendDatum;
	private int bareelUitlegNaam;
	private Date bareelUitlegDatum;
	private int bareelGetekendNaam;
	private Date bareelGetekendDatum;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public void setReglementUitlegDatum(Date regrementUitlegDatum) {
		this.reglementUitlegDatum = regrementUitlegDatum;
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

}
