package be.camping.campingzwaenepoel.service;

import java.util.List;
import java.util.Optional;

import be.camping.campingzwaenepoel.service.transfer.BadgeDTO;

public interface BadgeService {

	public List<String> getBadges();
	
	public Optional<BadgeDTO> findBadge(String nummer);
	
	public BadgeDTO createBadge(BadgeDTO badgeDTO);
	
}
