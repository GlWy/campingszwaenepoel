package be.camping.campingzwaenepoel.service.transfer;

import be.camping.campingzwaenepoel.domain.model.Stamboom;

public class StamboomAssembler {

	public Stamboom getStamBoom(StamboomDTO stamboomDTO) {
		Stamboom stamboom = new Stamboom();
		stamboom.setId(stamboomDTO.getId());
		stamboom.setNaam(stamboomDTO.getNaam());
		stamboom.setOpmerking(stamboomDTO.getOpmerking());
		stamboom.setStandplaatsId(stamboomDTO.getStandplaatsId());
		stamboom.setGeneratie(stamboomDTO.getGeneratie());
		stamboom.setRang(stamboomDTO.getRang());
		return stamboom;
	}
	
	public StamboomDTO getStamBoomDTO(Stamboom stamboom) {
		StamboomDTO stamboomDTO = new StamboomDTO();
		stamboomDTO.setId(stamboom.getId());
		stamboomDTO.setNaam(stamboom.getNaam());
		stamboomDTO.setOpmerking(stamboom.getOpmerking());
		stamboomDTO.setStandplaatsId(stamboom.getStandplaatsId());
		stamboomDTO.setGeneratie(stamboom.getGeneratie());
		stamboomDTO.setRang(stamboom.getRang());
		return stamboomDTO;
	}
}
