package be.camping.campingzwaenepoel.domain.repository;

import be.camping.campingzwaenepoel.domain.model.Persoon;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

public interface PersoonRepository {

    Persoon store(Persoon persoon);

    Persoon findById(int id);

    List<Object> getPersonen();

    void remove(Persoon persoon);

    List<Object[]> zoekPersonen(Map<String, Object> zoekCriteria);

    List<Persoon> zoekPersonen(Calendar geboortedatum, String naam, String voornaam);

    void changePersoon(int newId, int oldId);
}
