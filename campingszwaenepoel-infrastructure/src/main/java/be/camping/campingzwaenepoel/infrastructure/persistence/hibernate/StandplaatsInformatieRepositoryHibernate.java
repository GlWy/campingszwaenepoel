package be.camping.campingzwaenepoel.infrastructure.persistence.hibernate;

import be.camping.campingzwaenepoel.domain.model.GrondInformatie;
import be.camping.campingzwaenepoel.domain.repository.StandplaatsInformatieRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;

@Repository("StandplaatsInformatieRepository")
public class StandplaatsInformatieRepositoryHibernate extends AbstractRepositoryJpaImpl<GrondInformatie, Integer> implements StandplaatsInformatieRepository {

    @Override
    public void removeByNumber(int number) {
        String hql = "delete from grond_informatie where nummer = " + number;
        Query query = getEntityManager().createNativeQuery(hql);
        query.executeUpdate();
    }
}