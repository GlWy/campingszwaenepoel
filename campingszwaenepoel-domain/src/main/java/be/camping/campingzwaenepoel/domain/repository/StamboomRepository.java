package be.camping.campingzwaenepoel.domain.repository;

import java.util.List;

import be.camping.campingzwaenepoel.domain.model.Stamboom;

public interface StamboomRepository {

	Stamboom store(Stamboom stamboom);
	
	Stamboom findById(int id);
	
	List<Stamboom> getStamboom(int standplaatsId);
	
	void remove(Stamboom stamboom);

	List<Stamboom> zoekPersonen(String naam);
	
	void removeStamboomVanStandplaats(int standplaatsId);

}
