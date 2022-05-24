package be.camping.campingzwaenepoel.infrastructure;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import java.util.List;

/**
 * This is an interface to do the basic processes on a table: insert, save, find and delete. The method accepts any
 * class, which should be a domain object representing a table in a database.
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
public interface AbstractRepository<C, T> {

	/**
	 * This generic method will retrieve the data of one record of a table. The returned object should be a domain
	 * object representing the data of one row of the table.
	 * 
	 * @param id
	 *            the id (Long, String)
	 * @param entityClass
	 *            the class of the domain object to be returned
	 * @return a domain object representing the data of one row of a table
	 * @throws NoResultException
	 *             if no entity is found for the given id.
	 * @throws IllegalArgumentException
	 *             if the second argument does not denote an entity type or the first argument is is not a valid type
	 *             for that entity’s primary key or is null
	 */
	C findById(T id, Class<C> entityClass) throws NoResultException, IllegalArgumentException;


	/**
	 * This generic method removes one record of a table. The input parameter object should be a domain object
	 * representing the data of one row of the table.
	 * 
	 * @param t
	 *            the identifier of the object to delete
	 * @param entityClass
	 *            the class of the domain object to delete.
	 * 
	 * @return boolean true/false depending of the remove operation succeeded or not.
	 */
	boolean delete(T t, Class<C> entityClass);


	/**
	 * This generic method creates a criteria query corresponding to a JPQL "select c from C c" or the SLQ
	 * "select * from c"
	 * 
	 * @param entityClass
	 *            the class of the domain object to be returned
	 * @return CriteriaQuery<T>
	 * @throws IllegalStateException
	 *             if called for a Java Persistence query language UPDATE or DELETE statement
	 * @throws PersistenceException
	 *             thrown if
	 *             <ul>
	 *             <li>the query execution exceeds the query timeout value set and the transaction is rolled back</li>
	 *             <li>the query execution exceeds the query timeout value set and only the statement is rolled back</li>
	 *             <li>a lock mode has been set and there is no transaction</li>
	 *             <li>pessimistic locking fails and the transaction is rolled back</li>
	 *             <li>pessimistic locking fails and only the statement is rolled back</li>
	 *             </ul>
	 */
	List<C> findAll(Class<C> entityClass) throws IllegalStateException, PersistenceException;


	/**
	 * This generic method will persist or merge the data of one record from a table. The input parameter object should be a domain
	 * object representing the data of one row of the table. Use this method whenever you need to update an object
	 * existing in the data source and persist the changes again into database.
	 * 
	 * @param c
	 *            a domain object to merge, which represents one record of the table where it will be merged.
	 * @return a new domain object, that has been updated after the merge occurred.
	 */
	C update(C c);

	C createOrUpdate(C c, T t);


	/**
	 * Persists and manages the given entity, updates its fields to reflect that it has been persisted (i.e. record id,
	 * creation date, etc...). Use this method whenever you need to create and save a new persistence object into data
	 * source.
	 * 
	 * @param entity
	 *            the entity to persist.
	 * @throws PersistenceException
	 *             thrown by the {@link EntityManager#persist(Object)} method
	 * @throws IllegalArgumentException
	 *             thrown by the {@link EntityManager#persist(Object)} method
	 */
	void create(C entity) throws PersistenceException, IllegalArgumentException;
}
