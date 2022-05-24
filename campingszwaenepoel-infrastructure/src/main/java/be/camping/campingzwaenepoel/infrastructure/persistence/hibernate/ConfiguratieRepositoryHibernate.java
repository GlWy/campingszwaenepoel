package be.camping.campingzwaenepoel.infrastructure.persistence.hibernate;

import be.camping.campingzwaenepoel.domain.model.Configuratie;
import be.camping.campingzwaenepoel.domain.repository.ConfiguratieRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;

@Repository("ConfiguratieRepository")
public class ConfiguratieRepositoryHibernate extends AbstractRepositoryJpaImpl<Configuratie, Integer> implements ConfiguratieRepository {

    @Override
    public Configuratie findById(int id) {
        try {
            return findById(id, Configuratie.class);
        } catch (RuntimeException re) {
            throw re;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Configuratie findByNaam(String naam) {
        TypedQuery<Configuratie> query =
                getEntityManager().createNamedQuery(Configuratie.Queries.FindConfigurationByName.NAME, Configuratie.class);
        query.setParameter(Configuratie.Queries.FindConfigurationByName.CONFIGURATIE_NAAM, naam);
        return query.getSingleResult();
    }

    @Override
    public Configuratie store(Configuratie configuratie) {
        try {
            createOrUpdate(configuratie, configuratie.getId());
        } catch (RuntimeException re) {
            throw re;
        }
        return configuratie;
    }
}