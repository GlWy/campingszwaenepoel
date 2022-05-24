package be.camping.campingzwaenepoel.service;

import be.belgium.eid.eidlib.BeID;
import be.belgium.eid.exceptions.EIDException;
import be.camping.campingzwaenepoel.service.transfer.ContactgegevenDTO;
import be.camping.campingzwaenepoel.service.transfer.PersoonDTO;
import be.camping.campingzwaenepoel.service.transfer.WagenDTO;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

public interface PersoonService {

	List<Object> getPersonen();
	
	List<Object[]> zoekPersonen(Map<String, Object> zoekCriteria);
	
	List<PersoonDTO> zoekPersonen(Calendar geboortedatum, String naam, String voornaam);
	
	PersoonDTO store (PersoonDTO persoon);
	
	PersoonDTO findPersoonById(int id);
	
	void removeContactgegeven(ContactgegevenDTO contactgegeven);
	
	void removeWagen(WagenDTO wagen);
	
	List<String> getPersoonNamen();
	
	byte[] getPicture(BeID beID) throws EIDException;

	void changePersoon(int newId, int oldId);
	
	PersoonDTO getPersoonDataFromEid(BeID beID) throws EIDException;

}
