package be.camping.campingzwaenepoel.service.transfer;

import java.io.Serializable;

import be.camping.campingzwaenepoel.common.enums.LandEnum;

public class AdresDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String straat;
	private String huisnummer;
	private String bus;
	private String postcode;
	private String plaats;
	private LandEnum land;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStraat() {
		return straat;
	}
	public void setStraat(String straat) {
		this.straat = straat;
	}
	public String getHuisnummer() {
		return huisnummer;
	}
	public void setHuisnummer(String huisnummer) {
		this.huisnummer = huisnummer;
	}
	public String getBus() {
		return bus;
	}
	public void setBus(String bus) {
		this.bus = bus;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getPlaats() {
		return plaats;
	}
	public void setPlaats(String plaats) {
		this.plaats = plaats;
	}
	public LandEnum getLand() {
		return land;
	}
	public void setLand(LandEnum land) {
		this.land = land;
	}
}
