package be.camping.campingzwaenepoel.service.transfer;

import java.util.Date;

import be.camping.campingzwaenepoel.common.enums.TabBlad;

public class OpslagGegevenDTO {

	private String naam;
	private TabBlad tabblad;
	private Date datum;

	public OpslagGegevenDTO(String naam, TabBlad tabblad, Date datum) {
		this.naam = naam;
		this.tabblad = tabblad;
		this.datum = datum;
	}

	public String getNaam() {
		return naam;
	}

	public void setNaam(String naam) {
		this.naam = naam;
	}

	public TabBlad getTabblad() {
		return tabblad;
	}

	public void setTabblad(TabBlad tabblad) {
		this.tabblad = tabblad;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}
}
