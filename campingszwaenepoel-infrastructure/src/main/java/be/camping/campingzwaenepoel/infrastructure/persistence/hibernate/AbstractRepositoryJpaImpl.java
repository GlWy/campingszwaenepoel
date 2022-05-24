package be.camping.campingzwaenepoel.infrastructure.persistence.hibernate;

import be.camping.campingzwaenepoel.common.criteria.Pager;
import be.camping.campingzwaenepoel.common.exception.ExceptionTranslator;
import be.camping.campingzwaenepoel.infrastructure.AbstractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Abstract Class for the JPA implementation of the Repositories.
 * 
 * @author Glenn Wybo
 * @author craig.moore@cropdesign.com
 * @param <C>
 *            the class of the domain object.
 * @param <T>
 *            the class of the Id field of the domain object.
 * @version 1.0 Initial version
 * @version 2.0 Updated by Craig Moore while working on DRA-803
 *          <ul>
 *          <li>Added missing {@link IllegalArgumentException} to {@link #findById(Object, Class)} method signature</li>
 *          <li>Updated javadocs.</li>
 *          </ul>
 */
public abstract class AbstractRepositoryJpaImpl<C, T> implements AbstractRepository<C, T> {

	@PersistenceContext
//	@Qualifier("campingsZwaenepoelEntityManager")
	private EntityManager entityManager;

	/**
	 * The translator.
	 */
//	@Autowired
//	protected ExceptionTranslator exceptionTranslator;


	/**
	 * @return the entity manager for this database
	 */
	public EntityManager getEntityManager() {
		return this.entityManager;
	}


	/**
	 * Same as calling {@link EntityManager#getCriteriaBuilder() getEntityManager().getCriteriaBuilder()}
	 * 
	 * @return the criteria builder for creating JPA queries.
	 */
	public CriteriaBuilder getCriteriaBuilder() {
		return getEntityManager().getCriteriaBuilder();
	}


	@Override
	public C findById(final T id, final Class<C> entityClass) throws NoResultException, IllegalArgumentException {
		C entity = getEntityManager().find(entityClass, id);
		if (entity != null)
			return entity;
		else
			throw new NoResultException("Could not find " + entityClass.getName() + " with id '" + id + "'");
	}


	/**
	 * This generic method will merge the entity to the persistence context and update the database upon transaction
	 * commit. The entity should be an object representing the data of one row of the table.
	 * 
	 * @param entity
	 *            the entity to merge with the existing entity in the database.
	 * @return a new entity that is the result of merging the given entity with the existing entity in the database.
	 */
	@Override
	public C update(C entity) {
		// The merge operation does not operate "in place", it will produce a new object
		// as a consequence after the call c.id == null but c1.id == (db generated id)
		return getEntityManager().merge(entity);
	}

	@Override
	public void create(C entity) throws PersistenceException, IllegalArgumentException {
		getEntityManager().persist(entity);
	}

	@Override
	public C createOrUpdate(C c, T t) {
		if (t != null) {
			return update(c);
		} else {
			create(c);
			return c;
		}
	}


	/**
	 * This generic method removes one record of a table. The input parameter object should be a domain object
	 * representing the data of one row of the table.
	 * 
	 * @param entityClass
	 *            the class of the entity object to delete.
	 * @return boolean true/false depending of the remove operation succeeded or not.
	 */
	@Override
	public boolean delete(final T entityId, final Class<C> entityClass) {
		try {
			C c = findById(entityId, entityClass);
			getEntityManager().remove(c);
			return true;
		} catch (RuntimeException e) {
			throw e;
		}
	}


	/**
	 * This generic method creates a criteria query corresponding to a JPQL "select c from C c" or the SQL
	 * "select * from c"
	 * 
	 * @param entityClass
	 *            the class of the domain object to be returned
	 * @return List of Domain Objects C
	 */
	@Override
	public List<C> findAll(final Class<C> entityClass) throws IllegalStateException, PersistenceException {
		CriteriaQuery<C> cq = getCriteriaBuilder().createQuery(entityClass);
		Root<C> r = cq.from(entityClass);
		cq.select(r);
		TypedQuery<C> q = getEntityManager().createQuery(cq);
		return q.getResultList();
	}


	/**
	 * @param query
	 *            the query that the {@link TypedQuery} will created from.
	 * @param pager
	 *            the {@link Pager} used to create the query, observing the pageNumber and pageSize it defines.
	 * @return a typedQuery based on the given parameters.
	 * @throws IllegalArgumentException
	 *             thrown if CriteriaQuery parameter is null.
	 */
	protected TypedQuery<C> createTypedQuery(CriteriaQuery<C> query, Pager pager) throws IllegalArgumentException {
		if (query == null) {
			throw new IllegalArgumentException("Cannot create typed query, given criteria query argument is null");
		}
		TypedQuery<C> typedQuery = getEntityManager().createQuery(query);
		if (pager != null) {
			if (pager.getPageNumber() >= 0) {
				typedQuery.setFirstResult(pager.getPageNumber());
			}
			if (pager.getPageSize() >= 0) {
				typedQuery.setMaxResults(pager.getPageSize());
			}
		}
		return typedQuery;
	}
}
