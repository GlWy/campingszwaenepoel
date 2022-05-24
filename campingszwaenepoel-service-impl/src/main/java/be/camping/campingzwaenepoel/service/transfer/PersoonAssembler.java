package be.camping.campingzwaenepoel.service.transfer;

import java.util.HashSet;
import java.util.Set;

import be.camping.campingzwaenepoel.domain.model.Contactgegeven;
import be.camping.campingzwaenepoel.domain.model.Persoon;
import be.camping.campingzwaenepoel.domain.model.Wagen;
import be.camping.campingzwaenepoel.service.transfer.PersoonDTO;

public class PersoonAssembler {

	public PersoonDTO getPersoonDTO(Persoon persoon) {
		PersoonDTO persoonDTO = new PersoonDTO();
		persoonDTO.setId(persoon.getId());
		persoonDTO.setNaam(persoon.getNaam());
		persoonDTO.setVoornaam(persoon.getVoornaam());
		persoonDTO.setGeboorteplaats(persoon.getGeboorteplaats());
		persoonDTO.setGeboortedatum(persoon.getGeboortedatum());
		persoonDTO.setTaal(persoon.getTaal());
		persoonDTO.setOpmerking(persoon.getOpmerking());
		persoonDTO.setGeslacht(persoon.getGeslacht());
	    if (persoon.getAdres() != null) {
	    	AdresAssembler adresAssembler = new AdresAssembler();
	    	persoonDTO.setAdresDTO(adresAssembler.getAdresDTO(persoon.getAdres()));
	    }
	    persoonDTO.setBadge(persoon.getBadge());
	    
	    Set<ContactgegevenDTO> contactgegevenDTOs = new HashSet<ContactgegevenDTO>();
	    ContactgegevenAssembler contactgegevenAssembler = new ContactgegevenAssembler();
	    for (Contactgegeven contactgegeven : persoon.getContactgegevens()) {
	    	contactgegevenDTOs.add(contactgegevenAssembler.getContactgegevenDTO(contactgegeven));
	    }
	    persoonDTO.setContactgegevens(contactgegevenDTOs);
	    
	    Set<WagenDTO> wagenDTOs = new HashSet<WagenDTO>();
	    WagenAssembler wagenAssembler = new WagenAssembler();
	    for (Wagen wagen : persoon.getWagens()) {
	    	wagenDTOs.add(wagenAssembler.getWagenDTO(wagen));
	    }
	    persoonDTO.setWagens(wagenDTOs);
	    persoonDTO.setIdentiteitskaartnummer(persoon.getIdentiteitskaartnummer());
	    persoonDTO.setNationaliteit(persoon.getNationaliteit());
	    persoonDTO.setRijksregisternummer(persoon.getRijksregisternummer());
	    
		return persoonDTO;
	}
	
	public Persoon getPersoon(PersoonDTO persoonDTO) {
		Persoon persoon = new Persoon();
		persoon.setId(persoonDTO.getId());
		persoon.setNaam(persoonDTO.getNaam());
		persoon.setVoornaam(persoonDTO.getVoornaam());
		persoon.setGeboorteplaats(persoonDTO.getGeboorteplaats());
		persoon.setGeboortedatum(persoonDTO.getGeboortedatum());
		persoon.setTaal(persoonDTO.getTaal());
		persoon.setOpmerking(persoonDTO.getOpmerking());
		persoon.setGeslacht(persoonDTO.getGeslacht());
	    if (persoonDTO.getAdresDTO() != null) {
	    	AdresAssembler adresAssembler = new AdresAssembler();
	    	persoon.setAdres(adresAssembler.getAdres(persoonDTO.getAdresDTO()));
	    }
	    persoon.setBadge(persoonDTO.getBadge());
	    
	    Set<Contactgegeven> contactgegevens = new HashSet<Contactgegeven>();
	    ContactgegevenAssembler contactgegevenAssembler = new ContactgegevenAssembler();
	    for (ContactgegevenDTO contactgegevenDTO : persoonDTO.getContactgegevens()) {
	    	contactgegevens.add(contactgegevenAssembler.getContactgegeven(contactgegevenDTO));
	    }
	    persoon.setContactgegevens(contactgegevens);

	    Set<Wagen> wagens = new HashSet<Wagen>();
	    WagenAssembler wagenAssembler = new WagenAssembler();
	    for (WagenDTO wagenDTO : persoonDTO.getWagens()) {
	    	wagens.add(wagenAssembler.getWagen(wagenDTO));
	    }
	    persoon.setWagens(wagens);
	    persoon.setIdentiteitskaartnummer(persoonDTO.getIdentiteitskaartnummer());
	    persoon.setNationaliteit(persoonDTO.getNationaliteit());
	    persoon.setRijksregisternummer(persoonDTO.getRijksregisternummer());

		return persoon;
	}
}
