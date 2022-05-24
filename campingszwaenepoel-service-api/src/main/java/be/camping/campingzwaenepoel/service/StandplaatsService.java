package be.camping.campingzwaenepoel.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import be.camping.campingzwaenepoel.common.enums.BetalingRapportEnum;
import be.camping.campingzwaenepoel.common.enums.SoortHuurderEnum;
import be.camping.campingzwaenepoel.service.transfer.BadgeDTO;
import be.camping.campingzwaenepoel.service.transfer.GeschiedenisDTO;
import be.camping.campingzwaenepoel.service.transfer.InschrijvingDTO;
import be.camping.campingzwaenepoel.service.transfer.StandplaatsDTO;

public interface StandplaatsService {

	public StandplaatsDTO getStandplaats(int id);

	public StandplaatsDTO getStandplaatsGrond();

	public StandplaatsDTO getVolgendeStandplaats(int huidigeId, boolean offset);

	public StandplaatsDTO getVorigeStandplaats(int huidigeId, boolean offset);

	public StandplaatsDTO zoekStandplaats(String plaatsgroep, int plaatsnummer);

	public void storeStandplaatsen();

	public StandplaatsDTO storeStandplaats(StandplaatsDTO standplaatsDTO);

	public List<String> getStandplaatsNamen();

	public StandplaatsDTO getStandplaatsWithFacturenAndFactuurDetails(int standplaatsId);

	public StandplaatsDTO getStandplaatsMetInschrijvingenEnPersonen(int standplaatsId);

	public List<InschrijvingDTO> getInschrijvingen(int standplaatsId, SoortHuurderEnum soortHuurder);

	public InschrijvingDTO getInschrijvingVoorBadge(BadgeDTO badge);

	public StandplaatsDTO removeBadgeVanInschrijving(InschrijvingDTO inschrijving);

	public InschrijvingDTO getInschrijving(int inschrijvingId);

	public List<Integer> getStandplaatsIds();

	public int findLaatseFichenummer();

	public List<Object[]> zoekStandplaatsEnFicheGegevens(Map<String, Object> zoekCriteria,
			Map<String, Object> projectionList);

	public InschrijvingDTO getLaatsteInschrijving();

	public Map<String, String> getBadgeVoorStandplaats(int id);

	public List<Object[]> zoekNummerplaten(int jaar);

	public List<Object[]> zoekOpmerkingenGrond(int nummer, boolean printAlles, boolean namenTonen);

	public void createBetalingNieuwJaar();

	public void convertInschrijving();

	public void convertFacturatie();

	public void convertBetalers();

	public void convertBetalingen();

	public List<Date> getInschrijvingenMetBadge(int standplaatsId);

	public StandplaatsDTO getsStandplaatsMetGeschiedenis(int standplaatsId);

	public void removeGeschiedenis(GeschiedenisDTO geschiedenisDTO);

	public void removeGrondInformatie(int nummer);

	public void berekenFacturatie();

	public List<StandplaatsDTO> haalNietBetaaldeStandplaatsenOp(boolean uitstel);

	public List<Map<String, Object>> zoekGrondprijzen();

	List<Map<String, Object>> zoekBasisprijzen();

	void adjustBasePrices();

	public void storeNieuweGrondprijzen(List<Map<String, Double>> prijzen);

	public void removeInschrijving(int inschrijvingId);

	public List<StandplaatsDTO> getStandplaatsenBetalingNietOk(BetalingRapportEnum betalingRapportType);

	public List<Object[]> findNummerplaten(int year);

	public List<Object[]> getStandplaatsEnKostprijs();

	public boolean resetBetalingenNieuweGrondprijzen(FactuurDetailService factuurDetailService,
			final ConfiguratieService configuratieService, final int year);

	public boolean isResetBetalingBusy();

}
