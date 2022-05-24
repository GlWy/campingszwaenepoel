package be.camping.campingzwaenepoel.service.transfer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import be.camping.campingzwaenepoel.domain.model.Badge;
import be.camping.campingzwaenepoel.domain.model.Betaler;
import be.camping.campingzwaenepoel.domain.model.FactuurDetail;
import be.camping.campingzwaenepoel.domain.model.Geschiedenis;
import be.camping.campingzwaenepoel.domain.model.GrondInformatie;
import be.camping.campingzwaenepoel.domain.model.Inschrijving;
import be.camping.campingzwaenepoel.domain.model.InschrijvingPersoon;
import be.camping.campingzwaenepoel.domain.model.Persoon;
import be.camping.campingzwaenepoel.domain.model.Standplaats;

public class StandplaatsAssembler {

	public StandplaatsDTO getStandplaatsDTO(Standplaats standplaats) {
		StandplaatsDTO standplaatsDTO = new StandplaatsDTO();
		standplaatsDTO.setId(standplaats.getId());
		standplaatsDTO.setPlaatsgroep(standplaats.getPlaatsgroep());
		standplaatsDTO.setPlaatsnummer(standplaats.getPlaatsnummer());
		standplaatsDTO.setGeschiedenis(standplaats.getGeschiedenis());
		standplaatsDTO.setGeschiedenisEditor(standplaats.getGeschiedenisEditor());
		standplaatsDTO.setGeschiedenisDatum(standplaats.getGeschiedenisDatum());
		Set<GrondInformatieDTO> grondInformatieDTOs = new HashSet<GrondInformatieDTO>();
		GrondInformatieAssembler grondInformatieAssembler = new GrondInformatieAssembler();
		for (GrondInformatie grondInformatie : standplaats.getGrondInformaties()) {
			GrondInformatieDTO grondInformatieDTO = grondInformatieAssembler.getGrondInformatieDTO(grondInformatie);
			grondInformatieDTOs.add(grondInformatieDTO);
		}
		standplaatsDTO.setGrondInformaties(grondInformatieDTOs);
		if (standplaats.getBadge() != null) {
			BadgeAssembler badgeAssembler = new BadgeAssembler();
			standplaatsDTO.setBadge(badgeAssembler.getBadgeDTO(standplaats.getBadge()));
		}
		standplaatsDTO.setAlgemeneOpmerking(standplaats.getAlgemeneOpmerking());
		standplaatsDTO.setFotoOpmerking(standplaats.getFotoOpmerking());

		/*
		 * Set<InschrijvingDTO> inschrijvingDTOs = new HashSet<InschrijvingDTO>(); InschrijvingAssembler
		 * inschrijvingAssembler = new InschrijvingAssembler(); InschrijvingPersoonAssembler ipAssembler = new
		 * InschrijvingPersoonAssembler(); List<Integer> persoonIds = new ArrayList<Integer>(); List<PersoonDTO>
		 * persoonDTOs = new ArrayList<PersoonDTO>(); for (Inschrijving inschrijving : standplaats.getInschrijvingen())
		 * { InschrijvingDTO inschrijvingDTO = inschrijvingAssembler.getInschrijvingDTO(inschrijving);
		 * inschrijvingDTO.setInschrijvingPersonen(new HashSet<InschrijvingPersoonDTO>()); for (InschrijvingPersoon
		 * inschrijvingPersoon : inschrijving.getInschrijvingPersonen()) { InschrijvingPersoonDTO inschrijvingPersoonDTO
		 * = ipAssembler.getInschrijvingPersoonDTO(inschrijvingPersoon); if
		 * (persoonIds.contains(inschrijvingPersoon.getPersoon().getId())) {
		 * inschrijvingPersoonDTO.setPersoon(persoonDTOs
		 * .get(persoonIds.indexOf(inschrijvingPersoon.getPersoon().getId()))); } else {
		 * persoonIds.add(inschrijvingPersoon.getPersoon().getId());
		 * persoonDTOs.add(inschrijvingPersoonDTO.getPersoon()); }
		 * inschrijvingDTO.getInschrijvingPersonen().add(inschrijvingPersoonDTO); }
		 * inschrijvingDTOs.add(inschrijvingDTO); } standplaatsDTO.setInschrijvingen(inschrijvingDTOs);
		 */

		BetalerAssembler standplaatsBetalerAssembler = new BetalerAssembler();
		Set<BetalerDTO> standplaatsBetalerDTOs = new HashSet<BetalerDTO>();
		for (Betaler standplaatsBetaler : standplaats.getBetalers()) {
			standplaatsBetalerDTOs.add(standplaatsBetalerAssembler.getStandplaatsBetalerDTO(standplaatsBetaler));
		}
		standplaatsDTO.setBetalers(standplaatsBetalerDTOs);

		Set<FactuurDetailDTO> factuurDetailDTOs = new HashSet<FactuurDetailDTO>();
		FactuurDetailAssembler factuurDetailAssembler = new FactuurDetailAssembler();
		for (FactuurDetail factuurDetail : standplaats.getFactuurDetails()) {
			factuurDetailDTOs.add(factuurDetailAssembler.getFactuurDetailDTO(factuurDetail));
		}
		standplaatsDTO.setFactuurDetails(factuurDetailDTOs);
		standplaatsDTO.setKostprijs(standplaats.getKostprijs());
		standplaatsDTO.setTotaal(standplaats.getTotaal());
		standplaatsDTO.setGeschiedenisFlag(standplaats.getGeschiedenisFlag());
		standplaatsDTO.setTeBetalenSom(standplaats.getTeBetalenSom());
		standplaatsDTO.setBetaaldeSom(standplaats.getBetaaldeSom());

		return standplaatsDTO;
	}

	public Standplaats getStandplaats(StandplaatsDTO standplaatsDTO) {
		Standplaats standplaats = new Standplaats();
		standplaats.setId(standplaatsDTO.getId());
		standplaats.setPlaatsgroep(standplaatsDTO.getPlaatsgroep());
		standplaats.setPlaatsnummer(standplaatsDTO.getPlaatsnummer());
		standplaats.setGeschiedenis(standplaatsDTO.getGeschiedenis());
		standplaats.setGeschiedenisEditor(standplaatsDTO.getGeschiedenisEditor());
		standplaats.setGeschiedenisDatum(standplaatsDTO.getGeschiedenisDatum());
		Set<GrondInformatie> grondInformaties = new HashSet<GrondInformatie>();
		GrondInformatieAssembler grondInformatieAssembler = new GrondInformatieAssembler();
		for (GrondInformatieDTO grondInformatieDTO : standplaatsDTO.getGrondInformaties()) {
			GrondInformatie grondInformatie = grondInformatieAssembler.getGrondInformatie(grondInformatieDTO);
			grondInformaties.add(grondInformatie);
		}
		standplaats.setGrondInformaties(grondInformaties);

		standplaats.setAlgemeneOpmerking(standplaatsDTO.getAlgemeneOpmerking());
		standplaats.setFotoOpmerking(standplaatsDTO.getFotoOpmerking());

		List<Integer> badgeIds = new ArrayList<Integer>();
		List<Badge> badges = new ArrayList<Badge>();

		Set<Inschrijving> inschrijvingen = new HashSet<Inschrijving>();
		InschrijvingAssembler inschrijvingAssembler = new InschrijvingAssembler();
		InschrijvingPersoonAssembler ipAssembler = new InschrijvingPersoonAssembler();
		BadgeAssembler badgeAssembler = new BadgeAssembler();

		List<Integer> persoonIds = new ArrayList<Integer>();
		List<Persoon> personen = new ArrayList<Persoon>();
		for (InschrijvingDTO inschrijvingDTO : standplaatsDTO.getInschrijvingen()) {
			Inschrijving inschrijving = inschrijvingAssembler.getInschrijving(inschrijvingDTO);
			inschrijving.setInschrijvingPersonen(new HashSet<InschrijvingPersoon>());
			for (InschrijvingPersoonDTO inschrijvingPersoonDTO : inschrijvingDTO.getInschrijvingPersonen()) {
				InschrijvingPersoon inschrijvingPersoon = ipAssembler.getInschrijvingPersoon(inschrijvingPersoonDTO);
				if (persoonIds.contains(inschrijvingPersoonDTO.getPersoon().getId())) {
					inschrijvingPersoon.setPersoon(personen.get(persoonIds.indexOf(inschrijvingPersoonDTO.getPersoon()
							.getId())));
				} else {
					persoonIds.add(inschrijvingPersoonDTO.getPersoon().getId());
					personen.add(inschrijvingPersoon.getPersoon());
				}
				// if (inschrijving.getBadge() != null) {
				// if (badgeIds.contains(inschrijvingDTO.getBadge().getId())) {
				// inschrijving.setBadge(badges.get(badgeIds.indexOf(inschrijvingDTO.getBadge().getId())));
				// } else {
				// Badge badge = badgeAssembler.getBadge(inschrijvingDTO.getBadge());
				// inschrijving.setBadge(badge);
				// badgeIds.add(badge.getId());
				// badges.add(badge);
				// }
				// }
				inschrijving.getInschrijvingPersonen().add(inschrijvingPersoon);
			}
			if (inschrijving.getBadge() != null) {
				if (badgeIds.contains(inschrijvingDTO.getBadge().getId())) {
					inschrijving.setBadge(badges.get(badgeIds.indexOf(inschrijvingDTO.getBadge().getId())));
				} else {
					badgeIds.add(inschrijving.getBadge().getId());
					badges.add(inschrijving.getBadge());
				}
			}
			inschrijvingen.add(inschrijving);
		}
		standplaats.setInschrijvingen(inschrijvingen);

		Set<FactuurDetail> factuurDetails = new HashSet<FactuurDetail>();
		FactuurDetailAssembler factuurDetailAssembler = new FactuurDetailAssembler();
		for (FactuurDetailDTO factuurDetailDTO : standplaatsDTO.getFactuurDetails()) {
			factuurDetails.add(factuurDetailAssembler.getFactuurDetail(factuurDetailDTO));
		}
		standplaats.setFactuurDetails(factuurDetails);

		BetalerAssembler standplaatsBetalerAssembler = new BetalerAssembler();
		Set<Betaler> standplaatsBetalers = new HashSet<Betaler>();
		PersoonAssembler persoonAssembler = new PersoonAssembler();
		for (BetalerDTO betalerDTO : standplaatsDTO.getBetalers()) {
			Betaler betaler = standplaatsBetalerAssembler.getStandplaatsBetaler(betalerDTO);
			PersoonDTO hoofdbetaler = betalerDTO.getHoofdbetaler();
			PersoonDTO partnerBetaler = betalerDTO.getBetaler();
			if (hoofdbetaler != null) {
				if (persoonIds.contains(hoofdbetaler.getId())) {
					betaler.setHoofdbetaler(personen.get(persoonIds.indexOf(hoofdbetaler.getId())));
				} else {
					persoonIds.add(hoofdbetaler.getId());
					Persoon persoon = persoonAssembler.getPersoon(hoofdbetaler);
					personen.add(persoon);
				}
			}
			if (partnerBetaler != null) {
				if (persoonIds.contains(partnerBetaler.getId())) {
					betaler.setBetaler(personen.get(persoonIds.indexOf(partnerBetaler.getId())));
				} else {
					persoonIds.add(partnerBetaler.getId());
					Persoon persoon = persoonAssembler.getPersoon(partnerBetaler);
					personen.add(persoon);
				}
			}
			standplaatsBetalers.add(betaler);
		}
		standplaats.setBetalers(standplaatsBetalers);

		if (standplaatsDTO.getBadge() != null) {
			if (null != standplaatsDTO.getBadge().getId()) {
				if (badgeIds.contains(standplaatsDTO.getBadge().getId())) {
					standplaats.setBadge(badges.get(badgeIds.indexOf(standplaatsDTO.getBadge().getId())));
				} else {
					Badge badge = badgeAssembler.getBadge(standplaatsDTO.getBadge());
					standplaats.setBadge(badge);
					badgeIds.add(badge.getId());
					badges.add(badge);
				}
			} else {
				standplaats.setBadge(badgeAssembler.getBadge(standplaatsDTO.getBadge()));
			}
		}

		standplaats.setKostprijs(standplaatsDTO.getKostprijs());
		standplaats.setTotaal(standplaatsDTO.getTotaal());
		standplaats.setGeschiedenisFlag(standplaatsDTO.getGeschiedenisFlag());
		GeschiedenisAssembler geschiedenisAssembler = new GeschiedenisAssembler();
		Set<Geschiedenis> geschiedenisPunten = new HashSet<Geschiedenis>();
		for (GeschiedenisDTO geschiedenisDTO : standplaatsDTO.getGeschiedenisPunten()) {
			geschiedenisPunten.add(geschiedenisAssembler.getGeschiedenis(geschiedenisDTO));
		}
		standplaats.setGeschiedenisPunten(geschiedenisPunten);
		standplaats.setTeBetalenSom(standplaatsDTO.getTeBetalenSom());
		standplaats.setBetaaldeSom(standplaatsDTO.getBetaaldeSom());

		return standplaats;
	}

	public StandplaatsDTO getStandplaatsDTOWithFacturenAndFactuurDetails(Standplaats standplaats) {
		StandplaatsDTO standplaatsDTO = new StandplaatsDTO();
		standplaatsDTO.setId(standplaats.getId());
		standplaatsDTO.setPlaatsgroep(standplaats.getPlaatsgroep());
		standplaatsDTO.setPlaatsnummer(standplaats.getPlaatsnummer());
		standplaatsDTO.setGeschiedenis(standplaats.getGeschiedenis());
		standplaatsDTO.setGeschiedenisEditor(standplaats.getGeschiedenisEditor());
		standplaatsDTO.setGeschiedenisDatum(standplaats.getGeschiedenisDatum());
		Set<GrondInformatieDTO> grondInformatieDTOs = new HashSet<GrondInformatieDTO>();
		GrondInformatieAssembler grondInformatieAssembler = new GrondInformatieAssembler();
		for (GrondInformatie grondInformatie : standplaats.getGrondInformaties()) {
			GrondInformatieDTO grondInformatieDTO = grondInformatieAssembler.getGrondInformatieDTO(grondInformatie);
			grondInformatieDTOs.add(grondInformatieDTO);
		}
		standplaatsDTO.setGrondInformaties(grondInformatieDTOs);
		if (standplaats.getBadge() != null) {
			BadgeAssembler badgeAssembler = new BadgeAssembler();
			standplaatsDTO.setBadge(badgeAssembler.getBadgeDTO(standplaats.getBadge()));
		}
		standplaatsDTO.setAlgemeneOpmerking(standplaats.getAlgemeneOpmerking());
		standplaatsDTO.setFotoOpmerking(standplaats.getFotoOpmerking());
		Set<FactuurDetailDTO> factuurDetailDTOs = new HashSet<FactuurDetailDTO>();
		FactuurDetailAssembler factuurDetailAssembler = new FactuurDetailAssembler();
		for (FactuurDetail factuurDetail : standplaats.getFactuurDetails()) {
			factuurDetailDTOs.add(factuurDetailAssembler.getFactuurDetailDTO(factuurDetail));
		}
		standplaatsDTO.setFactuurDetails(factuurDetailDTOs);

		BetalerAssembler standplaatsBetalerAssembler = new BetalerAssembler();
		Set<BetalerDTO> standplaatsBetalerDTOs = new HashSet<BetalerDTO>();
		for (Betaler standplaatsBetaler : standplaats.getBetalers()) {
			standplaatsBetalerDTOs.add(standplaatsBetalerAssembler.getStandplaatsBetalerDTO(standplaatsBetaler));
		}
		standplaatsDTO.setBetalers(standplaatsBetalerDTOs);
		standplaatsDTO.setKostprijs(standplaats.getKostprijs());
		standplaatsDTO.setTotaal(standplaats.getTotaal());
		standplaatsDTO.setGeschiedenisFlag(standplaats.getGeschiedenisFlag());
		standplaatsDTO.setTeBetalenSom(standplaats.getTeBetalenSom());
		standplaatsDTO.setBetaaldeSom(standplaats.getBetaaldeSom());

		return standplaatsDTO;
	}

	public StandplaatsDTO getStandplaatsDTOMetInschrijvingenEnPersonen(Standplaats standplaats) {
		StandplaatsDTO standplaatsDTO = new StandplaatsDTO();
		standplaatsDTO.setId(standplaats.getId());
		standplaatsDTO.setPlaatsgroep(standplaats.getPlaatsgroep());
		standplaatsDTO.setPlaatsnummer(standplaats.getPlaatsnummer());
		standplaatsDTO.setGeschiedenis(standplaats.getGeschiedenis());
		standplaatsDTO.setGeschiedenisEditor(standplaats.getGeschiedenisEditor());
		standplaatsDTO.setGeschiedenisDatum(standplaats.getGeschiedenisDatum());
		Set<GrondInformatieDTO> grondInformatieDTOs = new HashSet<GrondInformatieDTO>();
		GrondInformatieAssembler grondInformatieAssembler = new GrondInformatieAssembler();
		for (GrondInformatie grondInformatie : standplaats.getGrondInformaties()) {
			GrondInformatieDTO grondInformatieDTO = grondInformatieAssembler.getGrondInformatieDTO(grondInformatie);
			grondInformatieDTOs.add(grondInformatieDTO);
		}
		standplaatsDTO.setGrondInformaties(grondInformatieDTOs);
		if (standplaats.getBadge() != null) {
			BadgeAssembler badgeAssembler = new BadgeAssembler();
			standplaatsDTO.setBadge(badgeAssembler.getBadgeDTO(standplaats.getBadge()));
		}
		standplaatsDTO.setAlgemeneOpmerking(standplaats.getAlgemeneOpmerking());
		standplaatsDTO.setFotoOpmerking(standplaats.getFotoOpmerking());

		Set<InschrijvingDTO> inschrijvingDTOs = new HashSet<InschrijvingDTO>();
		InschrijvingAssembler inschrijvingAssembler = new InschrijvingAssembler();
		InschrijvingPersoonAssembler ipAssembler = new InschrijvingPersoonAssembler();
		List<Integer> persoonIds = new ArrayList<Integer>();
		List<PersoonDTO> persoonDTOs = new ArrayList<PersoonDTO>();
		for (Inschrijving inschrijving : standplaats.getInschrijvingen()) {
			InschrijvingDTO inschrijvingDTO = inschrijvingAssembler.getInschrijvingDTO(inschrijving);
			inschrijvingDTO.setInschrijvingPersonen(new HashSet<InschrijvingPersoonDTO>());
			for (InschrijvingPersoon inschrijvingPersoon : inschrijving.getInschrijvingPersonen()) {
				InschrijvingPersoonDTO inschrijvingPersoonDTO = ipAssembler
						.getInschrijvingPersoonDTO(inschrijvingPersoon);
				if (persoonIds.contains(inschrijvingPersoon.getPersoon().getId())) {
					inschrijvingPersoonDTO.setPersoon(persoonDTOs.get(persoonIds.indexOf(inschrijvingPersoon
							.getPersoon().getId())));
				} else {
					persoonIds.add(inschrijvingPersoon.getPersoon().getId());
					persoonDTOs.add(inschrijvingPersoonDTO.getPersoon());
				}
				inschrijvingDTO.getInschrijvingPersonen().add(inschrijvingPersoonDTO);
			}
			inschrijvingDTOs.add(inschrijvingDTO);
		}
		standplaatsDTO.setInschrijvingen(inschrijvingDTOs);

		BetalerAssembler standplaatsBetalerAssembler = new BetalerAssembler();
		Set<BetalerDTO> standplaatsBetalerDTOs = new HashSet<BetalerDTO>();
		for (Betaler standplaatsBetaler : standplaats.getBetalers()) {
			standplaatsBetalerDTOs.add(standplaatsBetalerAssembler.getStandplaatsBetalerDTO(standplaatsBetaler));
		}
		standplaatsDTO.setBetalers(standplaatsBetalerDTOs);
		standplaatsDTO.setKostprijs(standplaats.getKostprijs());
		standplaatsDTO.setTotaal(standplaats.getTotaal());
		standplaatsDTO.setGeschiedenisFlag(standplaats.getGeschiedenisFlag());
		standplaatsDTO.setTeBetalenSom(standplaats.getTeBetalenSom());
		standplaatsDTO.setBetaaldeSom(standplaats.getBetaaldeSom());

		return standplaatsDTO;
	}

	public StandplaatsDTO getStandplaatsDTOMetGeschiedenis(Standplaats standplaats) {
		StandplaatsDTO standplaatsDTO = new StandplaatsDTO();
		standplaatsDTO.setId(standplaats.getId());
		standplaatsDTO.setPlaatsgroep(standplaats.getPlaatsgroep());
		standplaatsDTO.setPlaatsnummer(standplaats.getPlaatsnummer());
		standplaatsDTO.setGeschiedenis(standplaats.getGeschiedenis());
		standplaatsDTO.setGeschiedenisEditor(standplaats.getGeschiedenisEditor());
		standplaatsDTO.setGeschiedenisDatum(standplaats.getGeschiedenisDatum());
		standplaatsDTO.setAlgemeneOpmerking(standplaats.getAlgemeneOpmerking());
		standplaatsDTO.setFotoOpmerking(standplaats.getFotoOpmerking());

		standplaatsDTO.setKostprijs(standplaats.getKostprijs());
		standplaatsDTO.setTotaal(standplaats.getTotaal());
		standplaatsDTO.setGeschiedenisFlag(standplaats.getGeschiedenisFlag());
		GeschiedenisAssembler geschiedenisAssembler = new GeschiedenisAssembler();
		Set<GeschiedenisDTO> geschiedenisPunten = new HashSet<GeschiedenisDTO>();
		for (Geschiedenis geschiedenis : standplaats.getGeschiedenisPunten()) {
			geschiedenisPunten.add(geschiedenisAssembler.getGeschiedenisDTO(geschiedenis));
		}
		standplaatsDTO.setGeschiedenisPunten(geschiedenisPunten);
		standplaatsDTO.setTeBetalenSom(standplaats.getTeBetalenSom());
		standplaatsDTO.setBetaaldeSom(standplaats.getBetaaldeSom());
		return standplaatsDTO;
	}

}
