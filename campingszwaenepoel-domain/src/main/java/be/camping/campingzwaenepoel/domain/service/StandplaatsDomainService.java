package be.camping.campingzwaenepoel.domain.service;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import be.camping.campingzwaenepoel.common.enums.BetalingEnum;
import be.camping.campingzwaenepoel.domain.model.FactuurDetail;
import be.camping.campingzwaenepoel.domain.model.Standplaats;

public class StandplaatsDomainService {

	public static List<Standplaats> getStandplaatsenBetalingenLangeVersie(List<Standplaats> standplaatsen) {
		List<Standplaats> retStandplaatsen = new ArrayList<Standplaats>();
		for (Standplaats standplaats : standplaatsen) {
			if (standplaats.getBetalers().size() == 0) continue;
			if (standplaats.getFactuurDetails().size() == 0) continue;
			Date now = Calendar.getInstance().getTime();
			double som = 0;
			for (FactuurDetail factuurDetail : standplaats.getFactuurDetails()) {
				if (BetalingEnum.NOGTEBETALEN.equals(factuurDetail.getAardBetaling())) {
					som += factuurDetail.getBedrag() + factuurDetail.getAndereKosten() + factuurDetail.getUitstelkosten()
							+ factuurDetail.getRappel();
				} else if (BetalingEnum.BETAALD.equals(factuurDetail.getAardBetaling())) {
					som -= (factuurDetail.getBedrag() + factuurDetail.getAndereKosten() + factuurDetail.getUitstelkosten()
					+ factuurDetail.getRappel());
				} else if (BetalingEnum.UITSTEL.equals(factuurDetail.getAardBetaling()) && factuurDetail.getDatum().after(now)) {
					som -= (factuurDetail.getBedrag() + factuurDetail.getAndereKosten() + factuurDetail.getUitstelkosten()
							+ factuurDetail.getRappel());
				}
			}
			if (som > 0) retStandplaatsen.add(standplaats);
		}
		
		return retStandplaatsen;
	}

	public static List<Standplaats> getStandplaatsenBetalingenKorteVersie(List<Standplaats> standplaatsen) {
		List<Standplaats> retStandplaatsen = new ArrayList<Standplaats>();
		for (Standplaats standplaats : standplaatsen) {
			if (standplaats.getBetalers().size() == 0) continue;
			if (standplaats.getFactuurDetails().size() == 0) continue;
			Date now = Calendar.getInstance().getTime();
			FactuurDetail factuurDetailTotaal = new FactuurDetail();
			factuurDetailTotaal.setAardBetaling(BetalingEnum.NOGTEBETALEN);
			factuurDetailTotaal.setDatum(now);
			double tebetalen = 0;
			double betaald = 0;
			double uitstel = 0;
			for (FactuurDetail tmpFactuurDetail : standplaats.getFactuurDetails()) {
				if ((BetalingEnum.UITSTEL.equals(tmpFactuurDetail.getAardBetaling()) && tmpFactuurDetail.getDatum().after(now))
						|| BetalingEnum.BETAALD.equals(tmpFactuurDetail.getAardBetaling())) {
					factuurDetailTotaal.setBedrag(factuurDetailTotaal.getBedrag() - tmpFactuurDetail.getBedrag());
					factuurDetailTotaal.setRappel(factuurDetailTotaal.getRappel() - tmpFactuurDetail.getRappel());
					factuurDetailTotaal.setUitstelkosten(factuurDetailTotaal.getUitstelkosten() - tmpFactuurDetail.getUitstelkosten());
					factuurDetailTotaal.setAndereKosten(factuurDetailTotaal.getAndereKosten() - tmpFactuurDetail.getAndereKosten());
					
					if (BetalingEnum.BETAALD.equals(tmpFactuurDetail.getAardBetaling())) {
						betaald += tmpFactuurDetail.getBedrag() + tmpFactuurDetail.getRappel() + tmpFactuurDetail.getUitstelkosten()
									+ tmpFactuurDetail.getAndereKosten();
					} else {
						uitstel += tmpFactuurDetail.getBedrag() + tmpFactuurDetail.getRappel() + tmpFactuurDetail.getUitstelkosten()
									+ tmpFactuurDetail.getAndereKosten();
					}
				} else if (BetalingEnum.NOGTEBETALEN.equals(tmpFactuurDetail.getAardBetaling())) {
					factuurDetailTotaal.setBedrag(factuurDetailTotaal.getBedrag() + tmpFactuurDetail.getBedrag());
					factuurDetailTotaal.setRappel(factuurDetailTotaal.getRappel() + tmpFactuurDetail.getRappel());
					factuurDetailTotaal.setUitstelkosten(factuurDetailTotaal.getUitstelkosten() + tmpFactuurDetail.getUitstelkosten());
					factuurDetailTotaal.setAndereKosten(factuurDetailTotaal.getAndereKosten() + tmpFactuurDetail.getAndereKosten());
					tebetalen += tmpFactuurDetail.getBedrag() + tmpFactuurDetail.getRappel() + tmpFactuurDetail.getUitstelkosten()
					+ tmpFactuurDetail.getAndereKosten();
				}
				
			}

			if (tebetalen - betaald - uitstel > 0.1) {
				standplaats.setTeBetalenSom(tebetalen);
				standplaats.setBetaaldeSom(betaald);
				standplaats.setFactuurDetails(new HashSet<FactuurDetail>());
				standplaats.getFactuurDetails().add(factuurDetailTotaal);
				retStandplaatsen.add(standplaats);
			}
		}
		
		return retStandplaatsen;
	}

	public static List<FactuurDetail> sortFactuurdetails(Set<FactuurDetail> factuurDetails, boolean copyUitstel) {
    	List<FactuurDetail> sortedFactuurDetails = new ArrayList<FactuurDetail>();
    	for (FactuurDetail tmpFactuurDetail : factuurDetails) {
    		try {
        		if (tmpFactuurDetail.getDatum() == null || tmpFactuurDetail.getDatum().toString() == "" 
					|| (tmpFactuurDetail.getAardBetaling() == null)) {
					continue;
				}
				if (!copyUitstel && (!BetalingEnum.BETAALD.equals(tmpFactuurDetail.getAardBetaling()))) continue;
				
				boolean added = false;
				boolean toBeAdded = false;
				for (FactuurDetail sortedFactuurDetail : sortedFactuurDetails) {
					if (tmpFactuurDetail.getDatum().before(sortedFactuurDetail.getDatum())) {
						toBeAdded = true;
					} else if (tmpFactuurDetail.getDatum().equals(sortedFactuurDetail.getDatum())) {
						if (tmpFactuurDetail.getDatumAanmaak() < sortedFactuurDetail.getDatumAanmaak()) {
							toBeAdded = true;
						}
					}
					if (toBeAdded) {
						int index = sortedFactuurDetails.indexOf(sortedFactuurDetail);
						sortedFactuurDetails.add(index, copyFactuurDetail(tmpFactuurDetail));
						added = true;
						break;
					}
				}
				if (!added) {
					sortedFactuurDetails.add(copyFactuurDetail(tmpFactuurDetail));
				}
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    	}
    	return sortedFactuurDetails;
    }
	
	public static List<Standplaats> getStandplaatsenBetalingenTabel(List<Standplaats> standplaatsen) {
		List<Standplaats> retStandplaatsen = new ArrayList<Standplaats>();
		for (Standplaats standplaats : standplaatsen) {
			if (standplaats.getBetalers().size() == 0) continue;
			if (standplaats.getFactuurDetails().size() == 0) continue;
			Date now = Calendar.getInstance().getTime();
			FactuurDetail factuurDetailTotaal = new FactuurDetail();
			double bedrag = 0;
			double uitstel = 0;
			double rappel = 0;
			double andere = 0;
			String opmerkingen = "";
			boolean add = false;
			for (FactuurDetail tmpFactuurDetail : standplaats.getFactuurDetails()) {
				if (BetalingEnum.NOGTEBETALEN.equals(tmpFactuurDetail.getAardBetaling())) {
					bedrag += tmpFactuurDetail.getBedrag();
					uitstel += tmpFactuurDetail.getUitstelkosten();
					rappel += tmpFactuurDetail.getRappel();
					andere += tmpFactuurDetail.getAndereKosten();
					if (!StringUtils.isEmpty(tmpFactuurDetail.getOpmerking())) {
						if (!StringUtils.isEmpty(opmerkingen)) {
							opmerkingen += "\n";
						}
						opmerkingen += tmpFactuurDetail.getOpmerking();
					}
				} else if (BetalingEnum.BETAALD.equals(tmpFactuurDetail.getAardBetaling())
						|| BetalingEnum.UITSTEL.equals(tmpFactuurDetail.getAardBetaling()) && now.before(tmpFactuurDetail.getDatum())) {
					bedrag -= tmpFactuurDetail.getBedrag();
					uitstel -= tmpFactuurDetail.getUitstelkosten();
					rappel -= tmpFactuurDetail.getRappel();
					andere -= tmpFactuurDetail.getAndereKosten();
				}
			}
			if (bedrag > 0.1) {
				factuurDetailTotaal.setBedrag(bedrag);
				add = true;
			}
			if (uitstel > 0) {
				factuurDetailTotaal.setUitstelkosten(uitstel);
				add = true;
			}
			if (rappel > 0) {
				factuurDetailTotaal.setRappel(rappel);
				add = true;
			}
			if (andere > 0) {
				factuurDetailTotaal.setAndereKosten(andere);
				add = true;
			}
			if (add) {
				standplaats.setFactuurDetails(new HashSet<FactuurDetail>());
				factuurDetailTotaal.setOpmerking(opmerkingen);
				standplaats.getFactuurDetails().add(factuurDetailTotaal);
				retStandplaatsen.add(standplaats);
			}
		}
		return retStandplaatsen;
	}

	private static List<Integer> checkUitstelVolledigVoorBetaling(Set<FactuurDetail> factuurDetails) {
		List<Integer> ids = new ArrayList<Integer>();
		DecimalFormat df = new DecimalFormat("#,##0.00");
		double bedrag = 0;
		double rappel = 0;
		double uitstel = 0;
		double andere = 0;
		for (FactuurDetail factuurDetail : factuurDetails) {
			if (BetalingEnum.NOGTEBETALEN.equals(factuurDetail.getAardBetaling())) {
				bedrag += factuurDetail.getBedrag();
				rappel += factuurDetail.getRappel();
				uitstel += factuurDetail.getUitstelkosten();
				andere += factuurDetail.getAndereKosten();
			} else if (BetalingEnum.NOGTEBETALEN.equals(factuurDetail.getAardBetaling())) {
				bedrag -= factuurDetail.getBedrag();
				rappel -= factuurDetail.getRappel();
				uitstel -= factuurDetail.getUitstelkosten();
				andere -= factuurDetail.getAndereKosten();
			}
		}
		String sBedrag = df.format(bedrag);
		try {
			Number nBedrag = df.parse(sBedrag);
			bedrag = nBedrag.doubleValue();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ids;
	}

	private static FactuurDetail copyFactuurDetail(FactuurDetail factuurDetail) {
		FactuurDetail cFactuurDetail = new FactuurDetail();
		DecimalFormat df = new DecimalFormat("#,##0.00");
		String s = df.format(factuurDetail.getBedrag());
		Number n;
		try {
			n = df.parse(s);
			factuurDetail.setBedrag(n.doubleValue());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cFactuurDetail.setId(factuurDetail.getId());
		cFactuurDetail.setDatum(factuurDetail.getDatum());
		cFactuurDetail.setAardBetaling(factuurDetail.getAardBetaling());
		cFactuurDetail.setBedrag(factuurDetail.getBedrag());
		cFactuurDetail.setRappel(factuurDetail.getRappel());
		cFactuurDetail.setUitstelkosten(factuurDetail.getUitstelkosten());
		cFactuurDetail.setAndereKosten(factuurDetail.getAndereKosten());
		return cFactuurDetail;
	}
}
