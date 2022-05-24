package be.camping.campingzwaenepoel.domain.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import be.camping.campingzwaenepoel.domain.model.Geschiedenis;
import be.camping.campingzwaenepoel.domain.model.Inschrijving;
import be.camping.campingzwaenepoel.domain.model.Standplaats;

public interface StandplaatsRepository {

	Standplaats store(Standplaats standplaats);

	Standplaats findById(Integer id);

	Standplaats findStandplaats(int start, int max);

	Standplaats zoekStandplaats(String standplaatsNaam, int standplaatsNummer);

	List<String> getStandplaatsen();

	Standplaats getStandplaatsWithFactuurDetails(int standplaatsId);

	Standplaats getStandplaatsWithInschrijvingenEnPersonen(int standplaatsId);

	List<Integer> getStandplaatsIds();

	List<Date> getInschrijvingenMetBadge(int standplaatsId);

	List<Object[]> zoekStandplaatsEnFicheGegevens(Map<String, Object> zoekCriteria,
			Map<String, Object> projectionList);

	Map<String, String> getBadgeVoorStandplaats(int id);

	List<Object[]> zoekNummerplaten(int jaar);

	List<Object[]> zoekOpmerkingenGrond(int nummer, boolean printAlles, boolean namenTonen);

	List<Standplaats> zoekStandplaatsenNietBetaald();

	List<Standplaats> zoekStandplaatsenAndereNietBetaald();

	List<Object[]> haalBadgetoewijzigenOpVoorStandplaats(int id);

	List<Integer> findRegistratiesVoorBadgetoewijzing(int id);

	Object[] findKoppelVoorRegistratie(int id);

	List<Object[]> findKinderenVanKoppel(int id);

	List<Integer> findStandplaatsen();

	List<Object[]> getOudeFacturatieVoorStandplaats(int id);

	List<Integer> getBetalersAccessVoorStandplaats(int id);

	Object[] getBetalingenAccessVoorStandplaats(int id);

	List<Object[]> getVoorschottenAccessVoorBetaling(int id);

	void removeGrondInformatie(int nummer);

	List<Map<String, Object>> zoekGrondprijzen();

	List<Map<String, Object>> zoekBasisprijzen();

	void adjustBasePrices();

	void storeNieuweGrondprijzen(List<Map<String, Double>> prijzen);

	void removeInschrijving(int inschrijvingId);

	void removeGeschiedenis(Geschiedenis geschiedenis);

	List<Object[]> findOpmerkingenVoorFacturatie();

	List<Object[]> findNummerplaten(int year);

	List<Object[]> getStandplaatsEnKostprijs();
}