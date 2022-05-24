package be.camping.campingzwaenepoel.domain.repository;

import java.util.List;

import be.camping.campingzwaenepoel.domain.model.StandplaatsDetail;

public interface StandplaatsDetailRepository {
	
	public StandplaatsDetail findById(int id);
	
	public void store(StandplaatsDetail standplaatsDetail);

	public void remove(StandplaatsDetail standplaatsDetail);
	
	public List<StandplaatsDetail> getStandplaatsDetails();
}
