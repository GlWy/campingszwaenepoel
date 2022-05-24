package be.camping.campingzwaenepoel.domain.repository;

import java.util.List;

import be.camping.campingzwaenepoel.domain.model.CampingContact;

public interface CampingContactRepository {

	public CampingContact findById(int id);
	
	public void store(CampingContact campingContact);
	
	public void remove(CampingContact campingContact);
	
	public List<CampingContact> getContacten();
}
