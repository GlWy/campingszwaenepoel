package be.camping.campingzwaenepoel.domain.repository;

import java.util.List;
import java.util.Optional;

import be.camping.campingzwaenepoel.domain.model.Badge;

public interface BadgeRepository {

	Badge findById (int id);
	
	List<String> getBadgeNummers();
	
	Optional<Badge> findByNummer(String nummer);
		
	void store(Badge badge);

}
