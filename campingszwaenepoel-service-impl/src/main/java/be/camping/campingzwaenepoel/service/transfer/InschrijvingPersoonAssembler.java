package be.camping.campingzwaenepoel.service.transfer;

import be.camping.campingzwaenepoel.domain.model.InschrijvingPersoon;
import be.camping.campingzwaenepoel.service.DTOFactory;

public class InschrijvingPersoonAssembler {

	public InschrijvingPersoonDTO getInschrijvingPersoonDTO(InschrijvingPersoon inschrijvingPersoon) {
		InschrijvingPersoonDTO inschrijvingPersoonDTO = DTOFactory.getInschrijvingPersoonDTO();
		inschrijvingPersoonDTO.setId(inschrijvingPersoon.getId());
		inschrijvingPersoonDTO.setInschrijvingDatum(inschrijvingPersoon.getInschrijvingDatum());
		PersoonAssembler persoonAssembler = new PersoonAssembler();
		inschrijvingPersoonDTO.setPersoon(persoonAssembler.getPersoonDTO(inschrijvingPersoon.getPersoon()));
		inschrijvingPersoonDTO.setGezinsPositie(inschrijvingPersoon.getGezinsPositie());
		inschrijvingPersoonDTO.setHuurdersPositie(inschrijvingPersoon.getHuurdersPositie());
		return inschrijvingPersoonDTO;
	}
	
	public InschrijvingPersoon getInschrijvingPersoon(InschrijvingPersoonDTO inschrijvingPersoonDTO) {
		InschrijvingPersoon inschrijvingPersoon = DTOFactory.getInschrijvingPersoon();
		inschrijvingPersoon.setId(inschrijvingPersoonDTO.getId());
		inschrijvingPersoon.setInschrijvingDatum(inschrijvingPersoonDTO.getInschrijvingDatum());
		PersoonAssembler persoonAssembler = new PersoonAssembler();
		inschrijvingPersoon.setPersoon(persoonAssembler.getPersoon(inschrijvingPersoonDTO.getPersoon()));
		inschrijvingPersoon.setGezinsPositie(inschrijvingPersoonDTO.getGezinsPositie());
		inschrijvingPersoon.setHuurdersPositie(inschrijvingPersoonDTO.getHuurdersPositie());
		return inschrijvingPersoon;
	}
}
