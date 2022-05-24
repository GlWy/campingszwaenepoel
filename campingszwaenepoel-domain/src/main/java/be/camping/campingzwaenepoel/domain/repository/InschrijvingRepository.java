package be.camping.campingzwaenepoel.domain.repository;

import be.camping.campingzwaenepoel.common.enums.SoortHuurderEnum;
import be.camping.campingzwaenepoel.domain.model.Inschrijving;

import java.net.UnknownHostException;
import java.util.List;

public interface InschrijvingRepository {

    Inschrijving findById(Integer id);

    void store(Inschrijving inschrijving);

    Inschrijving getLaatsteInschrijving() throws UnknownHostException;

    int findLaatsteFichenummer();

    List<Inschrijving> findInschrijvingenVoorStandplaatsAndSoortHuurder(int standplaatsId, SoortHuurderEnum soortHuurder);

    List<Inschrijving> findInschrijvingenVoorStandplaats(int standplaatsId);

    Inschrijving findInschrijvingenVoorBadge(int badgeId);
}
