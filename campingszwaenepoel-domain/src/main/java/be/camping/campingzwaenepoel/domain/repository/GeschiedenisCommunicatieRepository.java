package be.camping.campingzwaenepoel.domain.repository;

import java.util.List;

import be.camping.campingzwaenepoel.domain.model.GeschiedenisCommunicatie;

public interface GeschiedenisCommunicatieRepository {

	public GeschiedenisCommunicatie findById(int id);
	
	public GeschiedenisCommunicatie store(GeschiedenisCommunicatie geschiedenisCommunicatie);
	
	public void remove(GeschiedenisCommunicatie geschiedenisCommunicatie);
	
	public List<GeschiedenisCommunicatie> findAll();
	
	public GeschiedenisCommunicatie findByCommunicatie(String communicatie);

}
