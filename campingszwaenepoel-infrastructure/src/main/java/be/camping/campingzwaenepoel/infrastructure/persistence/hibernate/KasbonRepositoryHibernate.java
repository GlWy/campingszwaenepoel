package be.camping.campingzwaenepoel.infrastructure.persistence.hibernate;

import be.camping.campingzwaenepoel.domain.model.Kasbon;
import be.camping.campingzwaenepoel.domain.repository.KasbonRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository("KasbonRepository")
public class KasbonRepositoryHibernate extends AbstractRepositoryJpaImpl<Kasbon, Integer> implements KasbonRepository {

    @Override
    public Kasbon findById(int id) {
        try {
            return findById(id, Kasbon.class);
        } catch (RuntimeException re) {
            throw re;
        }
    }

    @Override
    public Kasbon store(Kasbon kasbon) {
        try {
            createOrUpdate(kasbon, kasbon.getId());
        } catch (RuntimeException re) {
            throw re;
        }
        return kasbon;
    }

    @Override
    public void remove(Kasbon kasbon) {
        try {
            delete(kasbon.getId(), Kasbon.class);
        } catch (RuntimeException re) {
            throw re;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void removeAll() {
        getEntityManager().createNamedQuery(Kasbon.Queries.RemoveAllKasbons.NAME).executeUpdate();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Kasbon> getKasbons() {
        return findAll(Kasbon.class);
    }

    @Override
    public Kasbon findByKasbonNummer(int kasbonnummer) {
        TypedQuery<Kasbon> query = getEntityManager().createNamedQuery(Kasbon.Queries.FindByKasbonNummer.NAME, Kasbon.class);
        query.setParameter(Kasbon.Queries.FindByKasbonNummer.PARAMETER_NUMBER, kasbonnummer);
        return query.getSingleResult();
    }

    @Override
    public List<Kasbon> getKasbons(String computername) {
        TypedQuery<Kasbon> query = getEntityManager().createNamedQuery(Kasbon.Queries.FindByComputer.NAME, Kasbon.class);
        query.setParameter(Kasbon.Queries.FindByComputer.PARAMETER_COMPUTER, computername);
        return query.getResultList();
    }
}