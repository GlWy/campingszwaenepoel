package be.camping.campingzwaenepoel.domain.repository;

import java.util.List;

import be.camping.campingzwaenepoel.domain.model.KassaKostprijs;

public interface KassaKostprijsRepository {

	public KassaKostprijs store(KassaKostprijs kassaKostprijs);
	
	public KassaKostprijs findById(int id);
	
	public List<KassaKostprijs> kostprijzen();

}
