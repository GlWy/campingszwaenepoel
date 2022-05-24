package be.camping.campingzwaenepoel.service.transfer;

import java.sql.Timestamp;
import java.util.Date;

import be.camping.campingzwaenepoel.common.enums.ContactgegevenTypeEnum;
import be.camping.campingzwaenepoel.common.enums.GebruikerEnum;

public class GeschiedenisDTO {

	private Integer id;
	private Boolean verwittiging;
	private Date datum;
	private GebruikerEnum naam;
	private String thema;
	private String communicatie;
	private String geschiedenis;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getVerwittiging() {
		return this.verwittiging;
	}

	public void setVerwittiging(Boolean verwittiging) {
		this.verwittiging = verwittiging;
	}

	public Date getDatum() {
		return this.datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public GebruikerEnum getNaam() {
		return this.naam;
	}

	public void setNaam(GebruikerEnum naam) {
		this.naam = naam;
	}

	public String getThema() {
		return this.thema;
	}

	public void setThema(String thema) {
		this.thema = thema;
	}

	public String getCommunicatie() {
		return this.communicatie;
	}

	public void setCommunicatie(String communicatie) {
		this.communicatie = communicatie;
	}

	public String getGeschiedenis() {
		return this.geschiedenis;
	}

	public void setGeschiedenis(String geschiedenis) {
		this.geschiedenis = geschiedenis;
	}

}
