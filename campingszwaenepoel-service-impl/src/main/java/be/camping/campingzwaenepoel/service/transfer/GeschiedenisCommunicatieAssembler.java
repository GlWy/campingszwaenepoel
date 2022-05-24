package be.camping.campingzwaenepoel.service.transfer;

import be.camping.campingzwaenepoel.domain.model.GeschiedenisCommunicatie;

public class GeschiedenisCommunicatieAssembler {

	public GeschiedenisCommunicatie getGeschiedenisCommunicatie(GeschiedenisCommunicatieDTO geschiedenisCommunicatieDTO) {
		GeschiedenisCommunicatie geschiedenisCommunicatie = new GeschiedenisCommunicatie();
		geschiedenisCommunicatie.setId(geschiedenisCommunicatieDTO.getId());
		geschiedenisCommunicatie.setCommunicatie(geschiedenisCommunicatieDTO.getCommunicatie());
		return geschiedenisCommunicatie;
	}
	
	public GeschiedenisCommunicatieDTO getGeschiedenisCommunicatieDTO(GeschiedenisCommunicatie geschiedenisCommunicatie) {
		GeschiedenisCommunicatieDTO geschiedenisCommunicatieDTO = new GeschiedenisCommunicatieDTO();
		geschiedenisCommunicatieDTO.setId(geschiedenisCommunicatie.getId());
		geschiedenisCommunicatieDTO.setCommunicatie(geschiedenisCommunicatie.getCommunicatie());
		return geschiedenisCommunicatieDTO;
	}

}
