package ch.fhnw.edu.rental.persistence;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface Repository<T, ID extends Serializable> {
	/**
	 * Retrieves an entity by its id.
	 * 
	 * @param id must not be null.
	 * @return the entity with the given id or null of none was found.
	 * @throws IllegalArgumentException if id is null.
	 */
	Optional<T> findById(ID id);

	/**
	 * Returns all instances of the type.
	 * 
	 * @return all entities
	 */
	List<T> findAll();

	/**
	 * Saves a given entity. Use the returned instance for further operations as the
	 * save operation might have changed the entity instance completely.
	 * 
	 * @param entity
	 * @return the saved entity
	 */
	T save(T t);

	/**
	 * Deletes the entity with the given id.
	 * 
	 * @param id must not be {@literal null}.
	 * @throws IllegalArgumentException in case the given id is null
	 */
	void deleteById(ID id);

	/**
	 * Deletes a given entity.
	 * 
	 * @param entity
	 * @throws IllegalArgumentException in case the given entity is null.
	 */
	void delete(T entity);

	/**
	 * Returns whether an entity with the given id exists.
	 * 
	 * @param id must not be null.
	 * @return true if an entity with the given id exists, false otherwise
	 * @throws IllegalArgumentException if id is null
	 */
	boolean existsById(ID id);

	/**
	 * Returns the number of entities available.
	 * 
	 * @return the number of entities
	 */
	long count();
}
