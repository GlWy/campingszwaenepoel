package be.camping.campingzwaenepoel.service.impl;

import be.camping.campingzwaenepoel.common.constants.Constant;
import be.camping.campingzwaenepoel.common.constants.FotoConstant;
import be.camping.campingzwaenepoel.common.enums.*;
import be.camping.campingzwaenepoel.domain.model.*;
import be.camping.campingzwaenepoel.domain.repository.InschrijvingRepository;
import be.camping.campingzwaenepoel.domain.repository.PersoonRepository;
import be.camping.campingzwaenepoel.domain.repository.StandplaatsRepository;
import be.camping.campingzwaenepoel.domain.service.StandplaatsDomainService;
import be.camping.campingzwaenepoel.service.ConfiguratieService;
import be.camping.campingzwaenepoel.service.FactuurDetailService;
import be.camping.campingzwaenepoel.service.StandplaatsService;
import be.camping.campingzwaenepoel.service.cache.CacheManager;
import be.camping.campingzwaenepoel.service.threads.betaling.BetalingNieuwJaarThread;
import be.camping.campingzwaenepoel.service.transfer.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.net.UnknownHostException;
import java.util.*;

@Service("StandplaatsService")
@Transactional
public class StandplaatsServiceImpl implements StandplaatsService {

	private static Logger logger = Logger.getLogger(StandplaatsServiceImpl.class);

	@Autowired
	private StandplaatsRepository standplaatsRepository;

	@Autowired
	private InschrijvingRepository inschrijvingRepository;

	@Autowired
	private PersoonRepository persoonRepository;

	@Override
	public StandplaatsDTO getStandplaats(int id) {
		Standplaats standplaats = standplaatsRepository.findById(id);
		StandplaatsAssembler assembler = new StandplaatsAssembler();
		StandplaatsDTO standplaatsDTO = assembler.getStandplaatsDTO(standplaats);
		return standplaatsDTO;
	}

	@Override
	public StandplaatsDTO getStandplaatsGrond() {
		Standplaats standplaats = standplaatsRepository.findStandplaats(0, 1);
		StandplaatsDTO standplaatsDTO = null;
		if (standplaats != null) {
			StandplaatsAssembler assembler = new StandplaatsAssembler();
			standplaatsDTO = assembler.getStandplaatsDTO(standplaats);
		}
		return standplaatsDTO;
	}

	@Override
	public StandplaatsDTO getVolgendeStandplaats(int id, boolean offset) {
		StandplaatsDTO grondDTO = null;
		Standplaats grond = null;
		if (offset) {
			grond = standplaatsRepository.findStandplaats(id, Constant.STANDPLAATS_OFFSET);
		} else {
			grond = standplaatsRepository.findStandplaats(id, 1);
		}
		if (grond != null) {
			StandplaatsAssembler assembler = new StandplaatsAssembler();
			grondDTO = assembler.getStandplaatsDTO(grond);
		}
		return grondDTO;
	}

	@Override
	public StandplaatsDTO getVorigeStandplaats(int id, boolean offset) {
		Standplaats grond = null;
		StandplaatsDTO grondDTO = new StandplaatsDTO();
		if (offset) {
			grond = standplaatsRepository.findStandplaats(id, -Constant.STANDPLAATS_OFFSET);
		} else {
			grond = standplaatsRepository.findStandplaats(id, -1);
		}
		if (grond != null) {
			StandplaatsAssembler assembler = new StandplaatsAssembler();
			grondDTO = assembler.getStandplaatsDTO(grond);
		}
		return grondDTO;
	}

	@Override
	public StandplaatsDTO zoekStandplaats(String plaatsgroep, int plaatsnummer) {
		Standplaats standplaats = standplaatsRepository.zoekStandplaats(plaatsgroep, plaatsnummer);
		StandplaatsDTO standplaatsDTO = null;
		if (standplaats != null && standplaats.getId() != 0) {
			StandplaatsAssembler assembler = new StandplaatsAssembler();
			standplaatsDTO = assembler.getStandplaatsDTO(standplaats);
		}
		return standplaatsDTO;
	}

	@Override
	public void storeStandplaatsen() {
		// TODO
	}

	@Override
	public StandplaatsDTO storeStandplaats(StandplaatsDTO standplaatsDTO) {
		StandplaatsAssembler assembler = new StandplaatsAssembler();
		Standplaats standplaats = assembler.getStandplaats(standplaatsDTO);
		standplaats = standplaatsRepository.store(standplaats);
		return assembler.getStandplaatsDTOWithFacturenAndFactuurDetails(standplaats);
	}

	@Override
	public List<String> getStandplaatsNamen() {
		if (CacheManager.getStandplaatsen() == null) {
			CacheManager.setStandplaatsen(standplaatsRepository.getStandplaatsen());

		}
		return CacheManager.getStandplaatsen();
	}

	@Override
	public StandplaatsDTO getStandplaatsWithFacturenAndFactuurDetails(int standplaatsId) {
		Standplaats standplaats = standplaatsRepository.getStandplaatsWithFactuurDetails(standplaatsId);
		StandplaatsAssembler standplaatsAssembler = new StandplaatsAssembler();
		return standplaatsAssembler.getStandplaatsDTOWithFacturenAndFactuurDetails(standplaats);
	}

	@Override
	public StandplaatsDTO getStandplaatsMetInschrijvingenEnPersonen(int standplaatsId) {
		Standplaats standplaats = standplaatsRepository.getStandplaatsWithInschrijvingenEnPersonen(standplaatsId);
		StandplaatsAssembler standplaatsAssembler = new StandplaatsAssembler();
		return standplaatsAssembler.getStandplaatsDTOMetInschrijvingenEnPersonen(standplaats);
	}

	@Override
	public List<InschrijvingDTO> getInschrijvingen(int standplaatsId, SoortHuurderEnum soortHuurder) {
		List<Inschrijving> inschrijvingen;
		if (soortHuurder != null) {
			inschrijvingen = inschrijvingRepository.findInschrijvingenVoorStandplaatsAndSoortHuurder(standplaatsId,
					soortHuurder);
		} else {
			inschrijvingen = inschrijvingRepository.findInschrijvingenVoorStandplaats(standplaatsId);
		}
		List<InschrijvingDTO> inschrijvingDTOs = new ArrayList<>();
		InschrijvingAssembler inschrijvingAssembler = new InschrijvingAssembler();
		for (Inschrijving inschrijving : inschrijvingen) {
			inschrijvingDTOs.add(inschrijvingAssembler.getInschrijvingDTOMetPersonen(inschrijving));
		}
		return inschrijvingDTOs;
	}

	@Override
	public InschrijvingDTO getInschrijvingVoorBadge(BadgeDTO badgeDTO) {
		Inschrijving inschrijving = inschrijvingRepository.findInschrijvingenVoorBadge(badgeDTO.getId());
		InschrijvingDTO inschrijvingDTO = null;
		if (inschrijving != null) {
			InschrijvingAssembler assembler = new InschrijvingAssembler();
			inschrijvingDTO = assembler.getInschrijvingDTO(inschrijving);
		}
		return inschrijvingDTO;
	}

	@Override
	public StandplaatsDTO removeBadgeVanInschrijving(InschrijvingDTO inschrijvingDTO) {
		InschrijvingAssembler assembler = new InschrijvingAssembler();
		Inschrijving inschrijving = assembler.getInschrijving(inschrijvingDTO);
		inschrijving.setBadge(null);
		inschrijvingRepository.store(inschrijving);
		Standplaats standplaats = standplaatsRepository.findById(inschrijving.getStandplaatsId());
		StandplaatsAssembler standplaatsAssembler = new StandplaatsAssembler();
		return standplaatsAssembler.getStandplaatsDTO(standplaats);
	}

	@Override
	public InschrijvingDTO getInschrijving(int inschrijvingId) {
		Inschrijving inschrijving = inschrijvingRepository.findById(inschrijvingId);
		InschrijvingAssembler assembler = new InschrijvingAssembler();
		return assembler.getInschrijvingDTOMetPersonen(inschrijving);
	}

	@Override
	public List<Integer> getStandplaatsIds() {
		return standplaatsRepository.getStandplaatsIds();
	}

	@Override
	public int findLaatseFichenummer() {
		return inschrijvingRepository.findLaatsteFichenummer();
	}

	@Override
	public List<Object[]> zoekStandplaatsEnFicheGegevens(Map<String, Object> zoekCriteria,
			Map<String, Object> projectionList) {
		return standplaatsRepository.zoekStandplaatsEnFicheGegevens(zoekCriteria, projectionList);
	}

	@Override
	public InschrijvingDTO getLaatsteInschrijving() {
		try {
			Inschrijving inschrijving = inschrijvingRepository.getLaatsteInschrijving();
			if (inschrijving != null) {
				InschrijvingAssembler assembler = new InschrijvingAssembler();
				return assembler.getInschrijvingDTOMetPersonen(inschrijving);
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Map<String, String> getBadgeVoorStandplaats(int id) {
		return standplaatsRepository.getBadgeVoorStandplaats(id);
	}

	@Override
	public List<Object[]> zoekNummerplaten(int jaar) {
		return standplaatsRepository.zoekNummerplaten(jaar);
	}

	@Override
	public List<Object[]> zoekOpmerkingenGrond(int nummer, boolean printAlles, boolean namenTonen) {
		return standplaatsRepository.zoekOpmerkingenGrond(nummer, printAlles, namenTonen);
	}

	@Override
	public void createBetalingNieuwJaar() {
		// List<Integer> betalerStandplaatsIds =
		// standplaatsRepository.zoekStandplaatsIdsMetBetaler();
		List<Integer> standplaatsIds = standplaatsRepository.getStandplaatsIds();
		for (int id : standplaatsIds) {
			Standplaats standplaats = standplaatsRepository.findById(id);
			if (standplaats.getBetalers().size() == 0)
				continue;
			FactuurDetail factuurDetail = new FactuurDetail();
			Calendar cal = Calendar.getInstance();
			cal.set(2015, 0, 1);
			factuurDetail.setDatum(cal.getTime());
			factuurDetail.setDatumAanmaak(System.nanoTime());
			factuurDetail.setBedrag(standplaats.getKostprijs());
			factuurDetail.setAardBetaling(BetalingEnum.NOGTEBETALEN);
			standplaats.addFactuurDetail(factuurDetail);
			standplaatsRepository.store(standplaats);
		}
	}

	@Override
	public void convertInschrijving() {
		List<Integer> standplaatsen = standplaatsRepository.findStandplaatsen();
		boolean debug = false;
		for (Integer standplaatsId : standplaatsen) {
			// Haal standplaats op
			Standplaats standplaats = standplaatsRepository.getStandplaatsWithInschrijvingenEnPersonen(standplaatsId);
			List<Object[]> badgetoewijzingen = standplaatsRepository
					.haalBadgetoewijzigenOpVoorStandplaats(standplaatsId);
			List<Persoon> personen = new ArrayList<Persoon>();
			List<Integer> persoonIds = new ArrayList<Integer>();
			for (Betaler betaler : standplaats.getBetalers()) {
				if (betaler.getHoofdbetaler() != null) {
					personen.add(betaler.getHoofdbetaler());
					persoonIds.add(betaler.getHoofdbetaler().getId());
				}
				if (betaler.getBetaler() != null) {
					personen.add(betaler.getBetaler());
					persoonIds.add(betaler.getBetaler().getId());
				}
			}
			for (Object[] o : badgetoewijzingen) {
				try {
					// haal de registratie op voor deze Badgetoewijzing
					List<Integer> registraties = standplaatsRepository
							.findRegistratiesVoorBadgetoewijzing((Integer) o[0]);
					if (registraties != null) {
						for (Integer koppelId : registraties) {
							// haal koppel op voor deze registratie
							Inschrijving inschrijving = new Inschrijving();
							// bepaal datum van
							Date dateVan = (Date) o[3];
							Calendar calVan = Calendar.getInstance();
							calVan.setTime(dateVan);
							if (calVan.get(Calendar.YEAR) < 2010)
								continue;
							inschrijving.setDateVan(dateVan);
							// bepaal datum tot
							if (o[4] != null && o[4] != "") {
								Date dateTot = (Date) o[4];
								inschrijving.setDateTot(dateTot);
							}
							// bepaal soort inschrijving
							int badgetype = (Integer) o[1];
							if (badgetype == 1) {
								inschrijving.setSoorthuurder(SoortHuurderEnum.VAST);
							} else if (badgetype == 2) {
								inschrijving.setSoorthuurder(SoortHuurderEnum.LOS);
							}
							Object[] koppel = standplaatsRepository.findKoppelVoorRegistratie(koppelId);
							if (koppel != null) {
								if (koppel[2] == null) {
									continue;
								}
								Persoon persoon1 = persoonRepository.findById((Integer) koppel[1]);
								if (!debug && persoon1 == null)
									continue; // voor overname zijn er verwijzingen naar koppels die wellicht niet
								// bestaan
								if (persoonIds.contains(persoon1.getId())) {
									persoon1 = personen.get(persoonIds.indexOf(persoon1.getId()));
								} else {
									persoonIds.add(persoon1.getId());
									personen.add(persoon1);
								}
								Persoon persoon2 = null;
								try {
									if (koppel[3] != null && koppel[3] != "") {
										persoon2 = persoonRepository.findById((Integer) koppel[3]);
										if (persoonIds.contains(persoon2.getId())) {
											persoon2 = personen.get(persoonIds.indexOf(persoon2.getId()));
										} else {
											persoonIds.add(persoon2.getId());
											personen.add(persoon2);
										}
									}
								} catch (Exception e) {

								}
								InschrijvingPersoon inschrijvingPersoon1 = new InschrijvingPersoon();
								inschrijvingPersoon1.setInschrijvingDatum(System.nanoTime());
								inschrijvingPersoon1.setPersoon(persoon1);
								inschrijvingPersoon1.setGezinsPositie(GezinsPositieEnum.HOOFD);
								inschrijvingPersoon1.setHuurdersPositie(
										HuurderPositieEnum.getHuurdersPositieEnumVoorOudProgramma((Integer) koppel[2]));
								inschrijving.addInschrijvingPersoon(inschrijvingPersoon1);
								if (persoon2 != null) {
									InschrijvingPersoon inschrijvingPersoon2 = new InschrijvingPersoon();
									inschrijvingPersoon2.setInschrijvingDatum(System.nanoTime());
									inschrijvingPersoon2.setPersoon(persoon2);
									inschrijvingPersoon2.setGezinsPositie(GezinsPositieEnum.ECHTGENOTE);
									if (!debug && koppel[4] == null) {
										inschrijvingPersoon2.setHuurdersPositie(HuurderPositieEnum.ANDERE);
									} else {
										inschrijvingPersoon2.setHuurdersPositie(HuurderPositieEnum
												.getHuurdersPositieEnumVoorOudProgramma((Integer) koppel[4]));
									}
									inschrijving.addInschrijvingPersoon(inschrijvingPersoon2);
								}
								// haal kinderen op van koppel
								List<Object[]> kinderen = standplaatsRepository.findKinderenVanKoppel(koppelId);
								if (kinderen != null) {
									for (Object[] oKind : kinderen) {
										if (!debug && oKind[0] == null)
											continue; // kind bestaat niet
										Persoon kind = persoonRepository.findById((Integer) oKind[0]);
										if (!debug && kind == null)
											continue;
										if (persoonIds.contains(kind.getId())) {
											kind = personen.get(persoonIds.indexOf(kind.getId()));
										} else {
											persoonIds.add(kind.getId());
											personen.add(kind);
										}
										InschrijvingPersoon inschrijvingKind = new InschrijvingPersoon();
										inschrijvingKind.setInschrijvingDatum(System.nanoTime());
										inschrijvingKind.setPersoon(kind);
										inschrijvingKind.setGezinsPositie(GezinsPositieEnum.KIND);
										if (!debug && oKind[1] == null) { // huurderspositie soms niet ingevuld
											inschrijvingKind.setHuurdersPositie(HuurderPositieEnum.ANDERE);
										} else {
											inschrijvingKind.setHuurdersPositie(HuurderPositieEnum
													.getHuurdersPositieEnumVoorOudProgramma((Integer) oKind[1]));
										}
										inschrijving.addInschrijvingPersoon(inschrijvingKind);
									}
								}
								standplaats.addInschrijving(inschrijving);
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.err.println("problem converting inschrijving voor standplaats " + standplaatsId
							+ " van Badgetoewijzing " + o[0]);
				}
			}
			try {
				standplaatsRepository.store(standplaats);
			} catch (Exception e) {
				System.err.println("problem opslaan standplaats " + standplaatsId);
			}
		}
	}

	@Override
	public void convertFacturatie() {
		List<Integer> standplaatsIds = getStandplaatsIds();
		for (int id : standplaatsIds) {
			try {
				Standplaats standplaats = standplaatsRepository.findById(id);

				Object[] betaling = standplaatsRepository.getBetalingenAccessVoorStandplaats(id);
				FactuurDetail factuurDetailOrig = new FactuurDetail();
				Calendar calOrig = Calendar.getInstance();
				calOrig.set(2011, 0, 1);
				factuurDetailOrig.setDatum(calOrig.getTime());
				factuurDetailOrig.setDatumAanmaak(System.nanoTime());
				factuurDetailOrig.setBedrag((Double) betaling[1]);
				factuurDetailOrig.setAardBetaling(BetalingEnum.NOGTEBETALEN);
				standplaats.addFactuurDetail(factuurDetailOrig);

				List<Object[]> facturatie = standplaatsRepository.getOudeFacturatieVoorStandplaats(id);
				for (Object[] o : facturatie) {
					Date date = (Date) o[2];
					Calendar cal = Calendar.getInstance();
					cal.setTime(date);
					if (cal.get(Calendar.YEAR) < 2010)
						continue;
					FactuurDetail factuurDetail = new FactuurDetail();
					factuurDetail.setDatumAanmaak(System.nanoTime());
					factuurDetail.setDatum((Date) o[2]);
					Double d = (Double) o[1];
					switch ((Integer) o[3]) {
					case 1: // Rappel
						factuurDetail.setRappel(d);
						factuurDetail.setAardBetaling(BetalingEnum.NOGTEBETALEN);
						break;
					case 2: // Betaald
						factuurDetail.setUitstelkosten(d);
						factuurDetail.setAardBetaling(BetalingEnum.BETAALD);
						break;
					case 3: // Uitstel
						factuurDetail.setUitstelkosten(d);
						factuurDetail.setAardBetaling(BetalingEnum.NOGTEBETALEN);
						break;
					default:
						break;
					}
					standplaats.addFactuurDetail(factuurDetail);
				}
				List<Object[]> voorschotten = standplaatsRepository
						.getVoorschottenAccessVoorBetaling((Integer) betaling[0]);
				for (Object[] o : voorschotten) {
					// bedrag - datum - status
					FactuurDetail factuurDetail = new FactuurDetail();
					factuurDetail.setDatumAanmaak(System.nanoTime());
					factuurDetail.setDatum((Date) o[1]);
					Double d = (Double) o[0];
					switch ((Integer) o[2]) {
					case 1: // Rappel
						factuurDetail.setBedrag(d);
						factuurDetail.setAardBetaling(BetalingEnum.BETAALD);
						break;
					case 2: // Betaald
						factuurDetail.setBedrag(d);
						factuurDetail.setAardBetaling(BetalingEnum.UITSTEL);
						break;
					default:
						break;
					}
					standplaats.addFactuurDetail(factuurDetail);
				}
				double totaal = 0;
				for (FactuurDetail factuurDetail : standplaats.getFactuurDetails()) {
					Calendar now = Calendar.getInstance();
					if (factuurDetail.getAardBetaling().equals(BetalingEnum.UITSTEL)) {
						Date datum = factuurDetail.getDatum();
						Calendar cal = Calendar.getInstance();
						cal.setTime(datum);
						int jaar = cal.get(Calendar.YEAR);
						int maand = cal.get(Calendar.MONTH);
						int dag = cal.get(Calendar.DATE);
						if (cal.after(now) || ((jaar == now.get(Calendar.YEAR)) && maand == now.get(Calendar.MONTH))
								&& dag == now.get(Calendar.DATE)) {
							totaal = 0;
							break;
						}
					} else if (factuurDetail.getAardBetaling().equals(BetalingEnum.BETAALD)) {
						double tmpTotaal = factuurDetail.getBedrag() + factuurDetail.getAndereKosten()
								+ factuurDetail.getRappel() + factuurDetail.getUitstelkosten();
						totaal -= tmpTotaal;
					} else if (factuurDetail.getAardBetaling().equals(BetalingEnum.NOGTEBETALEN)) {
						double tmpTotaal = factuurDetail.getBedrag() + factuurDetail.getAndereKosten()
								+ factuurDetail.getRappel() + factuurDetail.getUitstelkosten();
						totaal += tmpTotaal;
					}
				}
				totaal = (totaal < 0.01) ? 0 : totaal;
				standplaats.setTotaal(totaal);

				if (standplaats.getFactuurDetails().size() > 0) {
					standplaatsRepository.store(standplaats);
				}
			} catch (Exception e) {
				System.err.println("problem converting facturatie voor standplaats " + id);
			}
		}
	}

	@Override
	public void convertBetalers() {
		List<Integer> standplaatsIds = getStandplaatsIds();
		for (int id : standplaatsIds) {
			try {
				Standplaats standplaats = standplaatsRepository.findById(id);
				List<Integer> betalers = standplaatsRepository.getBetalersAccessVoorStandplaats(id);
				if (betalers.size() == 0)
					continue;
				Betaler betaler = new Betaler();
				Calendar now = Calendar.getInstance();
				betaler.setDatumVan(now.getTime());
				for (int i = 0; i < betalers.size(); i++) {
					int persoonId = betalers.get(i);
					if (i == 0) {
						Persoon persoon = persoonRepository.findById(persoonId);
						betaler.setHoofdbetaler(persoon);
					} else if (i == 1) {
						Persoon persoon = persoonRepository.findById(persoonId);
						betaler.setBetaler(persoon);
					}
				}
				standplaats.addBetaler(betaler);
				standplaatsRepository.store(standplaats);
			} catch (Exception e) {
				System.err.println("problem converting betalers voor standplaats " + id);
			}
		}
	}

	@Override
	public void convertBetalingen() {
		List<Integer> standplaatsIds = getStandplaatsIds();
		for (int id : standplaatsIds) {
			try {
				Standplaats standplaats = standplaatsRepository.findById(id);
				List<Object[]> facturatie = standplaatsRepository.getOudeFacturatieVoorStandplaats(id);
				for (Object[] o : facturatie) {
					FactuurDetail factuurDetail = new FactuurDetail();
					factuurDetail.setDatumAanmaak(System.nanoTime());
					factuurDetail.setDatum((Date) o[2]);
					BigDecimal bd = (BigDecimal) o[1];
					Double d = Double.parseDouble(bd.toString());
					switch ((Integer) o[3]) {
					case 1: // Rappel
						factuurDetail.setRappel(d);
						factuurDetail.setAardBetaling(BetalingEnum.NOGTEBETALEN);
						break;
					case 2: // Betaald
						if (d == 10) {
							factuurDetail.setRappel(d);
						} else if (d == 30) {
							factuurDetail.setUitstelkosten(d);
						} else {
							factuurDetail.setBedrag(d);
						}
						factuurDetail.setAardBetaling(BetalingEnum.BETAALD);
						break;
					case 3: // Uitstel
						factuurDetail.setUitstelkosten(d);
						factuurDetail.setAardBetaling(BetalingEnum.NOGTEBETALEN);
						break;
					default:
						break;
					}
					standplaats.addFactuurDetail(factuurDetail);
				}
				if (facturatie.size() > 0) {
					standplaatsRepository.store(standplaats);
				}
			} catch (Exception e) {
				System.err.println("problem converting facturatie voor standplaats " + id);
			}
		}
	}

	@Override
	public List<Date> getInschrijvingenMetBadge(int standplaatsId) {
		List<Date> inschrijvingen = standplaatsRepository.getInschrijvingenMetBadge(standplaatsId);
		/*
		 * List<InschrijvingDTO> inschrijvingDTOs = new ArrayList<InschrijvingDTO>();
		 * InschrijvingAssembler inschrijvingAssembler = new InschrijvingAssembler();
		 * for (Inschrijving inschrijving : inschrijvingen) {
		 * inschrijvingDTOs.add(inschrijvingAssembler.getInschrijvingDTOMetPersonen(
		 * inschrijving)); } return inschrijvingDTOs;
		 */
		return inschrijvingen;
	}

	@Override
	public StandplaatsDTO getsStandplaatsMetGeschiedenis(int standplaatsId) {
		Standplaats standplaats = standplaatsRepository.findById(standplaatsId);
		StandplaatsAssembler standplaatsAssembler = new StandplaatsAssembler();
		StandplaatsDTO standplaatsDTO = standplaatsAssembler.getStandplaatsDTOMetGeschiedenis(standplaats);
		return standplaatsDTO;
	}

	@Override
	public void removeGrondInformatie(int nummer) {
		standplaatsRepository.removeGrondInformatie(nummer);
	}

	@Override
	public void berekenFacturatie() {
		List<Integer> standplaatsIds = standplaatsRepository.getStandplaatsIds();
		for (Integer id : standplaatsIds) {
			try {
				Standplaats standplaats = standplaatsRepository.getStandplaatsWithFactuurDetails(id);
				double totaal = 0;
				for (FactuurDetail factuurDetail : standplaats.getFactuurDetails()) {
					try {
						if (!BetalingEnum.UITSTEL.equals(factuurDetail.getAardBetaling())) {
							double tmpTotaal = factuurDetail.getBedrag() + factuurDetail.getUitstelkosten()
									+ factuurDetail.getRappel() + factuurDetail.getAndereKosten();
							if (BetalingEnum.BETAALD.equals(factuurDetail.getAardBetaling())) {
								tmpTotaal *= -1;
							}
							totaal += tmpTotaal;
						}
					} catch (Exception e) {
						logger.error(e.getMessage());
					}
				}
				if (totaal < 0.1)
					totaal = 0;
				standplaats.setTotaal(totaal);
				standplaatsRepository.store(standplaats);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
	}

	@Override
	public List<StandplaatsDTO> haalNietBetaaldeStandplaatsenOp(boolean uitstel) {
		List<Standplaats> standplaatsen = standplaatsRepository.zoekStandplaatsenNietBetaald();
		/*
		 * for (Standplaats standplaats : standplaatsen) {
		 * 
		 * }
		 */
		List<StandplaatsDTO> standplaatsDTOs = new ArrayList<StandplaatsDTO>();
		StandplaatsAssembler assembler = new StandplaatsAssembler();
		for (Standplaats standplaats : standplaatsen) {
			standplaatsDTOs.add(assembler.getStandplaatsDTOWithFacturenAndFactuurDetails(standplaats));
		}
		return standplaatsDTOs;
	}

	@Override
	public List<Map<String, Object>> zoekGrondprijzen() {
		return standplaatsRepository.zoekGrondprijzen();
	}

	@Override
	public List<Map<String, Object>> zoekBasisprijzen() {
		return standplaatsRepository.zoekBasisprijzen();
	}

	@Override
	public void adjustBasePrices() {
		standplaatsRepository.adjustBasePrices();
	}

	@Override
	public void storeNieuweGrondprijzen(List<Map<String, Double>> prijzen) {
		standplaatsRepository.storeNieuweGrondprijzen(prijzen);
	}

	@Override
	public void removeInschrijving(int inschrijvingId) {
		standplaatsRepository.removeInschrijving(inschrijvingId);
	}

	@Override
	public List<StandplaatsDTO> getStandplaatsenBetalingNietOk(BetalingRapportEnum rapportType) {
		List<Standplaats> standplaatsen = new ArrayList<Standplaats>();
		if (BetalingRapportEnum.UITSTEL.equals(rapportType) || BetalingRapportEnum.LANGEVERSIE.equals(rapportType)
				|| BetalingRapportEnum.KORTEVERSIE.equals(rapportType)
				|| BetalingRapportEnum.TABEL.equals(rapportType)) {

			standplaatsen = standplaatsRepository.zoekStandplaatsenNietBetaald();

			if (BetalingRapportEnum.LANGEVERSIE.equals(rapportType)) {
				standplaatsen = StandplaatsDomainService.getStandplaatsenBetalingenLangeVersie(standplaatsen);
			}

			if (BetalingRapportEnum.KORTEVERSIE.equals(rapportType)) {
				standplaatsen = StandplaatsDomainService.getStandplaatsenBetalingenKorteVersie(standplaatsen);
			}

			if (BetalingRapportEnum.TABEL.equals(rapportType)) {
				standplaatsen = StandplaatsDomainService.getStandplaatsenBetalingenTabel(standplaatsen);
			}

		} else if (BetalingRapportEnum.ANDERE.equals(rapportType)) {
			standplaatsen = standplaatsRepository.zoekStandplaatsenAndereNietBetaald();
			standplaatsen = StandplaatsDomainService.getStandplaatsenBetalingenLangeVersie(standplaatsen);
		}

		List<StandplaatsDTO> standplaatsDTOs = new ArrayList<StandplaatsDTO>();
		StandplaatsAssembler assembler = new StandplaatsAssembler();
		for (Standplaats standplaats : standplaatsen) {
			standplaatsDTOs.add(assembler.getStandplaatsDTOWithFacturenAndFactuurDetails(standplaats));
		}
		return standplaatsDTOs;
	}

	@Override
	public List<Object[]> findNummerplaten(int year) {
		return standplaatsRepository.findNummerplaten(year);
	}

	@Override
	public List<Object[]> getStandplaatsEnKostprijs() {
		return standplaatsRepository.getStandplaatsEnKostprijs();
	}

	@Override
	public boolean resetBetalingenNieuweGrondprijzen(FactuurDetailService factuurDetailService,
			final ConfiguratieService configuratieService, final int year) {
		FotoConstant.setPrijsAanpassingBusy(true);
		new BetalingNieuwJaarThread(this, factuurDetailService, configuratieService, year);
		return true;
	}

	@Override
	public boolean isResetBetalingBusy() {
		return FotoConstant.isPrijsAanpassingBusy();
	}

	@Override
	public void removeGeschiedenis(GeschiedenisDTO geschiedenisDTO) {
		GeschiedenisAssembler assembler = new GeschiedenisAssembler();
		standplaatsRepository.removeGeschiedenis(assembler.getGeschiedenis(geschiedenisDTO));
	}
}