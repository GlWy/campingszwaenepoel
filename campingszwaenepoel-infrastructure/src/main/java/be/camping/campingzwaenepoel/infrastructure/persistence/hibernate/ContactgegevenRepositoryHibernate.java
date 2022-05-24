package be.camping.campingzwaenepoel.infrastructure.persistence.hibernate;

import be.camping.campingzwaenepoel.domain.model.Contactgegeven;
import be.camping.campingzwaenepoel.domain.repository.ContactgegevenRepository;
import org.springframework.stereotype.Repository;

@Repository("ContactgegevenRepository")
public class ContactgegevenRepositoryHibernate extends AbstractRepositoryJpaImpl<Contactgegeven, Integer> implements ContactgegevenRepository {

    @Override
    public void removeContactgegeven(Contactgegeven contactgegeven) {
        delete(contactgegeven.getId(), Contactgegeven.class);
    }
}