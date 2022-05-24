package be.camping.campingzwaenepoel.infrastructure.persistence.hibernate;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import be.camping.campingzwaenepoel.domain.model.Stamboom;
import be.camping.campingzwaenepoel.domain.repository.StamboomRepository;

@Repository("StamboomRepository")
public class StamboomRepositoryHibernate extends AbstractRepositoryJpaImpl<Stamboom, Integer> implements StamboomRepository {

    @Override
    public Stamboom findById(int id) {
        try {
            return findById(id, Stamboom.class);
        } catch (RuntimeException re) {
            throw re;
        }
    }

    @Override
    public List<Stamboom> getStamboom(int standplaatsId) {
        TypedQuery<Stamboom> query = getEntityManager().createNamedQuery(Stamboom.Queries.FindByStandplaats.NAME, Stamboom.class);
        query.setParameter(Stamboom.Queries.FindByStandplaats.PARAMETER_STANDPLAATS, standplaatsId);
        return query.getResultList();
    }

    @Override
    public void remove(Stamboom stamboom) {
        delete(stamboom.getId(), Stamboom.class);
    }

    @Override
    public Stamboom store(Stamboom stamboom) {
        return createOrUpdate(stamboom, stamboom.getId());
    }

    @Override
    public List<Stamboom> zoekPersonen(String naam) {
        TypedQuery<Stamboom> query = getEntityManager().createNamedQuery(Stamboom.Queries.FindByPersoon.NAME, Stamboom.class);
        query.setParameter(Stamboom.Queries.FindByPersoon.PARAMETER_PERSOON, naam);
        return query.getResultList();
    }

    @Override
    public void removeStamboomVanStandplaats(int standplaatsId) {
        String sql = "delete from stamboom where fk_standplaats_id = " + standplaatsId;
        getEntityManager().createNativeQuery(sql).executeUpdate();
    }
}