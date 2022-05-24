package be.camping.campingzwaenepoel.infrastructure.persistence.hibernate;

import be.camping.campingzwaenepoel.domain.model.FactuurDetail;
import be.camping.campingzwaenepoel.domain.repository.FactuurDetailRepository;
import org.springframework.stereotype.Repository;

@Repository("FactuurDetailRepository")
public class FactuurDetailRepositoryHibernate extends AbstractRepositoryJpaImpl<FactuurDetail, Integer> implements FactuurDetailRepository {

    @Override
    public void remove(FactuurDetail factuurDetail) {
        try {
            delete(factuurDetail.getId(), FactuurDetail.class);
        } catch (RuntimeException re) {
            throw re;
        }
    }

    @Override
    public void removeFactuurDetails(int standplaatsId) {
        String sql = "DELETE FROM factuur_detail WHERE FK_STANDPLAATS_ID = " + standplaatsId;
        getEntityManager().createNativeQuery(sql).executeUpdate();
    }
}