package be.camping.campingzwaenepoel.service;

import java.util.List;

import be.camping.campingzwaenepoel.service.transfer.StandplaatsDetailDTO;

public interface StandplaatsDetailService {

	public StandplaatsDetailDTO getStandplaatsDetail(int id);
	
	public List<StandplaatsDetailDTO> getStandplaatsDetails();
	
	public void storeStandplaatsDetail(StandplaatsDetailDTO standplaatsDetailDTO);
	
	public void removeStandplaatsDetail(StandplaatsDetailDTO standplaatsDetailDTO);
}
