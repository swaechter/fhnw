package ch.fhnw.edu.rental.persistence.impl;

import ch.fhnw.edu.rental.persistence.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Optional;

public abstract class JpaBaseRepository<T> implements Repository<T, Long> {

    private final EntityManager entityManager;

    private final Class<T> entityClass;

    public JpaBaseRepository(EntityManager entityManager, Class<T> entityClass) {
        this.entityManager = entityManager;
        this.entityClass = entityClass;
    }

    protected final EntityManager getEm() {
        return entityManager;
    }

    @Override
    public Optional<T> findById(Long id) {
        return Optional.ofNullable(getEm().find(entityClass, id));
    }

    @Override
    public List<T> findAll() {
        TypedQuery<T> query = getEm().createQuery("SELECT item FROM " + entityClass.getSimpleName() + " item", entityClass);
        return query.getResultList();
    }

    @Override
    public T save(T t) {
        return getEm().merge(t);
    }

    @Override
    public void deleteById(Long aLong) {
        getEm().remove(getEm().getReference(entityClass, aLong));
    }

    @Override
    public void delete(T entity) {
        getEm().remove(getEm().merge(entity));
    }

    @Override
    public boolean existsById(Long aLong) {
        return findById(aLong).isPresent();
    }

    @Override
    public long count() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        query.select(builder.count(query.from(entityClass)));
        return entityManager.createQuery(query).getSingleResult();
    }
}
