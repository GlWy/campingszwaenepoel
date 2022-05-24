package be.camping.campingzwaenepoel.infrastructure.persistence.hibernate;

import be.camping.campingzwaenepoel.domain.model.Wagen;
import be.camping.campingzwaenepoel.domain.repository.WagenRepository;
import org.springframework.stereotype.Repository;

@Repository("WagenRepository")
public class WagenRepositoryHibernate extends AbstractRepositoryJpaImpl<Wagen, Integer> implements WagenRepository {

    @Override
    public void removeWagen(Wagen wagen) {
        delete(wagen.getId(), Wagen.class);
    }
}