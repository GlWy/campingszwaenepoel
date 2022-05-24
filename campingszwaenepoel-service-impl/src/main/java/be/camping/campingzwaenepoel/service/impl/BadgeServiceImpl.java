package be.camping.campingzwaenepoel.service.impl;

import java.util.List;
import java.util.Optional;

import be.camping.campingzwaenepoel.domain.model.Badge;
import be.camping.campingzwaenepoel.domain.repository.BadgeRepository;
import be.camping.campingzwaenepoel.service.BadgeService;
import be.camping.campingzwaenepoel.service.transfer.BadgeAssembler;
import be.camping.campingzwaenepoel.service.transfer.BadgeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("BadgeService")
@Transactional(readOnly = true)
public class BadgeServiceImpl implements BadgeService {

	@Autowired
	private BadgeRepository badgeRepository;
	
	@Override
	public List<String> getBadges() {
		List<String> badges = badgeRepository.getBadgeNummers();
		badges.add(0, "");
		return badges;
	}

	@Override
	public Optional<BadgeDTO> findBadge(String nummer) {
		Optional<Badge> badge = badgeRepository.findByNummer(nummer);
		BadgeAssembler badgeAssembler = new BadgeAssembler();
		return badge.map(b -> Optional.of(badgeAssembler.getBadgeDTO(badge.get()))).orElse(Optional.empty());
	}
	
	public BadgeDTO createBadge(BadgeDTO badgeDTO) {
		BadgeAssembler badgeAssembler = new BadgeAssembler();
		Badge badge = badgeAssembler.getBadge(badgeDTO);
		badgeRepository.store(badge);
		return badgeAssembler.getBadgeDTO(badge);
	}
}