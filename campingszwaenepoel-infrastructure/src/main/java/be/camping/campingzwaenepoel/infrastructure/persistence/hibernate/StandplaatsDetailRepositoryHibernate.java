package be.camping.campingzwaenepoel.infrastructure.persistence.hibernate;

import be.camping.campingzwaenepoel.domain.model.StandplaatsDetail;
import be.camping.campingzwaenepoel.domain.repository.StandplaatsDetailRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("StandplaatsDetailRepository")
public class StandplaatsDetailRepositoryHibernate extends AbstractRepositoryJpaImpl<StandplaatsDetail, Integer> implements StandplaatsDetailRepository {

    @Override
    public StandplaatsDetail findById(int id) {
        try {
            return findById(id, StandplaatsDetail.class);
        } catch (RuntimeException re) {
            throw re;
        }
    }

    @Override
    public void store(StandplaatsDetail standplaatsDetail) {
        try {
            createOrUpdate(standplaatsDetail, standplaatsDetail.getId());
        } catch (RuntimeException re) {
            throw re;
        }
    }

    @Override
    public void remove(StandplaatsDetail standplaatsDetail) {
        try {
            delete(standplaatsDetail.getId(), StandplaatsDetail.class);
        } catch (RuntimeException re) {
            throw re;
        }
    }

    @Override
    public List<StandplaatsDetail> getStandplaatsDetails() {
        return findAll(StandplaatsDetail.class);
    }
}