package be.camping.campingzwaenepoel.infrastructure.persistence.hibernate;

import be.camping.campingzwaenepoel.domain.model.KassaKostprijs;
import be.camping.campingzwaenepoel.domain.repository.KassaKostprijsRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("KassaKostprijsRepository")
public class KassaKostprijsRepositoryHibernate extends AbstractRepositoryJpaImpl<KassaKostprijs, Integer> implements KassaKostprijsRepository {

    @Override
    public KassaKostprijs findById(int id) {
        try {
            return findById(id, KassaKostprijs.class);
        } catch (RuntimeException re) {
            throw re;
        }
    }

    @Override
    public KassaKostprijs store(KassaKostprijs kassaKostprijs) {
        try {
            createOrUpdate(kassaKostprijs, kassaKostprijs.getId());
        } catch (RuntimeException re) {
            throw re;
        }
        return kassaKostprijs;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<KassaKostprijs> kostprijzen() {
        return findAll(KassaKostprijs.class);
    }
}