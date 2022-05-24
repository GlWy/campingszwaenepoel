package be.camping.campingzwaenepoel.infrastructure.persistence.hibernate;

import be.camping.campingzwaenepoel.domain.model.CampingContact;
import be.camping.campingzwaenepoel.domain.repository.CampingContactRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("CampingContactRepository")
public class CampingContactRepositoryHibernate extends AbstractRepositoryJpaImpl<CampingContact, Integer> implements CampingContactRepository {

    @Override
    public CampingContact findById(int id) {
        try {
            return findById(id, CampingContact.class);
        } catch (RuntimeException re) {
            throw re;
        }
    }

    @Override
    public void remove(CampingContact campingContact) {
        try {
            delete(campingContact.getId(), CampingContact.class);
        } catch (RuntimeException re) {
            throw re;
        }
    }

    @Override
    public void store(CampingContact campingContact) {
        try {
            createOrUpdate(campingContact, campingContact.getId());
        } catch (RuntimeException re) {
            throw re;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<CampingContact> getContacten() {
        return findAll(CampingContact.class);
    }
}
