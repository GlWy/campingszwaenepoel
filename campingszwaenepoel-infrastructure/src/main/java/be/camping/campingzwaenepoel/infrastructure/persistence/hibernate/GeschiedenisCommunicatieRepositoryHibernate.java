package be.camping.campingzwaenepoel.infrastructure.persistence.hibernate;

import be.camping.campingzwaenepoel.domain.model.GeschiedenisCommunicatie;
import be.camping.campingzwaenepoel.domain.repository.GeschiedenisCommunicatieRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository("GeschiedenisCommunicatieRepository")
public class GeschiedenisCommunicatieRepositoryHibernate extends AbstractRepositoryJpaImpl<GeschiedenisCommunicatie, Integer>
        implements GeschiedenisCommunicatieRepository {

    @Override
    public List<GeschiedenisCommunicatie> findAll() {
        TypedQuery<GeschiedenisCommunicatie> query =
                getEntityManager().createNamedQuery(GeschiedenisCommunicatie.Queries.FindAllGeschiedenisCommunicaties.NAME, GeschiedenisCommunicatie.class);
        return query.getResultList();
    }

    @Override
    public GeschiedenisCommunicatie findById(int id) {
            return findById(id, GeschiedenisCommunicatie.class);
    }

    @Override
    public GeschiedenisCommunicatie findByCommunicatie(String communicatie) {
        TypedQuery<GeschiedenisCommunicatie> query =
                getEntityManager().createNamedQuery(GeschiedenisCommunicatie.Queries.FindGeschiedenisCommunicatieByCommunicatie.NAME, GeschiedenisCommunicatie.class);
        query.setParameter(GeschiedenisCommunicatie.Queries.FindGeschiedenisCommunicatieByCommunicatie.PARAMETER_COMMUNICATION, communicatie);
        return query.getSingleResult();
    }

    @Override
    public void remove(GeschiedenisCommunicatie geschiedenisCommunicatie) {
        try {
            delete(geschiedenisCommunicatie.getId(), GeschiedenisCommunicatie.class);
        } catch (RuntimeException re) {
            throw re;
        }
    }

    @Override
    public GeschiedenisCommunicatie store(GeschiedenisCommunicatie geschiedenisCommunicatie) {
        try {
            createOrUpdate(geschiedenisCommunicatie, geschiedenisCommunicatie.getId());
        } catch (RuntimeException re) {
            throw re;
        }
        return geschiedenisCommunicatie;
    }

}
