package be.camping.campingzwaenepoel.service.transfer;

import be.camping.campingzwaenepoel.common.enums.ContactgegevenTypeEnum;


public class ContactgegevenDTO {

	private int id;
	private ContactgegevenTypeEnum contactgegevenType;
	private String waarde;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ContactgegevenTypeEnum getContactgegevenType() {
		return contactgegevenType;
	}
	public void setContactgegevenType(ContactgegevenTypeEnum contactgegevenType) {
		this.contactgegevenType = contactgegevenType;
	}
	public String getWaarde() {
		return waarde;
	}
	public void setWaarde(String waarde) {
		this.waarde = waarde;
	}

}
