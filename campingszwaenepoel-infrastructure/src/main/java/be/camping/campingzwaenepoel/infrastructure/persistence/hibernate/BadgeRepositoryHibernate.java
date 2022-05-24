package be.camping.campingzwaenepoel.infrastructure.persistence.hibernate;

import be.camping.campingzwaenepoel.common.enums.SoortHuurderEnum;
import be.camping.campingzwaenepoel.domain.model.Badge;
import be.camping.campingzwaenepoel.domain.repository.BadgeRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository("BadgeRepository")
public class BadgeRepositoryHibernate extends AbstractRepositoryJpaImpl<Badge, Integer> implements BadgeRepository {

    @Override
    public Badge findById(int id) {
        return findById(id, Badge.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<String> getBadgeNummers() {
        String sql = "select badgenummer from badge where badgetype = '" +  SoortHuurderEnum.LOS + "' order by badgenummer";
        Query query = getEntityManager().createNativeQuery(sql);
        return (List<String>) query.getResultList();
    }

    @Override
    public Optional<Badge> findByNummer(String nummer) {
        TypedQuery<Badge> query =
                getEntityManager().createNamedQuery(Badge.Queries.FindBadgeByNummer.NAME, Badge.class);
        query.setParameter(Badge.Queries.FindBadgeByNummer.BADGE_NUMMER, nummer);
        try {
        	Badge badge =  query.getSingleResult();
        	return Optional.of(badge);
        } catch (NoResultException ex) {
        	return Optional.empty();
        }
    }
    
    @Transactional
    @Override
    public void store(Badge badge) {
        try {
            createOrUpdate(badge, badge.getId());
        } catch (RuntimeException re) {
            throw re;
        }
    }
}