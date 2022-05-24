package be.camping.campingzwaenepoel.service;

import java.util.List;

import be.camping.campingzwaenepoel.service.transfer.StamboomDTO;

public interface StamboomService {

	public StamboomDTO storeStamboom (StamboomDTO stamboomDTO);
	
	public void removeStamboom (StamboomDTO stamboomDTO);
	
	public List<StamboomDTO> getStamboom(int standplaatsId);
	
	public List<StamboomDTO> zoekPersonen(String naam);
	
	public void removeStamboomVanStandplaats(int standplaatsId);
}
