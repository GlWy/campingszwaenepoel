package be.camping.campingzwaenepoel.service.transfer;

import be.camping.campingzwaenepoel.domain.model.Badge;

public class BadgeAssembler {

	public Badge getBadge(BadgeDTO badgeDTO) {
		Badge badge = new Badge();
		badge.setId(badgeDTO.getId());
		badge.setBadgenummer(badgeDTO.getBadgenummer());
		badge.setBadgetype(badgeDTO.getBadgetype());
		return badge;
	}
	
	public BadgeDTO getBadgeDTO(Badge badge) {
		BadgeDTO badgeDTO = null;
		if (badge != null) {
			badgeDTO = new BadgeDTO();
			badgeDTO.setId(badge.getId());
			badgeDTO.setBadgenummer(badge.getBadgenummer());
			badgeDTO.setBadgetype(badge.getBadgetype());
		}
		return badgeDTO;
	}
	
}
