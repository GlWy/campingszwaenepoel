package be.camping.campingzwaenepoel.service.transfer;

import java.util.HashSet;
import java.util.Set;

import be.camping.campingzwaenepoel.domain.model.Inschrijving;
import be.camping.campingzwaenepoel.domain.model.InschrijvingPersoon;

public class InschrijvingAssembler {

	public Inschrijving getInschrijving(InschrijvingDTO inschrijvingDTO) {
		Inschrijving inschrijving = new Inschrijving();
		inschrijving.setId(inschrijvingDTO.getId());
		inschrijving.setDateVan(inschrijvingDTO.getDateVan());
		inschrijving.setDateTot(inschrijvingDTO.getDateTot());
		inschrijving.setOpmerking(inschrijvingDTO.getOpmerking());
		inschrijving.setSoorthuurder(inschrijvingDTO.getSoorthuurder());
		inschrijving.setStandplaatsId(inschrijvingDTO.getStandplaatsId());
		InschrijvingPersoonAssembler assembler = new InschrijvingPersoonAssembler();
		Set<InschrijvingPersoon> inschrijvingPersonen = new HashSet<InschrijvingPersoon>();
		for (InschrijvingPersoonDTO inschrijvingPersoon : inschrijvingDTO.getInschrijvingPersonen()) {
			inschrijvingPersonen.add(assembler.getInschrijvingPersoon(inschrijvingPersoon));
		}
		inschrijving.setInschrijvingPersonen(inschrijvingPersonen);
		if (inschrijvingDTO.getBadge() != null) {
			BadgeAssembler badgeAssembler = new BadgeAssembler();
			inschrijving.setBadge(badgeAssembler.getBadge(inschrijvingDTO.getBadge()));
		}
		inschrijving.setFichenummer(inschrijvingDTO.getFichenummer());
		inschrijving.setComputer(inschrijvingDTO.getComputer());
		return inschrijving;
	}

	public InschrijvingDTO getInschrijvingDTO(Inschrijving inschrijving) {
		InschrijvingDTO inschrijvingDTO = new InschrijvingDTO();
		inschrijvingDTO.setId(inschrijving.getId());
		inschrijvingDTO.setDateVan(inschrijving.getDateVan());
		inschrijvingDTO.setDateTot(inschrijving.getDateTot());
		inschrijvingDTO.setOpmerking(inschrijving.getOpmerking());
		inschrijvingDTO.setSoorthuurder(inschrijving.getSoorthuurder());
		inschrijvingDTO.setStandplaatsId(inschrijving.getStandplaatsId());
		if (inschrijving.getBadge() != null) {
			BadgeAssembler badgeAssembler = new BadgeAssembler();
			inschrijvingDTO.setBadge(badgeAssembler.getBadgeDTO(inschrijving.getBadge()));
		}
		inschrijvingDTO.setFichenummer(inschrijving.getFichenummer());
		inschrijvingDTO.setComputer(inschrijving.getComputer());
		return inschrijvingDTO;
	}
	
	public InschrijvingDTO getInschrijvingDTOMetPersonen(Inschrijving inschrijving) {
		InschrijvingDTO inschrijvingDTO = new InschrijvingDTO();
		inschrijvingDTO.setId(inschrijving.getId());
		inschrijvingDTO.setDateVan(inschrijving.getDateVan());
		inschrijvingDTO.setDateTot(inschrijving.getDateTot());
		inschrijvingDTO.setOpmerking(inschrijving.getOpmerking());
		inschrijvingDTO.setSoorthuurder(inschrijving.getSoorthuurder());
		InschrijvingPersoonAssembler assembler = new InschrijvingPersoonAssembler();
		Set<InschrijvingPersoonDTO> inschrijvingPersonen = new HashSet<InschrijvingPersoonDTO>();
		for (InschrijvingPersoon inschrijvingPersoon : inschrijving.getInschrijvingPersonen()) {
			inschrijvingPersonen.add(assembler.getInschrijvingPersoonDTO(inschrijvingPersoon));
		}
		inschrijvingDTO.setInschrijvingPersonen(inschrijvingPersonen);
		if (inschrijving.getBadge() != null) {
			BadgeAssembler badgeAssembler = new BadgeAssembler();
			inschrijvingDTO.setBadge(badgeAssembler.getBadgeDTO(inschrijving.getBadge()));
		}
		inschrijvingDTO.setFichenummer(inschrijving.getFichenummer());
		inschrijvingDTO.setStandplaatsId(inschrijving.getStandplaatsId());
		inschrijvingDTO.setFichenummer(inschrijving.getFichenummer());
		inschrijvingDTO.setComputer(inschrijving.getComputer());
		return inschrijvingDTO;
	}
	
}
