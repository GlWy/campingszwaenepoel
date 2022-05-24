package be.camping.campingzwaenepoel.service.threads.betaling;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import be.camping.campingzwaenepoel.common.constants.FotoConstant;
import be.camping.campingzwaenepoel.common.enums.BetalingEnum;
import be.camping.campingzwaenepoel.service.ConfiguratieService;
import be.camping.campingzwaenepoel.service.DTOFactory;
import be.camping.campingzwaenepoel.service.FactuurDetailService;
import be.camping.campingzwaenepoel.service.StandplaatsService;
import be.camping.campingzwaenepoel.service.transfer.ConfiguratieDTO;
import be.camping.campingzwaenepoel.service.transfer.FactuurDetailDTO;
import be.camping.campingzwaenepoel.service.transfer.StandplaatsDTO;

public class BetalingNieuwJaarThread extends Thread {
	/**
	 * 1. haal standplaatsId en grondprijs op 2. loop per standplaats is
	 * betaalde som < 0.1 ja --> wis alle huidige facturatie gegevens insert
	 * nieuw factuur detail van nieuw jaar met te betalen grondprijs op dag van
	 * vandaag
	 */

	private StandplaatsService standplaatsService;

	public StandplaatsService getStandplaatsService() {
		return standplaatsService;
	}

	public void setStandplaatsService(StandplaatsService standplaatsService) {
		this.standplaatsService = standplaatsService;
	}

	private FactuurDetailService factuurDetailService;

	public FactuurDetailService getFactuurDetailService() {
		return factuurDetailService;
	}

	public void setFactuurDetailService(
			FactuurDetailService factuurDetailService) {
		this.factuurDetailService = factuurDetailService;
	}
	
	private ConfiguratieService configuratieService;

	public ConfiguratieService getConfiguratieService() {
		return configuratieService;
	}

	public void setConfiguratieService(ConfiguratieService configuratieService) {
		this.configuratieService = configuratieService;
	}

	private int year;

	public BetalingNieuwJaarThread(StandplaatsService standplaatsService, 
			FactuurDetailService factuurDetailService, final ConfiguratieService configuratieService, final int year) {
		this.standplaatsService = standplaatsService;
		this.factuurDetailService = factuurDetailService;
		this.configuratieService = configuratieService;
		this.year = year;
		this.start();
	}

	public void run() {
		List<Object[]> standplaatsen = getStandplaatsService().getStandplaatsEnKostprijs();
		System.err.println(standplaatsen.size());
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DATE, 1);
		cal.set(Calendar.MONTH, 0);
		cal.set(Calendar.YEAR, year);
		try {
			for (Object[] standplaats : standplaatsen) {
				try {
					StandplaatsDTO standplaatsDTO = getStandplaatsService().getStandplaats(((Integer) standplaats[0]));
					if (!checkDataUpdated(standplaatsDTO)) {
						if (checkData(standplaatsDTO)) {
							getFactuurDetailService().removeFactuurDetails((Integer) standplaats[0]);
							standplaatsDTO.setFactuurDetails(new HashSet<FactuurDetailDTO>());
						}
						if (standplaatsDTO.getKostprijs() > 0) {
							FactuurDetailDTO factuurDetailDTO = DTOFactory.getFactuurDetail();
							factuurDetailDTO.setAardBetaling(BetalingEnum.NOGTEBETALEN);
							factuurDetailDTO.setBedrag((Double) standplaats[1]);
							cal.set(Calendar.YEAR, year);
							cal.set(Calendar.MONTH, 0);
							cal.set(Calendar.DATE, 1);
							factuurDetailDTO.setDatum(cal.getTime());
							standplaatsDTO.getFactuurDetails().add(factuurDetailDTO);
						}
						standplaatsDTO.setTotaal(getTotaal(standplaatsDTO.getFactuurDetails()));
						getStandplaatsService().storeStandplaats(standplaatsDTO);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			ConfiguratieDTO prijsJaarConfiguratieDTO = configuratieService.getConfiguratie("PRIJS_JAAR");
			prijsJaarConfiguratieDTO.setWaarde(Integer.toString(year));
			configuratieService.storeConfiguratie(prijsJaarConfiguratieDTO);
		} finally {
			FotoConstant.setPrijsAanpassingBusy(false);
		}
	}
	
	public boolean checkData(StandplaatsDTO standplaatsDTO) {
		boolean ret = false;
		List<FactuurDetailDTO> factuurDetails = sortFactuurdetails(standplaatsDTO.getFactuurDetails());
		List<FactuurDetailDTO> betalingen = new ArrayList<FactuurDetailDTO>();
		double totaal = 0;
		for (FactuurDetailDTO factuurDetail : factuurDetails) {
			Calendar now = Calendar.getInstance();
			if (factuurDetail.getAardBetaling().equals(BetalingEnum.UITSTEL)) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(factuurDetail.getDatum());
				if (cal.after(now)){
					double tmpTotaal = factuurDetail.getBedrag() + factuurDetail.getAndereKosten() + factuurDetail.getRappel() + factuurDetail.getUitstelkosten();
					totaal -= tmpTotaal;
				}
			} else if (factuurDetail.getAardBetaling().equals(BetalingEnum.BETAALD)) {
				double tmpTotaal = factuurDetail.getBedrag() + factuurDetail.getAndereKosten() + factuurDetail.getRappel() + factuurDetail.getUitstelkosten();
				totaal -= tmpTotaal;
				betalingen.add(factuurDetail);
			} else if (factuurDetail.getAardBetaling().equals(BetalingEnum.NOGTEBETALEN)) {
				double tmpTotaal = factuurDetail.getBedrag() + factuurDetail.getAndereKosten() + factuurDetail.getRappel() + factuurDetail.getUitstelkosten();
				totaal += tmpTotaal;
			}
		}
		if (totaal < 0.1) ret = true;
		return ret;
	}

	public boolean checkDataUpdated(StandplaatsDTO standplaatsDTO) {
		boolean ret = false;
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR) + 1;

		for (FactuurDetailDTO factuurDetail : standplaatsDTO.getFactuurDetails()) {
			Calendar calTmp = Calendar.getInstance();
			calTmp.setTime(factuurDetail.getDatum());
			if (calTmp.get(Calendar.DATE) == 1 && calTmp.get(Calendar.MONTH) == 0 && calTmp.get(Calendar.YEAR) == year) {
				ret = true;
				break;
			}
		}
		return ret;
	}

	private List<FactuurDetailDTO> sortFactuurdetails(Set<FactuurDetailDTO> factuurDetails) {
    	List<FactuurDetailDTO> sortedFactuurDetails = new ArrayList<FactuurDetailDTO>();
    	for (FactuurDetailDTO tmpFactuurDetail : factuurDetails) {
    		if (tmpFactuurDetail.getDatum() == null || tmpFactuurDetail.getDatum().toString() == "" 
    												|| (tmpFactuurDetail.getAardBetaling() == null)) {
    			continue;
    		}
    		boolean added = false;
    		boolean toBeAdded = false;
    		for (FactuurDetailDTO sortedFactuurDetail : sortedFactuurDetails) {
    			if (tmpFactuurDetail.getDatum().before(sortedFactuurDetail.getDatum())) {
    				toBeAdded = true;
    			} else if (tmpFactuurDetail.getDatum().equals(sortedFactuurDetail.getDatum())) {
    				if (tmpFactuurDetail.getDatumAanmaak() < sortedFactuurDetail.getDatumAanmaak()) {
    					toBeAdded = true;
    				}
    			}
    			if (toBeAdded) {
    				int index = sortedFactuurDetails.indexOf(sortedFactuurDetail);
    				sortedFactuurDetails.add(index, tmpFactuurDetail);
    				added = true;
    				break;
    			}
    		}
    		if (!added) {
    			sortedFactuurDetails.add(tmpFactuurDetail);
    		}
    	}
    	return sortedFactuurDetails;
    }

	private double getTotaal(Set<FactuurDetailDTO> factuurDetailsSet) {
		List<FactuurDetailDTO> factuurDetails = sortFactuurdetails(factuurDetailsSet);
		double totaal = 0;
		int row = 0;
		for (FactuurDetailDTO factuurDetail : factuurDetails) {
			totaal = berekenTotaal(factuurDetail.getAardBetaling().getTranslationKey(), totaal, factuurDetail.getBedrag(),
					factuurDetail.getUitstelkosten(),
					factuurDetail.getRappel(), factuurDetail.getAndereKosten(),
					row);
			row++;
		}
		if (totaal < 0 && totaal > -1)
			totaal = 0;
		return totaal;
	}

	private double berekenTotaal(String s, double vorigTotaal, double factuur,
			double uitstelkosten, double rappel, double andere, int row) {
		double totaal = 0;
		if (!s.equals("UIT")) {
			double tmpTotaal = factuur + uitstelkosten + rappel + andere;
			if (s.equals("BT")) {
				tmpTotaal *= -1;
			}
			totaal = vorigTotaal + tmpTotaal;
			if (totaal < 0.1 && totaal > -1)
				totaal = 0;
		} else {
			totaal = vorigTotaal;
		}
		return totaal;
	}

}
