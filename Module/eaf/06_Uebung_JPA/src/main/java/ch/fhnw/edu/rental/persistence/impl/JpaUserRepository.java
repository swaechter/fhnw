package ch.fhnw.edu.rental.persistence.impl;

import ch.fhnw.edu.rental.model.User;
import ch.fhnw.edu.rental.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class JpaUserRepository extends JpaBaseRepository<User> implements UserRepository {

    @Autowired
    public JpaUserRepository(EntityManager entityManager) {
        super(entityManager, User.class);
    }

    @Override
    public List<User> findByLastName(String lastName) {
        CriteriaBuilder builder = getEm().getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.select(root).where(builder.equal(root.get("lastName"), lastName));
        return getEm().createQuery(query).getResultList();
    }

    @Override
    public List<User> findByFirstName(String firstName) {
        CriteriaBuilder builder = getEm().getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.select(root).where(builder.equal(root.get("firstName"), firstName));
        return getEm().createQuery(query).getResultList();
    }

    @Override
    public List<User> findByEmail(String email) {
        CriteriaBuilder builder = getEm().getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.select(root).where(builder.equal(root.get("email"), email));
        return getEm().createQuery(query).getResultList();
    }
}
