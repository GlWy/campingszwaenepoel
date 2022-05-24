package be.camping.campingzwaenepoel.infrastructure.persistence.hibernate;

import be.camping.campingzwaenepoel.common.enums.SoortHuurderEnum;
import be.camping.campingzwaenepoel.domain.model.Inschrijving;
import be.camping.campingzwaenepoel.domain.repository.InschrijvingRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

@Repository("InschrijvingRepository")
public class InschrijvingRepositoryHibernate extends AbstractRepositoryJpaImpl<Inschrijving, Integer> implements InschrijvingRepository {


    @Override
    public Inschrijving findById(Integer id) {
        return findById(id, Inschrijving.class);
    }

    @Override
    public void store(Inschrijving inschrijving) {
        createOrUpdate(inschrijving, inschrijving.getId());
    }

    @Override
    public Inschrijving getLaatsteInschrijving() {
        try {
            String computername = InetAddress.getLocalHost().getHostName();
            return (Inschrijving) getEntityManager()
                    .createNamedQuery(Inschrijving.Queries.GetLaatsteInschrijvingByComputerName.NAME)
                    .setParameter(Inschrijving.Queries.GetLaatsteInschrijvingByComputerName.PARAMETER_COMPUTER_NAME, computername)
                    .getSingleResult();
        } catch (UnknownHostException | NoResultException e) {
            e.printStackTrace();
        }
        return (Inschrijving) getEntityManager().createNamedQuery(Inschrijving.Queries.GetLaatsteInschrijving.NAME).getSingleResult();
    }

    @Override
    public int findLaatsteFichenummer() {
        int max = 0;
        Query query = getEntityManager().createNamedQuery(Inschrijving.Queries.FindLaatsteFichenummer.NAME, Integer.class);
        Object o = query.getSingleResult();
        if (o != null) {
            max = (Integer) o;
        }
        return max;
    }

    @Override
    public List<Inschrijving> findInschrijvingenVoorStandplaatsAndSoortHuurder(int standplaatsId, SoortHuurderEnum soortHuurder) {
        return getEntityManager().createNamedQuery(Inschrijving.Queries.FindInschrijvingenVoorStandplaatsAndSoortHuurder.NAME)
                .setParameter(Inschrijving.Queries.FindInschrijvingenVoorStandplaatsAndSoortHuurder.PARAMETER_STANDPLAATS_ID, standplaatsId)
                .setParameter(Inschrijving.Queries.FindInschrijvingenVoorStandplaatsAndSoortHuurder.PARAMETER_SOORT_HUURDER, soortHuurder)
                .getResultList();
    }

    @Override
    public List<Inschrijving> findInschrijvingenVoorStandplaats(int standplaatsId) {
        return getEntityManager().createNamedQuery(Inschrijving.Queries.FindInschrijvingenVoorStandplaats.NAME)
                .setParameter(Inschrijving.Queries.FindInschrijvingenVoorStandplaats.PARAMETER_STANDPLAATS_ID, standplaatsId)
                .getResultList();
    }

    @Override
    public Inschrijving findInschrijvingenVoorBadge(int badgeId) {
        return (Inschrijving) getEntityManager().createNamedQuery(Inschrijving.Queries.FindInschrijvingenVoorBadge.NAME)
                .setParameter(Inschrijving.Queries.FindInschrijvingenVoorBadge.PARAMETER_BADGE_ID, badgeId)
                .setMaxResults(1)
                .getSingleResult();
    }
}
