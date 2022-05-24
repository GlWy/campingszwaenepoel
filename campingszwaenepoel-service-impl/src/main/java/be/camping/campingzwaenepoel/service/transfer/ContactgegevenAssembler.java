package be.camping.campingzwaenepoel.service.transfer;

import be.camping.campingzwaenepoel.domain.model.Contactgegeven;

public class ContactgegevenAssembler {

	public ContactgegevenDTO getContactgegevenDTO(Contactgegeven contactgegeven) {
		ContactgegevenDTO contactgegevenDTO = new ContactgegevenDTO();
		contactgegevenDTO.setId(contactgegeven.getId());
		contactgegevenDTO.setContactgegevenType(contactgegeven.getContactgegevenType());
		contactgegevenDTO.setWaarde(contactgegeven.getWaarde());
		return contactgegevenDTO;
	}

	public Contactgegeven getContactgegeven(ContactgegevenDTO contactgegevenDTO) {
		Contactgegeven contactgegeven = new Contactgegeven();
		contactgegeven.setId(contactgegevenDTO.getId());
		contactgegeven.setContactgegevenType(contactgegevenDTO.getContactgegevenType());
		contactgegeven.setWaarde(contactgegevenDTO.getWaarde());
		return contactgegeven;
	}
}
