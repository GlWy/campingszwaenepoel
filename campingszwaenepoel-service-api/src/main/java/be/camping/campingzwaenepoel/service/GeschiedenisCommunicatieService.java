package be.camping.campingzwaenepoel.service;

import java.util.List;

import be.camping.campingzwaenepoel.service.transfer.GeschiedenisCommunicatieDTO;

public interface GeschiedenisCommunicatieService {

	public GeschiedenisCommunicatieDTO findGeschiedenisCommunicatie(int id);
	
	public GeschiedenisCommunicatieDTO storeGeschiedenisCommunicatie(GeschiedenisCommunicatieDTO geschiedenisCommunicatieDTO);
	
	public void removeGeschiedenisCommunicatie(GeschiedenisCommunicatieDTO geschiedenisCommunicatieDTO);
	
	public List<GeschiedenisCommunicatieDTO> findAllCommunicaties();
	
	public GeschiedenisCommunicatieDTO findGeschiedenisCommunicatie(String communicatie);
}
