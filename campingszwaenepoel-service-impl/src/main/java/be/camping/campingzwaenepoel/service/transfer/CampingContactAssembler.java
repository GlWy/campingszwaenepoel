package be.camping.campingzwaenepoel.service.transfer;

import be.camping.campingzwaenepoel.domain.model.CampingContact;

public class CampingContactAssembler {

	public CampingContactDTO getCampingContactDTO(CampingContact campingContact) {
		CampingContactDTO campingContactDTO = new CampingContactDTO();
		campingContactDTO.setId(campingContact.getId());
		campingContactDTO.setNaam(campingContact.getNaam());
		campingContactDTO.setTelefoon(campingContact.getTelefoon());
		return campingContactDTO;
	}

	public CampingContact getCampingContact(CampingContactDTO campingContactDTO) {
		CampingContact campingContact = new CampingContact();
		campingContact.setId(campingContactDTO.getId());
		campingContact.setNaam(campingContactDTO.getNaam());
		campingContact.setTelefoon(campingContactDTO.getTelefoon());
		return campingContact;
	}
}
