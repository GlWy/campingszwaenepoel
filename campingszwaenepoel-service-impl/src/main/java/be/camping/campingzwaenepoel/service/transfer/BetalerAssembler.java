package be.camping.campingzwaenepoel.service.transfer;

import be.camping.campingzwaenepoel.domain.model.Betaler;
import be.camping.campingzwaenepoel.service.DTOFactory;

public class BetalerAssembler {

	public Betaler getStandplaatsBetaler(BetalerDTO betalerDTO) {
		Betaler betaler = DTOFactory.getStandplaatsBetaler();
		betaler.setId(betalerDTO.getId());
		PersoonAssembler persoonAssembler = new PersoonAssembler();
		if (betalerDTO.getHoofdbetaler() != null) {			
			betaler.setHoofdbetaler(persoonAssembler.getPersoon(betalerDTO.getHoofdbetaler()));
		}
		if (betalerDTO.getBetaler() != null) {			
			betaler.setBetaler(persoonAssembler.getPersoon(betalerDTO.getBetaler()));
		}
		betaler.setDatumVan(betalerDTO.getDatumVan());
		betaler.setDatumTot(betalerDTO.getDatumTot());
		betaler.setOpmerking(betalerDTO.getOpmerking());
		betaler.setBareelGetekendDatum(betalerDTO.getBareelGetekendDatum());
		betaler.setBareelGetekendNaam(betalerDTO.getBareelGetekendNaam());
		betaler.setBareelUitlegDatum(betalerDTO.getBareelUitlegDatum());
		betaler.setBareelUitlegNaam(betalerDTO.getBareelUitlegNaam());
		betaler.setReglementGetekendDatum(betalerDTO.getReglementGetekendDatum());
		betaler.setReglementGetekendNaam(betalerDTO.getReglementGetekendNaam());
		betaler.setReglementUitlegNaam(betalerDTO.getReglementUitlegNaam());
		betaler.setReglementUitlegDatum(betalerDTO.getReglementUitlegDatum());
		betaler.setStandplaatsId(betalerDTO.getStandplaatsId());
		return betaler;
	}

	public BetalerDTO getStandplaatsBetalerDTO(Betaler betaler) {
		BetalerDTO betalerDTO = DTOFactory.getBetalerDTO();
		betalerDTO.setId(betaler.getId());
		PersoonAssembler persoonAssembler = new PersoonAssembler();
		if (betaler.getHoofdbetaler() != null) {
			betalerDTO.setHoofdbetaler(persoonAssembler.getPersoonDTO(betaler.getHoofdbetaler()));
		}
		if (betaler.getBetaler() != null) {
			betalerDTO.setBetaler(persoonAssembler.getPersoonDTO(betaler.getBetaler()));
		}
		betalerDTO.setDatumVan(betaler.getDatumVan());
		betalerDTO.setDatumTot(betaler.getDatumTot());
		betalerDTO.setOpmerking(betaler.getOpmerking());
		betalerDTO.setBareelGetekendDatum(betaler.getBareelGetekendDatum());
		betalerDTO.setBareelGetekendNaam(betaler.getBareelGetekendNaam());
		betalerDTO.setBareelUitlegDatum(betaler.getBareelUitlegDatum());
		betalerDTO.setBareelUitlegNaam(betaler.getBareelUitlegNaam());
		betalerDTO.setReglementGetekendDatum(betaler.getReglementGetekendDatum());
		betalerDTO.setReglementGetekendNaam(betaler.getReglementGetekendNaam());
		betalerDTO.setReglementUitlegNaam(betaler.getReglementUitlegNaam());
		betalerDTO.setReglementUitlegDatum(betaler.getReglementUitlegDatum());
		betalerDTO.setStandplaatsId(betaler.getStandplaatsId());
		return betalerDTO;
	}
}
