package be.camping.campingzwaenepoel.service.transfer;

import be.camping.campingzwaenepoel.domain.model.GrondInformatie;

public class GrondInformatieAssembler {

	public GrondInformatie getGrondInformatie(GrondInformatieDTO grondInformatieDTO) {
		GrondInformatie grondInformatie = new GrondInformatie();
		grondInformatie.setId(grondInformatieDTO.getId());
		grondInformatie.setNummer(grondInformatieDTO.getNummer());
		grondInformatie.setWaarde(grondInformatieDTO.getWaarde());
		return grondInformatie;
	}
	
	public GrondInformatieDTO getGrondInformatieDTO(GrondInformatie grondInformatie) {
		GrondInformatieDTO grondInformatieDTO = new GrondInformatieDTO();
		grondInformatieDTO.setId(grondInformatie.getId());
		grondInformatieDTO.setNummer(grondInformatie.getNummer());
		grondInformatieDTO.setWaarde(grondInformatie.getWaarde());
		return grondInformatieDTO;
	}

}
