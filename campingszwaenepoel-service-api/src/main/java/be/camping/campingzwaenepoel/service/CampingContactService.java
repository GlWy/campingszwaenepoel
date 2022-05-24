package be.camping.campingzwaenepoel.service;

import java.util.List;

import be.camping.campingzwaenepoel.service.transfer.CampingContactDTO;

public interface CampingContactService {

	public List<CampingContactDTO> findAllCampingContacts();
	
	public void store(CampingContactDTO campingContactDTO);
	
	public void remove(CampingContactDTO campingContactDTO);
}
