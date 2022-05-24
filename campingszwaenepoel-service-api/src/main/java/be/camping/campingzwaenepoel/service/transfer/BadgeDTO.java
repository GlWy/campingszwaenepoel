package be.camping.campingzwaenepoel.service.transfer;

import be.camping.campingzwaenepoel.common.enums.SoortHuurderEnum;


public class BadgeDTO {

	private Integer id;
	private String badgenummer;
	private SoortHuurderEnum badgetype;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBadgenummer() {
		return badgenummer;
	}

	public void setBadgenummer(String badgenummer) {
		this.badgenummer = badgenummer;
	}

	public SoortHuurderEnum getBadgetype() {
		return badgetype;
	}

	public void setBadgetype(SoortHuurderEnum badgetype) {
		this.badgetype = badgetype;
	}

}
