package be.camping.campingzwaenepoel.service;

import java.util.List;

import be.camping.campingzwaenepoel.service.transfer.KasbonDTO;

public interface KasbonService {

	public KasbonDTO getKasbon(int kasbonId);
	
	public KasbonDTO getKasbonByKasbonNummer(int kasbonnummer);
	
	public KasbonDTO store(KasbonDTO kasbon);
	
	public void remove(KasbonDTO kasbon);
	
	public void removeAll();
	
	public List<KasbonDTO> getKasbons();
	
	public List<KasbonDTO> getKasbons(String computername);
}
