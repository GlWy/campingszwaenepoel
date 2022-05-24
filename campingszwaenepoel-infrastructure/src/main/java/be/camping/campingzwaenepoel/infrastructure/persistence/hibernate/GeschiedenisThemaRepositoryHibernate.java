package be.camping.campingzwaenepoel.infrastructure.persistence.hibernate;

import be.camping.campingzwaenepoel.domain.model.GeschiedenisThema;
import be.camping.campingzwaenepoel.domain.repository.GeschiedenisThemaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository("GeschiedenisThemaRepository")
public class GeschiedenisThemaRepositoryHibernate extends AbstractRepositoryJpaImpl<GeschiedenisThema, Integer> implements GeschiedenisThemaRepository {

    @Override
    public GeschiedenisThema findById(int id) {
        try {
            return findById(id, GeschiedenisThema.class);
        } catch (RuntimeException re) {
            throw re;
        }
    }

    @Override
    public void remove(GeschiedenisThema geschiedenisThema) {
        try {
            delete(geschiedenisThema.getId(), GeschiedenisThema.class);
        } catch (RuntimeException re) {
            throw re;
        }
    }

    @Override
    public GeschiedenisThema store(GeschiedenisThema geschiedenisThema) {
        try {
            createOrUpdate(geschiedenisThema, geschiedenisThema.getId());
        } catch (RuntimeException re) {
            throw re;
        }
        return geschiedenisThema;
    }

    @Override
    public List<GeschiedenisThema> findAll() {
        return findAll(GeschiedenisThema.class);
    }

    @Override
    public GeschiedenisThema findByThema(String thema) {
        TypedQuery<GeschiedenisThema> query = getEntityManager().createNamedQuery(GeschiedenisThema.Queries.FindGeschiedenisThemaByThema.NAME, GeschiedenisThema.class);
        query.setParameter(GeschiedenisThema.Queries.FindGeschiedenisThemaByThema.GESCHIEDENIS_THEMA, thema);
        return query.getSingleResult();
    }
}